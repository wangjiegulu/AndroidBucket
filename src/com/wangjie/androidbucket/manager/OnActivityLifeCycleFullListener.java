package com.wangjie.androidbucket.manager;

import android.os.Bundle;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 8/11/14.
 */
public interface OnActivityLifeCycleFullListener extends OnActivityLifeCycleListener {
    public void onActivityStartCallBack();

    public void onActivityRestartCallBack();

    public void onActivityStopCallBack();

    public void onActivitySaveInstanceStateCallBack(Bundle outState);

    public void onActivityRestoreInstanceStateCallBack(Bundle savedInstanceState);
}
