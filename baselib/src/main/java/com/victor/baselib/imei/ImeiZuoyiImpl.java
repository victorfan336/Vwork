package com.victor.baselib.imei;

/**
 * 卓易系统IMEI接口
 * Created by fanwentao on 2018/1/15.
 */

public class ImeiZuoyiImpl extends IImeiUtil {

    @Override
    public String[] getImeis() {
        String imei1 = getProperty("persist.sys.freeme_imei1", "");
        String imei2 = getProperty("persist.sys.freeme_imei2", "");
        return new String[]{imei1, imei2 };
    }

}
