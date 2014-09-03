package com.wangjie.androidbucket.services.http;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-3 15:04
 */
public interface OnHttpResponse {
    /**
     * HTTP 返回回调
     */
    void onResponse(Object object);

    // HTTP 返回UI线程
    void onResult(ResultMessage object);
}
