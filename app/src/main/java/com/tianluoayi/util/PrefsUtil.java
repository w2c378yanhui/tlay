package com.tianluoayi.util;

import android.content.SharedPreferences;

import com.tianluoayi.application.NeoApplication;

import java.util.Set;


public class PrefsUtil {
    private SharedPreferences sharedPreferences;
    private static PrefsUtil prefsUtil = new PrefsUtil();

    //判断该版本是否第一次启动
    public static final String VERSION_FIRST_LOGIN = "version_first_login";
    //歌单数量
    public static final String MUSIC_LIST_COUNT = "music_list_count";

    public static PrefsUtil getPrefs() {
        return prefsUtil;
    }

    public PrefsUtil() {
        sharedPreferences = NeoApplication.getmInstance().getSharedPreferences();
    }

    public void setString(String key, String defValue) {
        sharedPreferences.edit().putString(key, defValue).apply();
    }

    public void setStringSet(String key, Set<String> defValues) {
        sharedPreferences.edit().putStringSet(key, defValues).apply();
    }

    public void setInt(String key, int defValue) {
        sharedPreferences.edit().putInt(key, defValue).apply();
    }

    public void setLong(String key, long defValue) {
        sharedPreferences.edit().putLong(key, defValue).apply();
    }

    public void setFloat(String key, float defValue) {
        sharedPreferences.edit().putFloat(key, defValue).apply();
    }

    public void setBoolean(String key, boolean defValue) {
        sharedPreferences.edit().putBoolean(key, defValue).apply();
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValues) {
        return sharedPreferences.getStringSet(key, defValues);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }
}
