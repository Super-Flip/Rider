/**
 * @file common.Item.java
 *
 * アイテム管理用クラス
 *
 * @version 0.0.1
 *
 * @since 2012/02/11
 * @date  2012/02/11
 */
package com.android.rider.common;

import android.graphics.Bitmap;
import android.util.Log;

/** アイテム管理用クラス.
*
* RiderBall 画面で使用するアイテム(アイテム・ゴールポケット)を管理するクラス
*
*/
public class Item {
    /** ログ用TAG. */
    private static final String TAG = "Item";
    /** ビットマップ. */
    public Bitmap bitmap;
    /** x 座標. */
    public float x;
    /** y 座標. */
    public float y;
    /** インデックス. */
    public int index;

    /** コンストラクタ.
     *
     * 指定された Bitmap 、座標を保管する。\n
     * @param bitmap
     * @param x     左 x 座標
     * @param y     上 y 座標
     * @param index インデックス
     */
    public Item(Bitmap bitmap, float x, float y, int index) {
        Log.i(TAG, "Item(Bitmap bitmap, float x, float y, int index) start");
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.index = index;
        Log.i(TAG, "Item(Bitmap bitmap, float x, float y, int index) finish");
    }

    @Override
    protected void finalize() throws Throwable {
        if(bitmap != null) {
            bitmap.recycle();
        }
    }

}
