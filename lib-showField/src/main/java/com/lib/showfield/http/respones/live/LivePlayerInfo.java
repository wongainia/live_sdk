package com.lib.showfield.http.respones.live;

import java.io.Serializable;

/**
 * Created by upingu
 * Date  2020-05-07 15:57
 * <p>
 * Description
 **/
public class LivePlayerInfo implements Serializable {

    /**
     * avatar : 10000/h5/33/2020/06/242ee7f8aa6be39c0643b5d9853ad4c4a1.jpg
     * nickname : 跋扈
     * roomNo : 10000
     * fansNum : 3
     * sportType : 足球
     * title : 我要开始测试直播了啊烦烦烦方法
     * hotNum : 0
     * anchorId : 2
     * liveStatus : 0
     * level : 1
     * liveId : 2
     * isProphecy : 0
     * prophesyTitle : 上海能不能赢
     * follow : false
     */

    private String avatar;
    private int roomType;
    private String nickname;
    private String roomNo;
    private int fansNum;
    private String sportType;
    private String title;
    private int hotNum;
    private int anchorId;
    private int liveStatus;
    private String level;
    private int liveId;
    private int hasGuessRole;
    private int isProphecy;
    private String prophesyTitle;
    private int isFollow;
    private String matchId;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public int getHasGuessRole() {
        return hasGuessRole;
    }

    public void setHasGuessRole(int hasGuessRole) {
        this.hasGuessRole = hasGuessRole;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHotNum() {
        return hotNum;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public int getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public int getIsProphecy() {
        return isProphecy;
    }

    public void setIsProphecy(int isProphecy) {
        this.isProphecy = isProphecy;
    }

    public String getProphesyTitle() {
        return prophesyTitle;
    }

    public void setProphesyTitle(String prophesyTitle) {
        this.prophesyTitle = prophesyTitle;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }
}
