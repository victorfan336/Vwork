package com.victor.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.baselib.adapter.StringAdapter;
import com.victor.baselib.base.BaseActivity;
import com.victor.baselib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/coordinator/actionbar")
public class CoordinatorActionBarActivity extends BaseActivity {

    @BindView(R2.id.recycler)
    RecyclerView recyclerView;
    @Autowired
    String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
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
        ToastUtil.getInstance().showToast(getApplicationContext(), content);
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
