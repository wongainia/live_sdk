package com.lib.showfield.http.respones.live;

/**
 * Created by JoeyChow
 * Date  2020-07-08 14:47
 * <p>
 * Description
 **/
public class BanTalkResponse {

    private boolean isBan;
    private String time;

    public boolean getIsBan() {
        return isBan;
    }

    public void setIsBan(boolean isBan) {
        this.isBan = isBan;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
