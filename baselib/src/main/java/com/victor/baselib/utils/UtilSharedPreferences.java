package com.victor.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yuchuan
 * DATE 3/22/16
 * TIME 12:25
 */
public class UtilSharedPreferences {

    public static final String KEY_CLASSIFICATION_STATE = "key_classification";

    public static void saveStringData(Context context, String key, String data) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public static String getStringData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void saveBooleanData(Context context, String key, boolean data) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, data);
        editor.apply();
    }

    public static boolean getBooleanData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void saveIntData(Context context, String key, int data) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, data);
        editor.commit();
    }

    public static int getIntData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }

    public static int getIntData(Context context, String key, int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }

    public static void saveLongData(Context context, String key, long data) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, data);
        editor.apply();
    }

    public static long getLongData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(ConstantUtils.LgInfoUpload.SHARE_KEY, Context.MODE_PRIVATE);
        return preferences.getLong(key, -1);
    }

}
