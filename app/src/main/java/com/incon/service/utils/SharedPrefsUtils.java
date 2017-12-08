package com.incon.service.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import com.incon.service.ConnectApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * A pack of helpful getter and setter methods for reading/writing to {@link SharedPreferences}.
 */
final public class SharedPrefsUtils {

    private static final String APP_PREF_KEY = "AppSharedPreferences";
    private static final String LOGIN_PREF_KEY = "LoginSharedPreferences";
    private static final String CACHE_PREF_KEY = "cacheSharedPreferences";


    private static Map<String, SharedPrefsUtils> preferencesMap = new HashMap<>();

    private SharedPreferences prefs;

    public static SharedPrefsUtils loginProvider() {
        return provider(LOGIN_PREF_KEY);
    }

    public static SharedPrefsUtils appProvider() {
        return provider(APP_PREF_KEY);
    }

    public static SharedPrefsUtils cacheProvider() {
        return provider(APP_PREF_KEY);
    }

    public static SharedPrefsUtils provider(String key) {
        if (preferencesMap.get(key) == null) {
            SharedPrefsUtils sharedPrefsUtils = new SharedPrefsUtils(
                    ConnectApplication.getAppContext().getSharedPreferences(
                            key, Context.MODE_PRIVATE));
            preferencesMap.put(key, sharedPrefsUtils);
        }
        return preferencesMap.get(key);
    }


    private SharedPrefsUtils(SharedPreferences prefs) {
        this.prefs = prefs;
    }


    private SharedPreferences getSharedPrefsInstance() {
        return prefs;
    }

    /**
     * Helper method to retrieve a String value from {@link SharedPreferences}.
     *
     * @param key
     * @return The value from shared preferences, or null if the value could not be read.
     */
    public String getStringPreference(String key) {
        String value = null;
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            value = preferences.getString(key, null);
        }
        return value;
    }

    /**
     * Helper method to write a String value to {@link SharedPreferences}.
     *
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public boolean setStringPreference(String key, String value) {
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve a float value from {@link SharedPreferences}.
     *
     * @param key
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public float getFloatPreference(String key, float defaultValue) {
        float value = defaultValue;
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            value = preferences.getFloat(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a float value to {@link SharedPreferences}.
     *
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public boolean setFloatPreference(String key, float value) {
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve a long value from {@link SharedPreferences}.
     *
     * @param key
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public long getLongPreference(String key, long defaultValue) {
        long value = defaultValue;
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            value = preferences.getLong(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a long value to {@link SharedPreferences}.
     *
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public boolean setLongPreference(String key, long value) {
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve an integer value from {@link SharedPreferences}.
     *
     * @param key
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public int getIntegerPreference(String key, int defaultValue) {
        int value = defaultValue;
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            value = preferences.getInt(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write an integer value to {@link SharedPreferences}.
     *
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public boolean setIntegerPreference(String key, int value) {
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve a boolean value from {@link SharedPreferences}.
     *
     * @param key
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public boolean getBooleanPreference(String key, boolean defaultValue) {
        boolean value = defaultValue;
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            value = preferences.getBoolean(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a boolean value to {@link SharedPreferences}.
     *
     * @param key
     * @param value
     * @return true if the new value was successfully written to persistent storage.
     */
    public boolean setBooleanPreference(String key, boolean value) {
        SharedPreferences preferences = getSharedPrefsInstance();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return false;
    }

    public boolean clear() {
        return prefs.edit().clear().commit();
    }
}