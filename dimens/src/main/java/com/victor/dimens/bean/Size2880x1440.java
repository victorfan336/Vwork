package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class Size2880x1440 extends AbstractSize {

    @Override
    public int getWidth() {
        return 1440;
    }

    @Override
    public int getHeight() {
        return 2880;
    }

    @Override
    public int getDPI() {
        return DisplayMetrics.DENSITY_XXXHIGH;
    }

    public String getFileRoot() {
        return "C:\\dimens\\" + "values-2880x1440\\";
    }
}