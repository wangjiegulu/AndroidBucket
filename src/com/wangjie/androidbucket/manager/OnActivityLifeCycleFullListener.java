package com.wangjie.androidbucket.manager;

import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 8/11/14.
 */
public interface OnActivityLifeCycleFullListener extends PreferenceManager.OnActivityStopListener{
    public void onActivityStartCallBack();

    public void onActivityRestartCallBack();

    public void onActivityStopCallBack();

    public void onActivitySaveInstanceStateCallBack(Bundle outState);

    public void onActivityRestoreInstanceStateCallBack(Bundle savedInstanceState);
}
