package com.github.ShinChven.lib.CommonLib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.*;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.*;

/**
 * Created by ShinChven on 2014/10/21.
 */
public class BitmapUtil {
    public static final String SCHEME_CONTENT = "content";
    public static final String SCHEME_FILE = "file";

    public static Bitmap resize(Context context, Uri uri,
                                int reqWidth, int reqHeight) throws IOException {

        // First decode with inJustDecodeBounds=true to check dimensions  开启测量模式
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        if (uri.getScheme().equals(SCHEME_CONTENT)) {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        } else if (uri.getScheme().equals(SCHEME_FILE)) {
            BitmapFactory.decodeStream(new FileInputStream(new File(uri.getPath())), null, options);
        } else {
            BitmapFactory.decodeStream(new FileInputStream(new File(uri.getPath())), null, options);
        }


        // Calculate inSampleSize  计算预览比例
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set 按照预览比例载入bitmap
        options.inJustDecodeBounds = false;
        Bitmap bitmap = null;
        if (uri.getScheme().equals(SCHEME_CONTENT)) {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        } else if (uri.getScheme().equals(SCHEME_FILE)) {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(uri.getPath())), null, options);
        }

        bitmap = getRotatedBitmap(context, uri, bitmap);
        return bitmap;
    }

    /**
     * 获取图片存储位置，并根据方向反转图片
     *
     * @param context
     * @param uri
     * @param bitmap
     * @return
     * @throws java.io.IOException
     */
    private static Bitmap getRotatedBitmap(Context context, Uri uri, Bitmap bitmap) throws IOException {
        String realPath = null;
//        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
//        if (cursor == null) { // Source is Dropbox or other similar local file path
//            realPath = uri.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            realPath = cursor.getString(idx);
//            cursor.close();
//        }

        realPath = uri.getPath();

        // 获取方向
        ExifInterface exif = new ExifInterface(realPath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        bitmap = rotateBitmap(bitmap, orientation);
        return bitmap;
    }

    /**
     * 反转图片
     *
     * @param bitmap
     * @param orientation
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Bitmap zip(Resources res, int resId, int size , int compressPercent, Bitmap.CompressFormat compressFormat) {
        Bitmap resized = resize(res, resId, size, size);
        return compress(resized, compressPercent, compressFormat);
    }

    public static Bitmap compress(Resources res, int resId, int compressPercent, Bitmap.CompressFormat compressFormat) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        return compress(bitmap, compressPercent, compressFormat);
    }

    public static Bitmap compress(Bitmap bitmap, int compressPercent, Bitmap.CompressFormat compressFormat) {
        Bitmap compressed = null;
        try {
            ByteArrayOutputStream bucket = new ByteArrayOutputStream();
            bitmap.compress(compressFormat, compressPercent, bucket);
            ByteArrayInputStream bais = new ByteArrayInputStream(bucket.toByteArray());
            compressed = BitmapFactory.decodeStream(bais);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        } catch (OutOfMemoryError e) {
            LogUtil.printStackTrace(e);
        }
        try {
            bitmap.recycle();
            bitmap = null;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return compressed;
    }

    public static Bitmap resize(Resources res, int resId,
                                int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        // 原始大小大于设置大小时，按照设置高宽长的一方为上限进行缩放等比缩放，而不考虑短的一方。inSampleSize反回2就是起这个作用。
        if (height > reqHeight || width > reqWidth) {

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((height / inSampleSize) > reqHeight
                    && (width / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * 将图片截取为圆角图片
     *
     * @param bitmap 原图片
     * @param ratio  截取比例，如果是8，则圆角半径是宽高的1/8，如果是2，则是圆形图片
     * @return 圆角矩形图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float ratio) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /********
     * 按短边截取正矩形
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRectBySmall(Bitmap bitmap) {
        int small = 0;
        int x = 0;
        int y = 0;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            x = (bitmap.getWidth() - bitmap.getHeight()) / 2;
            y = 0;
            small = bitmap.getHeight();
        } else {
            x = 0;
            y = (bitmap.getHeight() - bitmap.getWidth()) / 2;
            small = bitmap.getWidth();
        }


        return Bitmap.createBitmap(bitmap, x, y, small, small);
    }
}
