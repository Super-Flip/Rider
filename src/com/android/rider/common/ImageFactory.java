/**
 * @file common.ImageFactory.java
 *
 * Bitmap画像生成用クラス
 *
 * @version 0.0.1
 *
 * @since 2012/02/11
 * @date  2012/02/11
 */
package com.android.rider.common;

import java.util.Random;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.rider.R;
import com.android.rider.util.ImageUtils;

/** ライダー画像生成用クラス.
 *
 * ライダー画像の生成を行う
 *
 */
public class ImageFactory {
    /** ログ用TAG. */
    private static final String TAG = "ImageFactory";
    /** 親 Activity. */
    private Activity mParent = null;
    /** 画像リソース配列. */
    TypedArray mPictures = null;

    /** コンストラクタ.
     *
     * @param activity   親 Activity
     */
    public ImageFactory(Activity activity) {
        Log.i(TAG, "ImageFactory(Activity activity) start");
        mParent = activity;
        mPictures = mParent.getResources().obtainTypedArray(R.array.array_pictures);
        Log.i(TAG, "ImageFactory(Activity activity) finish");
    }

    /** ライダー画像取得.
     *
     * リソースファイルからランダムな一枚を生成し、返却する。
     *
     * @return Bitmap ライダー画像ビットマップ
     */
    public Bitmap getRiderBitmap() {
        Random rnd = new Random();
        Drawable drawable = mPictures.getDrawable(rnd.nextInt(mPictures.length()));
        Bitmap src = ((BitmapDrawable) drawable).getBitmap();
        Bitmap dst = ImageUtils.resizeBitmapToDisplaySize(mParent, src);
        return dst;
    }

    /** アイテム画像取得.
     *
     * @return Bitmap アイテムビットマップ
     */
    public Bitmap getItemBitmap(int id) {
        Bitmap src = null;
        switch(id) {
        case R.id.goal_pocket:
            src = BitmapFactory.decodeResource(mParent.getResources(), R.drawable.goal_pocket);
            break;
        case R.id.double_ball:
            src = BitmapFactory.decodeResource(mParent.getResources(), R.drawable.double_ball);
            break;
        }
        return src;
    }

}
