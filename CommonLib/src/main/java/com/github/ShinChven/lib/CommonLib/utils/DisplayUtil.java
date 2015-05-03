package com.github.ShinChven.lib.CommonLib.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ShinChven on 2014/10/31.
 */
public class DisplayUtil {

    /**
     * 将View 调整至目标高宽
     *
     * @param view   要调整的View
     * @param matrix 尺寸
     */
    public static void fit(View view, DisplayMatrix matrix) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = matrix.height;
        layoutParams.width = matrix.width;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按比例缩放
     *
     * @param displayWidth   显示宽度
     * @param originalWidth  原始宽度
     * @param originalHeight 原始高度
     * @return
     */
    public static DisplayMatrix zoomWithWidth(int displayWidth, int originalWidth, int originalHeight) {
        float ratio = (float) displayWidth / (float) originalWidth;
        float adjustedHeight = originalHeight * ratio;
        return new DisplayMatrix(displayWidth, (int) adjustedHeight);
    }

    /**
     * 按比例缩放
     *
     * @param displayHeight  显示高度
     * @param originalWidth  原始宽度
     * @param originalHeight 原始高度
     * @return
     */
    public static DisplayMatrix zoomWithHeight(int displayHeight, int originalWidth, int originalHeight) {
        float ratio = (float) displayHeight / (float) originalHeight;
        float adjustedWidth = originalWidth * ratio;
        return new DisplayMatrix((int) adjustedWidth, displayHeight);
    }

    /**
     * 保存高宽
     */
    public static class DisplayMatrix {

        public DisplayMatrix(int width, int height) {
            this.width = width;
            this.height = height;
            LogUtil.i("DisplayMatrix", String.format("%s x %s", String.valueOf(width), String.valueOf(height)));
        }

        /**
         * 宽度
         */
        public int width;
        /**
         * 高度
         */
        public int height;
    }
}
