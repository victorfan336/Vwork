package com.victor.baselib.net;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
*
 */

public class RetrofitClient {

    private com.victor.baselib.net.RetrofitService mRetrofitService;


    private RetrofitClient(OkHttpClient client, Retrofit.Builder builder) {
        if (client != null) {
            builder.client(client);
        }
        mRetrofitService = builder.build().create(com.victor.baselib.net.RetrofitService.class);
    }

    public com.victor.baselib.net.RetrofitService getService() {
        return mRetrofitService;
    }

    public static class RetrofitBuilder {
        private OkHttpClient client;
        private OkHttpClient.Builder okBuilder;
        private Retrofit.Builder retrofitBuilder;
        private Map<String, Retrofit.Builder> map = new HashMap<>();


        /**
         * 设置根url
         *
         * @param baseUrl 网址
         * @return
         */
        public RetrofitBuilder setBaseUrl(String baseUrl) {
            if (map != null && map.containsKey(baseUrl)) {
                retrofitBuilder = map.get(baseUrl);
            } else {
                retrofitBuilder = createBuilder();
                retrofitBuilder.baseUrl(baseUrl);
                retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
                if (map == null) {
                    map = new HashMap<>();
                }
                map.put(baseUrl, retrofitBuilder);
            }
            return this;
        }

        /**
         * 设置连接超时
         *
         * @param connectTimeout 超时时间
         * @return
         */
        public RetrofitBuilder setConnectTimeout(int connectTimeout) {
            if (okBuilder == null) {
                okBuilder = new OkHttpClient.Builder();
            }
            okBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
            return this;
        }

        /**
         * 设置读写超时
         *
         * @param timeout 超时时间
         * @return
         */
        public RetrofitBuilder setTimeout(int timeout) {
            if (okBuilder == null) {
                okBuilder = new OkHttpClient.Builder();
            }
            okBuilder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
            return this;
        }

        /**
         * 添加转换器
         *
         * @param factory 转换器
         * @return
         */
        public RetrofitBuilder addConverterFactory(Converter.Factory factory) {
            if (retrofitBuilder == null) {
                throw new IllegalArgumentException("Not set Base Url");
            }
            retrofitBuilder.addConverterFactory(factory);
            return this;
        }

        public RetrofitBuilder addCallAdapterFactory(CallAdapter.Factory factory) {
            if (retrofitBuilder == null) {
                throw new IllegalArgumentException("Not set Base Url");
            }
            retrofitBuilder.addCallAdapterFactory(factory);
            return this;
        }

        private Retrofit.Builder createBuilder() {
            return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create());
        }

        public RetrofitClient build() {
            if (okBuilder == null) {
                setConnectTimeout(30*1000);
            }
            client = okBuilder.build();
            if (retrofitBuilder == null) {
                retrofitBuilder = createBuilder();
            }
            return new RetrofitClient(client, retrofitBuilder);
        }

    }

}
