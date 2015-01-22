package com.wangjie.androidbucket.support.recyclerview.layoutmanager;

import android.support.v7.widget.RecyclerView;
import com.wangjie.androidbucket.support.recyclerview.listener.OnRecyclerViewScrollListener;
import com.wangjie.androidbucket.support.recyclerview.listener.OnRecyclerViewScrollLocationListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/22/15.
 */
public class RecyclerViewScrollManager {
    private List<OnRecyclerViewScrollListener> scrollListeners;

    public static interface OnScrollManagerLocation {
        boolean isTop(RecyclerView recyclerView);

        boolean isBottom(RecyclerView recyclerView);
    }

    private OnRecyclerViewScrollLocationListener onRecyclerViewScrollLocationListener;
    private OnScrollManagerLocation onScrollManagerLocation;

    public void setOnScrollManagerLocation(OnScrollManagerLocation onScrollManagerLocation) {
        this.onScrollManagerLocation = onScrollManagerLocation;
    }

    public void setOnRecyclerViewScrollLocationListener(OnRecyclerViewScrollLocationListener onRecyclerViewScrollLocationListener) {
        this.onRecyclerViewScrollLocationListener = onRecyclerViewScrollLocationListener;
    }

    private boolean isScrolling;

    public boolean isScrolling() {
        return isScrolling;
    }

    public void registerScrollListener(RecyclerView recyclerView) {
        addScrollListener(recyclerView, new OnRecyclerViewScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScrolling = false;
                    // 滚动到顶部和滚动到低部的回调
                    if (null != onRecyclerViewScrollLocationListener) {
                        checkTopWhenScrollIdle(recyclerView);
                        checkBottomWhenScrollIdle(recyclerView);
                    }
                } else {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }

            private void checkBottomWhenScrollIdle(RecyclerView recyclerView) {
                if (onScrollManagerLocation.isBottom(recyclerView)) {
                    onRecyclerViewScrollLocationListener.onBottomWhenScrollIdle(recyclerView);
                }
            }

            private void checkTopWhenScrollIdle(RecyclerView recyclerView) {
                if (onScrollManagerLocation.isTop(recyclerView)) {
                    onRecyclerViewScrollLocationListener.onTopWhenScrollIdle(recyclerView);
                }
            }
        });
    }

    public void addScrollListener(RecyclerView recyclerView, OnRecyclerViewScrollListener onRecyclerViewScrollListener) {
        if (null == onRecyclerViewScrollListener) {
            return;
        }
        if (null == scrollListeners) {
            scrollListeners = new ArrayList<>();
        }
        scrollListeners.add(onRecyclerViewScrollListener);
        ensureScrollListener(recyclerView);
    }

    RecyclerView.OnScrollListener onScrollListener;

    private void ensureScrollListener(RecyclerView recyclerView) {
        if (null == onScrollListener) {
            onScrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    for (OnRecyclerViewScrollListener listener : scrollListeners) {
                        listener.onScrollStateChanged(recyclerView, newState);
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    for (OnRecyclerViewScrollListener listener : scrollListeners) {
                        listener.onScrolled(recyclerView, dx, dy);
                    }
                }
            };
            recyclerView.setOnScrollListener(onScrollListener);
        }
    }

}
