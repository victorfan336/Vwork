package com.victor.baselib.net;

import android.content.Context;
import android.widget.Toast;

import com.victor.baselib.net.error.ApiException;
import com.victor.baselib.net.error.ErrorSubscriber;
import com.victor.baselib.utils.ToastUtil;

import org.reactivestreams.Subscription;

public class RxSubscriber<T> extends ErrorSubscriber<T> {
    private Context context;

//    @Override
//    public void onStart() {
//        Toast.makeText(context, "正在加载数据", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onError(ApiException ex) {
        ToastUtil.getInstance().showToast(context, ex.getMsg());
    }

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onComplete() {

    }
}

