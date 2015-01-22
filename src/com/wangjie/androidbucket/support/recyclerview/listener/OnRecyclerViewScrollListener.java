package com.wangjie.androidbucket.support.recyclerview.listener;

/**
 * 普通的滚动监听，可以在LayoutManager中获取到RecyclerViewScrollManager后add多个
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/22/15.
 */
public interface OnRecyclerViewScrollListener {
    public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState);

    public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy);
}
