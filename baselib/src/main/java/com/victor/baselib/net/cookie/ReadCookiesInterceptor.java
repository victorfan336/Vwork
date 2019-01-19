package com.victor.baselib.net.cookie;

import android.util.Log;

import com.victor.baselib.base.BaseApplication;
import com.victor.baselib.utils.UtilSharedPreferences;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ReadCookiesInterceptor  implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = (HashSet) UtilSharedPreferences.getSetData(BaseApplication.getContext(), SaveCookiesInterceptor.COOKIE);
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            Log.v("OkHttp", "Adding Header: " + cookie);
        }
        return chain.proceed(builder.build());
    }
}
