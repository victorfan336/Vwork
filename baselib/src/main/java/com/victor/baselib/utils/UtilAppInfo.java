package com.victor.baselib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

/**
 * Created by yuchuan
 * DATE 3/24/16
 * TIME 16:09
 */
public class UtilAppInfo {

    public static String getMetaDataString(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        XLog.e(XLog.TAG_GU, "channel = " + resultData);
        return resultData;
    }

    public static int getMetaDataInt(Context con, String key) {
        try {
            android.content.pm.ApplicationInfo mAppInfo = con
                    .getPackageManager().getApplicationInfo(
                            con.getPackageName(), PackageManager.GET_META_DATA);
            return mAppInfo.metaData.getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取标题栏高度
     *
     * @param window 窗口对象
     *
     * @return 标题栏的高度
     * 开发者: 伍孝权
     * 时间：2015-7-22 下午5:22:14
     */
    public static int getTitleBarHeight(Window window) {
        if (window == null) {
            return 0;
        }
        final Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * to check weather should show or hide the soft-keyboard
     * <p>
     * 2014-11-14
     *
     * @param v     the view that get the current focus
     * @param event
     *
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

}
