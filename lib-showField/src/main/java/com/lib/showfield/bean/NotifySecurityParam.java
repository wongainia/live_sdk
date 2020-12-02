package com.lib.showfield.bean;


public class NotifySecurityParam {

    public NotifySecurityParam() {
    }

    private int type;
    private int pageNum;
    private String channelId;

    public NotifySecurityParam(int type, int pageNum, String channelId) {
        this.type = type;
        this.pageNum = pageNum;
        this.channelId = channelId;
    }
}
