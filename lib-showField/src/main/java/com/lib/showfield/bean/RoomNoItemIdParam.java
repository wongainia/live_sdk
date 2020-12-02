package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/30 19:04
 * <p>
 * Description
 **/
public class RoomNoItemIdParam {

    private String roomNo;
    private int itemId;

    public RoomNoItemIdParam(String roomNo, int itemId) {
        this.roomNo = roomNo;
        this.itemId = itemId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
