package com.lib.showfield.bean;


public class ToppicUrl {

    public String getChatRoomId() {
        return roomNo;
    }

    public void setChatRoomId(String chatRoomId) {
        this.roomNo = chatRoomId;
    }

    private String roomNo;

    public ToppicUrl(String chatRoomId) {
        this.roomNo = chatRoomId;
    }

}
