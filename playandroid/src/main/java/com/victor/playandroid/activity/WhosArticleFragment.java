package com.victor.playandroid.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.victor.baselib.adapter.ViewPagerAdapter;
import com.victor.baselib.mvp.BaseMVPFragment;
import com.victor.baselib.ui.SimpleFragment;
import com.victor.libzxing.activity.ScannerActivity;
import com.victor.playandroid.R;
import com.victor.playandroid.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WhosArticleFragment extends BaseMVPFragment {

    @BindView(R2.id.whos_viewpager)
    ViewPager viewpager;
    @BindView(R2.id.whos_tablayout)
    TabLayout tablayout;

    List<Fragment> mFragments;
    String[] mTitles = new String[]{
            "玩安卓", "微博", "相册"
    };

    @Override
    protected int getLayoutId() {
        return R.layout.whos_fragment;
    }

    @Override
    protected void initView(View view) {
        enableScanner(ScannerActivity.class);
        setupViewPager();
    }

    @Override
    protected void initData() {

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
                new ViewPagerAdapter(getActivity().getSupportFragmentManager(), mFragments, mTitles);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(adapter);

        //  第三步：将ViewPager与TableLayout 绑定在一起
        tablayout.setupWithViewPager(viewpager);
    }

}
