package com.victor.baselib.net;

import com.victor.baselib.net.bean.ApiArrayResultBean;
import com.victor.baselib.net.bean.ApiObjResultBean;
import com.victor.baselib.net.bean.UserInfoBean;
import com.victor.baselib.net.bean.WhosArticleBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by fanwentao on 2018/1/30.
 */

public interface RetrofitService {

    // 玩Android开放API
    String BASE_URL_THEME = "http://www.wanandroid.com";

    @GET("/wxarticle/chapters/json")
    Observable<ApiArrayResultBean<WhosArticleBean>> getWxWhosArticle();

    @GET("/wxarticle/list/{id}/{index}/json")
    Observable<String> getWxArticles(@Path("id") int id, @Path("index") int pageIndex);

    /**
     *
     * @return
     */
    @POST("/user/login")
    Observable<Boolean> login(String username, String password);

    @FormUrlEncoded
    @POST("/user/register")
    Observable<ApiObjResultBean<UserInfoBean>> register(@Field("username") String username,
                                                                @Field("password") String password,
                                                                @Field("repassword") String repassword);

}
