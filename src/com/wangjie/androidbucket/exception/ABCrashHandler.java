package com.wangjie.androidbucket.exception;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABAppUtil;

import java.io.File;
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
        ABCrashHandler crashHandler = getInstance();
        if (null != toastMsg) {
            crashHandler.toastMsg = toastMsg;
        }
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
            // Sleep一会后结束程序
            // 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Logger.e(TAG, "uncaughtException: ", e);
            }
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
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
//        final String msg = ex.getLocalizedMessage();
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


//    /**
//     * 保存错误信息到文件中
//     *
//     * @param ex
//     * @return
//     */
//    private String saveCrashInfoToFile(Throwable ex) {
//        Writer info = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(info);
//        // printStackTrace(PrintWriter s)
//        // 将此 throwable 及其追踪输出到指定的 PrintWriter
//        ex.printStackTrace(printWriter);
//
//        // getCause() 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null。
//        Throwable cause = ex.getCause();
//        while (cause != null) {
//            cause.printStackTrace(printWriter);
//            cause = cause.getCause();
//        }
//
//        // toString() 以字符串的形式返回该缓冲区的当前值。
//        String result = info.toString();
//        printWriter.close();
//        mDeviceCrashInfo.put(STACK_TRACE, result);
//
//        try {
//            long timestamp = System.currentTimeMillis();
//            String fileName = "crash-" + timestamp + CRASH_REPORTER_EXTENSION;
//            // 保存文件
//            FileOutputStream trace = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
//            mDeviceCrashInfo.store(trace, "");
//            trace.flush();
//            trace.close();
//            return fileName;
//        } catch (Exception e) {
//            Log.e(TAG, "an error occured while writing report file...", e);
//        }
//        return null;
//    }

//    /**
//     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
//     *
//     * @param ctx
//     */
//    private void sendCrashReportsToServer(Context ctx) {
//        String[] crFiles = getCrashReportFiles(ctx);
//        if (crFiles != null && crFiles.length > 0) {
//            TreeSet<String> sortedFiles = new TreeSet<String>();
//            sortedFiles.addAll(Arrays.asList(crFiles));
//
//            for (String fileName : sortedFiles) {
//                File cr = new File(ctx.getFilesDir(), fileName);
//                postReport(cr);
//                cr.delete();// 删除已发送的报告
//            }
//        }
//    }

//    /**
//     * 获取错误报告文件名
//     *
//     * @param ctx
//     * @return
//     */
//    private String[] getCrashReportFiles(Context ctx) {
//        File filesDir = ctx.getFilesDir();
//        // 实现FilenameFilter接口的类实例可用于过滤器文件名
//        FilenameFilter filter = new FilenameFilter() {
//            // accept(File dir, String name)
//            // 测试指定文件是否应该包含在某一文件列表中。
//            public boolean accept(File dir, String name) {
//                return name.endsWith(CRASH_REPORTER_EXTENSION);
//            }
//        };
//        // list(FilenameFilter filter)
//        // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录
//        return filesDir.list(filter);
//    }

    private void postReport(File file) {
        // 使用HTTP Post 发送错误报告到服务器
    }

//    /**
//     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
//     */
//    public void sendPreviousReportsToServer() {
//        sendCrashReportsToServer(mContext);
//    }
}