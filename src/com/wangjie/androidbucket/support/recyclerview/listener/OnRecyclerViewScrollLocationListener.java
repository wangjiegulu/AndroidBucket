package com.wangjie.androidbucket.support.recyclerview.listener;

import android.support.v7.widget.RecyclerView;

/**
 * 特殊的滚动监听，可以监听滚动到顶部还是底部
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/22/15.
 */
public interface OnRecyclerViewScrollLocationListener {
    void onTopWhenScrollIdle(RecyclerView recyclerView);

    void onBottomWhenScrollIdle(RecyclerView recyclerView);
}
