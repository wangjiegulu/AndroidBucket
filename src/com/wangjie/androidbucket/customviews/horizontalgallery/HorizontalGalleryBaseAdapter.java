package com.wangjie.androidbucket.customviews.horizontalgallery;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/13/14.
 */
public abstract class HorizontalGalleryBaseAdapter extends BaseAdapter{

    /**
     * 传入的item（convertView）被选中了，可以在这个方法中对convertView进行修改（高亮等）
     * @param convertView
     * @param position
     */
    public abstract void viewSelected(View convertView, int position);

    /**
     * 传入的item（convertView）未被选中了，可以在这个方法中对convertView进行修改（取消高亮等）
     * @param convertView
     * @param position
     */
    public abstract void viewUnselected(View convertView, int position);


}
