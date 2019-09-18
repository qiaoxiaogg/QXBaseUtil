package com.yingchuang.qx.qxbaseutil;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yingchuang.qx.qxbaseutil.base.BaseActivity;
import com.yingchuang.qx.qxbaseutil.base.TitleBaseActivity;
import com.yingchuang.qx.qxbaseutil.camera.CameraActivity;
import com.yingchuang.qx.qxbaseutil.statusbar.SystemBarUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import butterknife.OnClick;

/*
* author 洋芋饭
* 启动页面
* */

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SystemBarUtil.alphaTopBar(R.color.white, this);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.camera)
    @Override
    protected void onUClick(View view) {
        super.onUClick(view);
        switch (view.getId()){
            case R.id.camera:
                MPermissions.requestPermissions(MainActivity.this, 24, Manifest.permission.CAMERA);
                break;
        }
    }
    @PermissionGrant(24)
    protected void requestSuccess(){
        startActivity(new Intent(this,CameraActivity.class));
    }
    @PermissionDenied(24)
    protected void requestFailed(){
        Toast.makeText(this,"未获取到相机权限",Toast.LENGTH_LONG).show();
    }

}
