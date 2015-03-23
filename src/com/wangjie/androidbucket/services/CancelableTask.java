package com.wangjie.androidbucket.services;

import java.util.Collection;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 可撤销任务接口
 * @Createdate 14-9-3 15:53
 */
public interface CancelableTask {

    public void addListener(Collection<CancelableTask> cancelableTaskCollection);

    public boolean cancel(boolean mayInterruptIfRunning);

    public void remove();
}
