package com.wangjie.androidbucket.services.network.exception;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 15/1/9 13:41
 */
public class RetryFailedException extends HippoException {

    public RetryFailedException(String detailMessage) {
        super(detailMessage);
    }

    public RetryFailedException(String detailMessage, Exception e) {
        super(detailMessage, e);
    }
}
