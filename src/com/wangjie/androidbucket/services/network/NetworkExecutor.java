package com.wangjie.androidbucket.services.network;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-25 11:48
 */
public interface NetworkExecutor<T extends HippoRequest<?>> {
    public NetworkResponse performRequest(T hippoRequest);
}
