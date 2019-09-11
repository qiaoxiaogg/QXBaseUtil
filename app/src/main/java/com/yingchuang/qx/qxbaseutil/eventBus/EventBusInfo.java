package com.yingchuang.qx.qxbaseutil.eventBus;

import android.os.Bundle;

/**
 * Created by 洋芋饭
 */

public class EventBusInfo {


    private Bundle bundle;

    public EventBusInfo(Bundle bundle) {
        this.bundle = bundle;
    }


    public Bundle getBundle() {
        return bundle;
    }
}
