package com.wangjie.androidbucket.support.recyclerview.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wangjie.androidbucket.support.recyclerview.listener.OnRecyclerViewScrollLocationListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/19/15.
 */
public class ABaseLinearLayoutManager extends LinearLayoutManager implements RecyclerViewScrollManager.OnScrollManagerLocation {
    private static final String TAG = ABaseLinearLayoutManager.class.getSimpleName();

    private RecyclerViewScrollManager recyclerViewScrollManager;

    public void setOnRecyclerViewScrollLocationListener(RecyclerView recyclerView, OnRecyclerViewScrollLocationListener onRecyclerViewScrollLocationListener) {
        ensureRecyclerViewScrollManager();
        recyclerViewScrollManager.setOnRecyclerViewScrollLocationListener(onRecyclerViewScrollLocationListener);
        recyclerViewScrollManager.setOnScrollManagerLocation(this);
        recyclerViewScrollManager.registerScrollListener(recyclerView);
    }

    public ABaseLinearLayoutManager(Context context) {
        super(context);
    }

    public ABaseLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public boolean isScrolling() {
        if (null != recyclerViewScrollManager) {
            return recyclerViewScrollManager.isScrolling();
        }
        return false;
    }

    public RecyclerViewScrollManager getRecyclerViewScrollManager() {
        ensureRecyclerViewScrollManager();
        return recyclerViewScrollManager;
    }
    private void ensureRecyclerViewScrollManager(){
        if (null == recyclerViewScrollManager) {
            recyclerViewScrollManager = new RecyclerViewScrollManager();
        }
    }

    @Override
    public boolean isTop(RecyclerView recyclerView) {
        return 0 == findFirstVisibleItemPosition();
    }

    @Override
    public boolean isBottom(RecyclerView recyclerView) {
        int lastVisiblePosition = findLastCompletelyVisibleItemPosition();
        int lastPosition = recyclerView.getAdapter().getItemCount() - 1;
        return lastVisiblePosition == lastPosition;
    }
}
