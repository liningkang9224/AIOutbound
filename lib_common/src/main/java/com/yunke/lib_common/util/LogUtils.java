package com.yunke.lib_common.util;

import android.util.Log;



public class LogUtils {
    public static final String TAG = "LogUtils";
    /**
     * 日志开关
     */
    public static boolean sLogSwitch = false;

    private LogUtils() {

    }

    /**
     * 日志是否可用
     *
     * @return
     */
    public static boolean isLogEnable() {
        return  sLogSwitch;
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (sLogSwitch) {
            Log.i(tag, msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (sLogSwitch) {
//            if ("release".equals(BuildConfig.BUILD_TYPE)) {
//                Log.d(tag, msg);
//                return;
//            }
            int segmentSize = 3 * 1024;
            long length = msg.length();
            // 长度小于等于限制直接打印
            if (length <= segmentSize) {
                Log.d(tag, msg);
            } else {
                // 循环分段打印日志
                while (msg.length() > segmentSize) {
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.d(tag, logContent);
                }
                // 打印剩余日志
                Log.d(tag, msg);
            }
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
//        if (BuildConfig.DEBUG || sLogSwitch) {
        if ( sLogSwitch) {
            Log.w(tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
//        if (BuildConfig.DEBUG || sLogSwitch) {
        if (sLogSwitch) {
            Log.e(tag, msg);
        }
    }
}
