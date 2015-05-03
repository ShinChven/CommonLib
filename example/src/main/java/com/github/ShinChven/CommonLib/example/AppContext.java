package com.github.ShinChven.CommonLib.example;

import android.app.Application;
import com.github.ShinChven.lib.CommonLib.utils.LogUtil;
import com.github.ShinChven.lib.CommonLib.example.BuildConfig;

/**
 * Created by ShinChven on 15/5/3.
 */
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(BuildConfig.DEBUG, "CommonTools");
    }
}
