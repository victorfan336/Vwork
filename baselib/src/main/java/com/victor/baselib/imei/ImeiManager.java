package com.victor.baselib.imei;

import android.text.TextUtils;

/**
 * Created by fanwentao on 2018/1/15.
 */

public class ImeiManager {

    public static String getImei() {
        String[] imeis = getImeis();
        if (imeis != null && imeis.length > 0) {
            return imeis[0];
        } else {
            return "";
        }
    }

    public static String[] getImeis() {
        String[] imeis =  new ImeiZuoyiImpl().getImeis();
        if (imeis == null || (TextUtils.isEmpty(imeis[0]) && TextUtils.isEmpty(imeis[1]))
                || (imeis[0].equalsIgnoreCase("unknown") && imeis[1].equalsIgnoreCase("unknown"))) {
            imeis = new ImeiPowerImpl().getImeis();
        }
        return imeis;
    }

}
