package com.victor.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.victor.baselib.adapter.StringAdapter;
import com.victor.baselib.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CoordinatorActionBarActivity extends BaseActivity {

    @BindView(R2.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple;
    }

    @Override
    protected String setTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        List list = new ArrayList();
        for (int i = 0; i < 80; i++) {
            list.add("第" + i + "项");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringAdapter adapter = new StringAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }


    @OnClick(R2.id.float_action)
    public void onViewClicked(View view) {
        Snackbar.make(view,"FBI",Snackbar.LENGTH_LONG)
                .setAction("cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                })
                .show();
    }
}
