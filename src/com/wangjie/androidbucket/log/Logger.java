package com.wangjie.androidbucket.log;

import com.wangjie.androidbucket.thread.Runtask;
import com.wangjie.androidbucket.thread.ThreadPool;
import com.wangjie.androidbucket.utils.ABTimeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 日志包装工具类
 * @author wangjie
 * @version 创建时间：2013-3-19 上午8:47:42
 */
public class Logger {
    /**
     * 是否开启调试，发布时请把DEBUG改为false
     */
    public static boolean debug = true;
    /**
     * 是否在客户端记录用户操作
     */
    public static boolean logFile = false;

    private static String logFilePath;

    public static void initLogger(LogConfig logConfig){
        if(null == logConfig){
            return;
        }
        debug = logConfig.isDebug();
        logFile = logConfig.isLogFile();
        logFilePath = logConfig.getLogFilePath();
    }


    public static void v(String tag, String msg) {
        if(debug) {
            android.util.Log.v(tag, msg); 
        }
        if(logFile ){
            writeLog(tag, msg, null, "VERBOSE");
        }
    }
    public static void v(String tag, String msg, Throwable tr) {
        if(debug) {
            android.util.Log.v(tag, msg, tr); 
        }
        if(logFile ){
            writeLog(tag, msg,tr, "VERBOSE");
        }
    }
    public static void d(String tag, String msg) {
        if(debug) {
            android.util.Log.d(tag, msg);
        }
        if(logFile ){
            writeLog(tag, msg, null, "debug");
        }
    }
    public static void d(String tag, String msg, Throwable tr) {
        if(debug) {
            android.util.Log.d(tag, msg, tr);
        }
        if(logFile ){
            writeLog(tag, msg, tr, "debug");
        }
    }
    public static void i(String tag, String msg) {
        if(debug) {
            android.util.Log.i(tag, msg);
        }
        if(logFile ){
            writeLog(tag, msg, null, "INFO");
        }
    }
    public static void i(String tag, String msg, Throwable tr) {
        if(debug) {
            android.util.Log.i(tag, msg, tr);
        }
        if(logFile ){
            writeLog(tag, msg, tr, "INFO");
        }
    }
    public static void w(String tag, String msg) {
        if(debug) {
            android.util.Log.w(tag, msg);
        }
        if(logFile ){
            writeLog(tag, msg, null, "WARN");
        }
    }
    public static void w(String tag, String msg, Throwable tr) {
        if(debug) {
            android.util.Log.w(tag, msg, tr);
        }
        if(logFile ){
            writeLog(tag, msg, tr, "WARN");
        }
    }
    public static void w(String tag, Throwable tr) {
        if(debug) {
            android.util.Log.w(tag, tr);
        }
        if(logFile){
            writeLog(tag, "", tr, "WARN");
        }
    }
    public static void e(String tag, String msg) {
        if(debug) {
            android.util.Log.e(tag, msg);
        }
        if(logFile ){
            writeLog(tag, msg, null, "ERROR");
        }
    }
    public static void e(String tag, String msg, Throwable tr) {
        if(debug) {
            android.util.Log.e(tag, msg, tr);
        }
        if(logFile ){
            writeLog(tag, msg, tr, "ERROR");
        }
    }
    public static void e(String tag, Throwable tr) {
        if(debug) {
            android.util.Log.e(tag, "", tr);
        }
        if(logFile ){
            writeLog(tag, "", tr, "ERROR");
        }
    }

    /**
     * 记录日志线程
     * @param tag
     * @param msg
     * @param tr
     * @param priority
     */
    private static void writeLog(String tag, String msg, Throwable tr, String priority){
        ThreadPool.go(new Runtask<Void, Void>(tag, msg, tr, priority) {
            @Override
            public Void runInBackground() {
                synchronized (Logger.class){
                    String tag = (String) objs[0];
                    String msg = (String) objs[1];
                    Throwable tr = (Throwable) objs[2];
                    String priority = (String) objs[3];

                    if(!logFilePath.endsWith(File.separator)){
                        logFilePath = logFilePath + File.separator;
                    }

                    String filename = logFilePath
                            + ABTimeUtil.millisToStringFilename(System.currentTimeMillis(), "yyyy-MM-dd")
                            + ".log";
                    File logFile = new File(filename);

                    OutputStream os = null;
                    try{
                        if(!logFile.exists()){
                            logFile.createNewFile();
                        }

                        os = new FileOutputStream(logFile, true);

                        String formatMsg = ABTimeUtil.millisToStringDate(System.currentTimeMillis()) + "\r\n[" + priority + "][" + tag + "]: \r\n"
                                + "User Message: " + msg + "\r\n"
                                + (null == tr ? "" :

                                "Throwable Message: " + tr.getMessage() + "\r\n"
                                        + "Throwable StackTrace: " + transformStackTrace(tr.getStackTrace())
                        )
                                + "\r\n";

                        os.write(formatMsg.getBytes("utf-8"));
                        os.flush();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{
                        if(null != os){
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }
                return null;
            }
        });

    }


    /**
     * 记录日志线程
     */
    /*
    static class WriteLogThread extends Thread{
        private String tag;
        private String msg;
        private Throwable tr;
        private String priority;

        WriteLogThread(String tag, String msg, Throwable tr, String priority) {
            this.tag = tag;
            this.msg = msg;
            this.tr = tr;
            this.priority = priority;
        }

        @Override
        public void run() {
            synchronized (WriteLogThread.class){

                String filename = logFilePath + File.separator
                        + ABTimeUtil.millisToStringFilename(System.currentTimeMillis(), "yyyy-MM-dd")
                        + ".log";
                File logFile = new File(filename);

                OutputStream os = null;
                try{
                    if(!logFile.exists()){
                        logFile.createNewFile();
                    }

                    os = new FileOutputStream(logFile, true);

                    String formatMsg = ABTimeUtil.millisToStringDate(System.currentTimeMillis()) + "\r\n[" + priority + "][" + tag + "]: \r\n"
                            + "User Message: " + msg + "\r\n"
                            + (null == tr ? "" :

                            "Throwable Message: " + tr.getMessage() + "\r\n"
                                    + "Throwable StackTrace: " + transformStackTrace(tr.getStackTrace())
                    )
                            + "\r\n";

                    os.write(formatMsg.getBytes("utf-8"));
                    os.flush();
                }catch(Exception ex){
                    ex.printStackTrace();
                    return;
                }finally{
                    if(null != os){
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }

        }
    }
    */

    /*
    private static synchronized void writeToLogFile(String tag, String msg, Throwable tr, String priority) {
        String filename = Variables.APP_LOG_SDPATH + File.separator
                + ABTimeUtil.millisToStringFilename(System.currentTimeMillis(), "yyyy-MM-dd")
                + ".log";
        File logFile = new File(filename);

        OutputStream os = null;
        try{
            if(!logFile.exists()){
                logFile.createNewFile();
            }

            os = new FileOutputStream(logFile, true);

            String formatMsg = ABTimeUtil.millisToStringDate(System.currentTimeMillis()) + "\r\n[" + priority + "][" + tag + "]: \r\n"
                    + "User Message: " + msg + "\r\n"
                    + (null == tr ? "" :

                    "Throwable Message: " + tr.getMessage() + "\r\n"
                            + "Throwable StackTrace: " + transformStackTrace(tr.getStackTrace())
                      )
                    + "\r\n";

            os.write(formatMsg.getBytes("utf-8"));
            os.flush();
        }catch(Exception ex){
            ex.printStackTrace();
            return;
        }finally{
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    */

    public static StringBuilder transformStackTrace(StackTraceElement[] elements){
        StringBuilder sb = new StringBuilder();
        for(StackTraceElement element : elements){
            sb.append(element.toString() + "\r\n");
        }
        return sb;
    }



} 
