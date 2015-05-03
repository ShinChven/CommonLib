package com.github.ShinChven.lib.CommonLib.utils;

import android.app.Activity;
import com.github.ShinChven.lib.CommonLib.R;

/**
 * Created by ShinChven on 14/8/19.
 * 使用此工具调用通用动画
 */
public class ActivitySwitchAnimator {

    /**
     * 从右边滚动进入
     *
     * @param thisActivity
     */
    public static void slideInFromRight(Activity thisActivity) {
        thisActivity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    /**
     * 从右边滚动退出
     *
     * @param thisActivity
     */
    public static void slideOutFromRight(Activity thisActivity) {
        thisActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    /**
     * 从右边覆盖进入
     *
     * @param thisActivity
     */
    public static void coverFromRight(Activity thisActivity) {
        thisActivity.overridePendingTransition(R.anim.slide_left_in, R.anim.abc_fade_out);
    }

    /**
     * 从右边覆盖退出
     *
     * @param thisActivity
     */
    public static void exitCoverFromRight(Activity thisActivity) {
        thisActivity.overridePendingTransition(R.anim.abc_fade_in, R.anim.slide_right_out);
    }


    /**
     * 向上滑入
     *
     * @param thisActivity
     */
    public static void enterSlideUp(Activity thisActivity) {
        try {
            thisActivity.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 向上滑入
     *
     * @param thisActivity
     */
    public static void exitSlideDown(Activity thisActivity) {
        thisActivity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_slide_out_bottom);
    }

}
