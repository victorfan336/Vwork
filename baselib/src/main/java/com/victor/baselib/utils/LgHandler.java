package com.victor.baselib.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by yuchuan on 8/17/16.
 */

public abstract class LgHandler<T> extends Handler {

    private WeakReference<T> weak;

    public LgHandler(T t) {
        this.weak = new WeakReference<T>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        if (null == weak || null == weak.get()) {
            return;
        }
        handleLgMessage(msg, weak.get());
        super.handleMessage(msg);
    }

    protected abstract void handleLgMessage(Message msg, T t);
}
