package com.victor.baselib.net.bean;

import java.io.Serializable;

/**
 * @Description
 * @author fanwentao
 * @date 2018/4/4
 */

public class ContactUsBean implements Serializable {


    /**
     * msg : 成功!
     * data : {"timeEnd":"周五","twitter":"22","timeStart":"周一","address":"深圳市南山区南头大厦cd栋","hoursStart":"8:00","mail":"zhaoqz@qq.com","faceBook":"http|:/www.baidu.com","phoneOne":"18682292314","text":"哈哈哈哈哈哈哈哈哈哈哈哈444","hoursEnd":"20:00","phoneTwo":"18682292315"}
     * code : 0
     */

    private String msg;
    private DataBean data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * timeEnd : 周五
         * twitter : 22
         * timeStart : 周一
         * address : 深圳市南山区南头大厦cd栋
         * hoursStart : 8:00
         * mail : zhaoqz@qq.com
         * faceBook : http|:/www.baidu.com
         * phoneOne : 18682292314
         * text : 哈哈哈哈哈哈哈哈哈哈哈哈444
         * hoursEnd : 20:00
         * phoneTwo : 18682292315
         */

        private String timeEnd;
        private String twitter;
        private String timeStart;
        private String address;
        private String hoursStart;
        private String mail;
        private String faceBook;
        private String phoneOne;
        private String text;
        private String hoursEnd;
        private String phoneTwo;

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHoursStart() {
            return hoursStart;
        }

        public void setHoursStart(String hoursStart) {
            this.hoursStart = hoursStart;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getFaceBook() {
            return faceBook;
        }

        public void setFaceBook(String faceBook) {
            this.faceBook = faceBook;
        }

        public String getPhoneOne() {
            return phoneOne;
        }

        public void setPhoneOne(String phoneOne) {
            this.phoneOne = phoneOne;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getHoursEnd() {
            return hoursEnd;
        }

        public void setHoursEnd(String hoursEnd) {
            this.hoursEnd = hoursEnd;
        }

        public String getPhoneTwo() {
            return phoneTwo;
        }

        public void setPhoneTwo(String phoneTwo) {
            this.phoneTwo = phoneTwo;
        }
    }
}
