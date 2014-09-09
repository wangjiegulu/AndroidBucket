package com.wangjie.androidbucket.services;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-4 10:50
 */
public interface OnResponseEvent<T extends BaseAccessResponse> {
    /**
     * 线程返回回调
     */
    T onResponse(Object object);

}
