package com.github.ShinChven.lib.CommonTools;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by ShinChven on 2014/10/21.
 */
public class BitmapTool {
    private static final String SCHEME_CONTENT = "content";
    private static final String SCHEME_FILE = "file";

    public static Bitmap zip(Context context, Uri uri,
                             int reqWidth, int reqHeight) throws IOException {

        // First decode with inJustDecodeBounds=true to check dimensions  开启测量模式
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // SCHEME 判断类型
        if (uri.getScheme().equals(SCHEME_CONTENT)) {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        } else if (uri.getScheme().equals(SCHEME_FILE)) {
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
     * @throws IOException
     */
    private static Bitmap getRotatedBitmap(Context context, Uri uri, Bitmap bitmap) throws IOException {
        String realPath = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            realPath = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            realPath = cursor.getString(idx);
            cursor.close();
        }

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

    public static Bitmap zip(Resources res, int resId,
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
}
