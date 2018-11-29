package com.leagoo.vendingmachine.upgrade;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.leagoo.vendingmachine.upgrade.download.DaoDownloadManager;
import com.leagoo.vendingmachine.upgrade.download.UpgradeMsgBean;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

/**
 *  管理apk升级
 * @author: fanwentao
 * @date: 2018/7/3
 * @deprecated 适用方法：
 *
 */
public class UpgradeManager {
    private static final String TAG_GU = "Upgrade";
    private static final long CHECK_VERSION_MIN_MUN_DAYS = 10;
    private static final int CHECK_VERSION_JOB_ID = 1000;
    private static final String KEY_LAST_SCHEDULE_JOB_TIMESTAMP = "last_schedule_time";
    private static final long CHECK_VERSION_INTERVAL = 3600000 * 24 * 1;

    /**
     * 有最新版本
     */
    public final static String UPGRADE_NEW = "1";

    private Context mContext;
    private Map<String, String> mMap;
    private static UpgradeManager mUpgradeManager;
    private AlertDialog alertDialog;

    /**
     * 创建管理器单例
     *
     * @param context 上下文
     * @return UpgradeManager
     */
    public static UpgradeManager getInstance(Context context) {
        if (mUpgradeManager == null) {
            synchronized (UpgradeManager.class) {
                if (mUpgradeManager == null) {
                    mUpgradeManager = new UpgradeManager(context.getApplicationContext());
                }
            }
        }
        return mUpgradeManager;
    }

    private UpgradeManager(Context context) {
        mContext = context;
        initChannelNotification();
    }

    /**
     * 添加channel Id
     */
    public void initChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Resources resources = mContext.getResources();
            // Create channel to show notifications.
            NotificationManager notificationManager =
                    mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(
                    new NotificationChannel(resources.getString(R.string.default_notification_channel_id),
                            resources.getString(R.string.default_notification_channel_name), NotificationManager.IMPORTANCE_LOW));
            notificationManager.createNotificationChannel(
                    new NotificationChannel(resources.getString(R.string.default_notification_update_channel_id),
                            resources.getString(R.string.default_notification_channel_update_name), NotificationManager.IMPORTANCE_LOW));

        }
    }

    /**
     * 主动拉取更新
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduleUpgradeJob() {
        SharedPreferences preferences = UpgradeMsgUtils.getSp(mContext);
        long lastTime = preferences.getLong(KEY_LAST_SCHEDULE_JOB_TIMESTAMP, 0);
//        if (System.currentTimeMillis() - lastTime < CHECK_VERSION_INTERVAL){
//            return;
//        }
        preferences.edit().putLong(KEY_LAST_SCHEDULE_JOB_TIMESTAMP, System.currentTimeMillis()).apply();

        JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(CHECK_VERSION_JOB_ID, new ComponentName(mContext, UpgradeJobService.class));
        //执行的最小延迟时间
        builder.setMinimumLatency(TimeUnit.SECONDS.toMillis(CHECK_VERSION_MIN_MUN_DAYS));
        //执行的最长延时时间
        builder.setOverrideDeadline(TimeUnit.DAYS.toMillis(10));
        //任意网络
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //线性重试方案
        builder.setBackoffCriteria(TimeUnit.MINUTES.toMillis(30), JobInfo.BACKOFF_POLICY_LINEAR);
//        builder.setRequiresCharging(false); // 未充电状态
        jobScheduler.cancel(CHECK_VERSION_JOB_ID);
        jobScheduler.schedule(builder.build());
    }

    /**
     * 仅sevice中主动查询是否需要更新版本
     */
    public void checkVersionLoad() {
        // test
        UpgradeMsgBean updateInfo = new UpgradeMsgBean();
        updateInfo.setNotificationTitle("版本更新");
        updateInfo.setVersionCode(1001);
        updateInfo.setVersionName("v1.1");
        updateInfo.setDownloadUrl("https://pro-app-qn.fir.im/8bc631dc74b03384807366e823ea4405da7f8920.apk?attname=ucstar_std_0511_test_107_604.apk_6.0.4.apk&e=1530862855&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:Nfucw5EtveNhsotwY_XMBMVWOVY=");
        updateInfo.setConfirm("更新版本");
        updateInfo.setCancel("以后更新");
        updateInfo.setMsgType("1");
        updateInfo.setMsgType("4");
//        saveUpdateInfo(updateInfo.toString());
//        EventBus.getDefault().post(new UpdateEvent());
        /*checkVersionLoad(new ImpRequest() {
            @Override
            public void onSuccess(final Object info) {
                if (info != null && info instanceof UpdateInfo) {
                    XLog.d(XLog.TAG_GU, info.toString());
                    final UpdateInfo updateInfo = (UpdateInfo) info;
                    if (UPGRADE_NEW.equals(updateInfo.getIsUpgrade())) {
                        new Handler(mContext.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                saveUpdateInfo(updateInfoConvertToGoogleMsg((UpdateInfo) info));
                                EventBus.getDefault().post(new UpdateEvent());
                            }
                        });

                    } else {
                        XLog.d(XLog.TAG_GU, "已是最新版本");
                    }
                }
            }

            @Override
            public void onFailure() {
                XLog.d(XLog.TAG_GU, "checkVersionLoad onFailure");
            }
        });*/
    }

    private void downloadApk(String apkUrl) {
        Log.e(TAG_GU, "开始下载");
        Intent intent = new Intent(mContext, DownloadIntentService.class);
        intent.putExtra("apkUrl", apkUrl);
        mContext.startService(intent);
    }

    /**
     * 检查是否有新版本
     *
     */
    public void checkUpdate() {
        // 判断是否有版本更新消息
        if (UpgradeMsgUtils.isHasUpdateInfo(mContext)) {
            UpgradeMsgBean upgradeMsgBean = UpgradeMsgUtils.getUpgradeMsg(mContext);
            if (upgradeMsgBean == null) {
                UpgradeMsgUtils.clearUpdateMsg(mContext);
                return;
            }
            // 没网且没有下载完，则不显示升级框
            if (!Tools.isNetAvailable(mContext) && !UpgradeApkInstallUtil.getInstance(mContext).isFileDonwloadFinished(mContext)) {
                return;
            }
            // 判断是否已经安装最新版本
            int versionCode = Tools.getVersionCode(mContext);
            if (versionCode >= upgradeMsgBean.getVersionCode()) {
                UpgradeMsgUtils.clearUpdateMsg(mContext);
                File file = new File(UpgradeApkInstallUtil.getInstance(mContext).getApkStorePath(mContext));
                if (file.exists()) {
                    file.delete();
                    Log.e(TAG_GU, "UpgradeManager 删除apk文件");
                }
                DaoDownloadManager.getInstance(mContext).delete(file.getName());
            }
            downloadApk(upgradeMsgBean.getDownloadUrl());
        }
    }

    public static void main(String... args) {
//        Context context = null;
//        UpgradeMsgUtils.storeUpdateInfo(context, true);
//        UpgradeMsgUtils.storeUpdateMsg(context, "{\n" +
//                "\"msgType\":\"msgType\",\n" +
//                "\"notificationTitle\":\"notificationTitle\",\n" +
//                "\"notificationContent\":\"notificationContent\",\n" +
//                "\"cancel\":\"cancel\",\n" +
//                "\"confirm\":\"confirm\",\n" +
//                "\"notificationLargeIcon\":\"notificationLargeIcon\",\n" +
//                "\"content\":\"content\",\n" +
//                "\"contentPic\":\"contentPic\",\n" +
//                "\"downloadUrl\":\"http://dldir1.qq.com/music/clntupate/QQMusic72282.apk",\n" +
//                "\"fileMd5\":\"fileMd5\",\n" +
//                "\"versionCode\":11,\n" +
//                "\"versionName\":\"versionName\"\n" +
//                "}");
//        UpgradeManager.getInstance(context).checkUpdate();
    }

}
