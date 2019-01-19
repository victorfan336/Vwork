package com.victor.baselib.net.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieManger implements CookieJar {
    private static final String TAG = "CookieManger";
    private static PersistentCookieStore cookieStore;
    /**
     * Mandatory constructor for the NovateCookieManger
     */
    public CookieManger() {
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore();
        }
    }
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
