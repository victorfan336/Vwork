package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class XXdpi extends AbstractSize {

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
        return DisplayMetrics.DENSITY_XXHIGH;
    }
}