package com.wangjie.androidbucket.support.recyclerview.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.wangjie.androidbucket.R;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/19/15.
 */
public abstract class ABRecyclerViewTypeAdapter extends RecyclerView.Adapter<ABRecyclerViewHolder> {
    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    public ABRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        AdapterTypeRender<ABRecyclerViewHolder> render = getAdapterTypeRender(viewType);
        ABRecyclerViewHolder holder = render.getReusableComponent();
        holder.itemView.setTag(R.id.ab__id_adapter_item_type_render, render);
        render.fitEvents();
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    public void onBindViewHolder(ABRecyclerViewHolder holder, int position) {
        AdapterTypeRender<ABRecyclerViewHolder> render = (AdapterTypeRender<ABRecyclerViewHolder>) holder.itemView.getTag(R.id.ab__id_adapter_item_type_render);
        render.fitDatas(position);
    }

    /**
     * 根据指定position的item获取对应的type，然后通过type实例化一个AdapterTypeRender的实现
     *
     * @return
     */
    public abstract AdapterTypeRender<ABRecyclerViewHolder> getAdapterTypeRender(int type);
}
