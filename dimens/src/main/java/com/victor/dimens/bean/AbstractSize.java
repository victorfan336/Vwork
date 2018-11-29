package com.victor.dimens.bean;


public abstract class AbstractSize {

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getDPI();

    public String getFileName() {
        return "dimens.xml";
    }

    public String getFileRoot() {
        return "C:\\dimens\\" + "values-sw" + getDPI() + "dp\\";
    }
    /**
     * 屏幕密度
     * （1）dpi = √（长度像素数² + 宽度像素数²） / 屏幕对角线英寸数
     * （2）density = dpi / 160
     *
     * @return
     */
    public float getDensity() {
        return getDPI() * 1.0f / 160;
    }
}
