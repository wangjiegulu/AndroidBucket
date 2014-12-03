package com.wangjie.androidbucket.adapter.typeadapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.wangjie.androidbucket.R;
import com.wangjie.androidbucket.adapter.ABaseAdapter;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 9/25/14.
 */
public abstract class BaseTypeAdapter extends ABaseAdapter {
    protected BaseTypeAdapter(ListView listView) {
        super(listView);
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterTypeRender typeRender;
        if (null == convertView) {
            typeRender = getAdapterTypeRender(position);
            convertView = typeRender.getConvertView();
            convertView.setTag(R.id.ab__id_adapter_item_type_render, typeRender);
            typeRender.fitEvents();
        } else {
            typeRender = (AdapterTypeRender) convertView.getTag(R.id.ab__id_adapter_item_type_render);
        }
        convertView.setTag(R.id.ab__id_adapter_item_position, position);

        if (null != typeRender) {
            typeRender.fitDatas(position);
        }

        return convertView;
    }

    /**
     * 根据指定position的item获取对应的type，然后通过type实例化一个AdapterTypeRender的实现
     *
     * @param position
     * @return
     */
    public abstract AdapterTypeRender getAdapterTypeRender(int position);
}
