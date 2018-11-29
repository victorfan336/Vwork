package com.victor.baselib.imei;

/**
 * Power系统IMEI接口
 * @author fanwentao
 * @date 2018/03/02.
 */

public class ImeiPowerImpl extends IImeiUtil {

    @Override
    public String[] getImeis() {
        String imei1 = getProperty("gsm.mtk.imei1", "");
        String imei2 = getProperty("gsm.mtk.imei2", "");
        return new String[]{imei1, imei2 };
    }

}
