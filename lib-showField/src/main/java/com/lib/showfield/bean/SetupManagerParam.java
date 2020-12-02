package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/23 18:20
 * <p>
 * Description
 **/
public class SetupManagerParam {

    private String roomNo;
    private int userId;
    private int anchorId;

    public SetupManagerParam(String roomNo, int userId, int anchorId) {
        this.roomNo = roomNo;
        this.userId = userId;
        this.anchorId = anchorId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
    }
}
