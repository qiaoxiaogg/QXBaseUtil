package com.yingchuang.qx.qxbaseutil.httpUtil;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;




/**
 * 网络请求 返回编码工具类
 * 洋芋饭
 */

public class HttpCodeUtil {


    public static boolean CodeUtil(Context context, int code, String data) {
        Activity activity = (Activity) context;
        switch (code) {
            case 100:
                return true;

            case 110:
                return true;

            case 200://浮动提示
                Toast.makeText(context,data,Toast.LENGTH_LONG).show();
                return false;

            case 300://弹窗提示
                Toast.makeText(context,data,Toast.LENGTH_LONG).show();
                return false;

            case 400://登录失效返回登陆界面
//                context.startActivity(new Intent(context, LoginActivity.class));
//                Toast.makeText(context,data,Toast.LENGTH_LONG).show();
//                if (!(activity instanceof MainActivity)) {
//                    activity.finish();
//                }
                return false;

            case 500://须重新同步私钥
//                context.startActivity(new Intent(context, LoginActivity.class));
//                new ShowToastUtil(context).showToastBottom(data);
//                if (!(activity instanceof MainActivity)) {
//                    activity.finish();
//                }
                return false;

            case 600://跳转绑定手机界面

                return false;

            case 700://服务器维护

                return false;

            default:

                return false;
        }
    }

}
