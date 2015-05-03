package com.github.ShinChven.lib.CommonLib.utils;

import android.util.Log;

/**
 * Created by ShinChven on 14/8/29. </p>
 * log 工具，使用此Log 工具可统一开启或者关闭打印log
 */
public class LogUtil {

    private static boolean enabled = false;


    public static void setEnabled(boolean enabled) {
        LogUtil.enabled = enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setDefaultTag(String defaultTag) {
        DEFAULT_TAG = defaultTag;
    }

    public static String getDefaultTag() {
        return DEFAULT_TAG;
    }

    /**
     * 设置log 开关</p>
     * 建议在Application 中设置开启
     * <pre>
     * LogUtil.init(BuildConfig.DEBUG,"your_default_log_tag");
     * </pre>
     *
     * @param enabled    是否开启log
     * @param defaultTag 默认log tag
     */
    public static void init(boolean enabled, String defaultTag) {
        setEnabled(enabled);
        setDefaultTag(defaultTag);
    }

    private static String DEFAULT_TAG = "LogUtil";

    public static void i(String tag, String msg) {
        if (enabled) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (enabled) {
            Log.i(DEFAULT_TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (enabled) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (enabled) {
            Log.d(DEFAULT_TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (enabled) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (enabled) {
            Log.e(DEFAULT_TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (enabled) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        if (enabled) {
            Log.w(DEFAULT_TAG, msg);
        }
    }

    /**
     * 用于打印异常
     *
     * @param e
     */
    public static void printStackTrace(Exception e) {
        if (enabled) {
            e.printStackTrace();
        }
    }

    /**
     * 用于打印错误
     *
     * @param e
     */
    public static void printStackTrace(Error e) {
        if (enabled) {
            e.printStackTrace();
        }
    }
}
