package com.lib.showfield.interfaces;

import com.lib.showfield.http.respones.live.GiftResponse;

/**
 * Created by JoeyChow
 * Date  2020/7/1 19:40
 * <p>
 * Description
 **/
public interface OnSendGiftInOScreenListener {

    void sendGift(GiftResponse bean);
}
