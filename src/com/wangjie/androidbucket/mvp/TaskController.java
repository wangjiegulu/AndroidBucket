package com.wangjie.androidbucket.mvp;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 15/1/28 12:29
 */
public interface TaskController {

    void registerController(ABBasePresenter presenter);

    void closeAllTask();
}
