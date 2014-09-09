package com.wangjie.androidbucket.services.http;

import com.wangjie.androidbucket.services.BaseAccessParameter;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

/**
 * @author Hubert He
 * @version V1.0
 * @Description Http访问参数
 * @Createdate 14-9-4 10:09
 */
public class HttpAccessParameter extends BaseAccessParameter {


    public enum SessionEnableMethod {ENABLE, DISABLE, AUTO}

    // URL
    private String webApi;

    // HTTP访问方法
    private ABHttpMethod method;

    // 表单参数
    private NameValuePair[] paramNameValuePairs;

    // HTTP头参数
    private NameValuePair[] headNameValuePairs;

    // 是否传递raw
    private boolean isRaw;

    // 传递的Entity
    private HttpEntity rawEntity;

    private SessionEnableMethod sessionEnableMethod;

    /**
     * 无参构造函数初始化url为空，SSL连接false，Http Method为GET，表单参数为空
     */
    public HttpAccessParameter() {
        this("", ABHttpMethod.GET, null);
    }

    /**
     * 构造函数
     *
     * @param webApi              URL
     * @param method              Http方法参见HttpMethod枚举值
     * @param paramNameValuePairs 表单参数
     */
    public HttpAccessParameter(String webApi, ABHttpMethod method, NameValuePair... paramNameValuePairs) {
        this.webApi = webApi;
        this.method = method;
        this.paramNameValuePairs = paramNameValuePairs;
        isRaw = false;
    }

    public String getWebApi() {
        return webApi;
    }

    public HttpAccessParameter setWebApi(String webApi) {
        this.webApi = webApi;
        return this;
    }

    public ABHttpMethod getMethod() {
        return method;
    }

    public HttpAccessParameter setMethod(ABHttpMethod method) {
        this.method = method;
        return this;
    }

    public NameValuePair[] getParamNameValuePairs() {
        return paramNameValuePairs;
    }

    public HttpAccessParameter setParamNameValuePairs(NameValuePair... paramNameValuePairs) {
        this.paramNameValuePairs = paramNameValuePairs;
        return this;
    }

    public NameValuePair[] getHeadNameValuePairs() {
        return headNameValuePairs;
    }

    public HttpAccessParameter setHeadNameValuePairs(NameValuePair... headNameValuePairs) {
        this.headNameValuePairs = headNameValuePairs;
        return this;
    }

    public boolean isRaw() {
        return isRaw;
    }

    public HttpAccessParameter setRaw(boolean isRaw) {
        this.isRaw = isRaw;
        return this;
    }

    public HttpEntity getRawEntity() {
        return rawEntity;
    }

    public HttpAccessParameter setRawEntity(HttpEntity rawEntity) {
        isRaw = true;
        this.rawEntity = rawEntity;
        return this;
    }

    public SessionEnableMethod getSessionEnableMethod() {
        return sessionEnableMethod;
    }

    public HttpAccessParameter setSessionEnableMethod(SessionEnableMethod sessionEnableMethod) {
        this.sessionEnableMethod = sessionEnableMethod;
        return this;
    }
}
