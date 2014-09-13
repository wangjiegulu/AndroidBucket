package com.wangjie.androidbucket.services;

import android.os.AsyncTask;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 服务访问基类
 * @Createdate 14-9-4 10:33
 */
public abstract class BaseAccessService<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> implements CancelableTask {

}
