package com.lib.showfield.http.model;

import java.io.Serializable;

/**
 * Created by JoeyChow
 * Date  2020-04-10 16:04
 * <p>
 * Description
 **/
public class BaseResponse<T> implements Serializable {

    //状态码
    private int code;
    //信息
    private String msg;
    //data
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
