package com.lib.showfield.http.respones.live;

public class DynamicMatchBean {

    /**
     * matchId : 355
     * leagueName : 土甲
     * liveStatus : 1
     * homeTeam : 梅内门
     * homeTeamLogo : http://uecent.oss-cn-shanghai.aliyuncs.com/football/nm/team/5914afa554b9fe3dea62c3d7a94db237.png
     * awayTeam : 波卢斯堡
     * awayTeamLogo : http://uecent.oss-cn-shanghai.aliyuncs.com/football/nm/team/c1e6b647d6a45fc6af8f753420e84510.png
     * startTime : 06/28 01:00
     * round : 第30场
     * hotNum : 22
     * roomNo :
     * cover :
     * avatar :
     * nickname :
     */
    private String matchId;
    private String leagueName;
    private int liveStatus;
    private String homeTeam;
    private String homeTeamLogo;
    private String awayTeam;
    private String awayTeamLogo;
    private String startTime;
    private String round;
    private int hotNum;
    private String roomNo;
    private String cover;
    private String avatar;
    private String nickname;
    private String title;
    private int isProphecy;
    private int hasRedPacket;//": 0,    //房间是否有红包，0: 不存在 1: 存在       1.3 新增
    private int screenMode;
    private String platform;
    private String pullUrl;
    private int sportType; //赛事类型
    private String eventName; //联赛名称

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

    public int getIsProphecy() {
        return isProphecy;
    }

    public void setIsProphecy(int isProphecy) {
        this.isProphecy = isProphecy;
    }

    public int getHasRedPacket() {
        return hasRedPacket;
    }

    public void setHasRedPacket(int hasRedPacket) {
        this.hasRedPacket = hasRedPacket;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getHomeTeamLogo() {
        return homeTeamLogo;
    }

    public void setHomeTeamLogo(String homeTeamLogo) {
        this.homeTeamLogo = homeTeamLogo;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getAwayTeamLogo() {
        return awayTeamLogo;
    }

    public void setAwayTeamLogo(String awayTeamLogo) {
        this.awayTeamLogo = awayTeamLogo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public int getHotNum() {
        return hotNum;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
