package com.wangjie.androidbucket.services.network;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 15/4/9 11:44
 */
public abstract class DispatchRequestListener<P, R> extends RequestListener<P> {

    private R result;

    @Override
    public final void onResponseInBackground(P response) {
        result = onRunInBackground(response);
    }

    public abstract R onRunInBackground(P response);

    public abstract void onResponseParsed(R result);

    @Override
    public final void onResponse(P response) {
        onResponseParsed(result);
    }
}
