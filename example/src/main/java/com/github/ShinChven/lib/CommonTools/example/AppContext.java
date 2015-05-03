package com.github.ShinChven.lib.CommonTools.example;

import android.app.Application;
import com.github.ShinChven.lib.CommonTools.LogTool;

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
