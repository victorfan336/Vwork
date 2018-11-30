package com.victor.baselib.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.victor.baselib.R;
import com.victor.baselib.font.CommTextView;
import com.victor.baselib.net.signal.EthernetDrawalbeImp;
import com.victor.baselib.net.signal.IPhoneSingnalDrawable;
import com.victor.baselib.net.signal.MobileDrawalbeImp;
import com.victor.baselib.net.signal.WiFiDrawalbeImp;
import com.victor.baselib.utils.UtilHttp;
import com.victor.baselib.utils.XLog;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 状态栏
 * @author fanwentao
 * @Description
 * @date 2018/8/8
 */
public class NotificationLayoutView extends LinearLayout{

    private Context mContext;
    private TextView tvSignalType;
    private ImageView ivStatus;
    private IPhoneSingnalDrawable signalDrawable;
    private Resources res;

    public NotificationLayoutView(Context context) {
        this(context, null);
    }

    public NotificationLayoutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotificationLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        res = mContext.getResources();
        setBackgroundResource(R.color.white);
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) res.getDimensionPixelSize(R.dimen.y74));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setLayoutDirection(LinearLayout.HORIZONTAL);
        setLayoutParams(params);
        registerSignalReceiver();
        initView();
    }

    private void initView() {
        tvSignalType = new CommTextView(mContext);
        tvSignalType.setPadding((int)res.getDimensionPixelSize(R.dimen.x30), 0, 0, 0);
        tvSignalType.setTextColor(res.getColor(R.color.lg_text_black));
        tvSignalType.setTextSize(COMPLEX_UNIT_PX, (int)res.getDimensionPixelSize(R.dimen.y48));
        LinearLayout.LayoutParams signalParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        signalParams.gravity = Gravity.CENTER_VERTICAL;
        tvSignalType.setLayoutParams(signalParams);
        addView(tvSignalType);

        ivStatus = new ImageView(mContext);
        ivStatus.setPadding((int)res.getDimensionPixelSize(R.dimen.x20), 0, 0, 0);
        ivStatus.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        statusParams.gravity = Gravity.CENTER_VERTICAL;
        ivStatus.setLayoutParams(statusParams);
        addView(ivStatus);

        TextClock textClock = new TextClock(mContext);
        textClock.setTextColor(res.getColor(R.color.lg_text_black));
        textClock.setPadding((int)res.getDimensionPixelSize(R.dimen.x30), 0, 0, 0);
        textClock.setTextSize(COMPLEX_UNIT_PX, (int)res.getDimensionPixelSize(R.dimen.y48));
        textClock.setFormat24Hour("HH:mm");
        LinearLayout.LayoutParams clockParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        clockParams.weight = 1.0f;
        clockParams.gravity = Gravity.CENTER_VERTICAL;
        textClock.setLayoutParams(clockParams);
        addView(textClock);

        TextView tvSn = new CommTextView(mContext);
        LinearLayout.LayoutParams snParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        snParams.gravity = Gravity.CENTER_VERTICAL;
        tvSn.setLayoutParams(snParams);
        tvSn.setPadding(0, 0,  (int)res.getDimensionPixelSize(R.dimen.x30), 0);
        tvSn.setTextColor(res.getColor(R.color.lg_text_black));
        tvSn.setTextSize(COMPLEX_UNIT_PX, (int)res.getDimensionPixelSize(R.dimen.y48));
        addView(tvSn);

        if (tvSn != null) {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission")
            String serialNum = telephonyManager.getSimSerialNumber();
            XLog.e(XLog.TAG_GU, "serialNum = " + serialNum);
            if (TextUtils.isEmpty(serialNum)) {
                serialNum = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);;
                XLog.e(XLog.TAG_GU, "serialNum = " + serialNum);
            }
            tvSn.setText(String.format(mContext.getString(R.string.finish), serialNum));
        }
    }

    private void registerSignalReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int netType = -1;

            // 得到连接管理器对象
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    netType = activeNetworkInfo.getType();
                }
            } else {
                ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                //获取所有网络连接的信息
                Network[] networks = connMgr.getAllNetworks();
                //通过循环将网络信息逐个取出来
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                    if (networkInfo.isConnected()) {
                        netType = networkInfo.getType();
                    }
                }
            }
            XLog.e(XLog.TAG_GU, "网络状态改变：" + netType);
            netTypeChanged(netType);
        }
    };

    private void netTypeChanged(int netType) {
        switch (netType) {
            case ConnectivityManager.TYPE_WIFI:
                tvSignalType.setVisibility(View.GONE);
                ivStatus.setVisibility(View.VISIBLE);
                if (signalDrawable != null) {
                    if (!(signalDrawable instanceof WiFiDrawalbeImp)) {
                        signalDrawable.destroy();
                        signalDrawable = new WiFiDrawalbeImp(mContext, ivStatus);
                    }
                } else {
                    signalDrawable = new WiFiDrawalbeImp(mContext, ivStatus);
                }
                break;
            case ConnectivityManager.TYPE_MOBILE:
                tvSignalType.setVisibility(View.VISIBLE);
                ivStatus.setVisibility(View.VISIBLE);
                tvSignalType.setText(UtilHttp.getNetworkClassByType(mContext));
                if (signalDrawable != null) {
                    if (!(signalDrawable instanceof MobileDrawalbeImp)) {
                        signalDrawable.destroy();
                        signalDrawable = new MobileDrawalbeImp(mContext, ivStatus);
                    }
                } else {
                    signalDrawable = new MobileDrawalbeImp(mContext, ivStatus);
                }
                break;
            case ConnectivityManager.TYPE_ETHERNET:
                tvSignalType.setVisibility(View.GONE);
                ivStatus.setVisibility(View.GONE);
                if (signalDrawable != null) {
                    if (!(signalDrawable instanceof EthernetDrawalbeImp)) {
                        signalDrawable.destroy();
                        signalDrawable = new EthernetDrawalbeImp();
                    }
                } else {
                    signalDrawable = new EthernetDrawalbeImp();
                }
                break;
            default:
                tvSignalType.setVisibility(View.GONE);
                ivStatus.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (signalDrawable != null) {
            signalDrawable.destroy();
        }
    }

}
