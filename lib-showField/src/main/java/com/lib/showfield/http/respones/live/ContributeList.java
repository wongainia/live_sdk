package com.lib.showfield.http.respones.live;

import java.util.List;

/**
 * Created by JoeyChow
 * Date  2020-06-05 10:33
 * <p>
 * Description
 **/
public class ContributeList {


    /**
     * nickname : 花花
     * level : LV10
     * avatar : abc.jpg
     * contribution : 996
     * rankList : [{"nickname":"年年","level":"LV1","avatar":"abc.jpg","contribution":"199.1万","isSelf":0},{"nickname":"岁岁","level":"LV2","avatar":"abc.jpg","contribution":"50.2万","isSelf":0}]
     */

    private String nickname;
    private String level;
    private String avatar;
    private String contribution;
    private List<RankListBean> rankList;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public List<RankListBean> getRankList() {
        return rankList;
    }

    public void setRankList(List<RankListBean> rankList) {
        this.rankList = rankList;
    }

    public static class RankListBean {
        /**
         * nickname : 年年
         * level : LV1
         * avatar : abc.jpg
         * contribution : 199.1万
         * isSelf : 0
         */
        private int userId;
        private String nickname;
        private String level;
        private String avatar;
        private String contribution;
        private int isSelf;
        private long totalValue;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(long totalValue) {
            this.totalValue = totalValue;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContribution() {
            return contribution;
        }

        public void setContribution(String contribution) {
            this.contribution = contribution;
        }

        public int getIsSelf() {
            return isSelf;
        }

        public void setIsSelf(int isSelf) {
            this.isSelf = isSelf;
        }
    }
}
