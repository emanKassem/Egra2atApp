package com.example.a2laa.egra2atapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a2laa.egra2atapp.app.App;

public class PrefUtils {
    /**
     * Storing Keys in shared preferences to
     * use it in retrofit requests and in any part of the app
     */
    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
    }

    public static void storeKeys(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getKeys(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }

    public static void clear(){
        SharedPreferences.Editor editor = getSharedPreferences(App.getContext()).edit();
        editor.clear();
        editor.commit();
    }
}
