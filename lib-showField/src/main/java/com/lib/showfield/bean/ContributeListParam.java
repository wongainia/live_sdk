package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/23 09:53
 * <p>
 * Description
 **/
public class ContributeListParam {

    private String roomNo;
    private int rankType;

    public ContributeListParam(String roomNo, int rankType) {
        this.roomNo = roomNo;
        this.rankType = rankType;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
