package com.leagoo.vendingmachine.upgrade;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.leagoo.vendingmachine.upgrade.download.DaoDownloadManager;
import com.leagoo.vendingmachine.upgrade.download.DownState;
import com.leagoo.vendingmachine.upgrade.download.DownTask;
import com.leagoo.vendingmachine.upgrade.download.DownloadInfo;
import com.leagoo.vendingmachine.upgrade.download.DownloadManager;
import com.leagoo.vendingmachine.upgrade.download.UpgradeMsgBean;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author fanwentao
 * @Description 负责下载apk,通知栏显示下载进度
 */

public class DownloadIntentService extends IntentService {
    private static final String TAG_GU = "DownloadIntentService";
//    private Notification mNotification;
    private static final int NOTIFY_ID = 1000;
    /**
     * 数据下载最大值
     */
    private static final int TYPE_MAXNUM = 100;
    private NotificationManager mNotificationManager;
    /**
     * 下载包安装路径
     */
    private String rootPath;
    private String fileName;
    private UpgradeMsgBean upgradeMsgBean;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadIntentService(String name) {
        super(name);
    }

    public DownloadIntentService() {
        super("download");
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DownloadInfo bean = (DownloadInfo) msg.obj;
            if (bean == null) {
                Log.e("victor", "bean == null");
                return;
            }
            long fileSize = bean.getFileSize();
            long completeSize = bean.getCompleteSize();
            if (fileSize <= 0) {
                return;
            }
            int pecent = (int) (100 * completeSize / fileSize);
            switch (msg.what) {
                case DownState.FINISH:
                    // 下载完毕
                    UpgradeApkInstallUtil.getInstance(getApplicationContext()).installApk(getApplicationContext());
                    cancelNotification();
                    stopSelf();// 停掉服务自身
                    Log.e("victor", "下载完成后");
                    break;
                case DownState.DOWNLOAD:
//                    RemoteViews contentview = mNotification.contentView;
//                    contentview.setTextViewText(R.id.version_notification_text, pecent + "%");
//                    contentview.setProgressBar(R.id.version_notification_progress, TYPE_MAXNUM, pecent, false);
//                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
                case DownState.PAUSE:
                    break;
                case DownState.FILEERROR:
                case DownState.NETERROR:
                    cancelNotification();
                    stopSelf();// 停掉服务自身
                    Log.e("victor", "下载出错");
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        rootPath = UpgradeApkInstallUtil.getInstance(this).getApkStorePath(this);
        fileName = UpgradeApkInstallUtil.getInstance(this).getApkName();
        upgradeMsgBean = UpgradeMsgUtils.getUpgradeMsg(this);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("tag", "onHandleIntent");
        // 判断是否之前就已经下载完成
        if (UpgradeApkInstallUtil.getInstance(this).isFileDonwloadFinished(this)) {
            UpgradeApkInstallUtil.getInstance(this).installApk(this);
            return;
        }
        if (intent != null) {
            String apkUrl = intent.getStringExtra("apkUrl");
            if (TextUtils.isEmpty(apkUrl) || !Tools.isNetAvailable(this)) {
                return;
            }
            downloadFile(rootPath + File.separator + fileName);
        }
    }

    /**
     * 处理下载前的逻辑判断
     */
    private void downloadFile(String filePath) {
        DownloadInfo downLoadBean = DaoDownloadManager.getInstance(this).getInfos(fileName);
        File apkFile = new File(filePath);
        if (downLoadBean == null) {
            // 清除未知的同名apk包
            if (apkFile.exists()) {
                apkFile.delete();
                Log.e(TAG_GU, "DownloadIntentService 存在老版本apk，删除，重新下载");
            }
            startDownloadData(null, this, mHandler, upgradeMsgBean);
        } else { // 已经下载过了
            int state = downLoadBean.getDownState();
            int currVersionCode = Tools.getVersionCode(this);
            if (downLoadBean.getFileSize()  > 0) {
                Log.e(TAG_GU, "DownloadIntentService 数据库已存在该版本地址信息，上次下载:"
                        + downLoadBean.getCompleteSize() * 100 / downLoadBean.getFileSize() + " % ");
            }
            // 数据库版本信息存在，但是apk已经不存在了，则清除数据库信息，重新下载最新版本
            if (!apkFile.exists()) {
                Log.e(TAG_GU, "DownloadIntentService 数据库存在apk信息，但是apk不存在，重新下载");
                DaoDownloadManager.getInstance(this).delete(fileName);
                startDownloadData(null, this, mHandler, upgradeMsgBean);
                return;
            } else
            // 判断数据库中的版本信息，跟当前需要下载的信息比较
            if (upgradeMsgBean != null && upgradeMsgBean.getVersionCode() > downLoadBean.getVersionCode()) {
                DaoDownloadManager.getInstance(this).delete(fileName);
                apkFile.delete();
                Log.e(TAG_GU, "DownloadIntentService 数据库存版本信息过旧versionCode="
                        + downLoadBean.getVersionCode()  + "，下载新版本versionCode=" + upgradeMsgBean.getVersionCode()
                        + "，当前版本versionCode=" + currVersionCode);
            }
            // 判断是否是之前未下载完成的文件
            try {
                FileInputStream inputStream = new FileInputStream(apkFile);
                Log.e(TAG_GU, "APK文件大小:" + inputStream.available()
                        + ", 数据库存在apk已下载大小:" + downLoadBean.getCompleteSize()
                        + ", 数据库存在apk文件大小:" + downLoadBean.getFileSize());
                if (inputStream.available() != downLoadBean.getCompleteSize()) {
//                    apkFile.delete();
//                    downLoadBean.setCompleteSize(0);
                    Log.e(TAG_GU, "APK文件大小和数据库存在apk已下载大小不符，进行校正");
                    downLoadBean.setCompleteSize(inputStream.available());
                }
                inputStream.close();
            } catch (Exception e) {

            }
            if (upgradeMsgBean != null && upgradeMsgBean.getVersionCode() > currVersionCode) {
                switch (state) {
                    case DownState.FINISH:
                        UpgradeApkInstallUtil.getInstance(this).installApk(this);
                        break;
                    case DownState.PAUSE:
                        break;
                    case DownState.DOWNLOAD:
                    case DownState.FILEERROR:
                    case DownState.NETERROR:
                    case DownState.LINKING:
                    default:
                        if (Tools.isNetAvailable(this)) {
                            startDownloadData(downLoadBean, this, mHandler, upgradeMsgBean);
                        }
                        break;
                }
            } else {
                DaoDownloadManager.getInstance(this).delete(fileName);
                if (apkFile.exists()) {
                    apkFile.delete();
                }
                UpgradeMsgUtils.clearUpdateMsg(this);
                Log.e(TAG_GU, "DownloadIntentService 版本升级消息版本过旧，删除apk和推送消息");
            }
        }
    }

    /**
     * 启动下载线程
     * @param downLoadBean
     * @param context
     * @param handler
     * @param upgradeMsgBean
     */
    public void startDownloadData(DownloadInfo downLoadBean, Context context, Handler handler, UpgradeMsgBean upgradeMsgBean) {
        setUpNotification(0);
        if (downLoadBean != null) {
            downLoadBean.setDownState(DownState.WAITING);
        } else {
            downLoadBean = new DownloadInfo();
            downLoadBean.setUrl(upgradeMsgBean.getDownloadUrl());
            downLoadBean.setCompleteSize(0);
            downLoadBean.setVersionCode(upgradeMsgBean.getVersionCode());
            downLoadBean.setVersionName(upgradeMsgBean.getVersionName());
            downLoadBean.setDownState(DownState.WAITING);
            downLoadBean.setFileName(fileName);

            downLoadBean.setDownPath(rootPath);
        }
        DownTask task = new DownTask(downLoadBean, context, handler);
        DownloadManager.getInstance().insertTask(task);
        DaoDownloadManager.getInstance(context).saveInfos(downLoadBean);
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFY_ID);
        }
    }

    /**
     * 创建通知
     */
    private void setUpNotification(int progress) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(),
                getString(R.string.default_notification_channel_id));
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_version))
                .setSmallIcon(R.drawable.icon_particulars_nor).build();
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_version);
        mRemoteViews.setTextViewText(R.id.version_notification_title, getString(R.string.is_downing));
        Intent intent = new Intent(this, Activity.class);
        mBuilder.setContent(mRemoteViews)
                .setContentIntent(PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setTicker(getString(R.string.is_downing));
        mBuilder.setProgress(100, progress, true);
//        mNotification = mBuilder.build();
//        mNotification.contentView = mRemoteViews;
    }

    @Override
    public void onDestroy() {
        Log.e("tag", "onDestroy");
        DownloadManager.getInstance().removeTask(upgradeMsgBean.getDownloadUrl());
        super.onDestroy();
    }
}
