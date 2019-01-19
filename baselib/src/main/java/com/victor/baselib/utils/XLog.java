package com.victor.baselib.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by victor fan
 * DATE 16/1/29
 * TIME 19:55
 */
public class XLog {
    //加载使数据过滤
    private static final String sLoaderTag = "loaderTag";
    public static final String TAG_GU = "Vwork-----";
    // 是否打印log，打包apk要设置为false
    private static final boolean sEnablePrint = true;//DebugVersion set true,for test,

    private static final String LOG_NAME = "vendingmachine.txt";

    public static String getLogName() {
        return LOG_NAME;
    }

    /**
     * 打印日志时获取当前的程序文件名、行号、方法名 输出格式为：[FileName | LineNumber | MethodName]
     *
     * @return tag
     */
    public static String getTag() {
        if (!sEnablePrint) {
            return "";
        }
        //StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
        return "[" + traceElement.getFileName() + " | "
                + traceElement.getLineNumber() + " | "
                + traceElement.getMethodName() + "]";

    }

    public static void v(String tag, String log) {
        if (sEnablePrint) {
            Log.v(tag, log);
        }
    }

    public static void i(String tag, String log) {
        if (sEnablePrint) {
            Log.i(tag, log);
        }
    }

    public static void e(String tag, String log) {
        if (sEnablePrint) {
            Log.e(tag, log);
        }
    }

    public static void d(String tag, String log) {
        if (sEnablePrint) {
            Log.d(tag, log);
        }
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将log写入手机SdCard中
     *
     * @param log    log内容
     * @param append 是否追加log
     */
    public static synchronized void writeLog(final String log, final boolean append) {
        writeLog(null, log, append);
    }

    /**
     * 将log及异常写入手机sdCard
     *
     * @param ex     异常
     * @param log    log信息
     * @param append 是否追加
     */
    public static synchronized void writeLog(final Exception ex, final String log, final boolean append) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                PrintStream writer = null;
                File rootFile = new File(ConstantUtils.ROOT_DIR);
                try {
                    if (!rootFile.exists()) {
                        rootFile.mkdirs();
                    }
                    File file = new File(rootFile.getPath(), getLogName());
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    StringBuffer date = new StringBuffer();
                    date.append(format.format(new Date())).append(" ");
                    writer = new PrintStream(new FileOutputStream(file, append), true);
                    writer.print(date.toString());
                    writer.print(log);
                    if (ex != null) {
                        ex.printStackTrace(writer);
                    }
                    writer.print("\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
        }).start();
    }


}
