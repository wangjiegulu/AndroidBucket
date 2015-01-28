package com.wangjie.androidbucket.mvp;

import com.wangjie.androidbucket.services.CancelableTask;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 业务处理基类
 * @Createdate 14-9-3 17:16
 */
public interface ABBaseTaskManager {

    /**
     * 添加可cancel任务
     *
     * @param cancelableTask
     */
    void addCancelableTask(CancelableTask cancelableTask);

    /**
     * 移除可cancel任务（task完成后调用此方法）
     *
     * @param cancelableTask
     */
    void removeCancelableTask(CancelableTask cancelableTask);

    /**
     * 关闭所有任务
     */
    void closeAllTask();
}
