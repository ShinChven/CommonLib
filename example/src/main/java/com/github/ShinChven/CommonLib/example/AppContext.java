package com.github.ShinChven.CommonLib.example;

import android.app.Application;
import com.github.ShinChven.lib.CommonLib.LogTool;
import com.github.ShinChven.lib.CommonLib.example.BuildConfig;

/**
 * Created by ShinChven on 15/5/3.
 */
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogTool.init(BuildConfig.DEBUG, "CommonTools");
    }
}
