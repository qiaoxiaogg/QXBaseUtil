package com.yingchuang.qx.qxbaseutil.camera;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jianjin.camera.utils.UriUtils;
import com.jianjin.camera.widget.CameraContainer;
import com.jianjin.camera.widget.CameraManager;
import com.jianjin.camera.widget.ISavePicCallback;
import com.yingchuang.qx.qxbaseutil.R;
import com.yingchuang.qx.qxbaseutil.base.TitleBaseActivity;
import com.yingchuang.qx.qxbaseutil.dimen.DimensUtil;
import com.yingchuang.qx.qxbaseutil.statusbar.SystemBarUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * pacakage:com.yingchuang.qx.qxbaseutil.camera
 * author:qx
 * date:2019/9/16
 * time:17:06
 * description:
 */
public class CameraActivity extends TitleBaseActivity  implements ISavePicCallback {

    @BindView(R.id.camera_container)
    CameraContainer cameraContainer;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private CameraManager mCameraManager;

    private String[] tabTitles = {"担保决议书", "推荐函", "放款通知书", "意向承诺函", "放款凭证"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SystemBarUtil.alphaTopBar(R.color.blackColor, this);
        setContentView(R.layout.layout_camera);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        tool_bar.setBackgroundColor(getResources().getColor(R.color.blackColor));
        setTool_bar_tx_left("上传资料");
        setTool_bar_tx_right("相册导入");

        initTabPager();
    }

    @Override
    protected void initData() {
        super.initData();
        mCameraManager = CameraManager.getInstance(this);
        cameraContainer.bindActivity(CameraActivity.this);
        if (cameraContainer != null) {
            cameraContainer.onStart();
        }


    }

    protected void initTabPager() {

        tabLayout.addTab(tabLayout.newTab().setText(tabTitles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitles[1]));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitles[2]));
        tabLayout.addTab(tabLayout.newTab().setText(tabTitles[3]));

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabView(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //第一次进来默认选中第一个
        tabLayout.getTabAt(0).select();
        updateTabView(tabLayout.getTabAt(0), true);
    }


    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
        textView.setText(tabTitles[currentPosition]);
        return view;
    }

    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        if (isSelect) {
            //选中加粗
            TextView tabSelect = (TextView) tab.getCustomView().findViewById(R.id.tab_item_textview);
            tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tabSelect.setText(tab.getText());
            tabSelect.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensUtil.getDimensValue(R.dimen.x30));
        } else {
            TextView tabUnSelect = (TextView) tab.getCustomView().findViewById(R.id.tab_item_textview);
            tabUnSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tabUnSelect.setText(tab.getText());
            tabUnSelect.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensUtil.getDimensValue(R.dimen.x24));
        }
    }

    @OnClick(R.id.camera_iv_take)
    @Override
    protected void onUClick(View view) {
        super.onUClick(view);
        switch (view.getId()) {
            case R.id.camera_iv_take:
                MPermissions.requestPermissions(CameraActivity.this, 26, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        if (res == RESULT_OK) {
            try {
                Uri uri = data.getData();
                if (uri != null) {
                    Intent intent = new Intent(CameraActivity.this, PicActivity.class);
                    intent.putExtra("imgUri", UriUtils.getPath(CameraActivity.this, uri));
                    startActivity(intent);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    @Override
    public void saveComplete(String picPath) {
        cameraContainer.releaseCamera();
        Intent intent = new Intent(this, PicActivity.class);
        intent.putExtra("imgUri", picPath);
        startActivity(intent);
    }

    @Override
    public void saveFailure(String msg) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (cameraContainer != null) {
            cameraContainer.onStart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraContainer != null) {
            cameraContainer.onStop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraManager.unbindView();
        cameraContainer.releaseCamera();
    }

    private int REQUEST_PICTURE = 2;
    @Override
    public void onClickRightTx() {
        MPermissions.requestPermissions(CameraActivity.this, 25, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @PermissionGrant(25)
    @Override
    protected void onRequestSuccess() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICTURE);
    }

    @PermissionDenied(25)
    @Override
    protected void onRequestFailed() {
        Toast.makeText(this,"未获取到sd卡存储权限",Toast.LENGTH_LONG).show();
    }
    @PermissionGrant(26)
    protected void onRequestWriteSuccess() {
        cameraContainer.takePicture(CameraActivity.this);
    }

    @PermissionDenied(26)
    protected void onRequestWriteFailed() {
        Toast.makeText(this,"未获取到sd卡存储权限",Toast.LENGTH_LONG).show();

    }
}
