package com.lib.showfield.interfaces;


import com.lib.showfield.http.respones.live.GiftResponse;

import java.util.List;

/**
 * Created by JoeyChow
 * Date  2020/7/1 20:31
 * <p>
 * Description
 **/
public interface OnSendGiftListListener {

    void sendGiftList(List<GiftResponse> list);
}
