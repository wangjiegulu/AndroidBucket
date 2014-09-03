package com.wangjie.androidbucket.services.http;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 返回消息
 * @Createdate 14-9-3 15:16
 */
public class ResultMessage {

    // 错误消息
    private String errorMessage;

    // 错误代码
    private int errorCode = 0;

    private boolean isError;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isError() {
        return errorCode == 0;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }
}
