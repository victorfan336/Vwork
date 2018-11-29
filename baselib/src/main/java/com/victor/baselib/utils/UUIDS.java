package com.victor.baselib.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * UUID
 * Created by fanwentao on 2018/2/1.
 */

public class UUIDS {

    private static UUIDS device;
    private Context mContext;
    private final static String DEFAULT_NAME = "system_device_id";
    private final static String DEFAULT_FILE_NAME = "system_device_id";
    private final static String DEFAULT_DEVICE_ID = "dervice_id";
    private final static String FILE_ANDROID = Environment.getExternalStoragePublicDirectory("Android") + File.separator + DEFAULT_FILE_NAME;
    private final static String FILE_DCIM = Environment.getExternalStoragePublicDirectory("DCIM") + File.separator + DEFAULT_FILE_NAME;
    private SharedPreferences preferences;


    public UUIDS(Context context) {
        this.mContext = context;
    }

    public static UUIDS getInstance(Context context) {
        if (device == null) {
            synchronized (UUIDS.class) {
                if (device == null) {
                    device = new UUIDS(context);
                    device.preferences = context.getSharedPreferences(DEFAULT_NAME, Context.MODE_PRIVATE);
                }
            }
        }
        return device;
    }

    /**
     * 获取UUID
     * @return
     */
    public String getUUID() {
        if (preferences == null && mContext != null) {
            preferences = mContext.getSharedPreferences(DEFAULT_NAME, Context.MODE_PRIVATE);
        }
        if (preferences != null) {
            return preferences.getString(DEFAULT_DEVICE_ID, DEFAULT_DEVICE_ID);
        } else {
            return DEFAULT_DEVICE_ID;
        }
    }

    //生成一个128位的唯一标识符
    private String createUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public void check() {
        if (preferences != null) {
            String uuid = preferences.getString(DEFAULT_DEVICE_ID, null);
            if (uuid == null) {
                if (checkAndroidFile() == null && checkDCIMFile() == null) {
                    uuid = createUUID();
                    saveAndroidFile(uuid);
                    saveDCIMFile(uuid);
                    XLog.d(XLog.TAG_GU, "new devices,create only id");
                } else if (checkAndroidFile() == null) {
                    uuid = checkDCIMFile();
                    saveAndroidFile(uuid);
                    XLog.d(XLog.TAG_GU, "Android directory was not found in UUID, from the DCIM directory to take out UUID\n");
                } else if (checkDCIMFile() == null) {
                    uuid = checkAndroidFile();
                    saveDCIMFile(uuid);
                    XLog.d(XLog.TAG_GU, "DCIM directory was not found in UUID, from the Android directory to take out UUID");
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(DEFAULT_DEVICE_ID, uuid);
                editor.commit();
                XLog.d(XLog.TAG_GU, "save uuid SharePref:" + uuid);
            } else {
                if (checkAndroidFile() == null) {
                    saveAndroidFile(uuid);
                }

                if (checkDCIMFile() == null) {
                    saveDCIMFile(uuid);
                }
            }
        }
    }

    private String checkAndroidFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            BufferedReader reader = null;
            try {
                File file = new File(FILE_ANDROID);
                reader = new BufferedReader(new FileReader(file));
                return reader.readLine();
            } catch (Exception e) {
                return null;
            } finally {
                UtilIO.closeQuietly(reader);
            }
        } else {
            return null;
        }
    }

    private void saveAndroidFile(String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                File file = new File(FILE_ANDROID);
                FileWriter writer = new FileWriter(file);
                writer.write(id);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String checkDCIMFile() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M
                && mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            BufferedReader reader = null;
            try {
                File file = new File(FILE_DCIM);
                reader = new BufferedReader(new FileReader(file));
                return reader.readLine();
            } catch (Exception e) {
                return null;
            } finally {
                UtilIO.closeQuietly(reader);
            }
        } else {
            return null;
        }
    }

    private void saveDCIMFile(String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                File file = new File(FILE_DCIM);
                FileWriter writer = new FileWriter(file);
                writer.write(id);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
