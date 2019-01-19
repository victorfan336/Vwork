package com.victor.baselib.net.cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class PersistentCookieStore {

    private ConcurrentHashMap<HttpUrl, List<Cookie>> cookies = new ConcurrentHashMap();

    public void add(HttpUrl url, Cookie cookie) {
        if (cookies.containsKey(url)) {
            List<Cookie> cookieList = new ArrayList<>();
            cookieList.add(cookie);
            cookies.put(url, cookieList);
        } else {
            List<Cookie> cookieList = cookies.get(url);
            cookieList.add(cookie);
            cookies.put(url, cookieList);
        }
    }

    public List<Cookie> get(HttpUrl url) {
        if (url != null) {
            return cookies.get(url);
        }
        return null;
    }
}
