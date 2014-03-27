package com.wangjie.androidbucket.thread;

import com.wangjie.androidbucket.log.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-26
 * Time: 下午3:37
 */
public class ThreadPool {
    public static final String TAG = ThreadPool.class.getSimpleName();
    static ExecutorService threadPool;

    public static void initThreadPool(int max){ // 需要在Application中进行配置
        if(max <= 3){
            max = 3;
        }
        threadPool = Executors.newFixedThreadPool(max);
        Logger.d(TAG, "[ThreadPool]ThreadPool init success...max thread: " + max);

    }

    public static ExecutorService getInstances(){
        return threadPool;
    }

    public synchronized static<U, R> void go(Runtask<U, R> runtask){
        if(null == threadPool){
            Logger.e(TAG, "ThreadPool没有被初始化，请在Application中进行初始化操作...");
            return;
        }
        runtask.onBefore();
        threadPool.execute(runtask);
    }





}
