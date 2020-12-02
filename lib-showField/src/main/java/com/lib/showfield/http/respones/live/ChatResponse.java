package com.lib.showfield.http.respones.live;

/**
 * Created by upingu
 * Date  2020-05-11 15:14
 * <p>
 * Description
 **/
public class ChatResponse {


    /**
     * user : {"userId":"668","nickName":"忧忧可乐","avatar":"http://www.baidu.com","userType":0,"userLevel":"1","certifiedAnchorStatus":1}
     * messageType : 1
     * content : hello every one
     */

    private UserBean user;
    private int messageType;
    private String content;
    private int isShowAll;

    public int getIsShowAll() {
        return isShowAll;
    }

    public void setIsShowAll(int isShowAll) {
        this.isShowAll = isShowAll;
    }

    private int userType;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getMessageType() {
        return messageType;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public static class UserBean {
        /**
         * userId : 668
         * nickName : 忧忧可乐
         * avatar : http://www.baidu.com
         * userType : 0
         * userLevel : 1
         * certifiedAnchorStatus : 1
         */

        private String userId;
        private String nickName;
        private String avatar;
        public int userType;
        private String userLevel;
        private String anchorLevel;
        private int certifiedAnchorStatus;
        public String supportTeamLogo;


        public String getAnchorLevel() {
            return anchorLevel;
        }

        public void setAnchorLevel(String anchorLevel) {
            this.anchorLevel = anchorLevel;
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

        public int getCertifiedAnchorStatus() {
            return certifiedAnchorStatus;
        }

        public void setCertifiedAnchorStatus(int certifiedAnchorStatus) {
            this.certifiedAnchorStatus = certifiedAnchorStatus;
        }
    }
}
