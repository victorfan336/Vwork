package com.victor.playandroid.register

import com.victor.baselib.net.RetrofitUtil
import com.victor.baselib.net.bean.ApiObjResultBean
import com.victor.baselib.net.bean.UserInfoBean
import io.reactivex.Observable

class RegisterModel : IRegisterModel {

    override
    fun register(userName: String, password: String, repassword: String):
            Observable<ApiObjResultBean<UserInfoBean>> {
        return RetrofitUtil.getInstance().build().register(userName, password, repassword)
                .map { ApiObjResultBean<UserInfoBean>() }
//                .onErrorResumeNext(HttpResponseFunc<UserInfoBean>())
    }

}