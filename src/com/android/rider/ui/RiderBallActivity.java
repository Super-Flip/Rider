/**
 * @file RiderActivity.java
 *
 * RiderBall 用 Activity クラス
 *
 * @version 0.0.1
 *
 * @since 2012/02/01
 * @date  2012/02/01
 */
package com.android.rider.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.android.rider.R;
import com.android.rider.common.RiderView;


/** RiderBall 画面用 Activity.
 *
 * RiderBall 画面用の Activity クラス。\n
 */
public class RiderBallActivity extends Activity implements SensorEventListener {
    /** ログ用TAG. */
    private static final String TAG = "RiderActivity";
    /** センサーマネージャー. */
    private SensorManager mSensorManager;
    /** センサー. */
    private Sensor mAccelerometer;
    /** SurfaceView. */
    private RiderView mRiderView;

    /** オプションメニューID クリア. */
    private static final int MENU_ID_CLEAR = (Menu.FIRST + 1);
    /** オプションメニューID 設定. */
    private static final int MENU_ID_SETTING = (Menu.FIRST + 2);


    /** Activity 起動.
     *
     * 表示 View の取得、及びセンサーの取得を行う。\n
     */
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle savedInstanceState) start");
        super.onCreate(savedInstanceState);

        /** 画面を縦表示で固定 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /** タイトル非表示 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mRiderView = new RiderView(this);
        setContentView(mRiderView);

        /** センサーの取得 */
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> list;
        list = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (list.size() > 0) {
            mAccelerometer = list.get(0);
        }
        Log.i(TAG, "onCreate(Bundle savedInstanceState) finish");
    }

    /** Activity 再開.
     *
     * センサーマネージャーにリスナーを設定し、処理を開始する。
     */
    protected void onResume() {
        Log.i(TAG, "onResume() start");
        super.onResume();

        /** センサー処理を開始する */
        if (mAccelerometer!=null) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }
        Log.i(TAG, "onResume() finish");
    }

    /** Activity 停止.
    *
    * Activity のバックグラウンド移行時にコールされる\n
    */
    @Override
    protected void onPause() {
        Log.i(TAG, "onPause() start");

        /** センサーの処理を停止する */
        mSensorManager.unregisterListener(this);

        super.onPause();
        Log.i(TAG, "onPause() finish");
    }

    /** センサー変化.
     *
     * センサー状態の変化時にコールされる。\n
     * 表示 View へ、状態変化の通知を行う。
     */
    public void onSensorChanged(SensorEvent event) {
        /** 加速度の取得 */
        if (event.sensor == mAccelerometer) {
            mRiderView.setAcce(-event.values[0]*0.2f, event.values[1]*0.2f);
        }
    }

    /** 精度変更イベントの処理.
     *
     */
    public void onAccuracyChanged(Sensor sensor,int accuracy) {
        // 現在未使用
    }

    /** Activity破棄.
     *
     */
    public void onDestroy() {
        Log.i(TAG, "onDestroy() start");
        super.onDestroy();
        Log.i(TAG, "onDestroy() finish");
    }

    /** オプションメニュー生成.
     *
     * Activity生成時に一度だけコールされる。\n
     * OptionMenuのアイテムをセットする。\n
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu(Menu menu) start");
        menu.add(Menu.NONE, MENU_ID_CLEAR, Menu.NONE, R.string.clear);
        menu.add(Menu.NONE,MENU_ID_SETTING, Menu.NONE, R.string.setting);
        Log.i(TAG, "onCreateOptionsMenu(Menu menu) finish");
        return super.onCreateOptionsMenu(menu);
    }

    /** オプションメニュー表示.
     *
     * OptionMenuの表示毎にコールされる。\n
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu(Menu menu) start");
        Log.i(TAG, "onPrepareOptionsMenu(Menu menu) finish");

        return super.onPrepareOptionsMenu(menu);
    }

    /** オプションメニュー選択.
     *
     * OptionMenuの選択時にコールされる。\n
     * 選択されたアイテム毎の処理を行う。
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected(MenuItem item) start");
        boolean ret = true;
        switch (item.getItemId()) {
        case MENU_ID_CLEAR:
            /** ボールを消去 */
            mRiderView.setClear();
            break;
        case MENU_ID_SETTING:
            /** 設定画面起動 */
            Intent intent = new Intent();
            intent.setClass(this, PreferenceMenuActivity.class);
            startActivity(intent);
            break;
        default:
            ret = super.onOptionsItemSelected(item);
            break;
        }
        Log.i(TAG, "onOptionsItemSelected(MenuItem item) finish");
        return ret;
    }

}
