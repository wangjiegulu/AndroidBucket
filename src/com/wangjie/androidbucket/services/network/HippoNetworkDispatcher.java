package com.wangjie.androidbucket.services.network;

import android.os.Handler;
import android.os.Looper;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.services.network.exception.HippoException;
import com.wangjie.androidbucket.services.network.interceptor.Interceptor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 14:43
 */
public class HippoNetworkDispatcher extends Thread {

    public static final String TAG = HippoNetworkDispatcher.class.getSimpleName();

    protected PriorityBlockingQueue<HippoRequest> queue;

    private boolean quit;

    private Network network;

    private Handler mHandler;

    private ResponseDispatcherRunnable mResponseDispatcherRunnable;

    private PreExecuteRunnable mPreExecuteRunnable;

    public HippoNetworkDispatcher(PriorityBlockingQueue<HippoRequest> queue,
                                  Network network) {
        this.queue = queue;
        this.network = network;
        quit = false;
        mHandler = new Handler(Looper.getMainLooper());
        mResponseDispatcherRunnable = new ResponseDispatcherRunnable();
        mPreExecuteRunnable = new PreExecuteRunnable();
    }

    @Override
    public void run() {
        while (!quit) {
            HippoRequest<?> request;
            try {
                request = queue.take();
            } catch (InterruptedException e) {
                Logger.e(TAG, e);
                continue;
            }
            // 如果取消则不执行网络访问
            if (request.isCancel()) {
                continue;
            }
            Logger.d(TAG, String.format("Request: %d get ticket to run.", request.getSeq()));
            request.setState(HippoRequest.State.EXECUTING);
            // 访问拦截
            try {
                intercept(request);
            } catch (HippoException e) {
                request.parseResponse(new NetworkResponse(e));
                continue;
            }
            // Before perform network request
            mPreExecuteRunnable.setRequest(request);
            mHandler.post(mPreExecuteRunnable);
            NetworkResponse networkResponse = null;
            try {
                // Perform network request
                networkResponse = network.performRequest(request);
            } catch (HippoException e) {
                networkResponse = new NetworkResponse(e);
                Logger.e(TAG, e);
            } catch (Exception e) {
                networkResponse = new NetworkResponse(new HippoException("Unexpected exception happened!", e));
                Logger.e(TAG, e);
            } finally {
                HippoResponse hippoResponse = request.parseResponse(networkResponse);

                // Notify when network response in thread
                request.notifyRunInBackground(hippoResponse);

                if (!request.isCancel()) {
                    // Delivery network response in main thread
                    mResponseDispatcherRunnable.generateResponse(request, hippoResponse);
                    mHandler.post(mResponseDispatcherRunnable);
                    Logger.d(TAG, String.format("Request: %d finish at %s.", request.getSeq(), new Date().toString()));
                }
                request.setState(HippoRequest.State.FINISHED);
            }
        }
    }

    private Set<Interceptor> interceptors = new HashSet<>();

    public Set<Interceptor> getInterceptors() {
        return interceptors;
    }

    public boolean add(Interceptor interceptor) {
        return interceptors.add(interceptor);
    }

    public boolean remove(Interceptor interceptor) {
        return interceptors.remove(interceptor);
    }

    public void intercept(HippoRequest<?> request) throws HippoException {
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                if (interceptor.getInterruptRule(request)) {
                    interceptor.dispatch(request);
                }
            }
        }
    }

    public void quit() {
        quit = true;
    }

    /**
     * Dispatch response in main thread
     */
    private static class ResponseDispatcherRunnable implements Runnable {
        private HippoResponse response;
        private HippoRequest<?> request;

        public void generateResponse(HippoRequest request, HippoResponse response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public void run() {
            request.deliverResponse(response);
        }
    }

    private static class PreExecuteRunnable implements Runnable {

        private HippoRequest<?> request;

        @Override
        public void run() {
            request.onGetTicket();
        }

        public void setRequest(HippoRequest<?> request) {
            this.request = request;
        }
    }
}
