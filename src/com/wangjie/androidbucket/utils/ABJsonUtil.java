package com.wangjie.androidbucket.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 对json解析的包装
 * @author wangjie
 * @version 创建时间：2013-3-26 上午11:08:45
 */
public class ABJsonUtil {

    public static String getString(JSONObject jo, String key){
        try {
            return jo.getString(key);
        } catch (JSONException e) {
            return "";
        }
    }

    public static String getString(JSONObject jo, String key, String defaultValue){
        try {
            return jo.getString(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }



    public static int getInt(JSONObject jo, String key){
        try {
            return jo.getInt(key);
        } catch (JSONException e) {
            return 0;
        }
    }

    public static int getInt(JSONObject jo, String key, int defaultValue){
        try {
            return jo.getInt(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }



    public static long getLong(JSONObject jo, String key){
        try {
            return jo.getLong(key);
        } catch (JSONException e) {
            return 0l;
        }
    }
    public static long getLong(JSONObject jo, String key, long defaultValue){
        try {
            return jo.getLong(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }


    public static boolean getBoolean(JSONObject jo, String key){
        try {
            return jo.getBoolean(key);
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean getBoolean(JSONObject jo, String key, boolean defaultValue){
        try {
            return jo.getBoolean(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }



    public static double getDouble(JSONObject jo, String key){
        try {
            return jo.getDouble(key);
        } catch (JSONException e) {
            return 0.0;
        }
    }

    public static double getDouble(JSONObject jo, String key, double defaultValue){
        try {
            return jo.getDouble(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }



    public static JSONObject getJSONObject(JSONObject jo, String key){
        try {
            return jo.getJSONObject(key);
        } catch (JSONException e) {
            return null;
        }
    }
    public static JSONObject getJSONArray(JSONObject jo, String key, JSONObject defaultValue){
        try {
            return jo.getJSONObject(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }


    public static JSONArray getJSONArray(JSONObject jo, String key){
        try {
            return jo.getJSONArray(key);
        } catch (JSONException e) {
            return null;
        }
    }
    public static JSONArray getJSONArray(JSONObject jo, String key, JSONArray defaultValue){
        try {
            return jo.getJSONArray(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }





}
