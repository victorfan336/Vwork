package com.leagoo.vendingmachine.upgrade;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.leagoo.vendingmachine.upgrade.download.DaoDownloadManager;
import com.leagoo.vendingmachine.upgrade.download.DownloadInfo;
import com.leagoo.vendingmachine.upgrade.download.UpgradeMsgBean;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author fanwentao
 * @Description 升级apk安装信息管理
 * @date 2018/4/18
 */

public class UpgradeApkInstallUtil {

    private final static String TAG_GU = "Upgrade";
    private final static String APP_ROOT = "vendingmachine";
    private static final String APK_NAME = "vendingmachine.apk";

    private String rootPath;
    private String filePath;
    private String fileName;
    private static UpgradeApkInstallUtil INSTANCE;


    private UpgradeApkInstallUtil(Context context) {
        rootPath = getApkStorePath(context);
        fileName = APK_NAME;
        filePath = rootPath + File.separator + fileName;
    }

    /**
     * 创建APK安装信息单例
     *
     * @param context 上下文
     * @return UpgradeApkInstallUtil
     */
    public static UpgradeApkInstallUtil getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UpgradeApkInstallUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UpgradeApkInstallUtil(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取apk文件名称
     *
     * @return
     */
    public String getApkName() {
        return fileName;
    }

    /**
     * 获取apk文件路径
     *
     * @param context
     * @return
     */
    public String getApkStorePath(Context context) {
        String savePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            savePath = context.getExternalFilesDir(null).getAbsolutePath() + File.separator + APP_ROOT;
        } else {
            savePath = context.getFilesDir().getAbsolutePath() + File.separator + APP_ROOT;
        }
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return savePath;
    }

    /**
     * 判断apk是否下载完成
     *
     * @return
     */
    public boolean isFileDonwloadFinished(Context context) {
        File apkRootPath = new File(rootPath);
        File apkFile = new File(rootPath, fileName);
        DownloadInfo downLoadBean = DaoDownloadManager.getInstance(context).getInfos(fileName);
        if (!apkRootPath.exists() || !apkFile.exists() || downLoadBean.getCompleteSize() == 0) {
            return false;
        }

        if (downLoadBean != null) {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(apkFile);
                Log.e("victor", "APK文件大小:" + inputStream.available()
                        + ", 数据库存在apk已下载大小:" + downLoadBean.getCompleteSize()
                        + "， apk文件大小:" + downLoadBean.getFileSize());
                if (inputStream.available() == downLoadBean.getFileSize()
                        && downLoadBean.getFileSize() == downLoadBean.getCompleteSize()) {
                    Log.e("victor", "文件已下载，可以直接安装");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }

    /**
     * 安装apk，同时校验MD5值
     */
    public void installApk(Context context) {
        File apkfile = new File(filePath);
        if (!apkfile.exists()) {
            return;
        }
        UpgradeMsgBean upgradeMsgBean = UpgradeMsgUtils.getUpgradeMsg(context);
        // 校验APK MD5值，判断是否非法文件
        /*if (upgradeMsgBean != null && !TextUtils.isEmpty(upgradeMsgBean.getFileMd5())
            && !upgradeMsgBean.getFileMd5().equalsIgnoreCase("null")) {
            try {
                String fileMd5 = Tools.getFileMD5(apkfile);
                if (!upgradeMsgBean.getFileMd5().equalsIgnoreCase(fileMd5)) {
                    Log.e(TAG_GU, "apk md5 校验失败， md5不正确");
                    apkfile.delete();
                    DaoDownloadManager.getInstance(context).delete(apkfile.getName());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG_GU, "apk md5 校验失败： " + e.getMessage());
            }
        }*/
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", apkfile);
//            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        context.startActivity(intent);
        install(apkfile.getAbsolutePath());
        Log.e(TAG_GU, "apk正在安装...");
    }

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     *
     * @param apkPath 要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public boolean install(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Log.d("TAG", "install msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }

}
