package com.wangjie.androidbucket.mvp;

import com.wangjie.androidbucket.services.CancelableTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 7/17/14.
 * <p/>
 * MVP的Presenter层，作为沟通View和Model的桥梁，它从Model层检索数据后，返回给View层，它也可以决定与View层的交互操作。
 * 它包含一个View层的引用和一个Model层的引用
 */
public class ABBasePresenter<V extends ABActivityViewer, I extends ABInteractor> implements ABBaseManager {
    public static final String TAG = ABBasePresenter.class.getSimpleName();

    protected V viewer;
    protected I interactor;

    protected ABBasePresenter() {
        cancelableTaskList = new ArrayList<>();
    }

    private List<CancelableTask> cancelableTaskList;

    @Override
    public void addCancelableTask(CancelableTask cancelableTask) {
        cancelableTaskList.add(cancelableTask);
    }

    @Override
    public void closeAllTask() {
        for (CancelableTask cancelableTask : cancelableTaskList) {
            if (cancelableTask != null) {
                cancelableTask.cancel(true);
            }
        }
    }
}
