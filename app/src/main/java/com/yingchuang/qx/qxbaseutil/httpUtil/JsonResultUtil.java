package com.yingchuang.qx.qxbaseutil.httpUtil;

import android.content.Context;
import android.widget.Toast;

import com.yingchuang.qx.qxbaseutil.aes.AESSecurity;
import com.yingchuang.qx.qxbaseutil.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;


/*
* 洋芋饭
* 解析网络请求返回的结果数据
* */
public class JsonResultUtil {

    /**
     * 解析结果
     *
     * @param context
     * @param result
     * @return
     */
    public static ResultRequest checkResult(Context context, String result, int dataTypeNum) {
        // 不显示错误提示
        ResultRequest reR = checkResult(context, result, false, dataTypeNum);
        return reR;

    }

    /**
     * 解析结果
     *
     * @param context
     * @param result
     * @param isShowErrorToast 是否显示错误提示
     * @return
     */
    public static ResultRequest checkResult(Context context, String result, boolean isShowErrorToast, int dataTypeNum) {

        ResultRequest reR = new ResultRequest();

        if (result == null) {
            Toast.makeText(context,"连接服务器出错!",Toast.LENGTH_LONG).show();
            return new ResultRequest("", 0, "");

        } else {
            LogUtil.i("result:" + result);
            /*返回数据结构为1类*/
            if (dataTypeNum == 1) {
                try {
                    JSONObject json = new JSONObject(result);
                    reR.setCode(json.getInt("code"));
                    reR.setMsg(json.getString("msg"));
                    if (!StringUtil.isNullOrEmpty(json.getString("data"))) {
                        reR.setData(AESSecurity.decrypt(StringUtil.getPrivateKey(context), json.getString("data")));
                        LogUtil.i("aessecurity decrypt data:" + AESSecurity.decrypt(StringUtil.getPrivateKey(context), json.getString("data")));
                    } else {
                        reR.setData(json.getString("data"));
                    }
                } catch (JSONException e) {
                    LogUtil.i("jsonexception:" + e.toString());
                }
            }

            return reR;
        }
    }

}
