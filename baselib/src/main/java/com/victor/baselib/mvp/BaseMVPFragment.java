package com.victor.baselib.mvp;


import com.victor.baselib.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMVPFragment extends BaseFragment {

    private List<RxBasePresenter> presenterList = new ArrayList<>();


    public void addPresenter(RxBasePresenter presenter) {
        if (presenter != null){
            presenterList.add(presenter);
        }
    }

    public void removePresenter(RxBasePresenter presenter) {
        if (presenter != null && presenterList.isEmpty()) {
            presenterList.remove(presenter);
        }
    }

    public void releasePresenter() {
        if (presenterList.isEmpty()) {
            for (RxBasePresenter presenter : presenterList) {
                presenter.release();
                presenter = null;
            }
        }
    }

    @Override
    public void onDestroy() {
        releasePresenter();
        super.onDestroy();
    }

}
