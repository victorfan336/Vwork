package com.victor.playandroid.view;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.baselib.mvp.BaseActivity;
import com.victor.playandroid.R;

@Route(path = "/customview/list")
public class CustomViewListActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_view;
    }

    @Override
    protected int getTitleRes() {
        return R.string.app_name;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

}
