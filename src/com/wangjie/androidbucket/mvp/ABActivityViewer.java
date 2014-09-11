package com.wangjie.androidbucket.mvp;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 7/17/14.
 * <p/>
 * MVP的View层，UI相关，Activity需要实现该interface
 * 它会包含一个Presenter的引用，当有事件发生（比如按钮点击后），会调用Presenter层的方法
 */
public interface ABActivityViewer {

    /**
     * 显示Toast信息
     *
     * @param message
     */
    void showToastMessage(String message);

    /**
     * 显示Info信息
     *
     * @param message
     */
    void showInfoDialog(String message);

    /**
     * 显示Info信息
     *
     * @param message
     */
    void showInfoDialog(String title, String message);

    /**
     * 显示正在加载对话框
     * @param message
     */
    void showLoadingDialog(String message);

    /**
     * 取消加载对话框
     */
    void cancelLoadingDialog();

}
