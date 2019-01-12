package com.victor.baselib.net.signal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.victor.baselib.R;


/**
 * @author fanwentao
 * @Description
 * @date 2018/8/7
 */
public class WiFiDrawalbeImp implements IPhoneSingnalDrawable {

    private Context mContext;
    private ImageView ivStatus;

    public WiFiDrawalbeImp(Context context, ImageView ivStatus) {
        mContext = context;
        this.ivStatus = ivStatus;
        registerSignalReceiver();
        ivStatus.setImageResource(getSignal4Drawable());
    }

    @Override
    public int getNoSignalDrawable() {
        return R.drawable.no_wifi;
    }

    @Override
    public int getSignal1Drawable() {
        return R.drawable.wifi1;
    }

    @Override
    public int getSignal2Drawable() {
        return R.drawable.wifi2;
    }

    @Override
    public int getSignal3Drawable() {
        return R.drawable.wifi3;
    }

    @Override
    public int getSignal4Drawable() {
        return R.drawable.wifi;
    }

    // 使用Handler实现UI线程与Timer线程之间的信息传递,每5秒告诉UI线程获得wifiInto
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 如果收到正确的消息就获取WifiInfo，改变图片并显示信号强度
                case 1:
                    ivStatus.setImageResource(getSignal4Drawable());
                    break;
                case 2:
                    ivStatus.setImageResource(getSignal3Drawable());
                    break;
                case 3:
                    ivStatus.setImageResource(getSignal2Drawable());
                    break;
                case 4:
                    ivStatus.setImageResource(getSignal1Drawable());
                    break;
                case 5:
                default:
                    //以防万一
                    ivStatus.setImageResource(getNoSignalDrawable());
                    ivStatus.setImageResource(getNoSignalDrawable());
                    break;
            }
        }
    };

    private void setIvStatus(int wifiLevel) {
        //根据获得的信号强度发送信息
//        XLog.e(XLog.TAG_GU, "WiFi信号强度：" + wifiLevel);
        if (wifiLevel <= 0 && wifiLevel >= -50) {
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        } else if (wifiLevel < -50 && wifiLevel >= -70) {
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
        } else if (wifiLevel < -70 && wifiLevel >= -80) {
            Message msg = new Message();
            msg.what = 3;
            handler.sendMessage(msg);
        } else if (wifiLevel < -80 && wifiLevel >= -100) {
            Message msg = new Message();
            msg.what = 4;
            handler.sendMessage(msg);
        } else {
            Message msg = new Message();
            msg.what = 5;
            handler.sendMessage(msg);
        }
    }

    private void registerSignalReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        mContext.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setIvStatus(intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, 0));
        }
    };

    /**
     * 不再使用后，必须调用destroy方法来终止线程
     */
    @Override
    public void destroy() {
        mContext.unregisterReceiver(broadcastReceiver);
    }

}
