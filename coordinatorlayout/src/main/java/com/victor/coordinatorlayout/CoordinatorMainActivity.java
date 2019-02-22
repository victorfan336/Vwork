package com.victor.coordinatorlayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.baselib.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/coordinator/main")
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
    protected int getTitleRes() {
        return R.string.app_name;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R2.id.action_bar)
    public void onActionBarClicked(View view) {
        ARouter.getInstance().build("/coordinator/actionbar").withString("content", "参数传递：hello arouter").navigation();
    }

    @OnClick(R2.id.tab_layout)
    public void onTabClicked(View view) {
        ARouter.getInstance().build("/coordinator/tab").navigation();
    }

    @OnClick(R2.id.collapsing)
    public void onCollapsingClicked(View view) {
        ARouter.getInstance().build("/coordinator/collapsing").navigation();
    }

    @OnClick(R2.id.rotate)
    public void onRoatateClicked() {
        ARouter.getInstance().build("/coordinator/titlebar").navigation();
    }

    @OnClick(R2.id.uc_head)
    public void onUcHeadClicked() {
        ARouter.getInstance().build("/coordinator/uc").navigation();
    }



}
