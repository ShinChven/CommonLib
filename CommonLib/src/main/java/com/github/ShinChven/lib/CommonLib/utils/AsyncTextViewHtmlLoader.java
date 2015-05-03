package com.github.ShinChven.lib.CommonLib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShinChven on 15/4/16.<p></p>
 * 异步向TextView中加载html 图片 <p></p>
 * use like this <p></p>
 * <pre>
 *      TextViewHtmlLoader.loadImageAsync(SplashActivity.this, htmlText, html, 20,
 *              new TextViewHtmlLoader.OnLoadingImageFinishedListener() {
 *                   @Override
 *                   public void onImageLoadingFinished(final Spanned htmlBody) {
 *                      mHandler.post(new Runnable() {
 *                          @Override
 *                          public void run() {
 *                              htmlText.setText(htmlBody);
 *                          }
 *                      });
 *                  }
 *              });
 * </pre>
 */
public class AsyncTextViewHtmlLoader {

    // use like this
    //        TextViewHtmlLoader.loadImageAsync(SplashActivity.this, htmlText, html, 20,
//                new TextViewHtmlLoader.OnLoadingImageFinishedListener() {
//                    @Override
//                    public void onImageLoadingFinished(final Spanned htmlBody) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                htmlText.setText(htmlBody);
//                            }
//                        });
//                    }
//                });


    public static final int LIMBO = 10;

    /**
     * @param context
     * @param htmlText 要加载的TextView，仅用于加载无图片的文字预览
     * @param html     HTML
     * @param textSize 字号
     * @param listener 响应监听，请在此监听中将返回的Spanned 手动填充到TextView中去
     */
    public static void loadImageAsync(final Context context, final TextView htmlText,
                                      final String html, int textSize,
                                      final AsyncTextViewHtmlLoader.OnLoadingImageFinishedListener listener) {
        final Map<String, Drawable> drawablePool = new HashMap<>();

        Spanned fromHtml = Html.fromHtml(html, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                // todo 先不加载图片
                return null;
            }
        }, null);
        htmlText.setText(fromHtml);

        if (textSize <= 0) {
            htmlText.setTextSize(20);
        } else {
            htmlText.setTextSize(textSize);
        }
        // 完成之后开起线程加载图片
        // todo 未完成功能：改用AsyncTask中的postExecute方法直接填充数据到TextView，而不需要再将spanned返回出去
        new Thread(new Runnable() {

            int count = 0;

            @Override
            public void run() {
                final Spanned full = Html.fromHtml(html, new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        count = count + 1;
                        if (count > LIMBO) { // 这个工具并不完善，没有处理性能上的问题，先限制图片个数，保证不会oom
                            LogUtil.i("count", String.valueOf(count));
                            return null;
                        }

                        // todo use universal image loader to manage memory
                        if (drawablePool.containsKey(source)) {
                            return drawablePool.get(source);
                        }
                        // todo load your image async here
                        ImageLoader loader = UniversalImageLoaderAssist.getLoader(context);
                        Bitmap bp = loader.loadImageSync(source);
                        Drawable drawable = new BitmapDrawable(context.getResources(), bp);
                        int width = htmlText.getMeasuredWidth();
                        DisplayUtil.DisplayMatrix matrix = DisplayUtil.zoomWithWidth(width, bp.getWidth(), bp.getHeight());
                        drawable.setBounds(0, 0, matrix.width, matrix.height);
                        return drawable;
                    }
                }, null);

                listener.onImageLoadingFinished(full);

            }
        }).start();

    }

    public interface OnLoadingImageFinishedListener {
        void onImageLoadingFinished(Spanned htmlBody);
    }

}

