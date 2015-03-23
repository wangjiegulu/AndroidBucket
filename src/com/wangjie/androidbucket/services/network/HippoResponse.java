package com.wangjie.androidbucket.services.network;

import com.wangjie.androidbucket.services.network.exception.HippoException;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 16:37
 */
public class HippoResponse<T> {

    private HippoException error;

    private T result;

    public HippoResponse(T result) {
        this.result = result;
        this.error = null;
    }

    public HippoResponse(HippoException error) {
        this.error = error;
    }


    /**
     * Callback interface for delivering error responses.
     */
    public abstract static class ErrorListener {
        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        public abstract void onErrorResponse(HippoException error);

        /**
         * Process error message in background.
         */
        public void onErrorProcessInBackground(HippoException error) {
            // Implement this if necessary
        }
    }


    public T getResult() {
        return result;
    }

    public boolean isSuccess() {
        return error == null;
    }

    public HippoException getError() {
        return error;
    }

}
