package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/23 12:58
 * <p>
 * Description
 **/
public class UserIdRoomNoParam {

    private int userId;
    private String roomNo;

    public UserIdRoomNoParam(int userId, String roomNo) {
        this.userId = userId;
        this.roomNo = roomNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
