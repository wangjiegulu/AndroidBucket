package com.wangjie.androidbucket.security.des3;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-4-4
 * Time: 上午10:44
 */
public class Des3 {
    // 默认密钥
//    private final static String secretKey = "liuyunqiang@lx100$#365#$";
    private final static String secretKey = "wangjie@x19900915$#365#$";
    // 默认向量
    private final static String iv = "01234567";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密（使用默认密钥和默认向量）
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        return encode(plainText, secretKey, iv);
    }

    /**
     * 3DES加密（指定密钥和指定向量）
     * @param plainText
     * @param sKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static String encode(String plainText, String sKey, String iv) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(sKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.encode(encryptData);
    }

    /**
     * 3DES解密（使用默认密钥和默认向量）
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        return decode(encryptText, secretKey, iv);
    }
    /**
     * 3DES解密（指定密钥和指定向量）
     *
     * @param encryptText 加密文本
     * @param sKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText, String sKey, String iv) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(sKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

        return new String(decryptData, encoding);
    }



}
