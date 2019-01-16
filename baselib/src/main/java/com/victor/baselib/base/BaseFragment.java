package com.victor.baselib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.baselib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    /**
     * 获取布局资源id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView(View view);

    /**
     * 初始化数据
     */
    protected abstract void initData();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        initData();

        return view;
    }

    public void enableBackButton(View titleLayout) {
        if (titleLayout != null) {
            View backView = titleLayout.findViewById(R.id.title_back);
            if (backView != null) {
                backView.setVisibility(View.VISIBLE);
                backView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            }
        }
    }

    public void enableScanner(View titleLayout, final Class clazzType) {
        if (titleLayout != null) {
            View menuView = titleLayout.findViewById(R.id.title_menu);
            if (menuView != null) {
                menuView.setVisibility(View.VISIBLE);
                menuView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().startActivity(new Intent(getActivity(), clazzType));
                    }
                });
            }
        }
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
