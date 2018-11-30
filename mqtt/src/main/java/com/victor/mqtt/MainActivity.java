package com.victor.mqtt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MQTTService.class));

        findViewById(R.id.btn_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MQTTService.publish("{\n" +
                        "  \"desired\": {\n" +
                        "    \"deviceState\": 13\n" +
                        "  },\n" +
                        "  \"reported\": {\n" +
                        "    \"deviceState\": 10,\n" +
                        "    \"networkType\": 5,\n" +
                        "    \"online\": 0\n" +
                        "  }\n" +
                        "}");
            }
        });
    }


    /*public static void main(String args[]){
        //消息的类型
        String topic        = "$baidu/iot/shadow/leagoo_device_shadow/update";
        //消息内容
        String content      = "{\n" +
                "  \"desired\": {},\n" +
                "  \"reported\": {\n" +
                "    \"storeNum\": 1234141241\n" +
                "  }\n" +
                "}";
        //消息发送的模式   选择消息发送的次数，依据不同的使用环境使用不同的模式
        int qos             = 1;
        //服务器地址
        String broker       = "tcp://77bb6e089f734ded9a66b7b14589dc05.mqtt.iot.gz.baidubce.com:1883";
        //客户端的唯一标识
        String clientId     = "CLIENTID JavaSample";
        //消息缓存的方式  内存缓存
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            //创建以恶搞MQTT客户端
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            //消息的配置参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            //不记忆上一次会话
            connOpts.setCleanSession(true);
            connOpts.setUserName("77bb6e089f734ded9a66b7b14589dc05/leagoo_device_shadow");
//            connOpts.setPassword("SNHKlFCB+s0zwb+tQyj+4q1LWYQaFlQF4A17wrmqY6Q=");
            System.out.println("Connecting to broker: "+broker);
            //链接服务器
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            //创建消息
            MqttMessage message = new MqttMessage(content.getBytes());
            //给消息设置发送的模式
            message.setQos(qos);
            //发布消息到服务器
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            //断开链接
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }*/

}
