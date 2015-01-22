package com.wangjie.androidbucket.support.recyclerview.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/22/15.
 */
public interface OnRecyclerViewScrollListener {
    void onTopWhenScrollIdle(RecyclerView recyclerView);

    void onBottomWhenScrollIdle(RecyclerView recyclerView);
}
