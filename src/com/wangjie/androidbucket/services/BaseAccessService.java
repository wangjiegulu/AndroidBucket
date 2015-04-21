package com.wangjie.androidbucket.services;

import android.annotation.TargetApi;
import android.os.*;
import android.os.Process;
import com.wangjie.androidbucket.log.Logger;

import java.util.Collection;
import java.util.concurrent.*;

/**
 * @author Hubert He
 * @version V1.0
 * @Description 服务访问基类
 * @Createdate 14-9-4 10:33
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public abstract class BaseAccessService<Params, Progress, Result> implements CancelableTask {

    private static final int MAX_THREAD_COUNT = 64;

    public static final Executor THREAD_POOL_EXECUTOR = Executors.newFixedThreadPool(MAX_THREAD_COUNT);

    public static final String TAG = BaseAccessService.class.getSimpleName();

    private Executor threadPoolExecutor = THREAD_POOL_EXECUTOR;

    private Collection<CancelableTask> cancelableTaskCollection;

    private Status status = Status.PENDING;

    private Handler handler = new Handler(Looper.getMainLooper());

    private WorkerRunnable worker;

    private final FutureTask<Result> futureTask;

    protected BaseAccessService() {
        worker = new WorkerRunnable<Params, Result>() {
            public Result call() throws Exception {
                android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                //noinspection unchecked
                return doInBackground(mParams);
            }
        };
        futureTask = new FutureTask<Result>(worker) {
            @Override
            protected void done() {
                try {
                    notifyResult(get());
                } catch (InterruptedException | ExecutionException e) {
                    Logger.e(TAG, e);
                }
            }
        };
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

    protected void onPostExecute(Result result) {
        // Implements in child class
    }

    protected void onPreExecute() {
        // Implements in child class
    }

    protected void onProgressUpdate(Progress... values) {
        // Implements in child class
    }

    protected abstract Result doInBackground(Params... params);

    @SafeVarargs
    public final void publishProgress(Progress... values) {
        notifyProgress(values);
    }

    private void notifyProgress(final Progress[] values) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(values);
            }
        });
    }

    @SafeVarargs
    public final BaseAccessService<Params, Progress, Result> executeOnExecutor(Executor threadPoolExecutor, final Params... params) {
        if (status != Status.PENDING) {
            switch (status) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task has already been executed "
                            + "(a task can be executed only once)");
            }
        }
        if (threadPoolExecutor != null) {
            this.threadPoolExecutor = threadPoolExecutor;
        }
        status = Status.RUNNING;
        // Run this before start thread
        onPreExecute();
        worker.mParams = params;
        this.threadPoolExecutor.execute(futureTask);
        return this;
    }

    @SafeVarargs
    public final BaseAccessService<Params, Progress, Result> execute(final Params... params) {
        return executeOnExecutor(null, params);
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }

    private void notifyResult(final Result result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
                remove();
                status = Status.FINISHED;
            }
        });
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return futureTask.cancel(mayInterruptIfRunning);
    }

    public enum Status {
        FINISHED,
        PENDING,
        RUNNING;
    }
}
