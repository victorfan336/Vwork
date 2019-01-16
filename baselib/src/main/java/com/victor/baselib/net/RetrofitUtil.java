package com.victor.baselib.net;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.baselib.net.bean.ApiArrayResultBean;
import com.victor.baselib.net.bean.ApiObjResultBean;
import com.victor.baselib.net.bean.WhosArticleBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * Created by fanwentao on 2018/1/30.
 */

public class RetrofitUtil {

    private RetrofitClient mRetrofitClient;
    private RetrofitClient.RetrofitBuilder mRetrofitbuild;
    private RetrofitService mRetrofitService;

    private RetrofitUtil() {
        setUrl(RetrofitService.BASE_URL_THEME);
        setConnectTimeout(20000);
        setTimeout(30000);
    }

    public static class RetrofitSingleton {
        private static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }

    public static RetrofitUtil getInstance() {
        return RetrofitSingleton.INSTANCE;
    }

    public RetrofitUtil setUrl(String url) {
        if (mRetrofitbuild == null) {
            mRetrofitbuild = new RetrofitClient.RetrofitBuilder();
        }
        mRetrofitbuild.setBaseUrl(url);
        return this;
    }

    /**
     * 设置连接超时
     *
     * @param connectTimeout 超时时间
     * @return
     */
    public RetrofitUtil setConnectTimeout(int connectTimeout) {
        if (mRetrofitbuild == null) {
            mRetrofitbuild = new RetrofitClient.RetrofitBuilder();
        }
        mRetrofitbuild.setConnectTimeout(connectTimeout);
        return this;
    }

    /**
     * 设置读写超时
     *
     * @param timeout 超时时间
     * @return
     */
    public RetrofitUtil setTimeout(int timeout) {
        if (mRetrofitbuild == null) {
            mRetrofitbuild = new RetrofitClient.RetrofitBuilder();
        }
        mRetrofitbuild.setTimeout(timeout);
        return this;
    }

    public RetrofitUtil build() {
        mRetrofitClient = mRetrofitbuild.build();
        mRetrofitService = mRetrofitClient.getService();
        return this;
    }

    public RetrofitService getRetrofiService() {
        return mRetrofitService;
    }

    public Observable<ApiArrayResultBean<WhosArticleBean>> getWxWhosArticle() {
        return mRetrofitService.getWxWhosArticle();
    }

    public Observable<ApiObjResultBean> register(String userName, String password, String repassword) {
        return mRetrofitService.register(userName, password, repassword);
    }

}
