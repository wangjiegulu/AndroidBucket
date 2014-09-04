package com.wangjie.androidbucket.services;

import android.os.AsyncTask;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 服务访问基类
 * @Createdate 14-9-4 10:33
 */
public class BaseAccessService<Params, Progress, Result extends BaseAccessResponse,
        AccessParameter extends BaseAccessParameter>
        extends AsyncTask<Params, Progress, Result> implements CancelableTask {

    // 访问参数
    private AccessParameter accessParameter;

    // 访问回调Event
    private OnResponseEvent<Result> onResponseEvent;

    // 是否后台运行
    private boolean isBackgroundService;

    public BaseAccessService(AccessParameter accessParameter, OnResponseEvent<Result> onResponseEvent, boolean isBackgroundService) {
        this.accessParameter = accessParameter;
        this.isBackgroundService = isBackgroundService;
        this.onResponseEvent = onResponseEvent;
    }

    @Override
    protected Result doInBackground(Params... params) {
        return null;
    }

    public boolean isBackgroundService() {
        return isBackgroundService;
    }

    public AccessParameter getAccessParameter() {
        return accessParameter;
    }

    public OnResponseEvent<Result> getOnResponseEvent() {
        return onResponseEvent;
    }
}
