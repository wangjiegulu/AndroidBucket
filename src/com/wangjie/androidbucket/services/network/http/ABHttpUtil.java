package com.wangjie.androidbucket.services.network.http;

import android.util.Log;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.services.network.http.interceptor.HttpMethodInterceptor;
import com.wangjie.androidbucket.utils.ABTextUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Hubert He
 * @version V1.0
 * @Description HTTP工具类
 * @Createdate 14-9-4 14:37
 */
public class ABHttpUtil {

    public static final String TAG = ABHttpUtil.class.getSimpleName();

    private static HttpConfig httpConfig;

    static {
        httpConfig = new HttpConfig();
    }

    public static interface OnHttpSessionConnectListener {
        String getSessionParameterUrl();

        String getDomain();

        int[] getPorts();
    }

    private static OnHttpSessionConnectListener onHttpSessionConnectListener;

    public static void initHttpConfig(HttpConfig httpConfig, OnHttpSessionConnectListener onHttpSessionConnectListener) {
        ABHttpUtil.httpConfig = getDefaultHttpConfig(httpConfig);
        ABHttpUtil.onHttpSessionConnectListener = onHttpSessionConnectListener;
    }

    private static HttpMethodInterceptor interceptor;

    public static void registerHttpMethodInterceptor(HttpMethodInterceptor interceptor) {
        ABHttpUtil.interceptor = interceptor;
    }


    /**
     * 获取SSL连接
     *
     * @param httpConfig HTTP配置
     * @return
     */
    public static HttpClient getSSLHttpClient(HttpConfig httpConfig) {
        try {
            // 获取一个可用的HttpConfig
            httpConfig = getDefaultHttpConfig(httpConfig);

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();

            // 设置超时时间
            HttpConnectionParams.setConnectionTimeout(params, httpConfig.getConnectionTimeout());
            HttpConnectionParams.setSoTimeout(params, httpConfig.getSoTimeout());
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();

            int[] ports;
            if (null == onHttpSessionConnectListener || null == (ports = onHttpSessionConnectListener.getPorts()) || ports.length < 2) {
                registry.register(new Scheme("http", sf, httpConfig.getHttpPort()));
                registry.register(new Scheme("https", sf, httpConfig.getHttpsPort()));
            } else {
                registry.register(new Scheme("http", sf, ports[0]));
                registry.register(new Scheme("https", sf, ports[1]));
            }


            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    /**
     * 获取SSL连接
     *
     * @return
     */
    public static HttpClient getSSLHttpClient() {
        return getSSLHttpClient(httpConfig.getSoTimeout(), httpConfig.getConnectionTimeout());
    }

    /**
     * 获取SSL连接
     *
     * @return
     */
    public static HttpClient getSSLHttpClient(int soTimeout, int connectionTimeout) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();

            // 设置超时时间
            HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
            HttpConnectionParams.setSoTimeout(params, soTimeout);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", sf, httpConfig.getHttpPort()));
            registry.register(new Scheme("https", sf, httpConfig.getHttpsPort()));


            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }


    /**
     * 获取Http返回值
     *
     * @param accessParameter
     * @return
     */
    public static String getHttpResponse(HttpAccessParameter accessParameter) throws Exception {
        return getHttpResponse(accessParameter, httpConfig.getSoTimeout(), httpConfig.getConnectionTimeout());
    }

    /**
     * 获取Http返回值
     *
     * @param accessParameter
     * @return
     */
    public static String getHttpResponse(HttpAccessParameter accessParameter, int soTimeout, int connectionTimeout) throws Exception {

        HttpUriRequest httpRequest;

        ABHttpMethod method = accessParameter.getMethod();

        // 生成URL
        String url = generateUrl(accessParameter);

        // 如果没有参数则为Get请求
        if (ABHttpMethod.GET == method) {
            if (null != interceptor) {
                interceptor.interceptGet(accessParameter);
            }

            Logger.d(TAG, "Initial Http Get connection");
            // 构造访问字符串
            httpRequest = new HttpGet(url);
        } else if (ABHttpMethod.POST == method) {
            if (null != interceptor) {
                interceptor.interceptPost(accessParameter);
            }
            Logger.d(TAG, "Initial Http Post connection");
            httpRequest = getHttpPostRequest(accessParameter, url);
        } else if (ABHttpMethod.DELETE == method) {
            if (null != interceptor) {
                interceptor.interceptDelete(accessParameter);
            }
            Logger.d(TAG, "Initial Http Delete connection");
            // 构造访问字符串
            httpRequest = new HttpDelete(url);
        } else {
            throw new RuntimeException(method.name() + " not support.");
        }

        // 添加头信息
        NameValuePair[] headers = accessParameter.getHeadNameValuePairs();
        if (headers != null) {
            for (NameValuePair header : headers) {
                httpRequest.addHeader(header.getName(), header.getValue());
            }
        }
        try {
            // 将流转换为字符串
            HttpResponse httpResponse = getHttpResponse(soTimeout,
                    connectionTimeout,
                    httpRequest,
                    url);
            String strResult = ABTextUtil.inputStream2String(httpResponse.getEntity().getContent());
            Logger.d(TAG, String.format("Get response from server value(\"%s\")", strResult));
            return strResult;
        } catch (ClientProtocolException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static HttpResponse getHttpResponse(int soTimeout, int connectionTimeout, HttpUriRequest httpRequest, String url) throws IOException {
        HttpClient httpClient;
        if (!isEnableSSL(url)) {
            Logger.d(TAG, "Initial none SSL HTTP connection.");
            httpClient = new DefaultHttpClient();

        } else {
            Logger.d(TAG, "Initial SSL HTTP connection.");
            httpClient = ABHttpUtil.getSSLHttpClient(soTimeout, connectionTimeout);
        }
        return httpClient.execute(httpRequest);
    }

    // 获取HttpPostRequest
    private static HttpUriRequest getHttpPostRequest(HttpAccessParameter accessParameter, String url) {
        Logger.d(TAG, "Initial Http Post connection");
        // 构造访问字符串
        HttpPost httpPost = new HttpPost(url);
        try {
            // 设置参数
            if (!accessParameter.isRaw()) {
                NameValuePair[] params = accessParameter.getParamNameValuePairs();
                if (!ABTextUtil.isEmpty(params)) {
                    httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(params), HTTP.UTF_8));
                }
            } else {
                httpPost.setEntity(accessParameter.getRawEntity());
            }

        } catch (UnsupportedEncodingException e) {
            Logger.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return httpPost;
    }

    /**
     * 根据是否根据SessionMethod来构造访问URL
     * 如果使用SessionEnableMethod.ENABLE则启用Session访问
     * 如果使用SessionEnableMethod.AUTO自动判断是否有设置Session
     * 如果使用SessionEnableMethod.DISABLE则不适用Session访问
     *
     * @param accessParameter
     * @return
     */
    private static String generateUrl(HttpAccessParameter accessParameter) throws Exception {
        String url =
                null == onHttpSessionConnectListener ? httpConfig.getDomain() : onHttpSessionConnectListener.getDomain();
        url += accessParameter.getWebApi();

        HttpAccessParameter.SessionEnableMethod sessionEnableMethod = accessParameter.getSessionEnableMethod();
        if (sessionEnableMethod == HttpAccessParameter.SessionEnableMethod.AUTO) {
            if (onHttpSessionConnectListener != null && onHttpSessionConnectListener.getSessionParameterUrl() != null) {
                url += (url.contains("?") ? "&" : "?") + onHttpSessionConnectListener.getSessionParameterUrl();
            }
        } else if (sessionEnableMethod == HttpAccessParameter.SessionEnableMethod.ENABLE) {
            if (onHttpSessionConnectListener != null && onHttpSessionConnectListener.getSessionParameterUrl() != null) {
                url += (url.contains("?") ? "&" : "?") + onHttpSessionConnectListener.getSessionParameterUrl();
            } else {
                throw new Exception("None session configuration problem.");
            }
        }
        Logger.d(TAG, "Connect to " + url);
        return url;
    }

    public static String getGeneratedUrl(String webApi, HttpAccessParameter.SessionEnableMethod sessionEnableMethod) throws Exception {
        String url =
                null == onHttpSessionConnectListener ? httpConfig.getDomain() : onHttpSessionConnectListener.getDomain()
                        + webApi;
        if (sessionEnableMethod == HttpAccessParameter.SessionEnableMethod.AUTO) {
            if (onHttpSessionConnectListener != null && onHttpSessionConnectListener.getSessionParameterUrl() != null) {
                url += (url.contains("?") ? "&" : "?") + onHttpSessionConnectListener.getSessionParameterUrl();
            }
        } else if (sessionEnableMethod == HttpAccessParameter.SessionEnableMethod.ENABLE) {
            if (onHttpSessionConnectListener != null && onHttpSessionConnectListener.getSessionParameterUrl() != null) {
                url += (url.contains("?") ? "&" : "?") + onHttpSessionConnectListener.getSessionParameterUrl();
            } else {
                throw new Exception("None session configuration problem.");
            }
        }
        Logger.d(TAG, "Connect to " + url);
        return url;
    }

    // 获取一个可用的Http配置，如果没有配置则返回一个默认值
    private static HttpConfig getDefaultHttpConfig(HttpConfig httpConfig) {
        if (null == httpConfig) {
            httpConfig = new HttpConfig();
        }
        return httpConfig;
    }

    private static boolean isEnableSSL(String url) {
        return url.toLowerCase().startsWith("https");
    }


    /**
     * map转为NameValuePairs
     *
     * @param map
     * @return
     */
    public static NameValuePair[] convert2NameValuePairs(Map<String, Object> map) {
        if (ABTextUtil.isEmpty(map)) {
            return null;
        }
        NameValuePair[] nvps = new NameValuePair[map.size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            nvps[i] = new BasicNameValuePair(entry.getKey(), entry.getValue() + "");
            i++;
        }
        return nvps;
    }

}
