package com.wangjie.androidbucket.utils;

import android.content.Context;
import android.text.ClipboardManager;
import com.wangjie.androidbucket.log.Logger;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-4-1
 * Time: 下午1:43
 */
public class ABIOUtil {
    public static final String TAG = ABIOUtil.class.getSimpleName();


    /**
     * 复制功能
     * @param context
     * @param content
     */
    public static void copy(Context context, String content){
        ClipboardManager cm = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(content);
    }



    /**
     * 关闭流
     * @param closeables
     */
    public static void closeIO(Closeable... closeables){
        if(null == closeables || closeables.length <= 0){
            return;
        }
        for(Closeable cb : closeables){
            try {
                if(null == cb){
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                Logger.e(TAG, "close IO ERROR...", e);
            }
        }
    }


    /**
     * 复制文件
     * @param from
     * @param to
     */
    public static void copyFile(File from, File to){
        if(null == from || !from.exists()){
            Logger.e(TAG, "file(from) is null or is not exists!!");
            return;
        }
        if(null == to){
            Logger.e(TAG, "file(to) is null!!");
            return;
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(from);
            if(!to.exists()){
                to.createNewFile();
            }
            os = new FileOutputStream(to);

            byte[] buffer = new byte[1024];
            int len = 0;
            while(-1 != (len = is.read(buffer))){
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            Logger.e(TAG, e);
        }finally {
            closeIO(is, os);
        }


    }




}
