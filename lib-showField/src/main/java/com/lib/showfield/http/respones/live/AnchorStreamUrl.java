package com.lib.showfield.http.respones.live;

import java.util.List;

public class AnchorStreamUrl {

    private List<String> pullUrls;
    private List<String> flvUrls;
    private List<String> m3u8Urls;

    public List<String> getPullUrls() {
        return pullUrls;
    }

    public void setPullUrls(List<String> pullUrls) {
        this.pullUrls = pullUrls;
    }

    public List<String> getFlvUrls() {
        return flvUrls;
    }

    public void setFlvUrls(List<String> flvUrls) {
        this.flvUrls = flvUrls;
    }

    public List<String> getM3u8Urls() {
        return m3u8Urls;
    }

    public void setM3u8Urls(List<String> m3u8Urls) {
        this.m3u8Urls = m3u8Urls;
    }
}
