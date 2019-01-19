package com.victor.baselib.net.bean;

public class ApiObjResultBean<T> extends ApiBaseResultBean<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
