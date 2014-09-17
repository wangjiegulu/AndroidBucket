package com.wangjie.androidbucket.core.collecion;

/**
 * 拼音排序的javabean必须实现该接口
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-12-9
 * Time: 下午4:43
 */
public interface PinyinKeySortable {
    /**
     * 实现该方法，并返回要拼音排序的字母
     * @return
     */
    public String getPendKey();
}
