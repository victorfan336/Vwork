package com.victor;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.baselib.base.BaseFragment;
import com.victor.baselib.net.RetrofitUtil;
import com.victor.baselib.net.bean.ApiArrayResultBean;
import com.victor.baselib.net.bean.WhosArticleBean;
import com.victor.baselib.utils.ToastUtil;
import com.victor.coordinatorlayout.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.victor.baselib.net.RetrofitService.BASE_URL_THEME;

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
        RetrofitUtil.getInstance().setUrl(BASE_URL_THEME).build().getWxWhosArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ApiArrayResultBean<WhosArticleBean>>() {
                    @Override
                    public void onNext(ApiArrayResultBean<WhosArticleBean> listApiArrayResultBean) {
                        if (listApiArrayResultBean != null) {
                            ToastUtil.getInstance().showToast(getActivity().getApplicationContext(), "公众号列表： " + listApiArrayResultBean.getData().size());
                        } else {
                            ToastUtil.getInstance().showToast(getActivity().getApplicationContext(), "公众号列表为空");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.getInstance().showToast(getActivity().getApplicationContext(), "error: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                });
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
                                ARouter.getInstance().build("/coordinator/main").navigation();
                            }
                        })
                        .show();
                break;
            case R.id.iv_scanner:
                ARouter.getInstance().build("/scanner/main").navigation();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
