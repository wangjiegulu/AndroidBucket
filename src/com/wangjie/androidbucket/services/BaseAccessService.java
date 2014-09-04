package com.wangjie.androidbucket.services;

import android.os.AsyncTask;
import com.wangjie.androidbucket.services.CancelableTask;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-4 10:33
 */
public class BaseAccessService<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> implements CancelableTask {




    @Override
    protected Result doInBackground(Params... params) {
        return null;
    }
}
