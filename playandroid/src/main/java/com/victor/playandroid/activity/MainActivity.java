package com.victor.playandroid.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.MenuItem;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.baselib.adapter.ViewPagerAdapter;
import com.victor.baselib.ui.BaseFitsSystemWindowsActivity;
import com.victor.baselib.ui.SimpleFragment;
import com.victor.playandroid.R;
import com.victor.playandroid.R2;
import com.victor.playandroid.home.WhosArticleFragment;
import com.victor.playandroid.login.LoginActivity;
import com.victor.playandroid.view.CustomViewListActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseFitsSystemWindowsActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Handler mHandler = new Handler();

    @BindView(R2.id.viewpager)
    ViewPager viewpager;
    @BindView(R2.id.tablayout)
    TabLayout tablayout;

    List<Fragment> mFragments;
    String[] mTitles = new String[]{
            "玩安卓", "微博", "相册"
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleRes() {
        return R.string.home;
    }

    @Override
    protected void initViews() {
        drawer = findViewById(R.id.drawer);
//        setDrawerLeftEdgeSize(this, drawer, 0.5f);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.app_name, R.string.app_name);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.content, new MainFragment()).commit();

        setupViewPager();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private void setupViewPager() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            List<String> datas = new ArrayList<>();
            for (int j = 0; j < mTitles.length * 10; j++) {
                datas.add("item " + j);
            }
            Fragment listFragment = null;
            if (i ==0) {
                listFragment = new WhosArticleFragment();
            } else {
                listFragment = SimpleFragment.getInstance(mTitles[i], datas);
            }
            mFragments.add(listFragment);
        }
        // 第二步：为ViewPager设置适配器
        ViewPagerAdapter adapter =
                new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);

        viewpager.setAdapter(adapter);

        //  第三步：将ViewPager与TableLayout 绑定在一起
        tablayout.setupWithViewPager(viewpager);
    }

    private void setDrawerLeftEdgeSize (Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    private void closeMenu() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawer.closeDrawers();
            }
        }, 700);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_login:
//                ARouter.getInstance().build("/login/main").navigation();
                startActivity(LoginActivity.class);
                break;
            case R.id.custom_view:
//                ARouter.getInstance().build("/customview/list").navigation();
                startActivity(CustomViewListActivity.class);
                break;
        }

        closeMenu();
        return false;
    }
}
