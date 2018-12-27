package com.victor.mqtt;

/**
 */
public class MqttConfig {

    private String clientId;

    private String userName;

    private String password;

    private int keepAlive;

    private String serverURI;

    private String subControlTopic;

    private String subDeltaTopic;

    private String pubReportTopic;

    private String pubUpdateTopic;


    public String getSubControlTopic() {
        return subControlTopic;
    }

    public void setSubControlTopic(String subControlTopic) {
        this.subControlTopic = subControlTopic;
    }

    public String getSubDeltaTopic() {
        return subDeltaTopic;
    }

    public void setSubDeltaTopic(String subDeltaTopic) {
        this.subDeltaTopic = subDeltaTopic;
    }

    public String getPubReportTopic() {
        return pubReportTopic;
    }

    public void setPubReportTopic(String pubReportTopic) {
        this.pubReportTopic = pubReportTopic;
    }

    public String getPubUpdateTopic() {
        return pubUpdateTopic;
    }

    public void setPubUpdateTopic(String pubUpdateTopic) {
        this.pubUpdateTopic = pubUpdateTopic;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getServerURI() {
        return serverURI;
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    @Override
    public String toString() {
        return "MqttConfig{" +
                "clientId='" + clientId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", keepAlive=" + keepAlive +
                ", serverURI='" + serverURI + '\'' +
                ", subControlTopic='" + subControlTopic + '\'' +
                ", subDeltaTopic='" + subDeltaTopic + '\'' +
                ", pubReportTopic='" + pubReportTopic + '\'' +
                ", pubUpdateTopic='" + pubUpdateTopic + '\'' +
                '}';
    }
}
