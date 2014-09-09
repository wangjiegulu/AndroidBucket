package com.wangjie.androidbucket.services;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 服务访问返回值基类
 * @Createdate 14-9-4 10:35
 */
public class BaseAccessResponse {

    // 是否成功标记
    private boolean success;

    // 错误代码
    private int code;

    // 消息说明
    private String msg;

    /**
     * 无参构造函数，初始化success为false
     */
    public BaseAccessResponse() {
        success = false;
        msg = "Default error message.";
    }

    public boolean isSuccess() {
        return success;
    }

    public BaseAccessResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseAccessResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseAccessResponse setMsg(String msg) {
        this.msg = msg;
        return this;
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
