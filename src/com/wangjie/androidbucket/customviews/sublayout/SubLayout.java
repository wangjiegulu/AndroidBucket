package com.wangjie.androidbucket.customviews.sublayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie
 * Date: 14-4-24
 * Time: 下午6:12
 * To change this template use File | Settings | File Templates.
 */
public class SubLayout implements ISubLayout{
    public Context context;
    public View layout;
    private boolean inited;

    public SubLayout(Context context) {
        this.context = context;
    }

    public void initLayout(){
        inited = true;
    }
    public void setInited(boolean isInited){
        inited = isInited;
    }

    public void setContentView(int resLayout){
        layout = LayoutInflater.from(context).inflate(resLayout, null);
    }

    public View getLayout(){
        return layout;
    }

    public boolean isInited(){
        return inited;
    }


    public View findViewById(int resId){
        return layout.findViewById(resId);
    }

    public void onResume(){

    }

    public void onPause(){

    }



}
