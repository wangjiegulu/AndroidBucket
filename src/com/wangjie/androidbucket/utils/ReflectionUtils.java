package com.wangjie.androidbucket.utils;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-24
 * Time: 下午4:41
 */
public abstract class ReflectionUtils {
    private static String TAG = ReflectionUtils.class.getSimpleName();

    public static interface FieldCallback {
        public void doWith(Field field) throws Exception;
    }

    public static void doWithFields(Class<?> clazz, FieldCallback fieldCallback) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            try {
                fieldCallback.doWith(f);
            } catch (Exception ex) {
                Log.e(TAG, "ReflectionUtils.doWithFields error", ex);
            }
        }
    }


}
