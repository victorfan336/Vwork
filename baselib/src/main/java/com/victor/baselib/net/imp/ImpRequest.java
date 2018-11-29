package com.victor.baselib.net.imp;

/**
 *
 *
 */
public interface ImpRequest<T> {

    void onSuccess(T o);

    void onFailure();

}
