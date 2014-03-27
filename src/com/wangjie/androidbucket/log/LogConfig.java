package com.wangjie.androidbucket.log;

/**
 * 日志配置
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-26
 * Time: 下午4:12
 */
public class LogConfig {
    private boolean debug = true; // 开启控制台输出模式
    private boolean logFile = false; // 开启客户端本地日志记录模式
    private String logFilePath; // 本地日志记录的路径

    public boolean isDebug() {
        return debug;
    }

    public LogConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public boolean isLogFile() {
        return logFile;
    }

    public LogConfig setLogFile(boolean logFile) {
        this.logFile = logFile;
        return this;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public LogConfig setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
        return this;
    }

}
