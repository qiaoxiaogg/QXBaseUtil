package com.yingchuang.qx.qxbaseutil.dimen;

import org.xutils.x;

/**
 * Created by 洋芋饭 .
 */

public class DimensUtil {
    public static int getDimensValue(int dimensId) {
        return (int) x.app().getResources().getDimension(dimensId);
    }
}
