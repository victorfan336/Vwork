package com.victor.playandroid.register

import com.victor.baselib.mvp.RxBasePresenter
import com.victor.baselib.net.bean.ApiObjResultBean
import com.victor.baselib.net.bean.UserInfoBean
import com.victor.baselib.utils.XLog
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.prefs.Preferences

class RegisterPresenter(view: IRegisterView, model: IRegisterModel) : RxBasePresenter<IRegisterView, IRegisterModel>(view, model) {

    fun register(userName: String, password: String, repassword: String) {
        var disposable = getModel().register(userName, password, repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ApiObjResultBean<UserInfoBean>>() {
                    override fun onNext(userObserver: ApiObjResultBean<UserInfoBean>) {
                        XLog.e(XLog.TAG_GU, "register : code = ${userObserver.errorCode}, error = ${userObserver.errorMsg}")
                    }

                    override fun onError(e: Throwable) {
                        XLog.e(XLog.TAG_GU, "register : " + e.message)
                    }

                    override fun onComplete() {
                        XLog.e(XLog.TAG_GU, "register : onComplete")
                    }
                })
        add(disposable)

    }
}