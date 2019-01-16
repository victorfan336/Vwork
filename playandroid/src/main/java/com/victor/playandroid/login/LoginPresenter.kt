package com.victor.playandroid.login

import com.victor.baselib.mvp.RxBasePresenter

class LoginPresenter(view: ILoginView, model: ILoginModel) : RxBasePresenter<ILoginView, ILoginModel>(view, model) {

    fun login(userName: String, password: String) {

    }

}