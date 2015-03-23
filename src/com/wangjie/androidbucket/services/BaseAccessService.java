package com.wangjie.androidbucket.services;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import java.util.Collection;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 服务访问基类
 * @Createdate 14-9-4 10:33
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public abstract class BaseAccessService<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> implements CancelableTask {

    Collection<CancelableTask> cancelableTaskCollection;

    @Override
    public void remove() {
        if (cancelableTaskCollection != null) {
            cancelableTaskCollection.remove(this);
        }
    }

    @Override
    public void addListener(Collection<CancelableTask> cancelableTaskCollection) {
        this.cancelableTaskCollection = cancelableTaskCollection;
    }
}
