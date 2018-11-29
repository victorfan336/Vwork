package com.victor.baselib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.victor.baselib.net.imp.ImpRequest;
import com.victor.baselib.ui.WebActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Administrator on 2016/4/7.
 */
public class Tools {
    private static String url;
    private static String des;
    private static Context mContext;

    public static void startToShare(Context context) {
        mContext = context;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, des + "\n "+ url);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public static void startToShare(Context context,String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        context.startActivity(intent);
    }

    public static boolean isPortrait(Context context) {
//        boolean isPortrait = context.getResources().getBoolean(R.bool.is_portrait);
        return true;
    }



    /**
     * 判断是否是平板
     *
     * @param con
     * @return
     */
    public static boolean isTablet(Context con) {
        return (con.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    public static String getMetaDataString(Context con,String key) {
        return UtilAppInfo.getMetaDataString(con, key);
    }

    /**
     * 获取标题栏高度
     * @param window 窗口对象
     * @return 标题栏的高度
     * 开发者: 伍孝权
     * 时间：2015-7-22 下午5:22:14
     */
    public static int getTitlebarHeight(Window window) {
        if (window == null) {
            return 0;
        }
        final Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 隐藏键盘
     *
     * @param context
     * @param view
     */
    static public void hideSoftKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示键盘
     * @param context
     * @param view
     */
    public static void showInputSoft(Context context, View view) {
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }
    /**
     * to check weather should show or hide the soft-keyboard
     *
     * 2014-11-14
     * @author gosund-huguoneng
     * @param v   the view that get the current focus
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();

            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 默认弹出所有可用浏览器来显示
     * @param url
     */
    public static void showWebDetailDf(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * 使用内嵌浏览器
     * @param title 标题没有时可以传Null
     * @param url 链接
     */
    public static void showWebInner(Context context, String title, String url) {
        if (!TextUtils.isEmpty(url) && (url.contains("http://") || url.contains("https://"))) {
            Intent intent = new Intent(context, WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("url", url);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    public static int string2Int(String value, int dfValue) {
        int result = dfValue;
        try {
            result = Integer.parseInt(value);
        } catch (Exception ex) {
            XLog.e(XLog.TAG_GU, value + " can't cast a int value !");
        }
        return result;
    }

    public static boolean writeResponseBodyToDisk(InputStream inputStream, String fileSavePath, final ImpRequest request) {
        OutputStream outputStream = null;
        if (inputStream == null || TextUtils.isEmpty(fileSavePath)) {
            return false;
        }
        try {
            File futureStudioIconFile = new File(fileSavePath);
            if (!futureStudioIconFile.exists()) {
                if (!futureStudioIconFile.createNewFile()) {
                    if (request != null) {
                        request.onFailure();
                    }
                    return false;
                }
            } else {
                if (request != null) {
                    request.onFailure();
                }
                return true;
            }
            byte[] fileReader = new byte[4096];

            outputStream = new FileOutputStream(futureStudioIconFile);

            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
            }
            outputStream.flush();
            if (request != null) {
                request.onSuccess("success");
            }
            return true;
        } catch (Exception e) {
            if (request != null) {
                request.onFailure();
            }
            return false;
        } finally {
            UtilIO.closeQuietly(inputStream);
            UtilIO.closeQuietly(outputStream);
        }
    }

    /**
     * 利用反射获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 跳转拨号界面
     * @param context
     * @param phoneNumber
     */
    public static void gotoDialerView(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到邮箱
     * @param context
     * @param mailAdress 接收邮箱地址
     * @param subject 主题
     * @param content 内容
     */
    public static void gotoEmail(Context context, String mailAdress, String subject, String content) {
        Uri uri = Uri.parse("mailto:" + mailAdress);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, content); // 正文
        context.startActivity(Intent.createChooser(intent, ""));
    }

    public static int parseString2Int(String source, int df) {
        int result = df;
        try {
            result = Integer.parseInt(source);
        } catch (Exception ex) {
            result = df;
        }
        return result;
    }

    public static long parseString2Long(String source, long df) {
        long result = df;
        try {
            result = Long.parseLong(source);
        } catch (Exception ex) {
            result = df;
        }
        return result;
    }

    /**
     * get file md5
     * @param file
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getFileMD5(File file) throws NoSuchAlgorithmException, IOException {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        digest = MessageDigest.getInstance("MD5");
        in = new FileInputStream(file);
        while ((len = in.read(buffer, 0, 1024)) != -1) {
            digest.update(buffer, 0, len);
        }
        in.close();
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String md5 = bigInt.toString(16);
        XLog.e(XLog.TAG_GU, " file md5 = " + md5);
        return md5;
    }

}
