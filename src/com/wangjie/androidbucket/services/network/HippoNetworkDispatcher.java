package com.wangjie.androidbucket.services.network;

import android.os.Handler;
import android.os.Looper;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.services.network.exception.HippoException;
import com.wangjie.androidbucket.services.network.interceptor.Interceptor;

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

    private NetworkExecutor networkExecutor;

    private Handler mHandler;

    public HippoNetworkDispatcher(PriorityBlockingQueue<HippoRequest> queue,
                                  NetworkExecutor networkExecutor) {
        this.queue = queue;
        this.networkExecutor = networkExecutor;
        quit = false;
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
            synchronized (request) {
                // 如果取消则不执行网络访问
                if (request.isCancel()) {
                    continue;
                }
            }
            Logger.d(TAG, String.format("Request: %d get ticket to run.", request.getSeq()));

            // 访问拦截
            try {
                intercept(request);
            } catch (HippoException e) {
                request.parseResponse(new NetworkResponse(e));
                continue;
            }
            NetworkResponse networkResponse = null;
            try {
                networkResponse = networkExecutor.performRequest(request);
            } catch (HippoException e) {
                networkResponse = new NetworkResponse(e);
                Logger.e(TAG, e);
            } finally {
                request.setFinish(true);
                mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new ResponseDispatcherRunnable(request, networkResponse));
                Logger.d(TAG, String.format("Request: %d finish at %d.", request.getSeq(), System.currentTimeMillis()));
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

    private static class ResponseDispatcherRunnable implements Runnable {
        private final NetworkResponse response;
        private final HippoRequest request;

        public ResponseDispatcherRunnable(HippoRequest request, NetworkResponse response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public void run() {
            request.parseResponse(response);
        }
    }
}
