package com.wangjie.androidbucket.utils;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    /**
     * 遍历所有field
     * @param clazz
     * @param fieldCallback
     */
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

    /**
     * 遍历所有field，包括父类
     * @param clazz
     * @param fieldCallback
     */
    public static void doWithFieldsWithSuper(Class<?> clazz, FieldCallback fieldCallback) {
        while (!Object.class.equals(clazz)) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                try {
                    fieldCallback.doWith(f);
                } catch (Exception ex) {
                    Log.e(TAG, "ReflectionUtils.doWithFieldsWithSuper error", ex);
                }
            }
            clazz = clazz.getSuperclass();
        }

    }

    /**
     * ************************************** modifier ****************************************
     */
    public static boolean isModifier(Field field, int modifier) {
        return (field.getModifiers() & modifier) == modifier;
    }

    public static boolean isModifier(Method method, int modifier) {
        return (method.getModifiers() & modifier) == modifier;
    }

    public static boolean isModifier(Class clazz, int modifier) {
        return (clazz.getModifiers() & modifier) == modifier;
    }

    /**
     * field是否全部符合modifers
     *
     * @param field
     * @param modifiers
     * @return
     */
    public static boolean isModifierAnd(Field field, int... modifiers) {
        if (ABTextUtil.isEmpty(modifiers)) {
            return true;
        }
        boolean isModifierAnd = true;
        for (int m : modifiers) {
            isModifierAnd = isModifierAnd && isModifier(field, m);
        }
        return isModifierAnd;
    }

    /**
     * method是否全部符合modifers
     *
     * @param method
     * @param modifiers
     * @return
     */
    public static boolean isModifierAnd(Method method, int... modifiers) {
        if (ABTextUtil.isEmpty(modifiers)) {
            return true;
        }
        boolean isModifierAnd = true;
        for (int m : modifiers) {
            isModifierAnd = isModifierAnd && isModifier(method, m);
        }
        return isModifierAnd;
    }

    /**
     * class是否全部符合modifers
     *
     * @param clazz
     * @param modifiers
     * @return
     */
    public static boolean isModifierAnd(Class clazz, int... modifiers) {
        if (ABTextUtil.isEmpty(modifiers)) {
            return true;
        }
        boolean isModifierAnd = true;
        for (int m : modifiers) {
            isModifierAnd = isModifierAnd && isModifier(clazz, m);
        }
        return isModifierAnd;
    }

    /**
     * Field是否符合部分modifers
     *
     * @param field
     * @param modifiers
     * @return
     */
    public static boolean isModifierOr(Field field, int... modifiers) {
        if (ABTextUtil.isEmpty(modifiers)) {
            return true;
        }
        boolean isModifierAnd = false;
        for (int m : modifiers) {
            isModifierAnd = isModifierAnd || isModifier(field, m);
        }
        return isModifierAnd;
    }

    /**
     * Field是否符合部分modifers
     *
     * @param method
     * @param modifiers
     * @return
     */
    public static boolean isModifierOr(Method method, int... modifiers) {
        if (ABTextUtil.isEmpty(modifiers)) {
            return true;
        }
        boolean isModifierAnd = false;
        for (int m : modifiers) {
            isModifierAnd = isModifierAnd || isModifier(method, m);
        }
        return isModifierAnd;
    }

    /**
     * clazz是否符合部分modifers
     *
     * @param clazz
     * @param modifiers
     * @return
     */
    public static boolean isModifierOr(Class clazz, int... modifiers) {
        if (ABTextUtil.isEmpty(modifiers)) {
            return true;
        }
        boolean isModifierAnd = false;
        for (int m : modifiers) {
            isModifierAnd = isModifierAnd || isModifier(clazz, m);
        }
        return isModifierAnd;
    }


}
