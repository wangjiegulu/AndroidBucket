package com.wangjie.androidbucket.support.recyclerview.adapter;

/**
 * 用于对不同类型item数据到UI的渲染
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 9/14/14.
 */
public interface AdapterTypeRender<T> {

    /**
     * 返回一个item的可复用de组件，可以是BaseAdapter中getView方法中返回的convertView，也可以是RecyclerView的ViewHolder
     *
     * @return
     */
    T getReusableComponent();

    /**
     * 填充item中各个控件的事件，比如按钮点击事件等
     */
    void fitEvents();

    /**
     * 对指定position的item进行数据的适配
     *
     * @param position
     */
    void fitDatas(int position);


}
