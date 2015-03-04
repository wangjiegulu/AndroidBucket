package com.wangjie.androidbucket.services.network;

/**
 * Callback interface for delivering parsed responses.
 */
public abstract class RequestListener<T> {

    public void onPreExecute() {
    }

    public void onResponseInBackground(T response) {
    }

    /**
     * Called when a response is received.
     */
    public abstract void onResponse(T response);
}
