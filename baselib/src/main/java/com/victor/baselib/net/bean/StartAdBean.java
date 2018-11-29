package com.victor.baselib.net.bean;

/**
 * @author fanwentao
 * @Description
 * @date 2018/4/27
 */
public class StartAdBean {

    /**
     * msg : 成功!
     * data : {"imgUrl":"/uploadFiles/images/1524204029432.png","linkUrl":"www.baidu.com"}
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
         * imgUrl : /uploadFiles/images/1524204029432.png
         * linkUrl : www.baidu.com
         */

        private String imgUrl;
        private String linkUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }
}
