package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/22 15:34
 * <p>
 * Description
 **/
public class SendMsgBean {

    private String roomNo;
    private String itemId;
    private String content;

    public SendMsgBean() {

    }

    public SendMsgBean(String roomNo, String itemId, String content) {
        this.roomNo = roomNo;
        this.itemId = itemId;
        this.content = content;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
