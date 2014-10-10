package com.wangjie.androidbucket.services.network;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 16:35
 */
public abstract class HippoRequest<T> implements Comparable<HippoRequest> {

    /**
     * Sequence
     */
    protected int seq;

    private boolean cancel = false;

    private boolean finish = false;

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
    }

    /**
     * 解析HttpResponse
     *
     * @return
     */
    public abstract void parseResponse(NetworkResponse response);


    @Override
    public int compareTo(HippoRequest hippoRequest) {
        return this.seq - hippoRequest.seq;
    }

    public void cancel(boolean isCancel) {
        synchronized (this) {
            this.cancel = isCancel;
        }
    }

    public boolean isCancel() {
        return cancel;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getSeq() {
        return seq;
    }
}
