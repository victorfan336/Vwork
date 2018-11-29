package com.victor.baselib.net.signal;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.ImageView;


import com.victor.baselib.R;

import java.lang.reflect.Method;

/**
 * @author fanwentao
 * @Description
 * @date 2018/8/7
 */
public class MobileDrawalbeImp implements IPhoneSingnalDrawable {

    private TelephonyManager tm;
    private ImageView ivStatus;

    /**
     * 每5秒检测一次信号强弱
     * @param context
     * @param ivStatus
     */
    public MobileDrawalbeImp(Context context, ImageView ivStatus) {
        this.ivStatus = ivStatus;
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        setMobileSignalListener(context);
        ivStatus.setImageResource(getSignal4Drawable());
    }

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
        tm.listen(mylistener, PhoneStateListener.LISTEN_NONE);
    }
    PhoneStateListener mylistener;
    /**
     * 得到当前的手机蜂窝网络信号强度
     * 获取LTE网络和3G/2G网络的信号强度的方式有一点不同，
     * LTE网络强度是通过解析字符串获取的，
     * 3G/2G网络信号强度是通过API接口函数完成的。
     * asu 与 dbm 之间的换算关系是 dbm=-113 + 2*asu
     */
    private void setMobileSignalListener(Context context) {


        mylistener = new PhoneStateListener(){
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);

                int dbm = 0;
                try {
                    Method method1 = signalStrength.getClass().getMethod("getDbm");
                    dbm = (int) method1.invoke(signalStrength);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                XLog.e(XLog.TAG_GU, "手机信号强度：" + dbm);

                if (ivStatus != null) {
                    if (dbm > -97) {
                        ivStatus.setImageResource(getSignal4Drawable());
                    } else if (dbm > -105) {
                        ivStatus.setImageResource(getSignal3Drawable());
                    } else if (dbm > -105) {
                        ivStatus.setImageResource(getSignal2Drawable());
                    } else if (dbm > -120) {
                        ivStatus.setImageResource(getSignal1Drawable());
                    } else {
                        ivStatus.setImageResource(getNoSignalDrawable());
                    }
                }
            }
        };
        //开始监听
        tm.listen(mylistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

    }

}
