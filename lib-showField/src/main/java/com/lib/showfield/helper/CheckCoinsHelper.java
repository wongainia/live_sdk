package com.lib.showfield.helper;

import com.lib.showfield.interfaces.OnCheckUserCoinsListener;

public class CheckCoinsHelper {

    private static CheckCoinsHelper checkCoinsHelper;

    private CheckCoinsHelper () {
    }

    public static synchronized CheckCoinsHelper getInstance() {

        if (checkCoinsHelper == null) {
            checkCoinsHelper = new CheckCoinsHelper();
        }
        return checkCoinsHelper;
    }

    protected OnCheckUserCoinsListener mOnCheckUserCoinsListener;

    public void setOnCheckUserCoinsListener(OnCheckUserCoinsListener listener) {
        this.mOnCheckUserCoinsListener = listener;
    }

    public void checkUserCoins(long giftPrice, String giftId) {
        mOnCheckUserCoinsListener.onDoCheckUserCoins(giftId, giftPrice);
    }
}
