package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020-05-07 19:12
 * <p>
 * Description
 **/
public class RoomNoParam {

    private String roomNo;

    public RoomNoParam(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomId() {
        return roomNo;
    }

    public void setRoomId(String roomNo) {
        this.roomNo = roomNo;
    }
}
