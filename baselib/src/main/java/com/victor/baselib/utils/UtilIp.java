package com.victor.baselib.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by fanwentao on 2017/12/26.
 */

public class UtilIp {

    public interface IpCallBack {
        void process(IpBean ipBean, int requestCode);
    }

    public static class IpBean {
        String as;//: "AS4134 No.31,Jin-rong Street",
        String city;//: "Shenzhen",
        String country;//: "China",
        String countryCode;//: "CN",
        String isp;//: "China Telecom Guangdong",
        String lat;//: 22.5333,
        String lon;//: 114.1333,
        String org;//: "China Telecom",
        String query;//: "218.18.171.112",
        String region;//: "44",
        String regionName;//: "Guangdong",
        String status;//: "success",
        String timezone;//: "Asia/Shanghai",


        public String getAs() {
            return as;
        }

        public void setAs(String as) {
            this.as = as;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getIsp() {
            return isp;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getOrg() {
            return org;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        /**
         * 获取外网IP
         * @return
         */
        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }
    }
    /**
     * 通过指定IP获取位置信息
     */
    public static final String sGetAddrUrl = "http://ip-api.com/json/";

    /**
     * 通过网络获取位置信息
     * JSON
     * {
     as: "AS4134 No.31,Jin-rong Street",
     city: "Shenzhen",
     country: "China",
     countryCode: "CN",
     isp: "China Telecom Guangdong",
     lat: 22.5333,
     lon: 114.1333,
     org: "China Telecom",
     query: "218.18.171.112",
     region: "44",
     regionName: "Guangdong",
     status: "success",
     timezone: "Asia/Shanghai",
     zip: ""
     }
     * @param callBack
     */
    public static void getLocationInfo(final int code, final IpCallBack callBack) {
        new Thread() {
            public void run() {
                String result = null;
                IpBean ipBean = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String requestStr = sGetAddrUrl;
                    HttpGet request = new HttpGet(requestStr);
                    HttpResponse response = httpClient.execute(request);
                    if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                        result = EntityUtils.toString(response.getEntity());
                        XLog.e(XLog.TAG_GU, XLog.getTag() + " -- locate info :" + result);
                        ipBean = new Gson().fromJson(result, IpBean.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (callBack != null) {
                        callBack.process(ipBean, code);
                    }
                }
            };
        }.start();

    }

    public static String getWifiIP(Context context) {
        String ip = null;
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            ip = "" + (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                    + "." + (i >> 24 & 0xFF);
        }
        return ip;
    }

    public static String getMobileIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            XLog.e("victor", ex.getMessage());
        }
        return null;
    }

    /**
     * 获取外网的IP
     *
     * @Title: GetNetIp
     * @param @return
     */
    public static void getNetIp(final IpCallBack callBack, final int code) {
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                URL infoUrl = null;
                InputStream inStream = null;
                IpBean ipLine = "";
                HttpURLConnection httpConnection = null;
                try {
                    infoUrl = new URL("http://ip.chinaz.com/getip.aspx");
                    URLConnection connection = infoUrl.openConnection();
                    httpConnection = (HttpURLConnection) connection;
                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(inStream, "utf-8"));
                        StringBuilder strber = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null)
                            strber.append(line + "\n");

                        Pattern pattern = Pattern
                                .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
                        Matcher matcher = pattern.matcher(strber.toString());
                        if (matcher.find()) {
                            ipLine = matcher.group();
                        }
                    }
                    XLog.e(XLog.TAG_GU, XLog.getTag() + " -- net ip = " + ipLine);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (callBack != null) {
                        callBack.process(ipLine, code);
                    }
                    UtilIO.closeQuietly(inStream);
                    if (httpConnection != null) {
                        try {
                            httpConnection.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();*/
    }
}
