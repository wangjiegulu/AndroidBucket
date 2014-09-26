package com.wangjie.androidbucket.services.network.exception;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 14:19
 */
public class HippoException extends Exception {

    private String errorMessage = null;

    private static final String TAG = HippoException.class.getSimpleName();

    public HippoException(String detailMessage) {
        super(TAG + ": " + detailMessage);
        errorMessage = detailMessage;
    }

    public HippoException(String detailMessage, Exception e) {
        super(TAG + ": " + detailMessage, e);
        errorMessage = detailMessage;
    }
}
