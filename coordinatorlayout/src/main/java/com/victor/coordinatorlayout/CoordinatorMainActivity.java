package com.victor.coordinatorlayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.victor.baselib.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class CoordinatorMainActivity extends BaseActivity {


    @BindView(R2.id.action_bar)
    Button button1;
    @BindView(R2.id.tab_layout)
    Button tabLayout;
    @BindView(R2.id.collapsing)
    Button collapsingView;
    @BindView(R2.id.rotate)
    Button rotate;
    @BindView(R2.id.uc_head)
    Button ucHead;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    protected String setTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R2.id.action_bar)
    public void onActionBarClicked(View view) {
        startActivity(CoordinatorActionBarActivity.class);
    }

    @OnClick(R2.id.tab_layout)
    public void onTabClicked(View view) {
        startActivity(CoordinatorTabActivity.class);
    }

    @OnClick(R2.id.collapsing)
    public void onCollapsingClicked(View view) {
        startActivity(CollapsingActivity.class);
    }

    @OnClick(R2.id.rotate)
    public void onRoatateClicked() {
        startActivity(TitileActionBarActivity.class);
    }

    @OnClick(R2.id.uc_head)
    public void onUcHeadClicked() {
        startActivity(UcHeadActivity.class);
    }



}
