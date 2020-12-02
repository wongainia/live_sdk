package com.lib.showfield.http.respones.live;

import java.io.Serializable;
import java.util.List;

/**
 * Created by upingu
 * Date  2020-04-10 15:38
 * <p>
 * Description
 **/
public class FocusRecommendLives {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable {
        /**
         * id : 360
         * cover : http://image.upingudata.cn/live-138cd440d0e74db9987937eb41ba2eb9--20200712113318.jpg
         * anchorId : 789
         * nickname : 小飞人
         * avatar : https://dp-live-test.oss-cn-hangzhou.aliyuncs.com/10000/android/789/2020/07/09/42bd53ce516d4ea0a6a212fdb6b1e40cavatar.jpg
         * hotNum : 1369
         * title : 小飞人的直播间
         * pullId : d18a08f5aaf44e83966f69a49c0d3f68
         * roomNo : 636373
         * isProphecy : 0
         */
        private int id;
        private String cover;
        private int anchorId;
        private String nickname;
        private String avatar;
        private int hotNum;
        private String title;
        private String pullId;
        private String roomNo;
        private int isProphecy;
        private int hasRedPacket;//": 0,    //房间是否有红包，0: 不存在 1: 存在       1.3 新增
        private int screenMode;
        private String platform;
        private String pullUrl;
        private int sportType;
        private String eventName;

        public int getSportType() {
            return sportType;
        }

        public void setSportType(int sportType) {
            this.sportType = sportType;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getPullUrl() {
            return pullUrl;
        }

        public void setPullUrl(String pullUrl) {
            this.pullUrl = pullUrl;
        }

        public int getScreenMode() {
            return screenMode;
        }

        public void setScreenMode(int screenMode) {
            this.screenMode = screenMode;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public int getHasRedPacket() {
            return hasRedPacket;
        }

        public void setHasRedPacket(int hasRedPacket) {
            this.hasRedPacket = hasRedPacket;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getAnchorId() {
            return anchorId;
        }

        public void setAnchorId(int anchorId) {
            this.anchorId = anchorId;
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

        public int getHotNum() {
            return hotNum;
        }

        public void setHotNum(int hotNum) {
            this.hotNum = hotNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPullId() {
            return pullId;
        }

        public void setPullId(String pullId) {
            this.pullId = pullId;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public int getIsProphecy() {
            return isProphecy;
        }

        public void setIsProphecy(int isProphecy) {
            this.isProphecy = isProphecy;
        }

        @Override
        public String toString() {
            return "RowsBean{" +
                    "id=" + id +
                    ", cover='" + cover + '\'' +
                    ", anchorId=" + anchorId +
                    ", nickname='" + nickname + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", hotNum=" + hotNum +
                    ", title='" + title + '\'' +
                    ", pullId='" + pullId + '\'' +
                    ", roomNo='" + roomNo + '\'' +
                    ", isProphecy=" + isProphecy +
                    '}';
        }
    }
}
