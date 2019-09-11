package com.yingchuang.qx.qxbaseutil.activitymanager;

import android.app.Activity;

import org.xutils.common.util.LogUtil;

import java.util.Stack;

/**
 * 管理所有的Activity
 *
 * @author 洋芋饭
 */
public class ActivityManager {
    private static ActivityManager instance;
    private Stack<Activity> activityStack;// activity栈

    private ActivityManager() {
    }

    /*单例模式*/
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /*把一个activity压入栈中*/
    public void addOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(actvity);
        LogUtil.d("size = " + activityStack.size());
    }

    /*获取栈顶的activity，先进后出原则*/
    public Activity getLastActivity() {
        return activityStack.lastElement();
    }

    /*移除一个activity*/
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }

    /* 结束指定类名的Activity*/
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                popOneActivity(activity);
            }
        }
    }


    /*退出所有activity*/
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null)
                    break;
                popOneActivity(activity);
            }
        }
    }

    /*返回activity个数*/
    public int activityNum() {
        return activityStack.size();
    }

    public Stack<Activity> getStack() {
        return activityStack;
    }


    /*判断摸个Activity是否存在栈中*/

    public boolean checkIsExit(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass() == cls) {
                return true;
            }
        }
        return false;
    }


}
