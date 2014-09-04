package com.wangjie.androidbucket.services.http;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.services.BaseAccessService;
import com.wangjie.androidbucket.utils.ABTextUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.security.KeyStore;

/**
 * @author Hubert He
 * @version V1.0
 * @Description HTTP 访问服务
 * @Createdate 14-9-3 14:48
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class HttpAccessService<Params, Progress, Result extends BaseHttpResponse,
        AccessParameter extends HttpAccessParameter>
        extends BaseAccessService<Params, Progress, Result, AccessParameter> {

    public static final String TAG = HttpAccessService.class.getSimpleName();

    public HttpAccessService(AccessParameter accessParameter,
                             OnHttpResponseEvent<Result> onHttpResponseEvent,
                             boolean isBackgroundService) {
        super(accessParameter, onHttpResponseEvent, isBackgroundService);
    }

    /**
     * @param
     * @return
     */
    @Override
    protected Result doInBackground(Params... objects) {
        super.doInBackground(objects);

        // 获取访问参数
        AccessParameter accessParameter = getAccessParameter();
        HttpUriRequest httpRequest;

        // 如果没有参数则为Get请求
        if (HttpMethod.GET == accessParameter.getMethod()) {
            Logger.d(TAG, "Initial Http Get connection");
            httpRequest = new HttpGet(accessParameter.getUrl());
        } else {
            Logger.d(TAG, "Initial Http Post connection");
            // Http client
            HttpPost httpPost = new HttpPost(accessParameter.getUrl());
            try {
                // 设置参数
                httpPost.setEntity(new UrlEncodedFormEntity(accessParameter.getNameValuePairs(), HTTP.UTF_8));
            } catch (UnsupportedEncodingException e) {
                Logger.e(TAG, e.getMessage(), e);
                throw new RuntimeException(e);
            }
            httpRequest = httpPost;
        }
        try {
            // 访问Web API
            // 是否SSL请求
            HttpResponse httpResponse;
            if (!accessParameter.isEnableSSL()) {
                Logger.d(TAG, "Initial none SSL connection");
                httpResponse = new DefaultHttpClient().execute(httpRequest);
            } else {
                httpResponse = null;
            }

            // 将流转换为字符串
            String strResult = ABTextUtil.inputStream2String(httpResponse.getEntity().getContent());
            Logger.d(TAG, "Result: " + strResult);

            // 防止返回Null导致空指针错误
            getOnResponseEvent().onResponse(ABTextUtil.isEmpty(strResult) ? "{}" : strResult);
        } catch (ClientProtocolException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Result response) {
        if (!isCancelled()) {
            getOnResponseEvent().onResult(response);
            // TODO: 进度条隐藏和关闭
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // TODO: 进度条显示和加载
    }

    public static HttpClient getSSLHttpClient(int connTimeout, int soTimeout) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(params, connTimeout);
            HttpConnectionParams.setSoTimeout(params, soTimeout);

            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            registry.register(new Scheme("https", sf, 9094));
            registry.register(new Scheme("https", sf, 9000));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            Logger.e(TAG, e);
            return new DefaultHttpClient();
        }
    }
}
