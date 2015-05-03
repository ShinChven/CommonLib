package com.github.ShinChven.lib.CommonLib.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by ShinChven on 2014/9/18.
 * 反射的ViewHolder，收集自互联网，未能找到原始出处。
 */
public class ViewHolder {

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
