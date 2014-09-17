package com.wangjie.androidbucket.adapter.listener;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import com.wangjie.androidbucket.R;

/**
 * 已经弃用，推荐使用OnConvertViewClickListener
 * Created by wangjie on 6/24/14.
 */
@Deprecated
public abstract class OnAdapterClickListener implements View.OnClickListener{

    private View convertView;
    private int[] positionIds;
    public OnAdapterClickListener(View convertView, int... positionIds) {
        this.convertView = convertView;
        this.positionIds = positionIds;
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
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
