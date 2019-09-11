package com.yingchuang.qx.qxbaseutil.httpUtil;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


import com.yingchuang.qx.qxbaseutil.aes.AESSecurity;
import com.yingchuang.qx.qxbaseutil.dialog.DialogProgress;
import com.yingchuang.qx.qxbaseutil.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;


import java.util.ArrayList;
import java.util.List;


/**
 * Created 洋芋饭
 * 网络请求
 */
public class HttpUtil implements Callback.CommonCallback<String> {

    private Context context;
    private Refresh refresh;
    private DialogProgress dialogProgress;
    private int taskId;
    private boolean isDialog = false;// 是否显示加载状态
    private boolean isReturnErrorMsg;// 是否返回错误信息
    private int dataTypeNum;


    public HttpUtil(Context context, Refresh refresh, int taskId, boolean isDialog, boolean isReturnErrorMsg, int dataTypeNum) {
        this.context = context;
        this.refresh = refresh;
        this.taskId = taskId;
        this.isDialog = isDialog;
        this.isReturnErrorMsg = isReturnErrorMsg;
        this.dataTypeNum = dataTypeNum;
    }

    private RequestParams requestParams;

    /*get请求*/
    public void httpGet(RequestParams requestParams) {
        x.http().get(requestParams, this);
    }

    public static ResultRequest resultRequest;




    /*post加密请求*/
    public void httpPost(RequestParams requestParams) {
        if (UrlConfig.baseUrl.contains(requestParams.getUri())) return;
        this.requestParams = requestParams;
        UrlConfig.baseUrl.add(requestParams.getUri());
        if (context == null) {
            return;
        }
        if (dialogProgress == null) {
            dialogProgress = new DialogProgress(context);
        }
        if (isDialog) {
            if (!((Activity) context).isFinishing()) {
                dialogProgress.show();
            }
        }
        RequestParams newRequestParams = new RequestParams(requestParams.getUri());
        List<KeyValue> keyValues = requestParams.getStringParams();
        for (int i = 0; i < keyValues.size(); i++) {
            LogUtil.i("requestParams：" + keyValues.get(i).key + "  :  " + keyValues.get(i).value);
        }
        JSONObject object = new JSONObject();
        for (int i = 0; i < keyValues.size(); i++) {
            try {
                object.put(keyValues.get(i).key, keyValues.get(i).value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String privateKey = StringUtil.getPrivateKey(context);
        /*对数据进行加密操作*/
        newRequestParams.addBodyParameter("data", AESSecurity.encrypt(privateKey, object.toString()));
        String token = "";
        if (!StringUtil.isNullOrEmpty(StringUtil.getToken(context))) {
            token = StringUtil.getToken(context);
        } else {
            token = StringUtil.getAppToken(context);
        }
        /*扩展参数 token*/
        newRequestParams.addBodyParameter("token", token);
        LogUtil.i("token:" + token);
        LogUtil.i("commitData:" + object.toString());
        LogUtil.i("aesecurity encrypt commitData:" + AESSecurity.encrypt(privateKey, object.toString()));
        x.http().post(newRequestParams, this);
    }


    @Override
    public void onSuccess(String result) {
        UrlConfig.baseUrl.clear();
        if (dialogProgress != null)
            dialogProgress.dismiss();
        LogUtil.i("http post success");
        try {
            resultRequest = JsonResultUtil.checkResult(context, result.toString(), isReturnErrorMsg, dataTypeNum);
        } catch (Exception e) {
            return;
        }
        if (resultRequest == null || !isReturnErrorMsg) {
            return;
        }
        List<Object> listBackParams = new ArrayList<>();
        if (dataTypeNum == 1) {
            listBackParams.add(resultRequest.getCode());
            listBackParams.add(resultRequest.getData());
            listBackParams.add(resultRequest.getMsg());
        }
        refresh.onRefresh(taskId, listBackParams.toArray());
    }


    public interface LinkTimeOut {
        void link();
    }

    private LinkTimeOut linkTimeOut;

    public void setLinkTimeOut(LinkTimeOut linkTimeOut) {
        this.linkTimeOut = linkTimeOut;
    }

    @Override
    public void onError(Throwable throwable, boolean b) {
        LogUtil.i("http post error");
        UrlConfig.baseUrl.clear();
        try {
            if (!HttpsLinkUtil.isNetworkAvailable(context)) {
               Toast.makeText(context,"获取数据失败，请检查网络!",Toast.LENGTH_LONG).show();
            } else {
                if (linkTimeOut != null) {
                    linkTimeOut.link();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialogProgress.dismiss();
        refresh.onError();

    }

    @Override
    public void onCancelled(CancelledException e) {
        UrlConfig.baseUrl.clear();
    }

    @Override
    public void onFinished() {
        UrlConfig.baseUrl.clear();
    }



    public void cancelRequst() {
        if (requestParams == null) return;
        x.http().post(requestParams, this).cancel();
    }
}
