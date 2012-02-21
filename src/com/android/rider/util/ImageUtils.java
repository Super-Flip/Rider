/**
 * @file ImageUtils.java
 *
 * Bitmap画像リサイズ用Utilityクラス
 *
 * @version 0.0.1
 *
 * @since 2012/02/01
 * @date  2012/02/01
 */
package com.android.rider.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;

/** Bitmap画像リサイズ用クラス.
 *
 * 画面に合わせた画像のリサイズを行う。
 *
 */
public class ImageUtils {
    /** ログ表示用TAG. */
    private static final String TAG = "ImageUtils";

    /** Bitmap 画像リサイズ.
     *
     * @param activity  Activity
     * @param src       リサイズ対象のBitmap
     * @return dst      リサイズ後のBitmap
     */
    public static Bitmap resizeBitmapToDisplaySize(Activity activity, Bitmap src) {
        Log.i(TAG,"resizeBitmapToDisplaySize(Activity activity, Bitmap src) start");
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();

        /** 画面サイズを取得 */
        Matrix matrix = new Matrix();
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float screenWidth = (float) metrics.widthPixels;
        float screenHeight = (float) metrics.heightPixels;


        float widthScale = screenWidth / srcWidth;
        float heightScale = screenHeight / srcHeight;
        if (widthScale > heightScale) {
            matrix.postScale(heightScale, heightScale);
        } else {
            matrix.postScale(widthScale, widthScale);
        }

        /** Bitmap画像のリサイズ */
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, srcWidth, srcHeight, matrix, true);
        src = null;
        Log.i(TAG,"resizeBitmapToDisplaySize(Activity activity, Bitmap src) finish");
        return dst;
    }

    /** Bitmap 画像リサイズ.
    *
    * @param src       リサイズ対象のBitmap
    * @param size      変換後のサイズ
    * @return dst      リサイズ後のBitmap
    */
   public static Bitmap resizeBitmapToSpecifiedSize(Bitmap src, float size) {
       Log.i(TAG,"resizeBitmapToSpecifiedSize(Bitmap src, int size) start");
       int srcWidth = src.getWidth();
       int srcHeight = src.getHeight();

       /** 画面サイズを取得 */
       Matrix matrix = new Matrix();
       float widthScale = size / srcWidth;
       float heightScale = size / srcHeight;
       if (widthScale > heightScale) {
           matrix.postScale(heightScale, heightScale);
       } else {
           matrix.postScale(widthScale, widthScale);
       }

       /** Bitmap画像のリサイズ */
       Bitmap dst = Bitmap.createBitmap(src, 0, 0, srcWidth, srcHeight, matrix, true);
       src = null;
       Log.i(TAG,"resizeBitmapToSpecifiedSize(Bitmap src, int size) finish");
       return dst;
   }
}