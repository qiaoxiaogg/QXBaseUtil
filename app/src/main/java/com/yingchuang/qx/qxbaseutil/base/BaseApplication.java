package com.yingchuang.qx.qxbaseutil.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Process;

import com.jianjin.camera.CustomCameraAgent;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.List;
import butterknife.ButterKnife;



/**
 * Created by Administrator on 2017/8/28.
 */

public class BaseApplication extends Application {

    public static Application mApplication;

//    {
//        PlatformConfig.setWeixin("wxc832c7bc972fa9f6", "8bfdd3841108bbc268faca0b0ec73444");
//        PlatformConfig.setQQZone("1106648182", "pAWWwD0FwgY1j0ev");
//        PlatformConfig.setSinaWeibo("2299262621", "2ef3dbb7b384bce0162e553a9f8987ff", "http://www.winpow.com/");
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;


        /*其他初始化*/
        x.Ext.init(this);
        LogUtil.customTagPrefix = "QiaoXiao";

        x.Ext.setDebug(true);//输出debug日志，开启会影响性能
        ButterKnife.setDebug(false);
        CustomCameraAgent.init(this);
        CustomCameraAgent.openLog();


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /*不使用用户设置的字体*/
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
