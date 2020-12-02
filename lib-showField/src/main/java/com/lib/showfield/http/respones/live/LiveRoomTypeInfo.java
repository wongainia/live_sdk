package com.lib.showfield.http.respones.live;

import java.io.Serializable;

public class LiveRoomTypeInfo implements Serializable {

    /**
     * coverUrl : https://dapai-live-test.oss-cn-hangzhou.aliyuncs.com//10000/ios/372952/2020/10/28/9d4d6f38f6a24f198dbc66d3477fad38f2c050bf3448a1e0a16a249108064007.png
     * anchorId : 372952
     * liveStatus : 1
     * roomNo : 950254
     * screenMode : 2
     * platform : ios
     * pullUrl : rtmp://pull-test.live.upingu.cn/live/a0cf65d9c9024d1cbd3173077148d598_350
     * title : 沙漠玫瑰的直播间
     */

    private String coverUrl;
    private int anchorId;
    private int liveStatus;
    private String roomNo;
    private int screenMode;
    private String platform;
    private String pullUrl;
    private String title;
    private int source;
    private String channelId;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
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

    public String getPullUrl() {
        return pullUrl;
    }

    public void setPullUrl(String pullUrl) {
        this.pullUrl = pullUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
