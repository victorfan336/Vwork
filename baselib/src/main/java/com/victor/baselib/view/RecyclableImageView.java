package com.leagoo.vendingmachine.baselib.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author fanwentao
 * @Description
 * @date 2018/7/28
 */
public class RecyclableImageView extends android.support.v7.widget.AppCompatImageView {
    public RecyclableImageView(Context context) {
        super(context);
    }

    public RecyclableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}
