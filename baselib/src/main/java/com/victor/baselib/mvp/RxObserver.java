package com.victor.baselib.mvp;

import io.reactivex.observers.DisposableObserver;

public class RxObserver<T> extends DisposableObserver<T> {

    private RxObserver.ObserverCallBack callBack;

    public RxObserver(RxObserver.ObserverCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onNext(T t) {
        if (callBack != null) {
            callBack.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onFail(1, e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    public interface ObserverCallBack<T> {
        void onSuccess(T result);
        void onFail(int errorCode, String errorMsg);
    }
}
