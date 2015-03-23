package com.wangjie.androidbucket.exception;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABAppUtil;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Properties;

/**
 * UncaughtExceptionHandler：线程未捕获异常控制器是用来处理未捕获异常的。
 * 如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 * 实现该接口并注册为程序中的默认未捕获异常处理
 * 这样当未捕获异常发生时，就可以做些异常处理操作
 * 例如：收集异常信息，发送错误报告 等。
 * <p/>
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */
public class ABCrashHandler implements UncaughtExceptionHandler {


    public static interface OnCrashHandler {
        boolean onCrash(Throwable e);
    }

    private OnCrashHandler onCrashHandler;

    /**
     * Debug Log Tag
     */
    public static final String TAG = ABCrashHandler.class.getSimpleName();
    /**
     * 是否开启日志输出, 在Debug状态下开启, 在Release状态下关闭以提升程序性能
     */
    public static final boolean DEBUG = true;
    /**
     * CrashHandler实例
     */
    private static ABCrashHandler INSTANCE;
    /**
     * 程序的Context对象
     */
    private Context mContext;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".log";

    private Properties mDeviceCrashInfo;

    private String toastMsg = "程序异常退出，请把日志发送给我们";

    /**
     * 保证只有一个CrashHandler实例
     */
    private ABCrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static ABCrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ABCrashHandler();
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public static void init(Context ctx) {
        init(ctx, null);
    }

    public static void init(Context ctx, String toastMsg) {
        init(ctx, toastMsg, null);
    }

    public static void init(Context ctx, String toastMsg, OnCrashHandler onCrashHandler) {
        ABCrashHandler crashHandler = getInstance();
        if (null != toastMsg) {
            crashHandler.toastMsg = toastMsg;
        }
        crashHandler.onCrashHandler = onCrashHandler;
        crashHandler.mContext = ctx;
        crashHandler.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    protected boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        if (onCrashHandler != null && onCrashHandler.onCrash(ex)) {
            return true;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                // Toast 显示需要出现在一个线程的消息队列中
                Looper.prepare();
                Toast.makeText(mContext, toastMsg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        Logger.e(TAG, "ABCrashHandler,deviceInfos: \n" + ABAppUtil.collectDeviceInfoStr(mContext), ex);
        return true;
    }

}