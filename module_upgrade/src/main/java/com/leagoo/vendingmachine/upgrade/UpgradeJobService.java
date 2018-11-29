package com.leagoo.vendingmachine.upgrade;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author fanwentao
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class UpgradeJobService  extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        doJob();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void doJob(){
        UpgradeManager.getInstance(this.getApplicationContext()).checkVersionLoad();
    }
}
