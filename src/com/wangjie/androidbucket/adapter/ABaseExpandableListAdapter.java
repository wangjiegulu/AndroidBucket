package com.wangjie.androidbucket.adapter;

import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import com.wangjie.androidbucket.adapter.listener.OnAdapterScrollListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/3/14.
 */
public abstract class ABaseExpandableListAdapter extends BaseExpandableListAdapter implements AbsListView.OnScrollListener {
    private OnAdapterScrollListener onAdapterScrollListener;
    private boolean isScrolling;

    public boolean isScrolling() {
        return isScrolling;
    }

    public void setOnAdapterScrollListener(OnAdapterScrollListener onAdapterScrollListener) {
        this.onAdapterScrollListener = onAdapterScrollListener;
    }

    protected ABaseExpandableListAdapter(ExpandableListView listView) {
        listView.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (null != onAdapterScrollListener) {
            onAdapterScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (null != onAdapterScrollListener) {
            onAdapterScrollListener.onScrollStateChanged(view, scrollState);
        }

        // 设置是否滚动的状态
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){ // 不滚动状态
            isScrolling = false;
            this.notifyDataSetChanged();
        }else{
            isScrolling = true;
        }

    }




}
