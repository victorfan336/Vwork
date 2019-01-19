package com.victor.playandroid.register

import com.victor.baselib.mvp.IBaseModel
import com.victor.baselib.net.bean.ApiObjResultBean
import com.victor.baselib.net.bean.UserInfoBean
import io.reactivex.Observable

interface IRegisterModel : IBaseModel {

    fun register(userName: String, password: String, repassword: String): Observable<ApiObjResultBean<UserInfoBean>>
}