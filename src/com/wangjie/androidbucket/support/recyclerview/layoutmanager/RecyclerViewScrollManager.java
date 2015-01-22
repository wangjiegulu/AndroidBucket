package com.wangjie.androidbucket.support.recyclerview.layoutmanager;

import android.support.v7.widget.RecyclerView;
import com.wangjie.androidbucket.support.recyclerview.listener.OnRecyclerViewScrollListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/22/15.
 */
public class RecyclerViewScrollManager {
    public static interface OnScrollManagerLocation {
        boolean isTop(RecyclerView recyclerView);

        boolean isBottom(RecyclerView recyclerView);
    }

    private OnRecyclerViewScrollListener onRecyclerViewScrollListener;
    private OnScrollManagerLocation onScrollManagerLocation;

    public void setOnScrollManagerLocation(OnScrollManagerLocation onScrollManagerLocation) {
        this.onScrollManagerLocation = onScrollManagerLocation;
    }

    public OnRecyclerViewScrollListener getOnRecyclerViewScrollListener() {
        return onRecyclerViewScrollListener;
    }

    public void setOnRecyclerViewScrollListener(OnRecyclerViewScrollListener onRecyclerViewScrollListener) {
        this.onRecyclerViewScrollListener = onRecyclerViewScrollListener;
    }

    private boolean isScrolling;
    public boolean isScrolling() {
        return isScrolling;
    }

    public void registerScrollListener(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScrolling = false;
                    // 滚动到顶部和滚动到低部的回调
                    if (null != onRecyclerViewScrollListener) {
                        checkTopWhenScrollIdle(recyclerView);
                        checkBottomWhenScrollIdle(recyclerView);
                    }
                } else {
                    isScrolling = true;
                }
            }

            private void checkBottomWhenScrollIdle(RecyclerView recyclerView) {
                if (onScrollManagerLocation.isBottom(recyclerView)) {
                    onRecyclerViewScrollListener.onBottomWhenScrollIdle(recyclerView);
                }
            }

            private void checkTopWhenScrollIdle(RecyclerView recyclerView) {
                if (onScrollManagerLocation.isTop(recyclerView)) {
                    onRecyclerViewScrollListener.onTopWhenScrollIdle(recyclerView);
                }
            }
        });
    }

}
