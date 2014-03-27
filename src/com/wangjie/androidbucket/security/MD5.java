package com.wangjie.androidbucket.security;

import com.wangjie.androidbucket.log.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie
 * Date: 14-3-27
 * Time: 下午9:18
 * To change this template use File | Settings | File Templates.
 */
public class MD5 {
    public static final String TAG = MD5.class.getSimpleName();
    public static String md5(String str) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();  //md5 32bit
            // result = buf.toString().substring(8, 24))); //md5 16bit
        } catch (NoSuchAlgorithmException e) {
            Logger.e(TAG, "MD5加密失败, ", e);
        }
        return result;
    }

}
