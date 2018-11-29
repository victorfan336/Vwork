package com.victor.baselib.net;

import com.leagoo.vendingmachine.upgrade.download.UpgradeMsgBean;
import com.victor.baselib.net.bean.CommonBean;
import com.victor.baselib.net.bean.ContactUsBean;
import com.victor.baselib.net.bean.RepairsBean;
import com.victor.baselib.net.bean.SalePolicyBean;
import com.victor.baselib.net.bean.StartAdBean;
import com.victor.baselib.net.bean.VerificationBean;
import com.victor.baselib.net.bean.WarrantyStatusBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by fanwentao on 2018/1/30.
 */

public interface RetrofitService {

//    String BASE_URL = "http://data.me2tek.com:39090/leagooapi/";
    String BASE_URL = "http://192.168.1.197:8080/leagooappapi/v1/";
    String BASE_URL_THEME = "http://theme.me2tek.com:8081/theme/api/";

    /**
     * 下载文件
     * @param fileUrl
     * @return
     */
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     * POST方法数据请求,更新版本号
     *
     * @param map 请求中要发送的数据表
     * @return 返回需要的对象
     */
    @FormUrlEncoded
    @POST("upgradeVersion.action?")
    Call<UpgradeMsgBean> checkVersion(@FieldMap Map<String, String> map);

    /**
     * 获取联系我们数据
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("getCompanyInfo.action?")
    Call<ContactUsBean> requestContactus(@FieldMap Map<String, String> map);

    /**
     * 售后政策
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("getSalePolicyByCountry.action?")
    Call<SalePolicyBean> requestSalePolicy(@FieldMap Map<String, String> map);

    /**
     * 启动页广告
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("getIndexAdvertisement.action?")
    Call<StartAdBean> requestStartAd(@FieldMap Map<String, String> map);

    /**
     * 常见问题
     * @param map
     * @return
     */
    @GET("getQuestionAnswersByCountry.action?")
    Call<CommonBean> getQuestionAnswersByCountry(@FieldMap Map<String,String> map);

    /**
     * 维修状态接口
     * @param map
     * @return
     */
    @GET("getRepairInfo.action?")
    Call<RepairsBean> getRepairInfo(@FieldMap Map<String,String> map);

    /**
     * 真伪鉴别接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("getTrueOrFalseInfo.action?")
    Call<VerificationBean> getTrueOrFalseInfo(@FieldMap Map<String, String> map);

    /**
     * 保修状态接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("getGetDeviceShelfLife.action?")
    Call<WarrantyStatusBean> getGetDeviceShelfLife(@FieldMap Map<String, String> map);

}
