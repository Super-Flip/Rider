/**
 * @file util/ImageBuffer.java
 *
 * Bitmap画像バッファクラス\n
 *
 * @version 0.0.1
 *
 * @since 2012/02/07
 * @date  2012/02/07
 */
package com.android.rider.util;

import android.graphics.Color;

/** 画像バッファクラス.
 *
 * 画像バッファ管理用クラス\n
 */
public class ImageBuffer
{
    /** 画面幅. */
    private int mWidth;
    /** 画面高さ. */
    private int mHeight;
    /** 画像バッファ. */
    private int[] mBuffer;

    /** 幅定数. */
    private final int DEFAULT_W = 8;
    /** 高さ定数. */
    private final int DEFAULT_H = 8;

    /** コンストラクタ.
     *
     * 引数なしの場合、クラス定数のバッファを生成する。\n
     */
    public ImageBuffer()
    {
        mBuffer = null;
        Resize(DEFAULT_W, DEFAULT_H);
    }

    /** コンストラクタ.
     *
     * 継承用ダミーコンストラクタ
     *
     * @param  dummy   super(null)を継承クラスで実行
     */
    public ImageBuffer(int[] dummy)
    {
    }

    /** コンストラクタ.
     *
     * 幅、高さ指定
     *
     * @param  w   画像幅
     * @param  h   画像高さ
     */
    public ImageBuffer(int w, int h)
    {
        mBuffer = null;
        Resize(w, h);
    }

    /** 画面幅取得.
     *
     * @return mWidth 画面幅\n
     */
    public int GetWidth() {
        return mWidth;
    }

    /** 画面高さ取得.
     *
     * @return mHeigth 画面高さ\n
     */
    public int GetHeight() {
        return mHeight;
    }

    /** 画像バッファ取得.
     *
     * @return mBuffer 画像バッファ\n
     */
    public int[] GetBuffer() {
        return mBuffer.clone();
    }

    /** バッファのリサイズ.
     *
     * @param  int   w   画像幅
     * @param  int   h   画像高さ
     */
    public void Resize(int w, int h)
    {
        mWidth = w;
        mHeight = h;
        mBuffer = new int[w * h];
    }

    /** 指定座標pixelのindexを取得.
     *
     * @param   w   画像幅
     * @param   h   画像高さ
     */
    public int GetPixelAddress(int x, int y)
    {
        if (x < 0 || x >= mWidth || y < 0 || y >= mHeight) {
            return -1;
        }
        return GetPixelAddressNC(x, y);
    }

    /** 指定座標pixelのindexを取得.
     *
     * ノンクリップ版
     *
     * @param   w   画像幅
     * @param   h   画像高さ
     */
    public int GetPixelAddressNC(int x, int y)
    {
        return (mWidth * y + x);
    }

    /** 指定座標pixelにカラーを設定.
     *
     * @param   w   画像幅
     * @param   h   画像高さ
     * @param   color   カラー(ARGB)
     */
    public void SetPixel(int x, int y, int color)
    {
        int pos = GetPixelAddress(x, y);
        if (pos == -1) {
            return;
        }
        mBuffer[pos] = color;
    }

    /** 指定座標pixelにカラーを設定.
     *
     * ノンクリップ版
     *
     * @param   w   画像幅
     * @param   h   画像高さ
     * @param   color   カラー(ARGB)
     *
     */
    public void SetPixelNC(int x, int y, int color)
    {
        mBuffer[GetPixelAddressNC(x, y)] = color;
    }

    /** 指定座標pixelのカラーを取得.
     *
     * 取得に失敗した場合、黒を返す。\n
     *
     * @param   w   画像幅
     * @param   h   画像高さ
     * @param   color   カラー(ARGB)
     */
    public int GetPixel(int x, int y)
    {
        int pos = GetPixelAddress(x, y);
        if (pos == -1) {
            return Color.BLACK;
        }
        return mBuffer[pos];
    }

    /** 指定座標pixelのカラーを取得
     *
     * ノンクリップ版
     *
     * @param   w   画像幅
     * @param   h   画像高さ
     * @param   color   カラー(ARGB)
     */
    public int GetPixelNC(int x, int y)
    {
        return mBuffer[GetPixelAddressNC(x, y)];
    }

    /** 透過処理.
     *
     * 指定された color の透過処理を行う。\n
     *
     * @param color 透過対象色
     */
    public void InvalidBufferFromColor(int color)
    {
        int buf_color = 0;
        for (int y = 0; y < mHeight; y++) {
            for (int x = 0; x < mWidth; x++) {
                buf_color = mBuffer[x + y * mWidth];
                if (((buf_color >>> 24) & 0xff) > 0) {
                    if ((buf_color & 0xffffff) == (color & 0xffffff)) {
                        mBuffer[x + y * mWidth] = (buf_color & 0xffffff);
                    }
                }
            }
        }
    }

    /** バッファ間クリップ矩形転送.
     *
     * @param   src   転送元画像バッファ
     * @param   info   転送情報
     */
    public void BltClip(ImageBuffer src, ImageBuffer.ClipBltInfo info)
    {
        ImageBuffer.ClipInfo ss, ds;
        ss = new ImageBuffer.ClipInfo(src.GetWidth(), src.GetHeight());
        ds = new ImageBuffer.ClipInfo(GetWidth(), GetHeight());

        /** 転送領域がない場合は、何もせずに終了 */
        if (!CheckClipBlt(ss, ds, info)) {
            return;
        }

        int[] src_buffer = src.GetBuffer();
        for (int y = info.dy; y < info.dy + info.h; y++) {
            int sx = src.GetPixelAddress(info.sx, info.sy + y - info.dy);
            int dx = GetPixelAddress(info.dx, y);
            System.arraycopy(src_buffer, sx, mBuffer, dx, info.w);
        }
        src_buffer = null;
    }

    /** 矩形転送時クリップ処理.
     *
     * @param   src   転送元クリップ範囲情報
     * @param   dst   転送先クリップ範囲情報
     * @param   info   転送情報
     */
    private boolean CheckClipBlt(ImageBuffer.ClipInfo src, ImageBuffer.ClipInfo dst, ImageBuffer.ClipBltInfo info)
    {
        /** 領域外の場合は何もせずに終了 */
        if ((info.w + info.dx) <= 0) {
            return false;
        }
        if ((info.h + info.dy) <= 0) {
            return false;
        }
        if (info.dx >= dst.w) {
            return false;
        }
        if (info.dy >= dst.h) {
            return false;
        }

        // 転送元の領域を参照しない(スキップ可)
        if (info.sx >= src.w) {
            return false;
        }
        if (info.sy >= src.h) {
            return false;
        }
        if ((info.sx + info.w) < 0) {
            return false;
        }
        if ((info.sy + info.h) < 0) {
            return false;
        }

        // 元画像をはみだして参照してしまう(要クリッピング)
        if ((info.sx + info.w) >= src.w) {
            info.w = src.w - info.sx;
        }
        if ((info.sy + info.h) >= src.h) {
            info.h = src.h - info.sy;
        }

        // 転送元の座標が負(要クリッピング)
        if (info.sx < 0) {
            info.dx += (-info.sx);
            info.w -= (-info.sx);
            info.sx = 0;
        }
        if (info.sy < 0) {
            info.dy += (-info.sy);
            info.h -= (-info.sy);
            info.sy = 0;
        }

        // 転送先座標が負(要クリッピング)
        if (info.dx < 0) {
            info.sx -= info.dx;
            info.w += info.dx;
            info.dx = 0;
        }
        if (info.dy < 0) {
            info.sy -= info.dy;
            info.h += info.dy;
            info.dy = 0;
        }

        // 転送元の矩形が、転送先の画像からはみ出る(要クリッピング)
        if ((info.sx + info.w) > dst.w) {
            info.w = dst.w - info.dx;
        }
        if ((info.sy + info.h) > dst.h) {
            info.h = dst.h - info.dy;
        }

        // 転送領域がない(スキップ)
        if (info.w < 1) {
            return false;
        }
        if (info.h < 1) {
            return false;
        }

        return true;
    }

    /** クリップ矩形塗りつぶし.
     *
     * @param   info   塗りつぶし情報
     */
    public void FillClip(ImageBuffer.ClipFillInfo info)
    {
        ImageBuffer.ClipInfo ds;
        ds = new ImageBuffer.ClipInfo(GetWidth(), GetHeight());

        /** 転送領域がない場合は何もせずに終了 */
        if (!CheckClipFill(ds, info)) {
            return;
        }

        for (int y = info.dy; y < info.dy + info.h; y++) {
            for (int x = info.dx; x < info.dx + info.w; x++) {
                SetPixelNC(x, y, info.color);
            }
        }
    }

    /** 矩形塗りつぶしクリップ処理.
     *
     * @param   dst   塗りつぶし先クリップ範囲
     * @param   info   塗りつぶし情報
     */
    private boolean CheckClipFill(ImageBuffer.ClipInfo dst, ImageBuffer.ClipFillInfo info)
    {
        // 描画の必要なし
        if (info.dx >= dst.w) {
            return false;
        }
        if (info.dy >= dst.h) {
            return false;
        }
        if ((info.dx + info.w) <= 0) {
            return false;
        }
        if ((info.dy + info.h) <= 0) {
            return false;
        }

        // 描画先が負なら幅、高さを縮める
        if (info.dx < 0) {
            info.w += info.dx;
            info.dx = 0;
        }
        if (info.dy < 0) {
            info.h += info.dy;
            info.dy = 0;
        }

        // 描画サイズがはみ出るので、同様に縮める
        if ((info.dx + info.w) > dst.w) {
            info.w = dst.w - info.dx;
        }
        if ((info.dy + info.h) > dst.h) {
            info.h = dst.h - info.dy;
        }

        return true;
    }

    /** クリップ範囲情報管理用内部クラス.
     *
     */
    private class ClipInfo {
        public int w = 0;
        public int h = 0;

        public ClipInfo(int w, int h)
        {
            this.w = w;
            this.h = h;
        }
    }

    /** 矩形転送クリップ情報管理用内部クラス.
     *
     */
    public class ClipBltInfo {
        public int sx;
        public int sy;
        public int dx;
        public int dy;
        public int w;
        public int h;

        public ClipBltInfo()
        {
            this.sx = 0;
            this.sy = 0;
            this.dx = 0;
            this.dy = 0;
            this.w = 0;
            this.h = 0;
        }

        public ClipBltInfo(int sx, int sy, int dx, int dy, int w, int h)
        {
            this.sx = sx;
            this.sy = sy;
            this.dx = dx;
            this.dy = dy;
            this.w = w;
            this.h = h;
        }
    }

    /** 矩形塗りつぶしクリップ情報管理用内部クラス.
     *
     */
    public class ClipFillInfo {
        public int dx;
        public int dy;
        public int w;
        public int h;
        public int color;

        public ClipFillInfo()
        {
            this.dx = 0;
            this.dy = 0;
            this.w = 0;
            this.h = 0;
            this.color = Color.BLACK;
        }

        public ClipFillInfo(int dx, int dy, int w, int h, int color)
        {
            this.dx = dx;
            this.dy = dy;
            this.w = w;
            this.h = h;
            this.color = color;
        }
    }
}
