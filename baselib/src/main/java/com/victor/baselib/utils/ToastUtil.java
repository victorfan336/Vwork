package com.victor.baselib.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.victor.baselib.R;


public class ToastUtil {

    private static final String THREAD_MAIN = "main";
    private Context mContext;
    private String sContent;
    private volatile Toast sToast;
    private static ToastUtil INSTANCE;

    private ToastUtil() {
    }

    public static ToastUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ToastUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ToastUtil();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 在主线程中做操作
     */
    private volatile Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            toast(mContext, sContent);
            sToast.show();
        }
    };

    public void showToast(@NonNull Context context, int strResId) {
        showToast(context, context.getString(strResId));
    }

    public void showToast(@NonNull Context context, String content) {
        String name = Thread.currentThread().getName();
        // 判断是否是主线程，如果不是通过handler
        if (!name.equals(THREAD_MAIN)) {
            mHandler.sendEmptyMessage(0);
            mContext = context;
            sContent = content;
        } else {
            toast(context, content);
            sToast.show();
        }
    }

    private void toast(Context context, String content) {
        if (context == null) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            View rootView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            sToast.setView(rootView);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        }
        sToast.setText(content);
    }

}

