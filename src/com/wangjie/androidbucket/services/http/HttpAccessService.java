package com.wangjie.androidbucket.services.http;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import com.wangjie.androidbucket.services.CancelableTask;

/**
 * @author Hubert He
 * @version V1.0
 * @Description HTTP 访问服务
 * @Createdate 14-9-3 14:48
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class HttpAccessService<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> implements CancelableTask {

    private String url;

    private OnHttpResponse onHttpResponse;
    private boolean isBackgroundService;

    public HttpAccessService(OnHttpResponse onHttpResponse, boolean isBackgroundService) {
        this.onHttpResponse = onHttpResponse;
        this.isBackgroundService = isBackgroundService;
    }

    @Override
    protected Result doInBackground(Params[] objects) {
        if (objects != null) {
            url = (String) objects[0];
        }

        onHttpResponse.onResponse("");
        // TODO: Http client access
        return null;
    }

    @Override
    protected void onPostExecute(Result o) {
        onHttpResponse.onResponse(o);
        // TODO: 进度条隐藏和关闭
    }

    @Override
    protected void onPreExecute() {
        // TODO: 进度条显示和加载
    }
}
