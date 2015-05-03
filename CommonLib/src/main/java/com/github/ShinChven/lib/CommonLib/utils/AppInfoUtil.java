package com.github.ShinChven.lib.CommonLib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by ShinChven on 14/9/3.
 * 获取应用的信息
 */
public class AppInfoUtil {
    /**
     * 获取应用版本号 versionCode
     *
     * @return
     * @throws Exception
     */
    public static int getApplicationVersionCode(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        int version = packInfo.versionCode;
        return version;
    }

    /**
     * 获取应用版本名 versionName
     *
     * @return
     * @throws Exception
     */
    public static String getApplicationVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        String versionName = "您正在使用最新版本";
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
