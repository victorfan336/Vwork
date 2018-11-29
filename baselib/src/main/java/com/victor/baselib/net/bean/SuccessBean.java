package com.victor.baselib.net.bean;


public class SuccessBean {

    public String errorMessage;
    public int errorCode;
    public int code;


    @Override
    public String toString() {
        return "SuccessBean{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errorCode=" + errorCode +
                ", code=" + code +
                '}';
    }
}
