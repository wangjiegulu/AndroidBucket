package com.wangjie.androidbucket.customviews.pinnedexpandablelistview;

import android.content.Context;
import android.view.View;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 8/20/14.
 */
public abstract class PinnedBaseExpandableAdapter extends BaseExpandableListAdapter implements PinnedExpandableListView.PinnedExpandableHeaderAdapter {
    public static final String TAG = PinnedBaseExpandableAdapter.class.getSimpleName();
    protected Context context;
    protected PinnedExpandableListView pinnedElv;

    protected PinnedBaseExpandableAdapter(Context context, PinnedExpandableListView pinnedElv) {
        this.context = context;
        this.pinnedElv = pinnedElv;
        View headView = getHeaderView();
        if(null != headView){
            this.pinnedElv.setHeaderView(headView);
        }
    }

    /**
     * 头部浮动的view
     * @return
     */
    public abstract View getHeaderView();

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    /**************** implements PinnedExpandableListView.PinnedExpandableHeaderAdapter impl *********************/

    @Override
    public int getPinnedExpandableHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1 && !pinnedElv.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }


    private HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();
    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.containsKey(groupPosition)) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
