package com.wangjie.androidbucket.manager;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 8/11/14.
 */
public class BaseActivityManager {

    private List<OnActivityLifeCycleListener> onActivityLifeCycleListeners;

    private Activity activity;

    public BaseActivityManager(Activity activity) {
        this.activity = activity;
    }

    public void registerOnActivityLifeCycleListeners(OnActivityLifeCycleListener listener) {
        synchronized (this) {
            if (onActivityLifeCycleListeners == null) {
                onActivityLifeCycleListeners = new ArrayList<OnActivityLifeCycleListener>();
            }

            if (!onActivityLifeCycleListeners.contains(listener)) {
                onActivityLifeCycleListeners.add(listener);
            }
        }
    }

    public void unregisterOnActivityStopListener(OnActivityLifeCycleListener listener) {
        synchronized (this) {
            if (onActivityLifeCycleListeners != null) {
                onActivityLifeCycleListeners.remove(listener);
            }
        }
    }

    public void dispatchActivityDestory() {
        List<OnActivityLifeCycleListener> list;
        synchronized (this) {
            if (onActivityLifeCycleListeners == null) {
                return;
            }
            list = new ArrayList<OnActivityLifeCycleListener>(onActivityLifeCycleListeners);
        }

        final int N = list.size();
        for (int i = 0; i < N; i++) {
            list.get(i).onActivityDestroyCallback();
        }
    }

    public void dispatchActivityResume() {
        List<OnActivityLifeCycleListener> list;
        synchronized (this) {
            if (onActivityLifeCycleListeners == null) {
                return;
            }
            list = new ArrayList<OnActivityLifeCycleListener>(onActivityLifeCycleListeners);
        }

        final int N = list.size();
        for (int i = 0; i < N; i++) {
            list.get(i).onActivityResumeCallback();
        }
    }

    public void dispatchActivityCreate(Bundle savedInstanceState) {
        List<OnActivityLifeCycleListener> list;
        synchronized (this) {
            if (onActivityLifeCycleListeners == null) {
                return;
            }
            list = new ArrayList<OnActivityLifeCycleListener>(onActivityLifeCycleListeners);
        }

        final int N = list.size();
        for (int i = 0; i < N; i++) {
            list.get(i).onActivityCreateCallback(savedInstanceState);
        }
    }

    public void dispatchActivityPause() {
        List<OnActivityLifeCycleListener> list;
        synchronized (this) {
            if (onActivityLifeCycleListeners == null) {
                return;
            }
            list = new ArrayList<OnActivityLifeCycleListener>(onActivityLifeCycleListeners);
        }

        final int N = list.size();
        for (int i = 0; i < N; i++) {
            list.get(i).onActivityPauseCallback();
        }
    }




}
