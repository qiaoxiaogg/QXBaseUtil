
package com.yingchuang.qx.qxbaseutil.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yingchuang.qx.qxbaseutil.R;


/**
 * 自定义加载动画
 *
 * @author 洋芋饭
 */
public class DialogProgress extends Dialog {

    public DialogProgress(Context context) {
        super(context, R.style.DialogProgress);
    }

    public DialogProgress(Context context, int style) {
        super(context, style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        Window windowDia = this.getWindow();
        WindowManager.LayoutParams lp = windowDia.getAttributes();
        lp.gravity = Gravity.CENTER;
        windowDia.setAttributes(lp);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }
}
