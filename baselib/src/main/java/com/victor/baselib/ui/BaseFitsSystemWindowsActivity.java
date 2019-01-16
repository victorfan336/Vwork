package com.victor.baselib.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.victor.baselib.base.BaseActivity;

/**
 * Created by ${victor fan} on 2018/5/23 0023.
 */
public abstract class BaseFitsSystemWindowsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHeight(mToolbar);
    }

    public void setHeight(View view) {
        // 获取actionbar的高度
        TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        float height = actionbarSizeTypedArray.getDimension(0, 0);
        // ToolBar的top值
        if (view != null) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            double statusBarHeight = getStatusBarHeight(this);
            lp.height = (int) (statusBarHeight + height);
            view.setPadding(0, (int) statusBarHeight, 0, 0);
//            mToolbar.setLayoutParams(lp);
        }
    }

    private double getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
