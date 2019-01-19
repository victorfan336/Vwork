package com.victor.baselib.net.cookie;

import com.victor.baselib.base.BaseApplication;
import com.victor.baselib.utils.UtilSharedPreferences;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class SaveCookiesInterceptor implements Interceptor {

    public static final String COOKIE = "vwork-cookie";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            UtilSharedPreferences.saveSetData(BaseApplication.getContext(), COOKIE, cookies);
        }

        return originalResponse;
    }
}