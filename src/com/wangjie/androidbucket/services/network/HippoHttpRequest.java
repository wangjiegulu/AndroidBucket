package com.wangjie.androidbucket.services.network;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    HttpUriRequest request;

    public void setUriRequest(HttpUriRequest httpUriRequest) {
        request = httpUriRequest;
    }

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
     * URL Params
     */
    private List<NameValuePair> urlParams;

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
        urlParams = new ArrayList<>();
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUrl() {
        if (url != null) {
            try {
                String appendUrl = generateUrlParam(urlParams);
                if (appendUrl != null || appendUrl.length() > 0) {
                    if (url.contains("?")) {
                        if (!url.endsWith("?")) {
                            url += "&";
                        }
                    } else {
                        url += "?";
                    }
                    return url + appendUrl;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
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

    public void addUrlParam(NameValuePair... nameValuePairs) {
        if (nameValuePairs != null) {
            urlParams.addAll(Arrays.asList(nameValuePairs));
        }
    }

    public static String generateUrlParam(NameValuePair... nameValuePairs) throws UnsupportedEncodingException {
        if (nameValuePairs != null)
            return URLEncodedUtils.format(Arrays.asList(nameValuePairs), DEFAULT_PARAMS_ENCODING);
        return "";
    }

    public static String generateUrlParam(List<NameValuePair> nameValuePairList) throws UnsupportedEncodingException {
        if (nameValuePairList != null)
            return URLEncodedUtils.format(nameValuePairList, DEFAULT_PARAMS_ENCODING);
        return "";
    }

    @Override
    public void abort() {
        if (getState() == State.FINISHING) {
            return;
        }
        super.abort();
        if (request != null) {
            request.abort();
        }
    }
}
