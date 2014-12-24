package com.wangjie.androidbucket.adapter.typeadapter.expand;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.adapter.ABaseExpandableListAdapter;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/19/14.
 */
public abstract class BaseExpandTypeAdapter extends ABaseExpandableListAdapter {
    protected BaseExpandTypeAdapter(ExpandableListView listView) {
        super(listView);
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandGroupAdapterTypeRender typeRender;
        if (null == convertView) {
            typeRender = getGroupAdapterTypeRender(groupPosition);
            convertView = typeRender.getConvertView();
            convertView.setTag(R.id.ab__id_adapter_item_type_render, typeRender);
            typeRender.fitEvents();
        } else {
            typeRender = (ExpandGroupAdapterTypeRender) convertView.getTag(R.id.ab__id_adapter_item_type_render);
        }
        convertView.setTag(R.id.ab__id_adapter_group_position, groupPosition);

        if (null != typeRender) {
            typeRender.fitDatas(groupPosition);
        }

        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ExpandChildAdapterTypeRender typeRender;
        if (null == convertView) {
            typeRender = getChildAdapterTypeRender(groupPosition, childPosition);
            convertView = typeRender.getConvertView();
            convertView.setTag(R.id.ab__id_adapter_item_type_render, typeRender);
            typeRender.fitEvents();
        } else {
            typeRender = (ExpandChildAdapterTypeRender) convertView.getTag(R.id.ab__id_adapter_item_type_render);
        }
        convertView.setTag(R.id.ab__id_adapter_group_position, groupPosition);
        convertView.setTag(R.id.ab__id_adapter_child_position, childPosition);

        if (null != typeRender) {
            typeRender.fitDatas(groupPosition, childPosition);
        }

        return convertView;
    }

    /**
     * 根据指定position的item获取对应的type，然后通过type实例化一个AdapterTypeRender的实现
     *
     * @param groupPosition
     * @return
     */
    public abstract ExpandGroupAdapterTypeRender getGroupAdapterTypeRender(int groupPosition);

    /**
     * 根据指定position的item获取对应的type，然后通过type实例化一个AdapterTypeRender的实现
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    public abstract ExpandChildAdapterTypeRender getChildAdapterTypeRender(int groupPosition, int childPosition);

}
