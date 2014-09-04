package com.wangjie.androidbucket.services.http;

import com.wangjie.androidbucket.services.OnResponseEvent;

/**
 * @author Hubert He
 * @version V1.0
 * @Description Http Response事件
 * @Createdate 14-9-3 15:04
 */
public interface OnHttpResponseEvent<T extends BaseHttpResponse> extends OnResponseEvent<T> {
    /**
     * HTTP 返回回调
     */
    T onResponse(Object object);

    // HTTP 返回UI线程
    void onResult(T object);
}
