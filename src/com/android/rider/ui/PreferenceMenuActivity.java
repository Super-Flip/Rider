/**
 * @file PreferenceMenuActivity.java
 *
 * Riderアプリケーション設定 Activity
 *
 * @version 0.0.1
 *
 * @since 2012/02/07
 * @date  2012/02/07
 */
package com.android.rider.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Window;

import com.android.rider.R;

/** Rider アプリケーション設定画面 Activity.
*
* Riderアプリケーションの設定画面を管理する Activity クラス。\n
*/
public class PreferenceMenuActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    /** ログ用TAG. */
    private static final String TAG = "PreferenceMenuActivity";

    /** Activity 起動.
     *
     * リソースから設定画面を取得し、表示する。\n
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle savedInstanceState) start");

        /** タイトル非表示 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.preferencemenu);
        Log.i(TAG, "onCreate(Bundle savedInstanceState) finish");
    }

    /** Activity 再開.
     *
     * リスナーの設定、及び画面更新を行う。
     */
    @Override
    protected void onResume() {
        Log.i(TAG, "onResume() start");
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        Update();
        Log.i(TAG, "onResume() finish");
    }

    /** 画面更新.
     *
     * 画面の更新時にコールされる。\n
     * 設定ファイルから現設定を取得し、サマリーへの表示を行う。\n
     */
    private void Update() {
        Log.i(TAG, "Update() start");
        TypedArray level_entries = this.getResources().obtainTypedArray(R.array.ball_size_entries);
        TypedArray level_entryvalues = this.getResources().obtainTypedArray(R.array.ball_size_entryvalues);
        ListPreference list_preference = (ListPreference)getPreferenceScreen().findPreference("ball_size_key");
        for (int i = 0; i < level_entries.length(); i++) {
            if (list_preference.getValue().equals(level_entryvalues.getString(i))){
                list_preference.setSummary(level_entries.getString(i));
                break;
            }
        }
        Log.i(TAG, "Update() finish");
    }

    /** Activity 一時停止.
     *
     * Activity がバックグラウンドへ遷移する際にコールされる。\n
     * リスナーの解放を行う。\n
     */
    @Override
    protected void onPause() {
        Log.i(TAG, "onPause() start");
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        Log.i(TAG, "onPause() finish");
    }

    /** 設定更新.
     *
     * 設定の更新時にコールされる。\n
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        Log.i(TAG, "onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) start");
        Update();

        Log.i(TAG, "onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) finish");
    }

}
