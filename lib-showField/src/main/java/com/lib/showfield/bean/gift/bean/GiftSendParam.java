package com.lib.showfield.bean.gift.bean;

/**
 * Created by JoeyChow
 * Date  2020/6/22 15:12
 * <p>
 * Description
 **/
public class GiftSendParam {

    private int anchorId;
    private String giftId;
    private int num;

    public GiftSendParam(int anchorId, String giftId, int num) {
        this.anchorId = anchorId;
        this.giftId = giftId;
        this.num = num;
    }

    public int getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
