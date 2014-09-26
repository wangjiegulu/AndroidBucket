package com.wangjie.androidbucket.services.network.toolbox;

import com.wangjie.androidbucket.services.network.HippoHttpRequest;
import com.wangjie.androidbucket.services.network.HippoResponse;
import com.wangjie.androidbucket.services.network.NetworkResponse;
import org.apache.http.NameValuePair;

import java.io.File;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-25 18:35
 */
public class UploadFileHippoHttpRequest extends HippoHttpRequest<File> {
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
    public UploadFileHippoHttpRequest(int method, String url, NameValuePair[] headers, byte[] body, HippoResponse.Listener<File> listener, HippoResponse.ErrorListener errorListener) {
        super(method, url, headers, body, listener, errorListener);
    }

    @Override
    public void parseResponse(NetworkResponse response) {

    }
}
