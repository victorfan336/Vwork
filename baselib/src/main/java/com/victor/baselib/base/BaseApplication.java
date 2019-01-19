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

    private static Application mContext;

    public static Application getContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = this;
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
    }
}
