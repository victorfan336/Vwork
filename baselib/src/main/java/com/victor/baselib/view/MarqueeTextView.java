package com.victor.baselib.view;

import android.content.Context;
import android.util.AttributeSet;

import com.victor.baselib.font.CommTextView;


/**
 * TextView跑马灯效果
 * <code>
     android:ellipsize="marquee"
     android:focusable="true"
     android:focusableInTouchMode="true"
     android:marqueeRepeatLimit="marquee_forever"
     android:scrollHorizontally="true"
     android:singleLine="true"
 * </code>
 * @author fanwentao
 * @Description
 * @date 2018/7/31
 */
public class MarqueeTextView extends CommTextView {

    private boolean focus = true;

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFocuse(boolean focus) {
        this.focus = focus;
    }

    /**强制被选中*/
    @Override
    public boolean isFocused() {
        return focus;
    }

}
