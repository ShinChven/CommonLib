package com.github.ShinChven.lib.CommonLib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ShinChven on 14/5/28.
 */
public class ToastUtil {
    public static void toastShortly(Context context, int content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
           LogUtil.printStackTrace(e);
        }
    }

    public static void toastShortly(Context context, String content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    public static void toastLonger(Context context, int content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    public static void toastLonger(Context context, String content) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

}
