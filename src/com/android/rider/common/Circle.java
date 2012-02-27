/**
 * @file Circle.java
 *
 * RiderBall 画面でのボール管理 用クラス
 *
 * @version 0.0.1
 *
 * @since 2012/02/01
 * @date  2012/02/01
 */
package com.android.rider.common;

import android.util.Log;

/** ボール管理用クラス.
 *
 * RiderBall 画面で使用するボールを管理するクラス
 *
 */
public class Circle {
    /** ログ用TAG. */
    private static final String TAG = "Circle";
    /** 半径 */
    public float radius;
    /** x 座標 */
    public float x;
    /** y 座標 */
    public float y;
    /** x 方向への進行距離 */
    public float dx;
    /** y 方向への進行距離 */
    public float dy;

    public float m;

    /** R(赤) */
    public int cr;
    /** G(緑) */
    public int cg;
    /** B(青) */
    public int cb;

    public float prevX;
    public float prevY;

    /** コンストラクタ.
     *
     * 指定されたボールサイズ、座標、移動距離、RGBを保管する。\n
     * @param radius
     * @param x
     * @param y
     * @param dx
     * @param dy
     * @param m
     * @param cr
     * @param cb
     * @param cg
     */
    public Circle(float radius, float x, float y, float dx, float dy, float m, int cr, int cb, int cg) {
        Log.i(TAG, "Circle(float radius, float x, float y, float dx, float dy, float m, int cr, int cb, int cg) start");
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.m = m;
        this.cr = cr;
        this.cg = cg;
        this.cb = cb;
        Log.i(TAG, "Circle(float radius, float x, float y, float dx, float dy, float m, int cr, int cb, int cg) finish");
    }
}
