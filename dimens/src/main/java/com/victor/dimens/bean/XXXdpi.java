package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class XXXdpi extends AbstractSize {

    @Override
    public int getWidth() {
        return 1440;
    }

    @Override
    public int getHeight() {
        return 2560;
    }

    @Override
    public int getDPI() {
        return DisplayMetrics.DENSITY_XXXHIGH;
    }
}