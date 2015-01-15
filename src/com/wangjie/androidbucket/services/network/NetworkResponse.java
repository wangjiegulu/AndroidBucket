package com.wangjie.androidbucket.services.network;

import com.wangjie.androidbucket.services.network.exception.HippoException;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 17:14
 */
public class NetworkResponse {

    private HippoException error;

    private byte[] data;

    public NetworkResponse() {
        this(null, null);
    }

    public NetworkResponse(HippoException error, byte[] data) {
        this.error = error;
        this.data = data;
    }

    public NetworkResponse(HippoException error) {
        this(error, null);
    }

    public NetworkResponse(byte[] data) {
        this(null, data);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public HippoException getError() {
        return error;
    }

    public void setError(HippoException error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return error == null;
    }
}
