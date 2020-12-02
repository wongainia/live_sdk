package com.lib.showfield.bean;

/**
 *
 * 贴片广告
 */
public class PatchAdBean {

    public String id;//": null,标题
    public String adName;//": "标题XXX",
    public String picUrl;//": // 广告图片 "https://dp-live-dev.oss-cn-hangzhou.aliyuncs.com/10000/h5/null/2020/06/30/ebc72c2c510a49e49791dce82b2d770b_5b385c98.jpg",
    public String jumpUrl;//": 跳转链接 "http://jump.com/",
    public String sort;//": 2

    public PatchAdBean(String id, String adName, String picUrl, String jumpUrl) {
        this.id = id;
        this.adName = adName;
        this.picUrl = picUrl;
        this.jumpUrl = jumpUrl;
    }
}
