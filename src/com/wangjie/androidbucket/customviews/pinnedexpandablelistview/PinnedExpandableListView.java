package com.wangjie.androidbucket.customviews.pinnedexpandablelistview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class PinnedExpandableListView extends ExpandableListView implements OnScrollListener,OnGroupClickListener {
    public PinnedExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        registerListener();
    }

    public PinnedExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        registerListener();
    }

    public PinnedExpandableListView(Context context) {
        super(context);
        registerListener();
    }

    /**
     * Adapter 接口 . 列表必须实现此接口 .
     */
    public interface PinnedExpandableHeaderAdapter {
        public static final int PINNED_HEADER_GONE = 0;
        public static final int PINNED_HEADER_VISIBLE = 1;
        public static final int PINNED_HEADER_PUSHED_UP = 2;

        /**
         * 获取 Header 的状态
         * @param groupPosition
         * @param childPosition
         * @return PINNED_HEADER_GONE,PINNED_HEADER_VISIBLE,PINNED_HEADER_PUSHED_UP 其中之一
         */
        int getPinnedExpandableHeaderState(int groupPosition, int childPosition);

        /**
         * 配置 QQHeader, 让 QQHeader 知道显示的内容
         * @param header
         * @param groupPosition
         * @param childPosition
         * @param alpha
         */
        void configurePinnedExpandableHeader(View header, int groupPosition, int childPosition, int alpha);

        /**
         * 设置组按下的状态
         * @param groupPosition
         * @param status
         */
        void setGroupClickStatus(int groupPosition, int status);

        /**
         * 获取组按下的状态
         * @param groupPosition
         * @return
         */
        int getGroupClickStatus(int groupPosition);

    }

    private static final int MAX_ALPHA = 255;

    private PinnedExpandableHeaderAdapter mAdapter;

    /**
     * 用于在列表头显示的 View,mHeaderViewVisible 为 true 才可见
     */
    private View mHeaderView;

    /**
     * 列表头是否可见
     */
    private boolean mHeaderViewVisible;

    private int mHeaderViewWidth;

    private int mHeaderViewHeight;

    public void setHeaderView(View view) {
        mHeaderView = view;
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        if (mHeaderView != null) {
            setFadingEdgeLength(0);
        }

        requestLayout();
    }

    private void registerListener() {
        setOnScrollListener(this);
        setOnGroupClickListener(this);
    }

    /**
     * 点击 HeaderView 触发的事件
     */
    private void headerViewClick() {
        long packedPosition = getExpandableListPosition(this.getFirstVisiblePosition());

        int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);

        if (mAdapter.getGroupClickStatus(groupPosition) == 1) {
//            this.collapseGroup(groupPosition);
            this.collapseGroup(groupPosition);
            mAdapter.setGroupClickStatus(groupPosition, 0);
        }
        else{
            this.expandGroup(groupPosition);
//            this.expandGroup(groupPosition, true);

            mAdapter.setGroupClickStatus(groupPosition, 1);
        }

        this.setSelectedGroup(groupPosition);
    }

    private float mDownX;
    private float mDownY;

    /**
     * 如果 HeaderView 是可见的 , 此函数用于判断是否点击了 HeaderView, 并对做相应的处理 ,
     * 因为 HeaderView 是画上去的 , 所以设置事件监听是无效的 , 只有自行控制 .
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mHeaderViewVisible) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = ev.getX();
                    mDownY = ev.getY();
                    if (mDownX <= mHeaderViewWidth && mDownY <= mHeaderViewHeight) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    float x = ev.getX();
                    float y = ev.getY();
                    float offsetX = Math.abs(x - mDownX);
                    float offsetY = Math.abs(y - mDownY);
                    // 如果 HeaderView 是可见的 , 点击在 HeaderView 内 , 那么触发 headerClick()
                    if (x <= mHeaderViewWidth && y <= mHeaderViewHeight
                            && offsetX <= mHeaderViewWidth && offsetY <= mHeaderViewHeight) {
                        if (mHeaderView != null) {
                            headerViewClick();
                        }

                        return true;
                    }
                    break;
                default:
                    break;
            }
        }

        return super.onTouchEvent(ev);

    }

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (PinnedExpandableHeaderAdapter) adapter;
    }

    /**
     *
     * 点击了 Group 触发的事件 , 要根据根据当前点击 Group 的状态来
     */
    @Override
    public boolean onGroupClick(ExpandableListView parent,View v,int groupPosition,long id) {
        if (mAdapter.getGroupClickStatus(groupPosition) == 0) {
            mAdapter.setGroupClickStatus(groupPosition, 1);
            parent.expandGroup(groupPosition);
            parent.setSelectedGroup(groupPosition);

        } else if (mAdapter.getGroupClickStatus(groupPosition) == 1) {
            mAdapter.setGroupClickStatus(groupPosition, 0);
            parent.collapseGroup(groupPosition);
        }

        // 返回 true 才可以弹回第一行 , 不知道为什么
        return true;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    private int mOldState = -1;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final long flatPostion = getExpandableListPosition(getFirstVisiblePosition());
        final int groupPos = ExpandableListView.getPackedPositionGroup(flatPostion);
        final int childPos = ExpandableListView.getPackedPositionChild(flatPostion);
        int state = groupPos < 0 || childPos < 0 ? PinnedExpandableHeaderAdapter.PINNED_HEADER_VISIBLE : mAdapter.getPinnedExpandableHeaderState(groupPos, childPos);
        if (mHeaderView != null && mAdapter != null && state != mOldState) {
            mOldState = state;
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
        }

        configureHeaderView(groupPos, childPos);
    }

    public void configureHeaderView(int groupPosition, int childPosition) {
        if (mHeaderView == null || mAdapter == null
                || ((ExpandableListAdapter) mAdapter).getGroupCount() == 0) {
            return;
        }

//        int state = mAdapter.getPinnedExpandableHeaderState(groupPosition, childPosition);
        int state = groupPosition < 0 || childPosition < 0 ? PinnedExpandableHeaderAdapter.PINNED_HEADER_VISIBLE : mAdapter.getPinnedExpandableHeaderState(groupPosition, childPosition);

        switch (state) {
            case PinnedExpandableHeaderAdapter.PINNED_HEADER_GONE: {
                mHeaderViewVisible = false;
                break;
            }

            case PinnedExpandableHeaderAdapter.PINNED_HEADER_VISIBLE: {
                mAdapter.configurePinnedExpandableHeader(mHeaderView, groupPosition,childPosition, MAX_ALPHA);

                if (mHeaderView.getTop() != 0){
                    mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
                }

                mHeaderViewVisible = true;

                break;
            }

            case PinnedExpandableHeaderAdapter.PINNED_HEADER_PUSHED_UP: {
                View firstView = getChildAt(0);
                int bottom = firstView.getBottom();

                // intitemHeight = firstView.getHeight();
                int headerHeight = mHeaderView.getHeight();

                int y;

                int alpha;

                if (bottom < headerHeight) {
                    y = (bottom - headerHeight);
                    alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
                } else {
                    y = 0;
                    alpha = MAX_ALPHA;
                }

                mAdapter.configurePinnedExpandableHeader(mHeaderView, groupPosition,childPosition, alpha);

                if (mHeaderView.getTop() != y) {
                    mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
                }

                mHeaderViewVisible = true;
                break;
            }
        }
    }

    @Override
    /**
     * 列表界面更新时调用该方法(如滚动时)
     */
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderViewVisible) {
            //分组栏是直接绘制到界面中，而不是加入到ViewGroup中
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        final long flatPos = getExpandableListPosition(firstVisibleItem);
        int groupPosition = ExpandableListView.getPackedPositionGroup(flatPos);
        int childPosition = ExpandableListView.getPackedPositionChild(flatPos);

        configureHeaderView(groupPosition, childPosition);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
    public View getmHeaderView(){
        return mHeaderView;
    }
}
