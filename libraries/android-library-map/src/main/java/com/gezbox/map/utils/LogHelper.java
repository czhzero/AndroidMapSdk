package com.gezbox.map.utils;

/**
 * Created by chenzhaohua on 16/7/25.
 * 日志打印
 *
 */
public class LogHelper {

    private static final String DEFAULT_TAG = "map";
    private static final boolean DEBUG = true;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            d(DEFAULT_TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            e(DEFAULT_TAG, msg);
        }
    }


}
