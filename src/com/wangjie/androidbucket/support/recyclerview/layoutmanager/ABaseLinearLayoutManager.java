package com.wangjie.androidbucket.support.recyclerview.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wangjie.androidbucket.log.Logger;

import static android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/19/15.
 */
public class ABaseLinearLayoutManager extends LinearLayoutManager {
    private static final String TAG = ABaseLinearLayoutManager.class.getSimpleName();

    public static interface OnRecyclerViewScrollListener {
        void onTopWhenScrollIdle(RecyclerView recyclerView);

        void onBottomWhenScrollIdle(RecyclerView recyclerView);
    }

    private OnRecyclerViewScrollListener onRecyclerViewScrollListener;

    public void setOnRecyclerViewScrollListener(OnRecyclerViewScrollListener onRecyclerViewScrollListener) {
        this.onRecyclerViewScrollListener = onRecyclerViewScrollListener;
    }

    public ABaseLinearLayoutManager(RecyclerView recyclerView, Context context) {
        super(context);
        init(recyclerView);
    }

    public ABaseLinearLayoutManager(RecyclerView recyclerView, Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init(recyclerView);
    }

    boolean isScrolling; // 是否是滚动状态

    private void init(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new OnScrollListener() {
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
                int lastVisiblePosition = findLastCompletelyVisibleItemPosition();
                int lastPosition = recyclerView.getAdapter().getItemCount() - 1;
                Logger.d(TAG, "lastVisiblePosition: " + lastVisiblePosition + ", lastPosition: " + lastPosition);
                if (lastVisiblePosition == lastPosition) {
                    if (null != onRecyclerViewScrollListener) {
                        onRecyclerViewScrollListener.onBottomWhenScrollIdle(recyclerView);
                    }
                }
            }

            private void checkTopWhenScrollIdle(RecyclerView recyclerView) {
                int firstVisiblePosition = findFirstCompletelyVisibleItemPosition();
                Logger.d(TAG, "firstVisiblePosition: " + firstVisiblePosition);
                if (0 == firstVisiblePosition) {
                    if (null != onRecyclerViewScrollListener) {
                        onRecyclerViewScrollListener.onTopWhenScrollIdle(recyclerView);
                    }
                }
            }
        });

    }


}
