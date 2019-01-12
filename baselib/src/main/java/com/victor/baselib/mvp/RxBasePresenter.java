package com.victor.baselib.mvp;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxBasePresenter<V extends IBaseView, M extends IBaseModel> extends BasePresenter<V, M> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RxBasePresenter(V view, M model) {
        super(view, model);
    }

    @Override
    public void release() {
        unregisterAll();
        super.release();
    }

    /**
     * Add a disposable to manager.
     *
     * @param disposable disposable
     */
    public void register(@NonNull Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * Remove a disposable from manager.
     *
     * @param disposable disposable
     */
    public void unregister(@NonNull Disposable disposable) {
        if (compositeDisposable == null) {
            return;
        }
        compositeDisposable.remove(disposable);
    }

    /**
     * Remove all disposables from manager.
     */
    private void unregisterAll() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

}
