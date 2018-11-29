package com.victor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.baselib.ui.BaseFragment;
import com.victor.coordinatorlayout.CoordinatorMainActivity;
import com.victor.coordinatorlayout.R2;
import com.victor.libzxing.activity.ScannerActivity;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author fanwentao
 * @Description
 * @date 2018/6/25
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R2.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    Unbinder unbinder;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view) {
        tvTitle.setText("Victor");
        view.findViewById(R.id.iv_scanner).setOnClickListener(this);
        view.findViewById(R.id.float_action).setOnClickListener(this);
//        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//
//            }
//
//            @Override
//            public void onStateChanged(int offset) {
//                int scrollMax = appBarLayout.getTotalScrollRange();
//                int statusHeight = Tools.getStatusBarHeight(getActivity());
//                CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
//                if (-offset < scrollMax - statusHeight) {
//                    params.height = getResources().getDimensionPixelSize(R.dimen.y130);
//                } else if (-offset < scrollMax) {
//                    int padding = (-offset - (scrollMax - statusHeight));
//                    tvTitle.setPadding(0, padding, 0, 0);
//                    params.height = getResources().getDimensionPixelSize(R.dimen.y130) + padding;
//                } else {
//                    params.height = getResources().getDimensionPixelSize(R.dimen.y130) + statusHeight;
//                    tvTitle.setPadding(0, statusHeight, 0, 0);
//                }
//                toolbar.setLayoutParams(params);
//            }
//        });

    }

    @Override
    protected void initData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_action:
                Snackbar.make(v, "Go CoordinatorLayout UI", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().startActivity(new Intent(getActivity(), CoordinatorMainActivity.class));
                            }
                        })
                        .show();
                break;
            case R.id.iv_scanner:
                startActivity(new Intent(getActivity(), ScannerActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}