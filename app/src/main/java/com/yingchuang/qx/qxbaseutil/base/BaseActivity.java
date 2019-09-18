package com.yingchuang.qx.qxbaseutil.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yingchuang.qx.qxbaseutil.R;
import com.yingchuang.qx.qxbaseutil.acache.ACache;
import com.yingchuang.qx.qxbaseutil.activitymanager.ActivityManager;
import com.yingchuang.qx.qxbaseutil.dimen.DimensUtil;
import com.yingchuang.qx.qxbaseutil.eventBus.EventBusInfo;
import com.yingchuang.qx.qxbaseutil.httpUtil.HttpUtil;
import com.yingchuang.qx.qxbaseutil.httpUtil.Refresh;
import com.yingchuang.qx.qxbaseutil.httpUtil.UrlConfig;
import com.yingchuang.qx.qxbaseutil.listener.ScreenListener;
import com.yingchuang.qx.qxbaseutil.statusbar.SystemBarUtil;
import com.zhy.m.permission.MPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;


/**
 * Created by 洋芋饭
 */

public class BaseActivity extends AppCompatActivity implements Refresh, SpringView.OnFreshListener,
        ViewPager.OnPageChangeListener,
        HttpUtil.LinkTimeOut, ScreenListener.ScreenStateListener {
    //兼容5.0以前的版本SVG
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected ActivityManager activityManager;
    protected Context context;
    protected Refresh refresh;
    protected LayoutInflater layoutInflater;


    /*网络相关*/
    protected HttpUtil httpUtil;
    protected RequestParams requestParams;
    protected int code;
    protected String data;
    protected String message;
    protected Gson gson;
    protected int page = 1;

    private ScreenListener screenListener;

    protected ACache aCache;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在Android5.0及以上版本，Android对WebView进行了优化，为了减少内存使用和提高性能，使用WebView加载网页时只绘制显示部分。
        // 如果我们不做处理，仍然使用上述代码截图的话，就会出现只截到屏幕内显示的WebView内容，其它部分是空白的情况。
        // 这时候，我们通过调用WebView.enableSlowWholeDocumentDraw()方法可以关闭这种优化，但要注意的是，该方法需要在WebView实例被创建前就要调用，否则没有效果。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.WebView.enableSlowWholeDocumentDraw();
        }
        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(this, R.color.line_background));
        EventBus.getDefault().register(this);

        x.view().inject(this);
        ButterKnife.bind(this);

        /*将activity加入栈中*/
        gson = new Gson();
        activityManager = ActivityManager.getInstance();
        activityManager.addOneActivity(this);
        refresh = this;
        context = this;
        screenListener = new ScreenListener(context);
        screenListener.begin(this);
        layoutInflater = LayoutInflater.from(context);
        aCache = ACache.get(context);
        initData();
        initView();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //TODO 某些操作
            newConfig.orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }


    /**
     * 初始化控件
     */
    protected void initView() {

    }

    /**
     * 获取数据
     */
    protected void getData() {

    }

    /**
     * 刷新空数据
     */
    protected void refreshData() {

    }


    /**
     * 刷新空数据1
     */
    protected void refreshData1() {

    }


    /**
     * 按钮点击
     *
     * @param view
     */
    protected void onUClick(View view) {


    }

    /**
     * item点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    protected void onUItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 隐藏空布局
     */
    protected void goneLayout() {

    }

    /**
     * 显示空布局
     */
    protected void visibleLayout() {

    }


    /**
     * 显示Toast消息
     *
     * @param msg
     */

    protected Toast mToast;


    protected void showToast(String msg) {
        View view = layoutInflater.inflate(R.layout.toast_style, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(msg);
        mToast = new Toast(this);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, DimensUtil.getDimensValue(R.dimen.y60));
        mToast.setView(view);
        mToast.show();
    }

    /**
     * 刷新网络数据
     *
     * @param taskId
     * @param params
     */
    @Override
    public void onRefresh(int taskId, Object... params) {
        code = (int) params[0];
        data = (String) params[1];
        message = (String) params[2];
    }

    /*
     * 请求数据异常
     *
     * */

    @Override
    public void onError() {

    }

    /**
     * 在ui线程执行
     *
     * @param event
     */
    protected Bundle eventBundle;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(EventBusInfo event) {
        eventBundle = event.getBundle();
    }


    /*权限处理问题*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 初始化刷新控件
     */
    protected void initSpringView(SpringView springView) {
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setGive(SpringView.Give.BOTH);
        springView.setType(SpringView.Type.OVERLAP);
        springView.setMoveTime(500);
        springView.setMoveTimeOver(500);
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

    }

    /**
     * 上啦加载
     */
    @Override
    public void onLoadmore() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 链接超时
     */
    @Override
    public void link() {

    }

    /**
     * 获得权限
     */
    protected void onRequestSuccess() {

    }

    /**
     * 未获得权限
     */
    protected void onRequestFailed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            UrlConfig.baseUrl.clear();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 关闭软键盘
     */
    public void closeKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开软键盘
     */
    public void showKeyboard() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 500);

    }


    //标记APP是否从后台再次进入的flag
    protected boolean flag = false;

    @Override
    protected void onStop() {
        super.onStop();
        boolean background = SystemBarUtil.isBackground(context);
        if (background) {
            flag = true;
        }
    }

    @Override
    public void onScreenOn() {
    }

    @Override
    public void onScreenOff() {

    }

    @Override
    public void onUserPresent() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (screenListener != null) {
            screenListener.unregisterListener();
        }
        if (httpUtil != null) {
            httpUtil.cancelRequst();
        }
        ActivityManager.getInstance().popOneActivity(this);
        EventBus.getDefault().unregister(this);

    }


}
