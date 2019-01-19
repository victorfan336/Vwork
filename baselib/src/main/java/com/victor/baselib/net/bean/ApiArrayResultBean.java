package com.victor.baselib.net.bean;

import java.util.List;

public class ApiArrayResultBean<T> extends ApiBaseResultBean<T> {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
