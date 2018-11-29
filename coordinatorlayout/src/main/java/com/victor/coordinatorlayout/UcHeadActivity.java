package com.victor.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.victor.baselib.adapter.StringAdapter;
import com.victor.baselib.adapter.ViewPagerAdapter;
import com.victor.baselib.ui.BaseActivity;
import com.victor.baselib.ui.SimpleFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * @author fanwentao
 * @Description
 * @date 2018/6/29
 */
public class UcHeadActivity extends BaseActivity {

    List<Fragment> mFragments;
    String[] mTitles = new String[]{
            "主页", "微博", "相册"
    };
//    @BindView(R2.id.tablayout)
//    TabLayout tablayout;
//    @BindView(R2.id.viewpager)
//    ViewPager viewpager;
    @BindView(R2.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupViewPager() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            List<String> datas = new ArrayList<>();
            for (int j = 0; j < 60; j++) {
                datas.add("item " + j);
            }
            Fragment listFragment = SimpleFragment.getInstance(mTitles[i], datas);
            mFragments.add(listFragment);
        }

        ViewPagerAdapter adapter =
                new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
//        viewpager.setAdapter(adapter);

        //  第三步：将ViewPager与TableLayout 绑定在一起
//        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_uc_head;
    }

    @Override
    protected String setTitle() {
        return "UC Head";
    }

    @Override
    protected void initView() {
//        setupViewPager();
        List<String> list = new ArrayList<>();
        for (int j = 0; j < 50; j++) {
            list.add("item " + j);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringAdapter adapter = new StringAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
