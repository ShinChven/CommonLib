package com.github.ShinChven.lib.CommonLib.pagetransformer;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by ShinChven on 2014/9/30.
 */
public class SupportDepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            ViewCompat.setAlpha(view, 0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            ViewCompat.setAlpha(view, 1);
            ViewCompat.setTranslationX(view, 0);
            ViewCompat.setScaleX(view, 1);
            ViewCompat.setScaleY(view, 1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            ViewCompat.setAlpha(view, 1 - position);

            // Counteract the default slide transition
            ViewCompat.setTranslationX(view, pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            ViewCompat.setScaleX(view, scaleFactor);
            ViewCompat.setScaleY(view, scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            ViewCompat.setAlpha(view, 0);
        }
    }
}
