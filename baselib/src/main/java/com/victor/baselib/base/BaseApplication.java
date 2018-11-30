package com.victor.baselib.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;


/**
 * @author fanwentao
 * @Description
 * @date 2018/7/23
 */
public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
    }
}
