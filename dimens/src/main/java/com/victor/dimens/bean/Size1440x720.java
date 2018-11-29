package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class Size1440x720 extends AbstractSize {

    @Override
    public int getWidth() {
        return 720;
    }

    @Override
    public int getHeight() {
        return 1440;
    }

    @Override
    public int getDPI() {
        return DisplayMetrics.DENSITY_XHIGH;
    }

    public String getFileRoot() {
        return "C:\\dimens\\" + "values-1440x720\\";
    }
}