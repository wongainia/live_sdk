package com.lib.showfield.http.respones.live;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by upingu
 * Date  2020/6/22 16:32
 * <p>
 * Description
 **/
public class ChatMessage implements MultiItemEntity {

    private String userId;
    private String nickName;
    private String avatar;
    private String userLevel;
    private String anchorLevel;
    private int anthorId;
    private int certifiedAnchorStatus;

    public String getAnchorLevel() {
        return anchorLevel;
    }

    public void setAnchorLevel(String anchorLevel) {
        this.anchorLevel = anchorLevel;
    }

    //礼物数
    private int giftNum;
    //礼物名
    private String giftName;
    //礼物图标
    private String giftIcon;

    private int messageType;
    private String content;
    //发送红包昵称
    private String senderNickName;
    //发送红包人 所在的战队图标
    private String supportTeamLogo;


    public int getAnthorId() {
        return anthorId;
    }

    public void setAnthorId(int anthorId) {
        this.anthorId = anthorId;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftIcon() {
        return giftIcon;
    }

    public void setGiftIcon(String giftIcon) {
        this.giftIcon = giftIcon;
    }

    @Override
    public int getItemType() {
        return messageType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public int getCertifiedAnchorStatus() {
        return certifiedAnchorStatus;
    }

    public void setCertifiedAnchorStatus(int certifiedAnchorStatus) {
        this.certifiedAnchorStatus = certifiedAnchorStatus;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderNickName() {
        return senderNickName;
    }

    public void setSenderNickName(String senderNickName) {
        this.senderNickName = senderNickName;
    }

    public String getSupportTeamLogo() {
        return supportTeamLogo;
    }

    public void setSupportTeamLogo(String supportTeamLogo) {
        this.supportTeamLogo = supportTeamLogo;
    }
}
