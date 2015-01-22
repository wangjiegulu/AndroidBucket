package com.wangjie.androidbucket.support.recyclerview.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wangjie.androidbucket.support.recyclerview.listener.OnRecyclerViewScrollListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/19/15.
 */
public class ABaseGridLayoutManager extends GridLayoutManager implements RecyclerViewScrollManager.OnScrollManagerLocation {
    private static final String TAG = ABaseGridLayoutManager.class.getSimpleName();

    private RecyclerViewScrollManager recyclerViewScrollManager;

    public void setOnRecyclerViewScrollListener(RecyclerView recyclerView, OnRecyclerViewScrollListener onRecyclerViewScrollListener) {
        if (null == recyclerViewScrollManager) {
            recyclerViewScrollManager = new RecyclerViewScrollManager();
        }
        recyclerViewScrollManager.setOnRecyclerViewScrollListener(onRecyclerViewScrollListener);
        recyclerViewScrollManager.setOnScrollManagerLocation(this);
        recyclerViewScrollManager.registerScrollListener(recyclerView);
    }

    public ABaseGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public ABaseGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public boolean isScrolling() {
        if (null != recyclerViewScrollManager) {
            return recyclerViewScrollManager.isScrolling();
        }
        return false;
    }

    @Override
    public boolean isTop(RecyclerView recyclerView) {
        return 0 == findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public boolean isBottom(RecyclerView recyclerView) {
        int lastVisiblePosition = findLastCompletelyVisibleItemPosition();
        int lastPosition = recyclerView.getAdapter().getItemCount() - 1;
        return lastVisiblePosition == lastPosition;
    }
}
