package com.victor.baselib.net;


import com.victor.baselib.net.bean.ApiObjResultBean;
import com.victor.baselib.net.error.ServerException;

import io.reactivex.functions.Function;

public class ServerObjResponseFunc<T> implements Function<ApiObjResultBean<T>, T> {
    @Override
    public T apply(ApiObjResultBean<T> response) throws Exception {
        if (response.getErrorCode() == 0) {
            return response.getData();
        } else {
            throw new ServerException(response.getErrorMsg(), response.getErrorCode());
        }
    }
}
