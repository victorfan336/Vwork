package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class TVMdpi extends AbstractSize {

    @Override
    public int getWidth() {
        return 720;
    }

    @Override
    public int getHeight() {
        return 1280;
    }

    @Override
    public int getDPI() {
        return DisplayMetrics.DENSITY_MEDIUM;
    }

    @Override
    public String getFileRoot() {
        return "C:\\dimens\\" + "values-tv-1280x720\\";
    }

}