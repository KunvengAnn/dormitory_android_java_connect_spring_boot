package com.example.dormitory_ui.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    private static final String PREFERENCES_FILE = "com.example.dormitory_ui.preferences";
    private static SharePref instance;
    private SharedPreferences sharedPreferences;

    private SharePref(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public static synchronized SharePref getInstance(Context context) {
        if (instance == null) {
            instance = new SharePref(context.getApplicationContext());
        }
        return instance;
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
