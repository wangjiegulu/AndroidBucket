package com.wangjie.androidbucket.services.network.interceptor;

import com.wangjie.androidbucket.services.network.HippoRequest;
import com.wangjie.androidbucket.services.network.exception.HippoException;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 18:08
 */
public interface Interceptor<T extends HippoRequest> {

    boolean getInterruptRule(T request);

    void dispatch(T request) throws HippoException;
}
