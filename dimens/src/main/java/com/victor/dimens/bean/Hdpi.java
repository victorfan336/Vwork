package com.victor.dimens.bean;

import android.util.DisplayMetrics;

public class Hdpi extends AbstractSize {

    @Override
    public int getWidth() {
        return 480;
    }

    @Override
    public int getHeight() {
        return 800;
    }

    @Override
    public int getDPI() {
        return DisplayMetrics.DENSITY_HIGH;
    }
}