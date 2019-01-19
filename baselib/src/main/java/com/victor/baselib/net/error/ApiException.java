package com.victor.baselib.net.error;

public class ApiException extends Exception {

    private String msg;
    private int code;


    public ApiException(Throwable throwable, int code) {
        super();
        this.msg = throwable.getMessage();
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
