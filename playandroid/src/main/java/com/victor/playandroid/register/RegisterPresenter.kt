package com.victor.playandroid.register

import com.victor.baselib.mvp.RxBasePresenter
import com.victor.baselib.net.RetrofitUtil
import com.victor.baselib.utils.XLog

class RegisterPresenter(view: IRegisterView, model: IRegisterModel) : RxBasePresenter<IRegisterView, IRegisterModel>(view, model) {

    fun register(userName: String, password: String, repassword: String) {
        val result = RetrofitUtil.getInstance().build().register(userName, password, repassword)
        XLog.e(XLog.TAG_GU, "hello" + result)
    }
}