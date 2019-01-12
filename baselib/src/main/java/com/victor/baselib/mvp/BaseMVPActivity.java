package com.victor.baselib.mvp;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseMVPActivity extends BaseActivity {

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
    protected void onDestroy() {
        releasePresenter();
        super.onDestroy();
    }

}
