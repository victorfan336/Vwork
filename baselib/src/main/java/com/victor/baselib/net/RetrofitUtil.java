package com.victor.baselib.net;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.leagoo.vendingmachine.upgrade.download.UpgradeMsgBean;
import com.victor.baselib.net.bean.CommonBean;
import com.victor.baselib.net.bean.ContactUsBean;
import com.victor.baselib.net.bean.RepairsBean;
import com.victor.baselib.net.bean.SalePolicyBean;
import com.victor.baselib.net.bean.StartAdBean;
import com.victor.baselib.net.bean.VerificationBean;
import com.victor.baselib.net.bean.WarrantyStatusBean;
import com.victor.baselib.net.imp.ImpRequest;
import com.victor.baselib.utils.Tools;
import com.victor.baselib.utils.UtilDevice;
import com.victor.baselib.utils.XLog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fanwentao on 2018/1/30.
 */

public class RetrofitUtil {

    private RetrofitClient mRetrofitClient;
    private RetrofitClient.RetrofitBuilder mRetrofitbuild;
    private RetrofitService mRetrofitService;

    private RetrofitUtil() {

    }

    public static class RetrofitSingleton {
        private static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }

    public static RetrofitUtil getInstance() {
        return RetrofitSingleton.INSTANCE;
    }

    public RetrofitUtil setUrl(String url) {
        if (mRetrofitbuild == null) {
            mRetrofitbuild = new RetrofitClient.RetrofitBuilder();
        }
        mRetrofitbuild.setBaseUrl(url);
        return this;
    }

    /**
     * 设置连接超时
     *
     * @param connectTimeout 超时时间
     * @return
     */
    public RetrofitUtil setConnectTimeout(int connectTimeout) {
        if (mRetrofitbuild == null) {
            mRetrofitbuild = new RetrofitClient.RetrofitBuilder();
        }
        mRetrofitbuild.setConnectTimeout(connectTimeout);
        return this;
    }

    /**
     * 设置读写超时
     *
     * @param timeout 超时时间
     * @return
     */
    public RetrofitUtil setTimeout(int timeout) {
        if (mRetrofitbuild == null) {
            mRetrofitbuild = new RetrofitClient.RetrofitBuilder();
        }
        mRetrofitbuild.setTimeout(timeout);
        return this;
    }

    public RetrofitUtil build() {
        mRetrofitClient = mRetrofitbuild.build();
        mRetrofitService = mRetrofitClient.getService();
        return this;
    }

    public RetrofitService getRetrofiService() {
        return mRetrofitService;
    }


    public String getCountryCode(Context context) {
        String countryCode = UtilDevice.getCountryCode(context);
        if (TextUtils.isEmpty(countryCode) || countryCode.equalsIgnoreCase("null")) {
            countryCode = UtilDevice.getLangCountryCode(context);
        }
        return countryCode;
    }

    /**
     * 版本号更新
     */
    public void checkVersions(Map<String, String> map, final ImpRequest impRequest) {
        Call<UpgradeMsgBean> call = mRetrofitService.checkVersion(map);

        call.enqueue(new Callback<UpgradeMsgBean>() {
            @Override
            public void onResponse(Call<UpgradeMsgBean> call, Response<UpgradeMsgBean> response) {
                if (impRequest != null) {
                    if (response.isSuccessful()) {
                        impRequest.onSuccess(response.body());
                    } else {
                        impRequest.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpgradeMsgBean> call, Throwable t) {
                HttpUrl url = call.request().url();
                XLog.d("onFailure", "onFailure" + url.url().toString());
                if (impRequest != null) {
                    impRequest.onFailure();
                }
            }
        });
    }


    /**
     * 下载单个文件
     * @param remoteUrl
     * @param fileSavePath
     * @param request
     */
    public void requestDownloadFile(String remoteUrl, final String fileSavePath, final ImpRequest request) {
        Call<ResponseBody> call = mRetrofitService.downloadFile(remoteUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.isSuccessful()) {
                    Tools.writeResponseBodyToDisk(response.body().byteStream(), fileSavePath, request);
                } else {
                    if (request != null) {
                        request.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (request != null) {
                    request.onFailure();
                }
                XLog.e(XLog.TAG_GU, "error : " + t.getMessage());
            }
        });
    }

    public void requestStartAd(Context context, @NonNull final ImpRequest request) {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("countryCode", getCountryCode(context));
        Call<StartAdBean> call = mRetrofitService.requestStartAd(dataMap);

        call.enqueue(new Callback<StartAdBean>() {
            @Override
            public void onResponse(Call<StartAdBean> call, Response<StartAdBean> response) {
                if (response != null && response.isSuccessful()) {
                    StartAdBean bean = response.body();
                    if (bean != null && bean.getCode() == 0) {
                        request.onSuccess(bean);
                    } else {
                        request.onFailure();
                    }
                } else {
                    request.onFailure();
                }
            }

            @Override
            public void onFailure(Call<StartAdBean> call, Throwable t) {
                request.onFailure();
            }
        });
    }

    public void requestContactUs(Context context, @NonNull final ImpRequest request) {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("countryCode", getCountryCode(context));
        Call<ContactUsBean> call = mRetrofitService.requestContactus(dataMap);

        call.enqueue(new Callback<ContactUsBean>() {
            @Override
            public void onResponse(Call<ContactUsBean> call, Response<ContactUsBean> response) {
                if (response != null && response.isSuccessful()) {
                    ContactUsBean bean = response.body();
                    if (bean != null && bean.getCode() == 0) {
                        request.onSuccess(bean);
                    } else {
                        request.onFailure();
                    }
                } else {
                    request.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ContactUsBean> call, Throwable t) {
                request.onFailure();
            }
        });
    }

    /**
     * 真伪鉴别接口
     * @param map
     * @param impRequest
     */
    public void requestVerification(Map<String, String> map,@NonNull final ImpRequest impRequest){
        Call<VerificationBean> call = mRetrofitService.getTrueOrFalseInfo(map);
        call.enqueue(new Callback<VerificationBean>() {
            @Override
            public void onResponse(Call<VerificationBean> call, Response<VerificationBean> response) {
                if (response != null && response.isSuccessful()) {
                    VerificationBean bean = response.body();
                    if (bean != null && bean.getCode() == 0) {
                        impRequest.onSuccess(bean);
                    } else {
                        impRequest.onFailure();
                    }
                } else {
                    impRequest.onFailure();
                }
            }
            @Override
            public void onFailure(Call<VerificationBean> call, Throwable t) {
                XLog.e(XLog.getTag(), XLog.TAG_GU+"--onFailure--");
                if (impRequest != null) {
                    impRequest.onFailure();
                }
            }
        });
    }
    /**
     * 常见问题接口
     * @param map
     * @param impRequest
     */
    public void requestCommonProblem(Map<String, String> map,@NonNull final ImpRequest impRequest){
        Call<CommonBean> call = mRetrofitService.getQuestionAnswersByCountry(map);
        call.enqueue(new Callback<CommonBean>() {
            @Override
            public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                if (response != null && response.isSuccessful()) {
                    CommonBean bean = response.body();
                    if (bean != null && bean.getCode() == 0) {
                        impRequest.onSuccess(bean);
                    } else {
                        impRequest.onFailure();
                    }
                } else {
                    impRequest.onFailure();
                }
            }
            @Override
            public void onFailure(Call<CommonBean> call, Throwable t) {
                XLog.e(XLog.getTag(), XLog.TAG_GU+"--onFailure--");
                if (impRequest != null) {
                    impRequest.onFailure();
                }
            }
        });
    }
    /**
     * 保修状态接口
     * @param map
     * @param impRequest
     */
    public void requestWarrantyStatus(Map<String, String> map,@NonNull final ImpRequest impRequest){
        Call<WarrantyStatusBean> call = mRetrofitService.getGetDeviceShelfLife(map);
        call.enqueue(new Callback<WarrantyStatusBean>() {
            @Override
            public void onResponse(Call<WarrantyStatusBean> call, Response<WarrantyStatusBean> response) {
                if (response != null && response.isSuccessful()) {
                    WarrantyStatusBean bean = response.body();
                    if (bean != null && bean.getCode() == 0) {
                        impRequest.onSuccess(bean);
                    } else {
                        impRequest.onFailure();
                    }
                } else {
                    impRequest.onFailure();
                }
            }
            @Override
            public void onFailure(Call<WarrantyStatusBean> call, Throwable t) {
                XLog.e(XLog.getTag(), XLog.TAG_GU+"--onFailure--");
                if (impRequest != null) {
                    impRequest.onFailure();
                }
            }
        });
    }
    /**
     * 维修进度接口
     * @param map
     * @param impRequest
     */
    public void requestRepairSchedule(Map<String, String> map,@NonNull final ImpRequest impRequest){
        Call<RepairsBean> call = mRetrofitService.getRepairInfo(map);
        call.enqueue(new Callback<RepairsBean>() {
            @Override
            public void onResponse(Call<RepairsBean> call, Response<RepairsBean> response) {
                if (response != null && response.isSuccessful()) {
                    RepairsBean bean = response.body();
                    if (bean != null && bean.getCode() == 0) {
                        impRequest.onSuccess(bean);
                    } else {
                        impRequest.onFailure();
                    }
                } else {
                    impRequest.onFailure();
                }
            }
            @Override
            public void onFailure(Call<RepairsBean> call, Throwable t) {
                XLog.e(XLog.getTag(), XLog.TAG_GU+"--onFailure--");
                if (impRequest != null) {
                    impRequest.onFailure();
                }
            }
        });
    }


    public void requestPolicy(Context context, @NonNull final ImpRequest request) {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("countryCode", getCountryCode(context));
        Call<SalePolicyBean> call = mRetrofitService.requestSalePolicy(dataMap);

        call.enqueue(new Callback<SalePolicyBean>() {
            @Override
            public void onResponse(Call<SalePolicyBean> call, Response<SalePolicyBean> response) {
                if (response != null && response.isSuccessful()) {
                    SalePolicyBean bean = response.body();
                    if (bean != null && bean.getCode() == 0) {
                        request.onSuccess(bean);
                    } else {
                        request.onFailure();
                    }
                } else {
                    request.onFailure();
                }
            }

            @Override
            public void onFailure(Call<SalePolicyBean> call, Throwable t) {
                request.onFailure();
            }
        });
    }
}
