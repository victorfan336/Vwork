package com.victor.baselib.net;

import com.victor.baselib.net.bean.ApiArrayResultBean;
import com.victor.baselib.net.bean.ApiObjResultBean;
import com.victor.baselib.net.bean.WhosArticleBean;

import java.util.List;

import io.reactivex.Observable;
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

    @POST("/user/register")
    Observable<ApiObjResultBean> register(String username, String password, String repassword);

}
