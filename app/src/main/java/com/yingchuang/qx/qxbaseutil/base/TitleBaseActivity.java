package com.yingchuang.qx.qxbaseutil.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yingchuang.qx.qxbaseutil.R;
import com.yingchuang.qx.qxbaseutil.activitymanager.ActivityManager;
import com.yingchuang.qx.qxbaseutil.httpUtil.UrlConfig;
import com.yingchuang.qx.qxbaseutil.listener.TitleIBtnOnClickListener;
/**
 * Created by Administrator on 2017/8/28.
 */

public class TitleBaseActivity extends BaseActivity implements TitleIBtnOnClickListener, View.OnClickListener{

    /*tool_bar相关控件*/
    protected RelativeLayout tool_bar;
    protected View tool_barView;
    protected RelativeLayout tool_bar_left_relativeLayout;
    protected AppCompatImageView tool_bar_left_icon;
    protected TextView tool_bar_tx_right;
    protected TextView tool_bar_tx_center;
    protected TextView tool_bar_tx_left;

    protected RelativeLayout tool_bar_right_relativeLayout_l;
    protected RelativeLayout tool_bar_right_relativeLayout;
    protected AppCompatImageView tool_bar_right_icon_l;
    protected AppCompatImageView tool_bar_right_icon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initBtn();
        super.onCreate(savedInstanceState);
    }

    private void initBtn() {
        tool_bar = (RelativeLayout) findViewById(R.id.tool_bar);
        tool_barView = findViewById(R.id.tool_barView);
        tool_bar_left_relativeLayout = (RelativeLayout) findViewById(R.id.tool_bar_left_relativeLayout);
        tool_bar_left_icon = (AppCompatImageView) findViewById(R.id.tool_bar_left_icon);
        tool_bar_tx_right = (TextView) findViewById(R.id.tool_bar_tx_right);
        tool_bar_tx_center = (TextView) findViewById(R.id.tool_bar_tx_center);
        tool_bar_tx_left = (TextView) findViewById(R.id.tool_bar_tx_left);
        tool_bar_right_relativeLayout = (RelativeLayout) findViewById(R.id.tool_bar_right_relativeLayout);
        tool_bar_right_relativeLayout_l = (RelativeLayout) findViewById(R.id.tool_bar_right_relativeLayout_l);
        tool_bar_right_icon = (AppCompatImageView) findViewById(R.id.tool_bar_right_icon);
        tool_bar_right_icon_l = (AppCompatImageView) findViewById(R.id.tool_bar_right_icon_l);
        tool_bar_left_relativeLayout.setOnClickListener(this);
        tool_bar_right_relativeLayout.setOnClickListener(this);
        tool_bar_right_relativeLayout_l.setOnClickListener(this);
        tool_bar_tx_right.setOnClickListener(this);
        tool_bar_tx_left.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_bar_left_relativeLayout:
                onClickLeftRelativeLayout();
                break;
            case R.id.tool_bar_tx_left:
                onClickLeftTx();
                break;
            case R.id.tool_bar_right_relativeLayout:
                onClickRightRelativeLayout();
                break;
            case R.id.tool_bar_right_relativeLayout_l:
                onClickRightRelativeLayout_l();
                break;
            case R.id.tool_bar_tx_right:
                onClickRightTx();
                break;
        }
    }

    /**
     * 点击tool_bar左边的文字
     */
    @Override
    public void onClickLeftTx() {
        finish();
    }

    /**
     * 点击tool_bar右边的文字
     */
    @Override
    public void onClickRightTx() {

    }

    /**
     * 点击tool_bar右边RelativeLayout
     */
    @Override
    public void onClickRightRelativeLayout() {

    }

    /**
     * 点击tool_bar右边RelativeLayout
     */
    @Override
    public void onClickRightRelativeLayout_l() {

    }


    /**
     * 点击tool_bar左边RelativeLayout
     */
    @Override
    public void onClickLeftRelativeLayout() {
        UrlConfig.baseUrl.clear();
        ActivityManager.getInstance().popOneActivity(this);
        closeKeyboard();
        finish();
    }

    /**
     * 设置左边的图标
     *
     * @param imgId
     */
    protected void setTool_bar_left_icon(int imgId) {
        tool_bar_left_icon.setImageResource(imgId);
    }


    /**
     * 设置左边的文字
     *
     * @param s
     */
    protected void setTool_bar_tx_left(String s) {
        tool_bar_tx_left.setVisibility(View.VISIBLE);
        tool_bar_tx_left.setText(s);
    }



    /**
     * 设置右的图标1
     *
     * @param imgId
     */
    protected void setTool_bar_right_icon(int imgId) {
        tool_bar_right_relativeLayout.setVisibility(View.VISIBLE);
        tool_bar_right_icon.setImageResource(imgId);
    }

    /**
     * 设置右的图标2
     *
     * @param imgId
     */
    protected void setTool_bar_right_icon_l(int imgId) {
        tool_bar_right_relativeLayout_l.setVisibility(View.VISIBLE);
        tool_bar_right_icon_l.setImageResource(imgId);
    }

    /**
     * 设置右边的文字
     *
     * @param s
     */
    protected void setTool_bar_tx_right(String s) {
        tool_bar_tx_right.setVisibility(View.VISIBLE);
        tool_bar_tx_right.setText(s);
    }

    /**
     * 设置中间的文字
     *
     * @param s
     */
    protected void setTool_bar_tx_center(String s) {
        tool_bar_tx_center.setText(s);
    }



    /**
     * 设置title颜色
     *
     * @param colorId
     */
    protected void setTool_barView_color(int colorId) {
        tool_barView.setBackgroundColor(ContextCompat.getColor(context, colorId));
    }

    /**
     * 设置title颜色
     *
     * @param f
     */
    protected void setTool_barView_Alph(float f) {
        tool_barView.setAlpha(f);
    }


}
