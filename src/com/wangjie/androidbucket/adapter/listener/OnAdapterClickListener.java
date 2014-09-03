package com.wangjie.androidbucket.adapter.listener;

import android.view.View;
import com.wangjie.androidbucket.R;

/**
 * Created by wangjie on 6/24/14.
 */
public abstract class OnAdapterClickListener implements View.OnClickListener{

    private View convertView;
    private int[] positionIds;
    public OnAdapterClickListener(View convertView, int... positionIds) {
        this.convertView = convertView;
        this.positionIds = positionIds;
    }

    @Override
    public void onClick(View v) {
        int len = positionIds.length;
        int[] positions = new int[len];
        for(int i = 0; i < len; i++){
            positions[i] = (int) convertView.getTag(positionIds[i]);
        }
        onClickCallBack(positions);
    }

    public abstract void onClickCallBack(int... positionIds);

}
