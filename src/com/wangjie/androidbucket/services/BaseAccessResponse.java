package com.wangjie.androidbucket.services;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 服务访问返回值基类
 * @Createdate 14-9-4 10:35
 */
public class BaseAccessResponse {
    // 是否成功标记
    private boolean success = false;

    // 错误代码
    private int code;

    // 消息说明
    private String msg = "Default error message.";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
