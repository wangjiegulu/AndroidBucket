package com.wangjie.androidbucket.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-28
 * Time: 上午10:57
 */
public class ABTextUtil {
    /**
     * 获得字体的缩放密度
     * @param context
     * @return
     */
    public static float getScaledDensity(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.scaledDensity;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /*****************************************************************/

    public static boolean isEmpty(Collection collection){
        if(null == collection){
            return true;
        }
        return collection.isEmpty();
    }

    public static boolean isEmpty(Object[] objs){
        if(null == objs){
            return true;
        }
        return objs.length <= 0;
    }

    public static boolean isEmpty(CharSequence charSequence){
        return null == charSequence || charSequence.length() <= 0;
    }

    public static boolean isBlank(CharSequence charSequence){
        return null == charSequence || charSequence.toString().trim().length() <= 0;
    }

}
