package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class TVhdpi extends AbstractSize {

    @Override
    public int getWidth() {
        return 1080;
    }

    @Override
    public int getHeight() {
        return 1920;
    }

    @Override
    public int getDPI() {
        return DisplayMetrics.DENSITY_HIGH;
    }

    @Override
    public String getFileRoot() {
        return "C:\\dimens\\" + "values-tv-1920x1080\\";
    }

}