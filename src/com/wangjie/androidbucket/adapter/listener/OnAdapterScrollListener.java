package com.wangjie.androidbucket.adapter.listener;

import android.widget.AbsListView;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/3/14.
 */
public interface OnAdapterScrollListener extends AbsListView.OnScrollListener {
    void onTopWhenScrollIdle(AbsListView view);

    void onBottomWhenScrollIdle(AbsListView view);

}
