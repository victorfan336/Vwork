package com.victor.baselib.net.bean;

import java.io.Serializable;

/**
 * @author fanwentao
 * @Description
 * @date 2018/4/27
 */
public class SalePolicyBean implements Serializable {

    /**
     * msg : 成功!
     * data : {"link":"http://www.baidu.com","title":"售后政策"}
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
         * link : http://www.baidu.com
         * title : 售后政策
         */

        private String link;
        private String title;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
