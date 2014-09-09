package com.wangjie.androidbucket.services;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import com.wangjie.androidbucket.application.ABApplication;
import com.wangjie.androidbucket.controls.dialog.LoadingDialog;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 服务访问基类
 * @Createdate 14-9-4 10:33
 */
public abstract class BaseAccessService<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> implements CancelableTask {

}
