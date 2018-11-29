package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class Xdpi extends AbstractSize {

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
        return DisplayMetrics.DENSITY_XHIGH;
    }
}