package com.wangjie.androidbucket.mvp;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 7/17/14.
 *
 * MVP的Presenter层，作为沟通View和Model的桥梁，它从Model层检索数据后，返回给View层，它也可以决定与View层的交互操作。
 * 它包含一个View层的引用和一个Model层的引用
 */
public class ABasePresenter<V extends ABActivityViewer, I extends ABInteractor> {
    public static final String TAG = ABasePresenter.class.getSimpleName();

    protected V viewer;
    protected I interactor;

    protected ABasePresenter() {
    }

}
