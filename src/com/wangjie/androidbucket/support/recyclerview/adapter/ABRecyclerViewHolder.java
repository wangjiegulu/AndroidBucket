package com.wangjie.androidbucket.support.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import com.wangjie.androidbucket.log.Logger;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/19/15.
 */
public class ABRecyclerViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = ABRecyclerViewHolder.class.getSimpleName();
    private SparseArray<View> holder = null;

    public ABRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 获取一个缓存的view
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T obtainView(int id) {
        if (null == holder) {
            holder = new SparseArray<>();
        }
        View view = holder.get(id);
        if (null != view) {
            return (T) view;
        }
        view = itemView.findViewById(id);
        if (null == view) {
            Logger.e(TAG, "no view that id is " + id);
            return null;
        }
        holder.put(id, view);
        return (T) view;
    }

    /**
     * 获取一个缓存的view，并自动转型
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T> T obtainView(int id, Class<T> viewClazz) {
        View view = obtainView(id);
        if (null == view) {
            return null;
        }
        return (T) view;
    }


}
