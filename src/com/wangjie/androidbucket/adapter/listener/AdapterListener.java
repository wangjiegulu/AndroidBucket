package com.wangjie.androidbucket.adapter.listener;


import android.view.View;

/**
 * Created by wangjie on 6/24/14.
 */
public class AdapterListener<T> {
    protected int positions;
    protected T listener;

    public int getPositions() {
        return positions;
    }

    public void setPositions(int positions) {
        this.positions = positions;
    }

    public T getListener() {
        return listener;
    }

    public void setListener(T listener) {
        this.listener = listener;
    }

    /**
     * 绑定监听器
     * @param view
     * @param position
     */
    public static <T> void bindListener(View view, int position, int listenerId){
        AdapterListener<T> adapterListener = (AdapterListener<T>) view.getTag(listenerId);
        if(null == adapterListener){
            adapterListener = new AdapterListener<T>();
            adapterListener.setListener(adapterListener.obtainListener());
            view.setTag(listenerId, adapterListener);
        }
        adapterListener.setPositions(position);
    }

    public T obtainListener(){
        return null;
    }


}
