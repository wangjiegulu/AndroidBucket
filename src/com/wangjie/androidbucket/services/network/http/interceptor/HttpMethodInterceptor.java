package com.wangjie.androidbucket.services.network.http.interceptor;

import com.wangjie.androidbucket.services.network.http.HttpAccessParameter;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 9/18/14.
 */
public interface HttpMethodInterceptor {

    Object interceptGet(HttpAccessParameter accessParameter) throws Exception;
    Object interceptPost(HttpAccessParameter accessParameter) throws Exception;
    Object interceptDelete(HttpAccessParameter accessParameter) throws Exception;

}
