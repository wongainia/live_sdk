package com.lib.showfield.bean.cmds;

/**
 * Created by JoeyChow
 * Date  2020/6/23 20:51
 * <p>
 * Description
 **/
public class GiftMessage {


    /**
     * user : {"userId":"792","uid":"865102","nickName":"人皇sky","avatar":"https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/android/792/2020/07/09/38bf475d30d8439bbe97dfcf780d65f6avatar.jpg","userType":0,"userLevel":"8","anchorLevel":null,"certifiedAnchorStatus":1}
     * gift : {"giftId":"001","name":"火箭","icon":"https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/h5/33/2020/07/10/82f27770cf9c4c478d617341d529afd5%E7%81%AB%E7%AE%AD%401x.png","quantity":1,"giftType":2,"floatingStatus":1,"playEffectUrl":"https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/h5/53/2020/07/10/541280da9de440a0a5e4c10d766ffd92%E7%81%AB%E7%AE%AD.svga","dynamicGraphUrl":"https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/h5/33/2020/07/10/1846253e128e43828783da457b22aa8d%E7%81%AB%E7%AE%AD%E9%A2%84%E8%A7%88.gif","doubleHit":2}
     * anchor : {"userId":"828","uid":"494809","nickName":"494809","avatar":"https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/defaultConfig/eagle_eye.png","userType":0,"userLevel":"1","anchorLevel":null,"certifiedAnchorStatus":1}
     */

    private UserBean user;
    private GiftBean gift;
    private AnchorBean anchor;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public GiftBean getGift() {
        return gift;
    }

    public void setGift(GiftBean gift) {
        this.gift = gift;
    }

    public AnchorBean getAnchor() {
        return anchor;
    }

    public void setAnchor(AnchorBean anchor) {
        this.anchor = anchor;
    }

    public static class UserBean {
        /**
         * userId : 792
         * uid : 865102
         * nickName : 人皇sky
         * avatar : https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/android/792/2020/07/09/38bf475d30d8439bbe97dfcf780d65f6avatar.jpg
         * userType : 0
         * userLevel : 8
         * anchorLevel : null
         * certifiedAnchorStatus : 1
         */

        private String userId;
        private String uid;
        private String nickName;
        private String avatar;
        private int userType;
        private String userLevel;
        private String anchorLevel;
        private int certifiedAnchorStatus;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        public String getAnchorLevel() {
            return anchorLevel;
        }

        public void setAnchorLevel(String anchorLevel) {
            this.anchorLevel = anchorLevel;
        }

        public int getCertifiedAnchorStatus() {
            return certifiedAnchorStatus;
        }

        public void setCertifiedAnchorStatus(int certifiedAnchorStatus) {
            this.certifiedAnchorStatus = certifiedAnchorStatus;
        }
    }

    public static class GiftBean {
        /**
         * giftId : 001
         * name : 火箭
         * icon : https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/h5/33/2020/07/10/82f27770cf9c4c478d617341d529afd5%E7%81%AB%E7%AE%AD%401x.png
         * quantity : 1
         * giftType : 2
         * floatingStatus : 1
         * playEffectUrl : https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/h5/53/2020/07/10/541280da9de440a0a5e4c10d766ffd92%E7%81%AB%E7%AE%AD.svga
         * dynamicGraphUrl : https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/h5/33/2020/07/10/1846253e128e43828783da457b22aa8d%E7%81%AB%E7%AE%AD%E9%A2%84%E8%A7%88.gif
         * doubleHit : 2
         */

        private String giftId;
        private String name;
        private String icon;
        private int quantity;
        private int giftType;
        private int floatingStatus;
        private String playEffectUrl;
        private String dynamicGraphUrl;
        private int doubleHit;
        private int floatingType;

        public int getFloatingType() {
            return floatingType;
        }

        public void setFloatingType(int floatingType) {
            this.floatingType = floatingType;
        }

        public String getGiftId() {
            return giftId;
        }

        public void setGiftId(String giftId) {
            this.giftId = giftId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getGiftType() {
            return giftType;
        }

        public void setGiftType(int giftType) {
            this.giftType = giftType;
        }

        public int getFloatingStatus() {
            return floatingStatus;
        }

        public void setFloatingStatus(int floatingStatus) {
            this.floatingStatus = floatingStatus;
        }

        public String getPlayEffectUrl() {
            return playEffectUrl;
        }

        public void setPlayEffectUrl(String playEffectUrl) {
            this.playEffectUrl = playEffectUrl;
        }

        public String getDynamicGraphUrl() {
            return dynamicGraphUrl;
        }

        public void setDynamicGraphUrl(String dynamicGraphUrl) {
            this.dynamicGraphUrl = dynamicGraphUrl;
        }

        public int getDoubleHit() {
            return doubleHit;
        }

        public void setDoubleHit(int doubleHit) {
            this.doubleHit = doubleHit;
        }
    }

    public static class AnchorBean {
        /**
         * userId : 828
         * uid : 494809
         * nickName : 494809
         * avatar : https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/defaultConfig/eagle_eye.png
         * userType : 0
         * userLevel : 1
         * anchorLevel : null
         * certifiedAnchorStatus : 1
         */

        private String userId;
        private String uid;
        private String nickName;
        private String avatar;
        private int userType;
        private String userLevel;
        private Object anchorLevel;
        private int certifiedAnchorStatus;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        public Object getAnchorLevel() {
            return anchorLevel;
        }

        public void setAnchorLevel(Object anchorLevel) {
            this.anchorLevel = anchorLevel;
        }

        public int getCertifiedAnchorStatus() {
            return certifiedAnchorStatus;
        }

        public void setCertifiedAnchorStatus(int certifiedAnchorStatus) {
            this.certifiedAnchorStatus = certifiedAnchorStatus;
        }
    }
}
