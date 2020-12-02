package com.lib.showfield.bean;

import java.io.Serializable;

/**
 * Created by JoeyChow
 * Date  2020-05-07 17:11
 * <p>
 * Description
 **/
public class FocusChatAnchorParam implements Serializable {

    private String followId;

    public FocusChatAnchorParam(String followId, Integer opType, Integer followSceneType, String roomNo) {
        this.followId = followId;
        this.opType = opType;
        this.followSceneType = followSceneType;
        this.roomNo = roomNo;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }


    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Integer getFollowSceneType() {
        return followSceneType;
    }

    public void setFollowSceneType(Integer followSceneType) {
        this.followSceneType = followSceneType;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    private Integer opType;
    private Integer followSceneType;
    private String roomNo;
}
