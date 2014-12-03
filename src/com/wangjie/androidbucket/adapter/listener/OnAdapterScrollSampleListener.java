package com.wangjie.androidbucket.adapter.listener;

import android.widget.AbsListView;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/3/14.
 */
public class OnAdapterScrollSampleListener implements OnAdapterScrollListener{
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

    @Override
    public void onTopWhenScrollIdle(AbsListView view) {}

    @Override
    public void onBottomWhenScrollIdle(AbsListView view) {}
}
