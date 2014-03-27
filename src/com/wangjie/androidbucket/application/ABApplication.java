package com.wangjie.androidbucket.application;

import android.app.Application;
import com.wangjie.androidbucket.exception.ABCrashHandler;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.thread.ThreadPool;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-26
 * Time: 下午4:37
 */
public class ABApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initLogger(); // 初始化日志工具
        initThreadPool(); // 初始化线程池
        initImageLoader(); // 初始化图片加载器
        initCrashHandler(); // 初始化程序崩溃捕捉处理

    }

    /**
     * 初始化线程池
     */
    protected void initThreadPool(){
        ThreadPool.initThreadPool(7);
    }

    /**
     * 初始化日志
     */
    protected void initLogger(){
        Logger.initLogger(null);
    }

    /**
     * 初始化图片加载器（子类需要重写）
     */
    protected void initImageLoader(){

    }

    /**
     * 初始化程序崩溃捕捉处理
     */
    protected void initCrashHandler(){
        ABCrashHandler.init(getApplicationContext());
    }





}
