package com.yingchuang.qx.qxbaseutil.httpUtil;

public interface Refresh {

    /**
     * 刷新界面回调方法
     * @param taskId
     * @param params
     */
    void onRefresh(int taskId,Object... params);

    void onError();

}