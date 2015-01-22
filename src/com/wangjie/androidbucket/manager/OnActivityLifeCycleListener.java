package com.wangjie.androidbucket.manager;

import android.os.Bundle;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 8/11/14.
 */
public interface OnActivityLifeCycleListener {
    @Deprecated
    public void onActivityCreateCallback(Bundle savedInstanceState);

    public void onActivityResumeCallback();

    public void onActivityPauseCallback();

    public void onActivityDestroyCallback();


}
