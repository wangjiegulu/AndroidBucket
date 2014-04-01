package com.wangjie.androidbucket.utils;

import android.content.Context;
import android.text.ClipboardManager;
import com.wangjie.androidbucket.log.Logger;

import java.io.Closeable;
import java.io.IOException;

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
                cb.close();
            } catch (IOException e) {
                Logger.e(TAG, "close IO ERROR...", e);
                continue;
            }
        }
    }




}
