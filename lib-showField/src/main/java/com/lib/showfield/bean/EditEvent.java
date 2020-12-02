package com.lib.showfield.bean;

/**
 - @Description:
 - @Author:
 - @Time:  2020-05-19 14:24
 */
public class EditEvent {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;
    private String msg;

    public EditEvent(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
