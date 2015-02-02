package com.wangjie.androidbucket.support.recyclerview.pinnedlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.support.recyclerview.layoutmanager.ABaseLinearLayoutManager;
import com.wangjie.androidbucket.support.recyclerview.listener.OnRecyclerViewScrollListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/2/15.
 */
public class PinnedRecyclerViewLayout extends RelativeLayout {

    private static final String TAG = PinnedRecyclerViewLayout.class.getSimpleName();

    public static interface OnRecyclerViewPinnedViewListener {
        void onPinnedViewRender(PinnedRecyclerViewLayout pinnedRecyclerViewLayout, View pinnedView, int position);
    }

    private OnRecyclerViewPinnedViewListener onRecyclerViewPinnedViewListener;

    public void setOnRecyclerViewPinnedViewListener(OnRecyclerViewPinnedViewListener onRecyclerViewPinnedViewListener) {
        this.onRecyclerViewPinnedViewListener = onRecyclerViewPinnedViewListener;
    }

    public PinnedRecyclerViewLayout(Context context) {
        super(context);
        init(context);
    }

    public PinnedRecyclerViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PinnedRecyclerViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
    }

    private View pinnedView;
    private ABaseLinearLayoutManager layoutManager;

    public void initRecyclerPinned(RecyclerView recyclerView, ABaseLinearLayoutManager layoutManager, View pinnedView) {
        this.pinnedView = pinnedView;
        this.layoutManager = layoutManager;
        this.addView(this.pinnedView);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.pinnedView.setLayoutParams(lp);
        layoutManager.getRecyclerViewScrollManager().addScrollListener(recyclerView, new OnRecyclerViewScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                refreshPinnedView();
            }
        });
        pinnedView.setVisibility(GONE);
    }

    public void refreshPinnedView() {
        if (null == pinnedView || null == layoutManager) {
            Logger.e(TAG, "Please init pinnedView and layoutManager with initRecyclerPinned method first!");
            return;
        }
        if (VISIBLE != pinnedView.getVisibility()) {
            pinnedView.setVisibility(VISIBLE);
        }
        int curPosition = layoutManager.findFirstVisibleItemPosition();
        if (RecyclerView.NO_POSITION == curPosition) {
            return;
        }
        View curItemView = layoutManager.findViewByPosition(curPosition);
        if (null == curItemView) {
            return;
        }

        int displayTop;
        int itemHeight = curItemView.getHeight();
        int curTop = curItemView.getTop();
        int floatHeight = pinnedView.getHeight();
        if (curTop < floatHeight - itemHeight) {
            displayTop = itemHeight + curTop - floatHeight;
        } else {
            displayTop = 0;
            if (null != onRecyclerViewPinnedViewListener) {
                onRecyclerViewPinnedViewListener.onPinnedViewRender(this, pinnedView, curPosition);
            }
        }
        RelativeLayout.LayoutParams lp = (LayoutParams) pinnedView.getLayoutParams();
        lp.topMargin = displayTop;
        pinnedView.setLayoutParams(lp);
        pinnedView.invalidate();
    }


}
