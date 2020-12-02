package com.lib.showfield.http.respones.live;

/**
 * Created by JoeyChow
 * Date  2020/6/23 12:56
 * <p>
 * Description
 **/
public class UserCard {

    /**
     * avatar : https://dapai-live-dev.oss-cn-hangzhou.aliyuncs.com/大飞哥
     * isManager : false
     * level : 1
     * nickname : aa
     * fansNum : 0
     * roomNo : 882851
     * isFollow : false
     * canSpeak : true
     * isAnchor : 0
     * userId : 5
     */

    private String avatar;
    private boolean isManager;
    private String level;
    private String nickname;
    private int fansNum;
    private String roomNo;
    private boolean isFollow;
    private boolean canSpeak;
    private int isAnchor;
    private int userId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public boolean isIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

    public boolean isCanSpeak() {
        return canSpeak;
    }

    public void setCanSpeak(boolean canSpeak) {
        this.canSpeak = canSpeak;
    }

    public int getIsAnchor() {
        return isAnchor;
    }

    public void setIsAnchor(int isAnchor) {
        this.isAnchor = isAnchor;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserCard{" +
                "avatar='" + avatar + '\'' +
                ", isManager=" + isManager +
                ", level='" + level + '\'' +
                ", nickname='" + nickname + '\'' +
                ", fansNum=" + fansNum +
                ", roomNo='" + roomNo + '\'' +
                ", isFollow=" + isFollow +
                ", canSpeak=" + canSpeak +
                ", isAnchor=" + isAnchor +
                ", userId=" + userId +
                '}';
    }
}
