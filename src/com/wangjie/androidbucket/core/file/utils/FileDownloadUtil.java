package com.wangjie.androidbucket.core.file.utils;

import com.wangjie.androidbucket.utils.ABIOUtil;
import com.wangjie.androidbucket.utils.ABTextUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-15 18:33
 */
public class FileDownloadUtil {

    /**
     * 通过URL下载文件到指定路径
     *
     * @param url      URL
     * @param filePath 指定路径
     * @param headers  HTTP头
     * @return
     * @throws IOException
     */
    public static File download(String url, String filePath, NameValuePair... headers) throws IOException {
        File file = new File(filePath);
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        if (!ABTextUtil.isEmpty(headers)) {
            for (NameValuePair header : headers) {
                request.addHeader(header.getName(), header.getValue());
            }
        }
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                is = response.getEntity().getContent();
                fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            ABIOUtil.closeIO(is, fos);
        }

        return file;
    }

    /**
     * 通过URL下载文件到指定路径
     *
     * @param url      URL
     * @param filePath 指定路径
     * @param headers  HTTP头
     * @return
     * @throws IOException
     */
    public static File downloadAnyWay(String url, String filePath, NameValuePair... headers) throws IOException {
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        return download(url, filePath, headers);
    }

    /**
     * 如果不存在，则下载；如果存在则不下载，直接返回
     * @param url
     * @param filePath
     * @param headers
     * @return
     * @throws Exception
     */
    public static File downloadIfNotExist(String url, String filePath, NameValuePair... headers) throws Exception{
        File file = new File(filePath);
        if(file.exists()){
            return file;
        }
        return download(url, filePath, headers);
    }

    /**
     * 通过URL下载文件到指定路径
     *
     * @param url      URL
     * @param filePath 指定路径
     * @return
     * @throws IOException
     */
    public static File download(String url, String filePath) throws IOException {
        return download(url, filePath, null);
    }
}
