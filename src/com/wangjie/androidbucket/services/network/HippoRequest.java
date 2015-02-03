package com.wangjie.androidbucket.services.network;

import com.wangjie.androidbucket.services.CancelableTask;
import com.wangjie.androidbucket.services.network.exception.HippoException;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 16:35
 */
public abstract class HippoRequest<T> implements Comparable<HippoRequest>, CancelableTask {

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

    public static enum State {
        READY, EXECUTING, CANCELING, CANCELED, FINISHING, FINISHED;
    }

    /**
     * 成功返回监听器
     */
    protected HippoResponse.Listener<T> listener;

    /**
     * 失败返回监听器
     */
    protected HippoResponse.ErrorListener errorListener;


    public HippoRequest(HippoResponse.Listener<T> listener, HippoResponse.ErrorListener errorListener) {
        this.listener = listener;
        this.errorListener = errorListener;
        this.retryPolicy = new RetryPolicy();
        setState(State.READY);
    }

    /**
     * 解析HttpResponse
     *
     * @return
     */
    public abstract void parseResponse(NetworkResponse response);

    public void retry(HippoException e) throws HippoException {
        retryPolicy.retry(e);
    }


    @Override
    public int compareTo(HippoRequest hippoRequest) {
        return this.seq - hippoRequest.seq;
    }

    public void cancel() {
        if (state == State.FINISHING) {
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
        setState(State.CANCELING);
        Thread.interrupted();
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

}
