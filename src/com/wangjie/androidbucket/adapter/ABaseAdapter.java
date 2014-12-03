package com.wangjie.androidbucket.adapter;

import android.widget.*;
import com.wangjie.androidbucket.adapter.listener.OnAdapterScrollListener;
import com.wangjie.androidbucket.log.Logger;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/3/14.
 */
public abstract class ABaseAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private static final String TAG = ABaseAdapter.class.getSimpleName();
    private OnAdapterScrollListener onAdapterScrollListener;
    /**
     * 当前listview是否属于滚动状态
     */
    private boolean isScrolling;

    public boolean isScrolling() {
        return isScrolling;
    }

    public void setOnAdapterScrollListener(OnAdapterScrollListener onAdapterScrollListener) {
        this.onAdapterScrollListener = onAdapterScrollListener;
    }

    protected ABaseAdapter(AbsListView listView) {
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
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) { // 不滚动状态
            isScrolling = false;
            this.notifyDataSetChanged();

            // 滚动到顶部和滚动到低部的回调
            if (null != onAdapterScrollListener) {
                checkTopWhenScrollIdle(view);
                checkBottomWhenScrollIdle(view);
            }
        } else {
            isScrolling = true;
        }

    }

    /**
     * 监测是否滚动到顶部
     *
     * @param view
     */
    private void checkTopWhenScrollIdle(final AbsListView view) {
        if (view.getFirstVisiblePosition() <= 0) {
            if (null != onAdapterScrollListener) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        onAdapterScrollListener.onTopWhenScrollIdle(view);
                    }
                });
            }
        }
    }

    /**
     * 监测是否滚动到底部
     *
     * @param view
     */
    private void checkBottomWhenScrollIdle(final AbsListView view) {
        if (view.getLastVisiblePosition() >= (view.getCount() - 1)) {
            if (null != onAdapterScrollListener) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        onAdapterScrollListener.onBottomWhenScrollIdle(view);
                    }
                });
            }
        }
    }


}
