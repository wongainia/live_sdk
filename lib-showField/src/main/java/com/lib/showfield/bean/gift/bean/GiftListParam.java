package com.lib.showfield.bean.gift.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/22 13:27
 * <p>
 * Description
 **/
public class GiftListParam {

    private String roomNo;
    private int roomType;

    public GiftListParam(String roomNo, int roomType) {
        this.roomNo = roomNo;
        this.roomType = roomType;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }
}
