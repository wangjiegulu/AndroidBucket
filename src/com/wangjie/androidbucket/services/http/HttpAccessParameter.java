package com.wangjie.androidbucket.services.http;

import com.wangjie.androidbucket.services.BaseAccessParameter;
import org.apache.http.NameValuePair;

import java.util.Collection;
import java.util.List;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-4 10:09
 */
public class HttpAccessParameter extends BaseAccessParameter {

    // URL
    private String url;

    // SSL开启
    private boolean enableSSL;

    // HTTP访问方法
    private HttpMethod method;

    // 表单
    private List<NameValuePair> nameValuePairs;

    public HttpAccessParameter() {
        this("", false, HttpMethod.GET, null);
    }

    public HttpAccessParameter(String url, boolean enableSSL, HttpMethod method, List<NameValuePair> nameValuePairs) {
        this.url = url;
        this.enableSSL = enableSSL;
        this.method = method;
        this.nameValuePairs = nameValuePairs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEnableSSL() {
        return enableSSL;
    }

    public void setEnableSSL(boolean enableSSL) {
        this.enableSSL = enableSSL;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public List<NameValuePair> getNameValuePairs() {
        return nameValuePairs;
    }

    public void setNameValuePairs(List<NameValuePair> nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }
}
