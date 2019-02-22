package com.victor.baselib.base;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.baselib.R;
import com.victor.baselib.utils.ToastUtil;
import com.victor.baselib.utils.Tools;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private TextView tvTitle;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        setStatusBarTextLight(false);
        setTitle(getTitleRes());
        initViews();
        initData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract @StringRes int getTitleRes();

    protected abstract void initViews();

    protected abstract void initData(Bundle savedInstanceState);

    public void setTitle(@StringRes int titleRes) {
        tvTitle = findViewById(R.id.title_name);
        if (tvTitle != null && titleRes != 0) {
            tvTitle.setText(titleRes);
        }
    }


    public void startActivity(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void showToast(String content) {
        if (!TextUtils.isEmpty(content)) {
            ToastUtil.getInstance().showToast(getApplicationContext(), content);
        }
    }

    public void showToast(@StringRes int msg) {
        if (msg != 0) {
            ToastUtil.getInstance().showToast(getApplicationContext(), msg);
        }
    }

    /**
     * 设置状态栏问题颜色（黑/白）
     *
     * @param isLight true:白色 false:黑色
     */
    protected void setStatusBarTextLight(boolean isLight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | (isLight ? View.SYSTEM_UI_FLAG_LAYOUT_STABLE : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
        }
    }

    public void enableBackButton(int resId) {
        ImageView backView = findViewById(R.id.title_back);
        if (backView != null) {
            backView.setImageResource(resId);
            backView.setVisibility(View.VISIBLE);
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void enableMenu(final Class clazzType, int resId) {
        ImageView ivMenu = findViewById(R.id.title_menu);
        if (ivMenu != null) {
            ivMenu.setImageResource(resId);
            ivMenu.setVisibility(View.VISIBLE);
            ivMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), clazzType));
                }
            });
        }
    }

    public void disableMenu() {
        ImageView ivMenu = findViewById(R.id.title_menu);
        if (ivMenu != null) {
            ivMenu.setVisibility(View.GONE);
        }
    }

    public void showLoading(boolean show) {
        View loadingView = findViewById(R.id.title_logding);
        if (loadingView != null) {
            if (show) {
                loadingView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
