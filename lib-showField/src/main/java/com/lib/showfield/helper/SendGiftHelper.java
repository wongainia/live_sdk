package com.lib.showfield.helper;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lib.showfield.bean.gift.bean.GiftSendParam;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.interfaces.OnCheckUserCoinsListener;
import com.lib.showfield.model.LiveViewModel;

public class SendGiftHelper {

    private FragmentActivity mActivity;
    private LiveViewModel liveViewModel;

    private static SendGiftHelper sendGiftHelper;

    public static synchronized SendGiftHelper getInstance(FragmentActivity activity) {

        if (sendGiftHelper == null) {
            sendGiftHelper = new SendGiftHelper(activity);
        }
        return sendGiftHelper;
    }

    protected OnCheckUserCoinsListener mOnCheckUserCoinsListener;

    public SendGiftHelper(FragmentActivity mActivity) {
        this.mActivity = mActivity;
        liveViewModel = new ViewModelProvider(mActivity).get(LiveViewModel.class);

    }

    public void sendGift(GiftSendParam giftSendParam) {
        liveViewModel.sendGift().observe(mActivity, new Observer<Lcee<Object>>() {
            @Override
            public void onChanged(Lcee<Object> lcee) {
                updateSendGiftView(lcee);
            }
        });

        liveViewModel.loadGiftSend(giftSendParam);
    }


    private void updateSendGiftView(Lcee<Object> lcee) {
        switch (lcee.status) {
            case Content:
                if (lcee.data.getCode() == 200) {
                    Log.d("zzy", "礼物发送成功");
                }
                break;
            case OCCURY:
                break;
        }
    }
}
