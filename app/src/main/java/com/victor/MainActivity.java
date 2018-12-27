package com.victor;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.MenuItem;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.baselib.ui.BaseFitsSystemWindowsActivity;

import java.lang.reflect.Field;

public class MainActivity extends BaseFitsSystemWindowsActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Handler mHandler = new Handler();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String setTitle() {
        return "";
    }

    @Override
    protected void initView() {

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new MainFragment()).commit();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

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
            case R.id.nav_coordinatorlayout:
                ARouter.getInstance().build("/coordinator/main").navigation();
                break;
            case R.id.nav_fingerprint:
//                startActivity(FingerAndPasswordActivity.class);
                break;
        }

        closeMenu();
        return false;
    }

}
