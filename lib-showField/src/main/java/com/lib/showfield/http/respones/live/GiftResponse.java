package com.lib.showfield.http.respones.live;

import com.lib.showfield.bean.gift.BaseGiftBean;

/**
 * Created by JoeyChow
 * Date  2020/6/22 13:25
 * <p>
 * Description
 **/
public class GiftResponse extends BaseGiftBean {

    /**
     * id : 4
     * giftType : 1
     * giftId : 003
     * giftName : 洋娃娃
     * giftPrice : 20000
     * defaultGraphUrl :
     * dynamicGraphUrl :
     * playEffectUrl :
     * doubleHit : 1
     * floatingStatus : 1
     * floatingType : 1
     */

    private int id;
    private int giftType;
    private String giftId;
    private String giftName;
    private int giftPrice;
    private String defaultGraphUrl;
    private String dynamicGraphUrl;
    private String playEffectUrl;
    private int doubleHit;
    private int floatingStatus;
    private int floatingType;
    private int userId;

    private boolean isSelected;

    private String userAvatar;
    private String userName;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 礼物持续时间
     */
    private long giftStayTime = 3000;

    /**
     * 单次礼物数目
     */
    private int giftSendSize;


    @Override
    public String getTheGiftId() {
        return giftId;
    }

    @Override
    public void setTheGiftId(String gid) {
        this.giftId = gid;
    }

    @Override
    public int getTheUserId() {
        return userId;
    }

    @Override
    public void setTheUserId(int uid) {
        this.userId = uid;
    }

    @Override
    public int getTheSendGiftSize() {
        return giftSendSize;
    }

    @Override
    public void setTheSendGiftSize(int size) {
        this.giftSendSize = size;
    }

    @Override
    public long getTheGiftStay() {
        return giftStayTime;
    }

    @Override
    public void setTheGiftStay(long stay) {
        this.giftStayTime = stay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGiftType() {
        return giftType;
    }

    public void setGiftType(int giftType) {
        this.giftType = giftType;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(int giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getDefaultGraphUrl() {
        return defaultGraphUrl;
    }

    public void setDefaultGraphUrl(String defaultGraphUrl) {
        this.defaultGraphUrl = defaultGraphUrl;
    }

    public String getDynamicGraphUrl() {
        return dynamicGraphUrl;
    }

    public void setDynamicGraphUrl(String dynamicGraphUrl) {
        this.dynamicGraphUrl = dynamicGraphUrl;
    }

    public String getPlayEffectUrl() {
        return playEffectUrl;
    }

    public void setPlayEffectUrl(String playEffectUrl) {
        this.playEffectUrl = playEffectUrl;
    }

    public int getDoubleHit() {
        return doubleHit;
    }

    public void setDoubleHit(int doubleHit) {
        this.doubleHit = doubleHit;
    }

    public int getFloatingStatus() {
        return floatingStatus;
    }

    public void setFloatingStatus(int floatingStatus) {
        this.floatingStatus = floatingStatus;
    }

    public int getFloatingType() {
        return floatingType;
    }

    public void setFloatingType(int floatingType) {
        this.floatingType = floatingType;
    }

    @Override
    public String toString() {
        return "GiftResponse{" +
                "id=" + id +
                ", giftType=" + giftType +
                ", giftId='" + giftId + '\'' +
                ", giftName='" + giftName + '\'' +
                ", giftPrice=" + giftPrice +
                ", defaultGraphUrl='" + defaultGraphUrl + '\'' +
                ", dynamicGraphUrl='" + dynamicGraphUrl + '\'' +
                ", playEffectUrl='" + playEffectUrl + '\'' +
                ", doubleHit=" + doubleHit +
                ", floatingStatus=" + floatingStatus +
                ", floatingType=" + floatingType +
                ", userId=" + userId +
                ", isSelected=" + isSelected +
                ", userAvatar='" + userAvatar + '\'' +
                ", userName='" + userName + '\'' +
                ", giftStayTime=" + giftStayTime +
                ", giftSendSize=" + giftSendSize +
                '}';
    }
}
