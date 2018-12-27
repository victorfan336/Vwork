package com.victor.mqtt;

/**
 * C
 */
public class MqttConsts {
    /**
     * 收到消息
     */
    public static final String MESSAGE_RECEIVED_ACTION = "com.leagoo.sdk.mqtt.MESSAGE_RECEIVED_ACTION";

    /**
     * 发送消息结果
     */
    public static final String MESSAGE_DELIVERY_COMPLETE_ACTION = "com.leagoo.sdk.mqtt.MESSAGE_DELIVERY_COMPLETE_ACTION";


    public static final String MESSAGE_RECEIVED_TRANSPOND_ACTION = "com.leagoo.sdk.mqtt.MESSAGE_RECEIVED_TRANSPOND_ACTION";

    /**
     * 连接变化通知
     */
    public static final String CONNECT_CHANGED_ACTION = "com.leagoo.sdk.mqtt.MESSAGE_CONNECT_CHANGED";

    /**
     * 重连延时
     */
    public static final long RECONNECT_DELAY_MILLIS = 5000L;

    /**
     * CONNECT STATE key
     */
    public static final String KEY_CONNECT_STATE = "connectState";

    /**
     * Messageid key
     */
    public static final String KEY_MESSAGE_ID = "messageId";
    /**
     * Topic key
     */
    public static final String KEY_MQTT_TOPIC = "mqttTopic";

    /**
     * Message key
     */
    public static final String KEY_MQTT_MSG = "mqttMsg";

    /**
     * isRetained key
     */
    public static final String KEY_IS_RETAINED = "isRetained";

    /**
     * isDuplicate key
     */
    public static final String KEY_IS_DUPLICATE = "isDuplicate";

    /**
     * qos key
     */
    public static final String KEY_QOS = "qos";
}
