package com.victor.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.baselib.adapter.ViewPagerAdapter;
import com.victor.baselib.base.BaseActivity;
import com.victor.baselib.ui.SimpleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author fanwentao
 * @Description
 * @date 2018/6/25
 */
@Route(path = "/coordinator/tab")
public class CoordinatorTabActivity extends BaseActivity {

    @BindView(R2.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    List<Fragment> mFragments;
    String[] mTitles = new String[]{
            "主页", "微博", "相册"
    };
    @BindView(R2.id.tablayout)
    TabLayout tablayout;
    @BindView(R2.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupViewPager() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            List<String> datas = new ArrayList<>();
            for (int j = 0; j < mTitles.length * 10; j++) {
                datas.add("item " + j);
            }
            Fragment listFragment = SimpleFragment.getInstance(mTitles[i], datas);
            mFragments.add(listFragment);
        }
        // 第二步：为ViewPager设置适配器
        ViewPagerAdapter adapter =
                new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);

        viewpager.setAdapter(adapter);

        //  第三步：将ViewPager与TableLayout 绑定在一起
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinator_tab;
    }

    @Override
    protected int getTitleRes() {
        return R.string.tab;
    }

    @Override
    protected void initView() {
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        setupViewPager();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
