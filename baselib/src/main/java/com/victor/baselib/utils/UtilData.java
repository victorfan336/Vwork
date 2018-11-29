package com.victor.baselib.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by yuchuan
 * DATE 3/24/16
 * TIME 15:58
 */
public class UtilData {

    public final static int TWENTY_FOUR_HOURS = 24 * 60 * 60 *1000;
//    public final static int TWENTY_FOUR_HOURS = 12*60*1000;//set for test
    public final static int FOUR_HOURS = 4 *  60 * 60 * 1000;
    public final static int HOURS = 60 * 60 * 1000;
//    public final static int HOURS = 60*1000; //set time for test

    public final static int MINUTE = 1000 * 60;
    public final static int SRCOND = 1000;

    /**
     * 获取系统日期
     *
     * @return
     */
    public static String getSystemData() {
        SimpleDateFormat formatter = new SimpleDateFormat(ConstantUtils.Day.FORMAT_DATE_YYMMDD);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }


    public static String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat(ConstantUtils.Day.FORMAT_DATE_YYMMDDHHMMSS);
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    /**
     * 获取系统时间
     *
     * @param con
     *
     * @return
     */
    public static String getSystemTime(Context con) {
        ContentResolver cv = con.getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv,
                android.provider.Settings.System.TIME_12_24);
        String template;
        String ampmValues;
        if (strTimeFormat != null && strTimeFormat.equals("24")) {
            template = "HH:mm";
        } else {
            template = "hh:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.getDefault());
        Calendar mCalendar = Calendar.getInstance(Locale.getDefault());
        if (mCalendar.get(Calendar.AM_PM) == 0) {
            ampmValues = "上午  ";
        } else {
            ampmValues = "下午  ";
        }
        return ampmValues + sdf.format(new Date());
    }

    /**
     * 设置系统时间
     *
     * @param hourOfDay
     * @param minute
     *
     * @throws InterruptedException
     */
    public static void setSystemTime(int hourOfDay, int minute) {
        Calendar canlendartimer = Calendar.getInstance();
        canlendartimer.set(Calendar.HOUR_OF_DAY, hourOfDay);
        canlendartimer.set(Calendar.MINUTE, minute);
        canlendartimer.set(Calendar.SECOND, 0);
        canlendartimer.set(Calendar.MILLISECOND, 0);
        long when_time = canlendartimer.getTimeInMillis();
        if (when_time / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when_time);
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex_86 = "[+][8][6][1][358]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && (mobiles.matches(telRegex) || mobiles.matches(telRegex_86));
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date     日期
     * @param parttern 日期格式
     *
     * @return 日期字符串
     */
    public static String DateToString(Date date, String parttern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date      日期
     * @param dateStyle 日期风格
     *
     * @return 日期字符串
     */
    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     *
     * @return SimpleDateFormat对象
     *
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        return new SimpleDateFormat(pattern);
    }

    /***
     * 转成格林威治时间
     *
     * @param LocalDate           2015-03-23  12:12:12
     * @return
     */
    public static String LocalToGTM(String LocalDate) {
        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date result_date;
        long result_time = 0;

        if (null == LocalDate) {
            return LocalDate;
        } else {
            try {
                format.setTimeZone(TimeZone.getDefault());
                result_date = format.parse(LocalDate);
                result_time = result_date.getTime();
                format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                return format.format(result_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return LocalDate;
    }

    /***
     * 将当前时间转成格林威治时间戳
     *
     * @return
     */
    public static String localToGTM() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
        try {
            Date currDate = Calendar.getInstance().getTime();
//            XLog.e(XLog.TAG_GU, "转换前的时间：" + currDate.toString() + " : " + currDate.getTime());
            long resultTime = currDate.getTime();
            String newTime = format.format(resultTime);
            SimpleDateFormat gmtformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            gmtformat.setTimeZone(TimeZone.getDefault());
            Date newDate = gmtformat.parse(newTime);
//            XLog.e(XLog.TAG_GU, "转换后的时间 : " + newTime + " : "+ newDate.getTime());
            return "" + newDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 根据date格式转换成时间戳
     * @param date
     * @return
     */
    public static long parseDate(String date, String dateFormat) {
        try {
            SimpleDateFormat gmtformat = new SimpleDateFormat(dateFormat, Locale.getDefault());
            gmtformat.setTimeZone(TimeZone.getDefault());
            Date newDate = gmtformat.parse(date);
            return newDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }
}
