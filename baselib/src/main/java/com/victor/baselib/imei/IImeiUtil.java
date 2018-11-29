package com.victor.baselib.imei;

import java.lang.reflect.Method;

/**
 * Created by fanwentao on 2018/1/15.
 */

public abstract class IImeiUtil {

    abstract String[] getImeis();

    protected String getImei() {
        String[] imeis = getImeis();
        if (imeis != null && imeis.length > 0) {
            return imeis[0];
        } else {
            return "";
        }
    }

    protected String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String)(get.invoke(c, key, "" ));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return value;
        }
    }

}
