package com.yingchuang.qx.qxbaseutil.aes;

import android.util.Base64;

import org.xutils.common.util.MD5;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 洋芋饭 .
 * 加密工具类
 */

public class AESSecurity {

    public static String publicKey = "www.winpower.com";//公钥


    /*生成key*/
    public static String getKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");// 获得一个key生成器（AES加密模式）
            keyGen.init(256);      // 设置密匙长度128位
            SecretKey secretKey = keyGen.generateKey();  // 获得密匙
            byte[] raw = secretKey.getEncoded();   // 返回密匙的byte数组供加解密使用
            return MD5.md5(raw.toString()).substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*数据加密*/
    public static String encrypt(String key, String data) {  // 加密
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES"); // 根据上一步生成的密匙指定一个密匙（密匙二次加密？）
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 获得Cypher实例对象
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);  // 初始化模式为加密模式，并指定密匙
            byte[] encode = cipher.doFinal(data.getBytes());  // 执行加密操作。 input为需要加密的byte数组
            return Base64.encodeToString(encode, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*数据解密*/
    public static String decrypt(String key, String data) { // 解密
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES"); // 根据上一步生成的密匙指定一个密匙（密匙二次加密？）
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 获得Cypher实例对象
            cipher.init(Cipher.DECRYPT_MODE, keySpec);  // 初始化模式为加密模式，并指定密匙
            byte[] r = Base64.decode(data.getBytes(), Base64.DEFAULT);
            byte[] encode = cipher.doFinal(r);  // 执行加密操作。 input为需要加密的byte数组
            return new String(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
