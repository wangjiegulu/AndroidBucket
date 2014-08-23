package com.wangjie.androidbucket.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import com.wangjie.androidbucket.adapter.listener.AdapterListener;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-4-3
 * Time: 下午1:47
 */
public class ABViewUtil {

    /**
     * 适用于Adapter中简化ViewHolder相关代码
     * @param convertView
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T obtainView(View convertView, int id) {
        SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
        if (holder == null) {
            holder = new SparseArray<View>();
            convertView.setTag(holder);
        }
        View childView = holder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            holder.put(id, childView);
        }
        return (T) childView;
    }

    public static void bindViewListener(View view, AdapterListener listener, int position){


    }



    /**
     * view设置background drawable
     * @param view
     * @param drawable
     */
    public static void setBackgroundDrawable(View view, Drawable drawable){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            view.setBackgroundDrawable(drawable);
        }else{
            view.setBackground(drawable);
        }
    }



    /**
     * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view){
//        int height = view.getMeasuredHeight();
//        if(0 < height){
//            return height;
//        }
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件的宽度，如果获取的宽度为0，则重新计算尺寸后再返回宽度
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(View view){
//        int width = view.getMeasuredWidth();
//        if(0 < width){
//            return width;
//        }
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * 测量控件的尺寸
     * @param view
     */
    public static void calcViewMeasure(View view){
//        int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//        int height = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//        view.measure(width,height);

        int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }




}
