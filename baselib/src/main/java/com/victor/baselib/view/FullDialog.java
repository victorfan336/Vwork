package com.victor.baselib.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.victor.baselib.R;
import com.victor.baselib.utils.UtilDensity;


/**
 * 全屏对话框
 * @author fanwentao
 * @Description
 * @date 2018/7/27
 */
public class FullDialog extends Dialog {

    public FullDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setOwnerActivity((Activity) context);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = (int) (UtilDensity.getScreenWidth(getContext()) - getContext().getResources().getDimension(R.dimen.x144));
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
