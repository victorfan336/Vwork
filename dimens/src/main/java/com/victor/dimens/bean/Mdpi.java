package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class Mdpi extends AbstractSize {

    @Override
    public int getWidth() {
        return 320;
    }

    @Override
    public int getHeight() {
        return 480;
    }

    @Override
    public int getDPI() {
        return DisplayMetrics.DENSITY_MEDIUM;
    }
}