package com.wangjie.androidbucket.services.network;

import com.wangjie.androidbucket.services.network.exception.HippoException;
import com.wangjie.androidbucket.services.network.exception.RetryFailedException;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 15/2/2 10:59
 */
public class RetryPolicy {

    private static final int DEFAULT_RETRY_COUNT = 3;

    public RetryPolicy() {
        this(DEFAULT_RETRY_COUNT);
    }

    public RetryPolicy(int currentCount) {
        this.currentCount = currentCount;
    }

    private int currentCount;

    public int getCurrentCount() {
        return currentCount;
    }

    public void retry(HippoException e) throws RetryFailedException {
        if (currentCount <= 0) {
            throw new RetryFailedException("Retry request failed.", e);
        }
        currentCount--;
    }

    @Override
    public String toString() {
        return "RetryPolicy{" +
                "currentCount=" + currentCount +
                '}';
    }
}
