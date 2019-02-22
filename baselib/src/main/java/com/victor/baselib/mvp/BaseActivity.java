package com.victor.baselib.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.noober.background.BackgroundLibrary;

public abstract class BaseActivity extends com.victor.baselib.base.BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 需要在其他库调用layoutInflater.setFactory之后调用
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
    }
}
