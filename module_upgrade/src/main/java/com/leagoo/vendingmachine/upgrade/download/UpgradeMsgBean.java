package com.leagoo.vendingmachine.upgrade.download;


import com.leagoo.vendingmachine.upgrade.Tools;

import java.util.Map;

/**
 * @author fanwentao
 * @Description
 * @date 2018/4/13
 */

public class UpgradeMsgBean {


    /**
     * msgType : msgType
     * notificationTitle : notificationTitle
     * notificationContent : notificationContent
     * cancel : cancel
     * confirm : confirm
     * notificationLargeIcon : notificationLargeIcon
     * content : content
     * contentPic : contentPic
     * downloadUrl : downloadUrl
     * fileMd5 : fileMd5
     * versionCode : versionCode
     * versionName : versionName
     */

    private String msgType;
    private String notificationTitle;
    private String notificationContent;
    private String cancel;
    private String confirm;
    private String notificationLargeIcon;
    private String content;
    private String contentPic;
    private String downloadUrl;
    private String fileMd5;
    private int versionCode;
    private String versionName;

//    public static UpgradeMsgBean getInstance(Map<String, String> dataMap) {
//        if (dataMap != null && dataMap.size() > 0) {
//            UpgradeMsgBean upgradeMsg = new UpgradeMsgBean();
//            upgradeMsg.setNotificationTitle(dataMap.get("notificationTitle"));
//            upgradeMsg.setNotificationContent(dataMap.get("notificationContent"));
//            upgradeMsg.setNotificationLargeIcon(dataMap.get("notificationLargeIcon"));
//            upgradeMsg.setMsgType(dataMap.get("msgType"));
//            upgradeMsg.setContent(dataMap.get("content"));
//            upgradeMsg.setContentPic(dataMap.get("contentPic"));
//            upgradeMsg.setDownloadUrl(dataMap.get("downloadUrl"));
////            upgradeMsg.setDownloadUrl("http://ucdl.25pp.com/fs01/union_pack/Wandoujia_267909_web_seo_baidu_homepage.apk");
//            upgradeMsg.setCancel(dataMap.get("cancel"));
//            upgradeMsg.setConfirm(dataMap.get("confirm"));
//
//            upgradeMsg.setFileMd5(dataMap.get("fileMd5"));
//            upgradeMsg.setVersionCode(Tools.parseString2Int(dataMap.get("versionCode"), 0));
//            upgradeMsg.setVersionName(dataMap.get("versionName"));
//
//            return upgradeMsg;
//        }
//        return null;
//    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getNotificationLargeIcon() {
        return notificationLargeIcon;
    }

    public void setNotificationLargeIcon(String notificationLargeIcon) {
        this.notificationLargeIcon = notificationLargeIcon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentPic() {
        return contentPic;
    }

    public void setContentPic(String contentPic) {
        this.contentPic = contentPic;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
