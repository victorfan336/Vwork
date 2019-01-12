package com.victor.baselib.mvp;


import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IBaseView, M extends IBaseModel> {

    protected WeakReference<V> view;
    protected M model;

    public BasePresenter(V view, M model) {
        this.view = new WeakReference<>(view);
        this.model = model;
    }

    public V getView() {
        if (isReleased()) {
            return null;
        } else {
            return view.get();
        }
    }

    public void setView(V view) {
        this.view = new WeakReference<>(view);
    }

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public boolean isReleased() {
        return (view == null || view.get() == null);
    }

    void release() {
        view = null;
        model = null;
    }

}
