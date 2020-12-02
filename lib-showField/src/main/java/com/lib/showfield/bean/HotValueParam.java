package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020-05-19 16:57
 * <p>
 * Description
 **/
public class HotValueParam {

    private String userId;
    private int num;

    public HotValueParam(String userId, int num) {
        this.userId = userId;
        this.num = num;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
