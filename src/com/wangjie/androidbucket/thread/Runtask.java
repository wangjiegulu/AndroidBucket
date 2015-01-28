package com.wangjie.androidbucket.thread;

import android.os.Handler;
import android.os.Message;
import com.wangjie.androidbucket.services.CancelableTask;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-26
 * Time: 下午3:50
 */
public abstract class Runtask<U, R> implements Runnable, CancelableTask {
    public Object[] objs;

    protected Runtask(Object... objs) {
        this.objs = objs;
    }

    private static final int TASK_BEFORE_UI = 0X001;
    private static final int TASK_UPDATE_UI = 0x002;
    private static final int TASK_RESULT = 0x003;

    private boolean isCanceled;

    protected Handler rHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case TASK_BEFORE_UI:
                    onBefore();
                    break;
                case TASK_UPDATE_UI:
                    onUpdateUiCallBack((U) msg.obj);
                    break;
                case TASK_RESULT:
                    onResult((R) msg.obj);
                    break;
            }

        }
    };


    @Override
    public void run() {
        if (isCanceled) {
            return;
        }

        Message msg = rHandler.obtainMessage();
        msg.arg1 = TASK_BEFORE_UI;
        rHandler.sendMessage(msg);

        if (isCanceled) {
            return;
        }
        R result = runInBackground();
        if (isCanceled) {
            return;
        }

        msg = rHandler.obtainMessage();
        msg.arg1 = TASK_RESULT;
        msg.obj = result;
        rHandler.sendMessage(msg);
    }

    public abstract R runInBackground();

    public void updateUi(U obj) {
        Message msg = rHandler.obtainMessage();
        msg.arg1 = TASK_UPDATE_UI;
        msg.obj = obj;
        rHandler.sendMessage(msg);
    }

    public void onBefore() {
    }

    public void onUpdateUiCallBack(U obj) {
    }

    public void onResult(R result) {
    }

    public void cancel() {
        if (!isCanceled) {
            isCanceled = true;
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (mayInterruptIfRunning) {
            Thread.currentThread().interrupt();
        }
        cancel();
        return true;
    }

    public boolean isCanceled() {
        return isCanceled;
    }


}
