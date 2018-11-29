package com.victor.baselib.net.signal;

import android.support.annotation.DrawableRes;

/**
 * @author fanwentao
 * @Description
 * @date 2018/8/7
 */
public interface IPhoneSingnalDrawable {

    @DrawableRes int getNoSignalDrawable();
    @DrawableRes int getSignal1Drawable();
    @DrawableRes int getSignal2Drawable();
    @DrawableRes int getSignal3Drawable();
    @DrawableRes int getSignal4Drawable();

    void destroy();
}
