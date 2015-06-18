package com.wangjie.androidbucket.services.network.toolbox;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.services.NetworkUtils;
import com.wangjie.androidbucket.services.network.HippoHttpRequest;
import com.wangjie.androidbucket.services.network.HippoRequest;
import com.wangjie.androidbucket.services.network.Network;
import com.wangjie.androidbucket.services.network.NetworkResponse;
import com.wangjie.androidbucket.services.network.exception.HippoException;
import com.wangjie.androidbucket.services.network.interceptor.Interceptor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 14:47
 */
public class HttpNetwork implements Network<HippoHttpRequest<?>> {

    public static final String TAG = HttpNetwork.class.getSimpleName();

    private static int DEFAULT_POOL_SIZE = 4096;

    private static final ByteArrayPool mPool = new ByteArrayPool(DEFAULT_POOL_SIZE);

    /**
     * HttpClient
     */
    private DefaultHttpClient httpClient;

    public HttpNetwork(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Reads the contents of HttpEntity into a byte[].
     */
    public static byte[] entityToBytes(HttpEntity entity) throws IOException, HippoException {
        PoolingByteArrayOutputStream bytes =
                new PoolingByteArrayOutputStream(mPool, (int) entity.getContentLength());
        byte[] buffer = null;
        try {
            InputStream in = entity.getContent();
            if (in == null) {
                throw new HippoException("Cannot open stream when get content of response entity.");
            }
            buffer = mPool.getBuf(1024);
            int count;
            while ((count = in.read(buffer)) != -1) {
                bytes.write(buffer, 0, count);
            }
            return bytes.toByteArray();
        } finally {
            try {
                // Close the InputStream and release the resources by "consuming the content".
                entity.consumeContent();
            } catch (IOException e) {
                // This can happen if there was an exception above that left the entity in
                // an invalid state.
                Logger.d(TAG, "Error occured when calling consumingContent");
            }
            mPool.returnBuf(buffer);
            bytes.close();
        }
    }

    /**
     * 继承他来添加自己的访问参数
     *
     * @param httpUriRequest
     */
    protected void prepareRequest(HttpUriRequest httpUriRequest) {
    }

    private void addHeaders(HttpUriRequest httpUriRequest, HippoHttpRequest request) {
        if (request.getHeaders() != null) {
            for (NameValuePair nameValuePair : request.getHeaders()) {
                httpUriRequest.setHeader(new BasicHeader(nameValuePair.getName(), nameValuePair.getValue()));
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

    /**
     * Creates the appropriate subclass of HttpUriRequest for passed in request.
     */
    @SuppressWarnings("deprecation")
    /* protected */ static HttpUriRequest createHttpRequest(HippoHttpRequest<?> request) {
        switch (request.getMethod()) {
            case HippoHttpRequest.Method.GET:
                return new HttpGet(request.getUrl());
            case HippoHttpRequest.Method.DELETE:
                return new HttpDelete(request.getUrl());
            case HippoHttpRequest.Method.POST: {
                HttpPost postRequest = new HttpPost(request.getUrl());
                setEntityIfNonEmptyBody(postRequest, request);
                return postRequest;
            }
            case HippoHttpRequest.Method.PUT: {
                HttpPut putRequest = new HttpPut(request.getUrl());
                setEntityIfNonEmptyBody(putRequest, request);
                return putRequest;
            }
            case HippoHttpRequest.Method.HEAD:
                return new HttpHead(request.getUrl());
            case HippoHttpRequest.Method.OPTIONS:
                return new HttpOptions(request.getUrl());
            case HippoHttpRequest.Method.TRACE:
                return new HttpTrace(request.getUrl());
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }

    private static void setEntityIfNonEmptyBody(HttpEntityEnclosingRequestBase httpRequest,
                                                HippoHttpRequest<?> request) {
        HttpEntity httpEntity = request.getEntity();
        if (httpEntity != null) {
            httpRequest.setEntity(httpEntity);
        }
    }

    @Override
    public NetworkResponse performRequest(HippoHttpRequest<?> request) throws HippoException {
        if (httpClient == null) {
            Logger.d(TAG, "Use default http client.");
            httpClient = new DefaultHttpClient();
        }
        while (true) {
            if (request.isCancel()) {
                return new NetworkResponse();
            }
            NetworkResponse networkResponse;
            HttpResponse httpResponse = null;
            byte[] responseContents = null;
            try {
                HttpUriRequest httpUriRequest = createHttpRequest(request);
                addHeaders(httpUriRequest, request);
                // 用户自定义参数
                prepareRequest(httpUriRequest);
                Logger.d(TAG, "Url: " + request.getUrl());
                request.setUriRequest(httpUriRequest);
                httpClient.getCookieStore().clear();
                httpResponse = httpClient.execute(httpUriRequest);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                Logger.w(TAG, "Http response status code: " + statusCode);
                request.setState(HippoRequest.State.FINISHING);
                responseContents = entityToBytes(httpResponse.getEntity());
                // 如果接收到的回复为空，默认赋值为长度为0的byte数组
                if (responseContents == null) {
                    responseContents = new byte[0];
                }
                // 解析返回值
                networkResponse = new NetworkResponse(responseContents);
                return networkResponse;
            } catch (SocketTimeoutException e) {
                attemptRetryOnException(request, new HippoException("Socket Timeout"));
            } catch (ConnectTimeoutException e) {
                attemptRetryOnException(request, new HippoException("Connect Timeout"));
            } catch (HippoException e) {
                networkResponse = new NetworkResponse(e);
                return networkResponse;
            } catch (IOException e) {
                Logger.w(TAG, "Http access exception: " + e.getMessage());
                if (httpResponse != null) {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    try {
                        responseContents = entityToBytes(httpResponse.getEntity());
                    } catch (IOException ignored) {
                    } finally {
                        if (responseContents != null) {
                            networkResponse = new NetworkResponse(new HippoException("Error status code: " + statusCode), responseContents);
                        } else {
                            networkResponse = new NetworkResponse(new HippoException("No network connection.", e));
                        }
                    }
                } else {
                    networkResponse = new NetworkResponse(new HippoException("No network connection.", e));
                }
                return networkResponse;
            } catch (IllegalArgumentException e) {
                Logger.w(TAG, e);
                networkResponse = new NetworkResponse(new HippoException("Wrong arguments: " + e.getMessage(), e));
                return networkResponse;
            } catch (Exception e) {
                Logger.w(TAG, e);
                networkResponse = new NetworkResponse(new HippoException("Wrong arguments: " + e.getMessage(), e));
                return networkResponse;
            }
        }
    }

    private static void attemptRetryOnException(HippoHttpRequest<?> request,
                                                HippoException exception) throws HippoException {
        Logger.w(TAG, String.format("Attempt to retry http connect due to error: %s happen.", exception.getMessage()));
        request.retry(exception);
    }
}
