package com.lib.showfield.interfaces;

import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;

/**
 * 投屏
 */
public interface OnPrectionListener {
    void onStartConnect();
    void onConnectSuccess(LelinkServiceInfo selectInfo);
    void onDisConnectFail();
}
