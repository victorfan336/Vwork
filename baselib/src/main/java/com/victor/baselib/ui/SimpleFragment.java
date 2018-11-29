package com.victor.baselib.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.baselib.R;
import com.victor.baselib.R2;
import com.victor.baselib.adapter.StringAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.internal.Util;

/**
 * @author fanwentao
 * @Description
 * @date 2018/6/26
 */
public class SimpleFragment extends BaseFragment {

    private List<String> list = new ArrayList<>();
    private String title;
    @BindView(R2.id.simple_recyclerview)
    RecyclerView recyclerView;
    @BindView(R2.id.simple_title)
    TextView tvTitle;

    public static SimpleFragment getInstance(@NonNull String title, @NonNull List<String> list) {
        SimpleFragment fragment = new SimpleFragment();
        fragment.list = list;
        fragment.title = title;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_simple;
    }

    @Override
    public void initView(View view) {
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        list.clear();
        for (int j = 0; j < 50; j++) {
            list.add("item " + j);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StringAdapter adapter = new StringAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }


}
