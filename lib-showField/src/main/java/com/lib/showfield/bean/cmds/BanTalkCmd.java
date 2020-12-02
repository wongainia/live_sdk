package com.lib.showfield.bean.cmds;

/**
 * Created by JoeyChow
 * Date  2020-07-09 15:02
 * <p>
 * Description
 **/
public class BanTalkCmd {

    /**
     * user : {"userId":"684","nickName":"鹰眼用户_178454","avatar":"https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/defaultConfig/eagle_eye.png","userType":0,"userLevel":"3","anchorLevel":null,"certifiedAnchorStatus":1}
     * time : 30
     */

    private UserBean user;
    private int time;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static class UserBean {
        /**
         * userId : 684
         * nickName : 鹰眼用户_178454
         * avatar : https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/defaultConfig/eagle_eye.png
         * userType : 0
         * userLevel : 3
         * anchorLevel : null
         * certifiedAnchorStatus : 1
         */

        private String userId;
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
