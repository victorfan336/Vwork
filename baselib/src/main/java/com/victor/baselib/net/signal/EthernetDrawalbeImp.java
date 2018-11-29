package com.victor.baselib.net.signal;


import com.victor.baselib.R;
import com.victor.baselib.utils.XLog;

/**
 * @author fanwentao
 * @Description
 * @date 2018/8/7
 */
public class EthernetDrawalbeImp implements IPhoneSingnalDrawable {

    @Override
    public int getNoSignalDrawable() {
        return R.drawable.no_signal;
    }

    @Override
    public int getSignal1Drawable() {
        return R.drawable.signal;
    }

    @Override
    public int getSignal2Drawable() {
        return R.drawable.signal2;
    }

    @Override
    public int getSignal3Drawable() {
        return R.drawable.signal3;
    }

    @Override
    public int getSignal4Drawable() {
        return R.drawable.signal;
    }

    @Override
    public void destroy() {
        XLog.e(XLog.TAG_GU, "EthernetDrawalbeImp.destroy()");
    }
}
