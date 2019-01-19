package com.victor.baselib.net;

import com.victor.baselib.net.error.ExceptionEngine;


import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {


    @Override
    public Observable apply(Throwable throwable) throws Exception {
        //ExceptionEngine为处理异常的驱动器
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
