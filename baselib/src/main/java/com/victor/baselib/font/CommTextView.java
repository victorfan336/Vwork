package com.victor.baselib.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * @author fanwentao
 * @Description
 * @date 2018/7/23
 */
public class CommTextView extends android.support.v7.widget.AppCompatTextView {


    public CommTextView(Context context) {
        super(context);
    }

    public CommTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }


    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(createTypeface(getContext(),"fzkt.ttf"), style);
    }

}
