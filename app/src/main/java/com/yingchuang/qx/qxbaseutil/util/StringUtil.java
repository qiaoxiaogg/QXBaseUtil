package com.yingchuang.qx.qxbaseutil.util;

import android.content.Context;

import com.yingchuang.qx.qxbaseutil.sharepref.ShareUserInfoUtil;

/**
 * author:洋芋饭
 */
public class StringUtil {

    /**
     * 检测是否为空，或者不存在
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");

    }

    /*获取私钥*/
    public static String getPrivateKey(Context context) {
        return ShareUserInfoUtil.getInstance(context).getString(ShareUserInfoUtil.PRIVATEKEY, "");
    }

    /*获取AppToken*/
    public static String getAppToken(Context context) {
        return ShareUserInfoUtil.getInstance(context).getString(ShareUserInfoUtil.APPTOKEN, "");
    }

    /*获取Token*/
    public static String getToken(Context context) {
        return ShareUserInfoUtil.getInstance(context).getString(ShareUserInfoUtil.TOKEN, "");
    }


}
