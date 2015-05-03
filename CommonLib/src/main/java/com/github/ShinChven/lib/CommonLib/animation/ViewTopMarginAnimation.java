package com.github.ShinChven.lib.CommonLib.animation;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by ShinChven on 15/5/3.
 * <p>
 * you may use like this <p>
 * <pre>
 *    Animation anim = new ViewTopMarginAnimation().setup(view, marginMovement, duration);
 *    view.setAnimation(anim);
 * </pre>
 */
public class ViewTopMarginAnimation extends Animation {


    private int marginMovement;
    private View view;

    public ViewTopMarginAnimation setup(View view, int marginMovement, int duration) {
        this.view = view;
        this.marginMovement = marginMovement;
        this.setDuration(duration);
        return this;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.topMargin = (int) (marginMovement * interpolatedTime);
        view.setLayoutParams(params);
    }

}
