package com.wangjie.androidbucket.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie
 * Date: 14-3-29
 * Time: 上午12:18
 * To change this template use File | Settings | File Templates.
 */
public class ABPrefsUtil {
    private static ABPrefsUtil prefsUtil;
    public Context context;
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;

    public synchronized static ABPrefsUtil getInstance(){
        return prefsUtil;
    }

    public static void init(Context context, String prefsname, int mode) {
        prefsUtil = new ABPrefsUtil();
        prefsUtil.context = context;
        prefsUtil.prefs = prefsUtil.context.getSharedPreferences(prefsname, mode);
        prefsUtil.editor = prefsUtil.prefs.edit();
    }

    private ABPrefsUtil() {
    }


    public boolean getBoolean(String key, boolean defaultVal){
        return this.prefs.getBoolean(key, defaultVal);
    }
    public boolean getBoolean(String key){
        return this.prefs.getBoolean(key, false);
    }


    public String getString(String key, String defaultVal){
        return this.prefs.getString(key, defaultVal);
    }
    public String getString(String key){
        return this.prefs.getString(key, null);
    }

    public int getInt(String key, int defaultVal){
        return this.prefs.getInt(key, defaultVal);
    }
    public int getInt(String key){
        return this.prefs.getInt(key, 0);
    }


    public float getFloat(String key, float defaultVal){
        return this.prefs.getFloat(key, defaultVal);
    }
    public float getFloat(String key){
        return this.prefs.getFloat(key, 0f);
    }

    public long getLong(String key, long defaultVal){
        return this.prefs.getLong(key, defaultVal);
    }
    public long getLong(String key){
        return this.prefs.getLong(key, 0l);
    }

    public Set<String> getStringSet(String key, Set<String> defaultVal){
        return this.prefs.getStringSet(key, defaultVal);
    }
    public Set<String> getStringSet(String key){
        return this.prefs.getStringSet(key, null);
    }

    public Map<String, ?> getAll(){
        return this.prefs.getAll();
    }


    public void putString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value){
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putLong(String key, long value){
        editor.putLong(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putStringSet(String key, Set<String> value){
        editor.putStringSet(key, value);
        editor.commit();
    }


}
