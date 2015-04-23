package com.wangjie.androidbucket.services.network;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.services.CancelableTask;
import com.wangjie.androidbucket.services.network.exception.HippoException;
import com.wangjie.androidbucket.services.network.exception.RetryFailedException;

import java.util.Collection;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 16:35
 * @Modifydate 15-3-4
 * @ModifyDescription Add life cycle control for request
 */
public abstract class HippoRequest<T> implements Comparable<HippoRequest>, CancelableTask {

    private static final String TAG = HippoRequest.class.getSimpleName();

    /**
     * Sequence
     */
    protected int seq;

    protected static final String DEFAULT_RESPONSE_ENCODING = "UTF-8";

    protected RetryPolicy retryPolicy;

    /**
     * Running state
     */
    protected State state;

    /**
     * Request collection
     */
    private Collection<CancelableTask> cancelableTaskCollection;

    /**
     * Executing network
     */
    private Network network;

    /**
     * Called when request get turn to run
     */
    public void onGetTicket() {
        if (listener != null)
            listener.onPreExecute();
    }

    /**
     * Called when right after network response and parse to request type
     *
     * @param response HippoResponse user defined
     */
    public void notifyRunInBackground(HippoResponse<T> response) {
        if (response.isSuccess()) {
            if (listener != null)
                listener.onResponseInBackground(response.getResult());
        } else {
            if (errorListener != null)
                errorListener.onErrorProcessInBackground(response.getError());
        }
    }

    /**
     * Delivery response when finish request
     *
     * @param response HippoResponse user defined
     */
    public void deliverResponse(HippoResponse<T> response) {
        if (response.isSuccess()) {
            if (listener != null)
                listener.onResponse(response.getResult());
        } else {
            if (errorListener != null)
                errorListener.onErrorResponse(response.getError());
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public enum State {
        READY, EXECUTING, CANCELING, CANCELED, FINISHING, FINISHED;
    }

    /**
     * 成功返回监听器
     */
    protected RequestListener<T> listener;

    /**
     * 失败返回监听器
     */
    protected HippoResponse.ErrorListener errorListener;


    public HippoRequest(RequestListener<T> listener, HippoResponse.ErrorListener errorListener) {
        this(listener, errorListener, -1);
    }

    public HippoRequest(RequestListener<T> listener, HippoResponse.ErrorListener errorListener, int retryCount) {
        this.listener = listener;
        this.errorListener = errorListener;
        if (retryCount >= 0) {
            this.retryPolicy = new RetryPolicy(retryCount);
        } else {
            this.retryPolicy = new RetryPolicy();
        }
        setState(State.READY);
    }

    /**
     * 解析HttpResponse
     *
     * @return NetworkResponse
     */
    public abstract HippoResponse<T> parseResponse(NetworkResponse response);

    public void retry(HippoException e) throws RetryFailedException {
        retryPolicy.retry(e);
    }


    @Override
    public int compareTo(HippoRequest hippoRequest) {
        return this.seq - hippoRequest.seq;
    }

    public void cancel() {
        if (state == State.FINISHING || state == State.FINISHED) {
            return;
        }
        setState(State.CANCELING);
    }

    public boolean isCancel() {
        return State.CANCELING == state;
    }

    public boolean isFinish() {
        return state == State.FINISHED;
    }

    public int getSeq() {
        return seq;
    }

    public boolean isRequestOnly() {
        return listener == null;
    }

    @Override
    public String toString() {
        return "HippoRequest{" +
                "seq=" + seq +
                ", retryPolicy=" + retryPolicy +
                ", state=" + state +
                ", listener=" + listener +
                ", errorListener=" + errorListener +
                '}';
    }

    public void abort() {
        if (state == State.FINISHING) {
            return;
        }
        if (network != null) {
            try {
                network.notify();
            } catch (Exception e) {
                // Ignore
                Logger.w(TAG, "Ignore this thread exception.", e);
            }
        }
        setState(State.CANCELING);
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (mayInterruptIfRunning) {
            abort();
        } else {
            cancel();
        }
        return true;
    }

    @Override
    public void remove() {
        if (cancelableTaskCollection != null) {
            cancelableTaskCollection.remove(this);
        }
    }

    @Override
    public void addListener(Collection<CancelableTask> cancelableTaskCollection) {
        this.cancelableTaskCollection = cancelableTaskCollection;
    }
}
