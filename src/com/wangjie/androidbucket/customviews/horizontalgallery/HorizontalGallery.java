package com.wangjie.androidbucket.customviews.horizontalgallery;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABAppUtil;
import com.wangjie.androidbucket.utils.ABViewUtil;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/13/14.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class HorizontalGallery extends HorizontalScrollView implements View.OnClickListener {
    private static final String TAG = HorizontalGallery.class.getSimpleName();

    public HorizontalGallery(Context context) {
        super(context);
        init(context);
    }

    public HorizontalGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
    }

    private Context context;
    private HorizontalGalleryBaseAdapter adapter;

    private LinearLayout rootView;

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(HorizontalGalleryBaseAdapter adapter) {
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(dataSetObserver);

        screenWidth = ABAppUtil.getDeviceWidth(context);

        rootView = new LinearLayout(context);
        rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(rootView);

        initData();
    }

    /**
     * 调用Adapter的notifyDataChanged等方法时会回调
     */
    private DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            fitData();
        }

        @Override
        public void onInvalidated() {

        }
    };

    /**
     * 每个item的宽度
     */
    private int itemWidth;
    /**
     * item总宽度
     */
    private int hgLength;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 一个屏幕最多容纳多少个item
     */
    private int screenItemCounts;

    /**
     * 初始化数据（包括所有控件、每个item的宽度、item总宽度、一个屏幕最多所能容纳的item数量）
     */
    private void initData() {
        int itemCount = adapter.getCount();
        for (int i = 0; i < itemCount; i++) {
            View view = adapter.getView(i, null, null);
            view.setOnClickListener(this);
            rootView.addView(view);
            if (itemWidth <= 0) {
                itemWidth = ABViewUtil.getViewMeasuredWidth(view);
                hgLength = itemWidth * itemCount;
                screenItemCounts = screenWidth / itemWidth;
                Logger.d(TAG, "itemWidth: " + itemWidth + ", hgLength: " + hgLength + ", screenItemCounts: " + screenItemCounts);
            }
        }
        setSelected(0);
    }

    /**
     * 重新填充所有item中各个控件的数据
     */
    private void fitData() {
        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            fitData(i);
        }
    }

    /**
     * 重新填充某个item中控件的数据
     * @param position
     */
    private void fitData(int position) {
        View view = this.getChildAt(position);
        adapter.getView(position, view, null);
        view.invalidate();
    }

    /**
     * 当前选中的item索引
     */
    private int selectedIndex;
    /**
     * 用于存储被选中的位置信息
     */
    int[] location = new int[2];

    /**
     * 选中某一个
     *
     * @param position
     */
    public void setSelected(int position) {
        selectedIndex = position;
        View selectedView = rootView.getChildAt(position);
        selectedView.getLocationOnScreen(location);

        if (location[0] < 0) { // 如果在屏幕左外边，则把它移动到0的位置
            int exceptWidth = position * itemWidth;
            this.smoothScrollTo(exceptWidth, 0);
        } else if (location[0] + itemWidth > screenWidth) { // 如果在屏幕右外边，则把它移动到屏幕的最后一个位置
            int exceptWidth = (position - (screenItemCounts - 1)) * itemWidth;
            this.smoothScrollTo(exceptWidth, 0);
        }

        executeSelectedChange(selectedView, position);

    }

    /**
     * 选中某一个后，对选中的和未选中的进行修改（高亮等）
     * @param selectedView
     * @param selectedPosition
     */
    private void executeSelectedChange(View selectedView, int selectedPosition) {
        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            if (selectedPosition == i) {
                adapter.viewSelected(selectedView, selectedPosition);
            } else {
                adapter.viewUnselected(rootView.getChildAt(i), i);
            }
        }
    }

    /**
     * 某个view被点击（也就是选中）
     * @param v
     */
    @Override
    public void onClick(View v) {
        int position = getPosition(v);
        setSelected(position);
        if(null != onHorizontalGalleryItemSelectedListener){
            onHorizontalGalleryItemSelectedListener.onItemSelected(position);
        }
    }

    /**
     * 通过view获取到它再rootView中的一个位置索引
     * @param view
     * @return
     */
    private int getPosition(View view) {
        int position = 0;
        int childCount = rootView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (view == rootView.getChildAt(i)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private OnHorizontalGalleryItemSelectedListener onHorizontalGalleryItemSelectedListener;
    public void setOnHorizontalGalleryItemSelectedListener(OnHorizontalGalleryItemSelectedListener onHorizontalGalleryItemSelectedListener) {
        this.onHorizontalGalleryItemSelectedListener = onHorizontalGalleryItemSelectedListener;
    }

    /**
     * 某个选中回调
     */
    public static interface OnHorizontalGalleryItemSelectedListener{
        void onItemSelected(int position);
    }


}
