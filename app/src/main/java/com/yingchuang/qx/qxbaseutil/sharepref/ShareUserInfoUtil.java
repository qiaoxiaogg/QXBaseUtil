package com.yingchuang.qx.qxbaseutil.sharepref;

import android.content.Context;


/**
 * 本地sp用户信息工具
 *
 * @author 洋芋饭
 */
public class ShareUserInfoUtil {

    private SharePrefHelper shareHelper;
    static ShareUserInfoUtil instance = null;

    public static String PRIVATEKEY = "privateKey";//密钥
    public static String APPTOKEN = "appToken";//appToken
    public static String TOKEN = "token";// key值


    public static ShareUserInfoUtil getInstance(Context context) {
        if (instance == null)
            instance = new ShareUserInfoUtil(context);
        return instance;
    }

    public ShareUserInfoUtil(Context context) {
        shareHelper = new SharePrefHelper(context,
                SharePrefHelper.SHARE_USERINFO);
    }

    /**
     * 获取
     *
     * @param key
     * @param defVal
     * @return
     */
    public String getString(String key, String defVal) {
        return shareHelper.getSharePre(key, defVal);
    }

    /**
     * 存储
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        shareHelper.setSharePre(key, value);
    }

    /**
     * 获取
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key, Boolean defVal) {
        return shareHelper.getSharePreBoolean(key, defVal);
    }

    /**
     * 存储
     *
     * @param key
     * @param value
     */
    public void setBoolean(String key, Boolean value) {
        shareHelper.setSharePre(key, value);
    }

    /**
     * 获取
     *
     * @param key
     * @return
     */
    public int getInt(String key, int defVal) {
        return shareHelper.getSharePreInt(key, defVal);
    }

    /**
     * 存储
     *
     * @param key
     * @param value
     */
    public void setInt(String key, int value) {
        shareHelper.setSharePre(key, value);
    }








}
