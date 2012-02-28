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
    /** 速度. */
    public float m;

    /** 前回 X 座標 */
    public float prevX = -1;
    /** 前回 Y 座標 */
    public float prevY = -1;

    /** コンストラクタ.
     *
     * 指定されたボールサイズ、座標、移動距離、RGBを保管する。\n
     * @param radius
     * @param x
     * @param y
     * @param dx
     * @param dy
     * @param m
     */
    public Circle(float radius, float x, float y, float dx, float dy, float m) {
        Log.i(TAG, "Circle(float radius, float x, float y, float dx, float dy, float m) start");

        this.radius = radius;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.m = m;

        Log.i(TAG, "Circle(float radius, float x, float y, float dx, float dy, float m) finish");
    }
}
