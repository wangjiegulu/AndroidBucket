package com.wangjie.androidbucket.services;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 可撤销任务接口
 * @Createdate 14-9-3 15:53
 */
public interface CancelableTask {

    public boolean cancel(boolean mayInterruptIfRunning);
}
