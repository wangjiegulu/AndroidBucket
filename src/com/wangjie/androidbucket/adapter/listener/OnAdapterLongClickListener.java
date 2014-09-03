package com.wangjie.androidbucket.adapter.listener;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 9/2/14.
 */
public abstract class OnAdapterLongClickListener implements View.OnLongClickListener{
    private View convertView;
    private int[] positionIds;
    public OnAdapterLongClickListener(View convertView, int... positionIds) {
        this.convertView = convertView;
        this.positionIds = positionIds;
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    public boolean onLongClick(View v) {
        int len = positionIds.length;
        int[] positions = new int[len];
        for(int i = 0; i < len; i++){
            positions[i] = (int) convertView.getTag(positionIds[i]);
        }
        onLongClickCallBack(positions);
        return true;
    }

    public abstract void onLongClickCallBack(int... positionIds);

}
