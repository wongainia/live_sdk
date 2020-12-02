package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/23 17:25
 * <p>
 * Description
 **/
public class ForbiddenParam {

    private String roomNo;
    private int anchorId;
    private int userId;
    private int time;

    public ForbiddenParam(String roomNo, int anchorId, int userId, int time) {
        this.roomNo = roomNo;
        this.anchorId = anchorId;
        this.userId = userId;
        this.time = time;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
