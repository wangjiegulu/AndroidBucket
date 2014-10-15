package com.wangjie.androidbucket.services.network;

import com.wangjie.androidbucket.services.network.exception.HippoException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.util.Arrays;

/**
 * @author Hubert He
 * @version V1.0
 * @Description Http网络访问请求基类
 * @Createdate 14-9-24 14:05
 */
public abstract class HippoHttpRequest<T> extends HippoRequest<T> {

    /**
     * Default encoding for POST or PUT parameters.
     */
    protected static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    protected static final String DEFAULT_RESPONSE_ENCODING = "UTF-8";

    /**
     * Supported request methods.
     */
    public interface Method {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
    }

    /**
     * 访问方法 refer to Method
     */
    private int method;

    /**
     * 访问URL
     */
    private String url;

    /**
     * HTTP头信息
     */
    private NameValuePair[] headers;

    /**
     * HTTP Body
     */
    private byte[] body;

    /**
     * 完全构造函数
     *
     * @param method  HTTP Method
     * @param url     网址
     * @param headers HTTP header information
     * @param body    HTTP body bytes array
     */
    public HippoHttpRequest(int method,
                            String url,
                            NameValuePair[] headers,
                            byte[] body,
                            HippoResponse.Listener<T> listener,
                            HippoResponse.ErrorListener errorListener) {
        super(listener, errorListener);
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;

    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NameValuePair[] getHeaders() {
        return headers;
    }

    public void setHeaders(NameValuePair[] headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public HttpEntity getEntity() {
        HttpEntity entity = null;
        if (body != null) {
            entity = new ByteArrayEntity(body);
        }
        return entity;
    }
}
