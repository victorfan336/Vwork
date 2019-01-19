package com.victor.baselib.net;


import com.victor.baselib.net.bean.ApiArrayResultBean;
import com.victor.baselib.net.error.ServerException;

import java.util.List;

import io.reactivex.functions.Function;

public class ServerArrayResponseFunc<T> implements Function<ApiArrayResultBean<T>, List<T>> {

    @Override
    public List<T> apply(ApiArrayResultBean<T> response) throws Exception {
        if (response.getErrorCode() == 0) {
            return response.getData();
        } else {
            throw new ServerException(response.getErrorMsg(), response.getErrorCode());
        }
    }
}
