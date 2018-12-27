package com.victor.mqtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.victor.baselib.utils.XLog;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by hanbin.zhang on 2018\8\18 0018
 */
public class MqttManager implements MqttCallback {

    private static volatile MqttManager INSTANCE;


    private Context mAppContext;

//    private MqttAsyncClient mMqttAsyncClient;
    private MqttAndroidClient client;
    private final MqttConnectOptions mConnectOpts = new MqttConnectOptions();

    private boolean isInited;

    private boolean isStarted;

    private boolean isConnected;

    private boolean isReg;

    private HandlerThread mMqttThread = new HandlerThread("mqtt-thread");

    private Handler mHandler;

    private LocalBroadcastManager mLocalBroadcastManager;

    public static MqttManager getInstance(@NonNull Context appContext) {
        if (INSTANCE == null) {
            synchronized (MqttManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MqttManager(appContext.getApplicationContext());
                }
            }
        }

        return INSTANCE;
    }

    private MqttManager(@NonNull Context appContext) {
        mAppContext = appContext;

        mMqttThread.start();
        mHandler = new Handler(mMqttThread.getLooper());

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mAppContext);
    }

    /**
     * 初始化
     * @param mqttConfig
     * @throws MqttException
     */
    public synchronized void initMqttClient(@NonNull MqttConfig mqttConfig) {
        if (isInited) {
            return;
        }
        isInited = true;

        // 服务器地址（协议+地址+端口号）
        client = new MqttAndroidClient(mAppContext, mqttConfig.getServerURI(), mqttConfig.getClientId());
        // 设置MQTT监听并且接受消息
        client.setCallback(this);

        // 清除缓存
        mConnectOpts.setCleanSession(false);
        mConnectOpts.setAutomaticReconnect(true);
        // 设置超时时间，单位：秒
        mConnectOpts.setConnectionTimeout(20);
        // 心跳包发送间隔，单位：秒
        mConnectOpts.setKeepAliveInterval(20);
        // 用户名
        mConnectOpts.setUserName(mqttConfig.getUserName());
        // 密码
        mConnectOpts.setPassword(mqttConfig.getPassword().toCharArray());
    }

    /**
     * 开启mqtt连接
     */
    public synchronized void start() {
        if (!isInited && isStarted) {
            return;
        }

        registerReceivers();
        isStarted = true;
    }

    /**
     * 停止mqtt连接
     */
    public synchronized void stop() {
        if (!isStarted) {
            return;
        }
        isStarted = false;
        if (isReg) {
            mAppContext.unregisterReceiver(mReceiver);
            isReg = false;
        }
        if (isConnected()){
            try {
                client.disconnect(null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken iMqttToken) {
                        setConnected(false);
                    }

                    @Override
                    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {

                    }
                });

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 销毁
     */
    public void destory(){
        mMqttThread.quit();
        stop();
        INSTANCE = null;
    }

    /**
     * 是否已启动
     * @return
     */
    public boolean isStarted(){
        return isStarted;
    }

    /**
     * 是否连接
     * @return
     */
    public boolean isConnected() {
        return client != null && client.isConnected();
    }

    /**
     * 订阅主题
     * @param topics
     * @param qos
     * @param mqttActionListener
     * @throws MqttException
     */
    public void subscribe(String[] topics, int[] qos, IMqttActionListener mqttActionListener) throws MqttException {
        client.subscribe(topics, qos,null, mqttActionListener);
    }

    /**
     * 取消订阅主题
     * @param topics
     * @param mqttActionListener
     * @throws MqttException
     */
    public void unSubscribe(String[] topics, IMqttActionListener mqttActionListener) throws MqttException {
        client.unsubscribe(topics, null, mqttActionListener);
    }

    /**
     * 发布消息
     * @param topic
     * @param payload
     * @param qos
     * @param retained
     * @param mqttActionListener
     * @throws MqttException
     */
    public void publish(String topic, byte[] payload, int qos, boolean retained, IMqttActionListener mqttActionListener) throws MqttException {
        client.publish(topic, payload, qos, retained,null, mqttActionListener);
    }

    /**
     * 连接
     * @param delayMillis 延时
     */
    private void connect(long delayMillis){
        if (isConnected()) {
            return;
        }
        mHandler.removeCallbacks(mConnectTask);
        mHandler.postDelayed(mConnectTask, delayMillis);
    }

    /**
     * 连接任务
     */
    private Runnable mConnectTask = new Runnable() {
        @Override
        public void run() {
            try {
                client.connect(mConnectOpts, null, new IMqttActionListener(){

                    @Override
                    public void onSuccess(IMqttToken iMqttToken) {
                        setConnected(true);
                    }

                    @Override
                    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                        setConnected(false);
                        XLog.e(XLog.TAG_GU, throwable.getMessage());
                        connect(MqttConsts.RECONNECT_DELAY_MILLIS);
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
                switch (e.getReasonCode()){
                    case MqttException.REASON_CODE_CLIENT_CONNECTED:
                        setConnected(true);
                        break;
                    default: //超时重连
                        connect(MqttConsts.RECONNECT_DELAY_MILLIS);
                        break;
                }
            }
        }
    };


    private void registerReceivers() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mAppContext.registerReceiver(mReceiver, intentFilter);
        isReg = true;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                NetworkInfo.State state = NetworkInfo.State.DISCONNECTED;

                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isAvailable() && mNetworkInfo.isConnected()) {
                    state = mNetworkInfo.getState();
                }

                if (state == NetworkInfo.State.CONNECTED) {
                    connect(0);
                }
            }
        }
    };

    private void setConnected(boolean connected) {
        isConnected = connected;
        XLog.writeLog("Mqtt connect state = " + connected, true);
        Intent intent = new Intent(MqttConsts.CONNECT_CHANGED_ACTION);
        intent.setPackage(mAppContext.getPackageName());
        intent.putExtra(MqttConsts.KEY_CONNECT_STATE, isConnected);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        setConnected(false);
        XLog.e(XLog.TAG_GU,"MQTT连接丢失");
        XLog.writeLog("MQTT连接丢失,连接状态：" + isConnected(), true);
        connect(MqttConsts.RECONNECT_DELAY_MILLIS);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        XLog.e(XLog.TAG_GU, "接收到主题:" + s +"------playload:"+ new String(mqttMessage.getPayload()));

        Intent intent = new Intent(MqttConsts.MESSAGE_RECEIVED_ACTION);
        intent.setPackage(mAppContext.getPackageName());
        intent.putExtra(MqttConsts.KEY_MQTT_TOPIC, s);
        intent.putExtra(MqttConsts.KEY_MQTT_MSG, new String(mqttMessage.getPayload()));
        intent.putExtra(MqttConsts.KEY_IS_DUPLICATE, mqttMessage.isDuplicate());
        intent.putExtra(MqttConsts.KEY_IS_RETAINED, mqttMessage.isRetained());
        intent.putExtra(MqttConsts.KEY_QOS, mqttMessage.getQos());
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        try {
            XLog.e(XLog.TAG_GU, "主题接收完成:" + iMqttDeliveryToken.getMessage().toString());
        } catch (MqttException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(MqttConsts.MESSAGE_DELIVERY_COMPLETE_ACTION);
        intent.setPackage(mAppContext.getPackageName());
        intent.putExtra(MqttConsts.KEY_MESSAGE_ID, iMqttDeliveryToken.getMessageId());
        mLocalBroadcastManager.sendBroadcast(intent);
    }

}
