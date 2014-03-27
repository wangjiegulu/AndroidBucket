package com.wangjie.androidbucket.customviews.borderscrollview;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-9-6
 * Time: 下午2:53
 */
public interface OnScrollChangedListener {
    public void onScrollChanged(int l, int t, int oldl, int oldt);

    public void onScrollTop();

    public void onScrollBottom();

}
