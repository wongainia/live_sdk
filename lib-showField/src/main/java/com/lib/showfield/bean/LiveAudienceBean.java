package com.lib.showfield.bean;

import java.util.List;

public class LiveAudienceBean {

    /**
     * total : 1
     * list : [{"userId":372953,"roomNo":"686255","nickname":"人皇sky","avatar":"https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/android/372953/2020/10/21/105bc79035be4460be23b848ed44a53bavatar.jpg","userLevel":"29"}]
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * userId : 372953
         * roomNo : 686255
         * nickname : 人皇sky
         * avatar : https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com/10000/android/372953/2020/10/21/105bc79035be4460be23b848ed44a53bavatar.jpg
         * userLevel : 29
         */

        private int userId;
        private String roomNo;
        private String nickname;
        private String avatar;
        private String userLevel;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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
    }
}
