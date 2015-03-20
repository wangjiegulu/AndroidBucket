package com.wangjie.androidbucket.services.network.toolbox;

import com.wangjie.androidbucket.services.CancelableTask;
import com.wangjie.androidbucket.services.network.HippoHttpRequest;
import com.wangjie.androidbucket.services.network.NetworkResponse;
import com.wangjie.androidbucket.services.network.HippoResponse;
import com.wangjie.androidbucket.services.network.RequestListener;
import com.wangjie.androidbucket.services.network.exception.HippoException;
import org.apache.http.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 17:09
 */
public class StringHippoHttpRequest extends HippoHttpRequest<String> {

    private final String encoding;

    private Collection<CancelableTask> cancelableTaskCollection;

    /**
     * 完全构造函数
     *
     * @param method
     * @param url
     * @param headers
     * @param body
     * @param listener
     * @param errorListener
     */
    public StringHippoHttpRequest(int method,
                                  String encoding,
                                  String url,
                                  NameValuePair[] headers,
                                  byte[] body,
                                  RequestListener<String> listener,
                                  HippoResponse.ErrorListener errorListener) {
        super(method, url, headers, body, listener, errorListener);
        this.encoding = encoding;
    }


    /**
     * 构造函数
     *
     * @param url
     * @param headers
     * @param body
     * @param listener
     * @param errorListener
     */
    public StringHippoHttpRequest(String url,
                                  NameValuePair[] headers,
                                  byte[] body,
                                  RequestListener<String> listener,
                                  HippoResponse.ErrorListener errorListener) {
        this(Method.GET, DEFAULT_RESPONSE_ENCODING, url, headers, body, listener, errorListener);
    }

    /**
     * 构造函数
     *
     * @param url
     * @param headers
     * @param body
     * @param listener
     * @param errorListener
     */
    public StringHippoHttpRequest(int method,
                                  String url,
                                  NameValuePair[] headers,
                                  byte[] body,
                                  RequestListener<String> listener,
                                  HippoResponse.ErrorListener errorListener) {
        this(method, DEFAULT_RESPONSE_ENCODING, url, headers, body, listener, errorListener);
    }

    @Override
    public HippoResponse<String> parseResponse(NetworkResponse response) {
        HippoResponse<String> hippoResponse;
        if (response.isSuccess()) {
            try {
                hippoResponse = new HippoResponse<>(new String(response.getData(), encoding));
            } catch (UnsupportedEncodingException e) {
                hippoResponse = new HippoResponse<>(new HippoException("Oops! Unsupported encoding, you must kidding me!", e));
            }
        } else {
            hippoResponse = new HippoResponse<>(new HippoException("Cannot parse response due to this error happen.", response.getError()));
        }
        return hippoResponse;
    }

    @Override
    public void addListener(Collection<CancelableTask> cancelableTaskCollection) {
        this.cancelableTaskCollection = cancelableTaskCollection;
    }

    @Override
    public void remove() {
        if (cancelableTaskCollection != null) {
            cancelableTaskCollection.remove(this);
        }
    }
}
