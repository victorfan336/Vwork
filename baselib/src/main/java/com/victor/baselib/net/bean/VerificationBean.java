package com.victor.baselib.net.bean;

import java.io.Serializable;

/**
 * @author lzb
 * @date 2018-4-25
 * 真伪辨别 bean
 * {"msg":"成功!","code":0,"data":{"info":"您购买的是正品!"}}
 */
public class VerificationBean implements Serializable {

    private String msg;
    private VerificationBean.DataBean data;
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
        private String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
