package com.victor.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.baselib.base.BaseActivity;

import butterknife.BindView;

/**
 * @author fanwentao
 * @Description
 * @date 2018/6/26
 */
@Route(path = "/coordinator/collapsing")
public class CollapsingActivity extends BaseActivity {

    @BindView(R2.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_collapsing;
    }

    @Override
    protected int getTitleRes() {
        return R.string.collapsingActivity;
    }

    @Override
    protected void initViews() {
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

}
