package com.yingchuang.qx.qxbaseutil.httpUtil;

/**
 * Created by Administrator on 2015/10/28 0028.
 */

/**
 * 请求返回实体类
 *
 * @author 洋芋饭
 */
public class ResultRequest {


    // 状态值
    private int code;

    // json数据
    private String data;

    // 提示信息
    private String msg;

    public ResultRequest() {
    }

    public ResultRequest(String data, int code, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultRequest{" +
                "code=" + code +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}

