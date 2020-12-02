package com.lib.showfield.helper;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hjq.toast.ToastUtils;
import com.lib.showfield.bean.RoomNoParam;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.http.respones.live.LiveRoomTypeInfo;
import com.lib.showfield.model.LiveViewModel;
import com.lib.showfield.ui.activty.ShowFieldLiveRoomActivity;

public class LiveInfoHelper {

    private FragmentActivity mActivity;
    private LiveViewModel liveViewModel;

    private static LiveInfoHelper liveInfoHelper;

    private LiveInfoHelper(FragmentActivity activity) {
        this.mActivity = activity;
        liveViewModel = new ViewModelProvider(activity).get(LiveViewModel.class);
    }

    public static synchronized LiveInfoHelper getInstance(FragmentActivity activity) {

        if (liveInfoHelper == null) {
            liveInfoHelper = new LiveInfoHelper(activity);
        }
        return liveInfoHelper;
    }

    public void getLiveInfo(String roomNo) {
        liveViewModel.getLiveRoomType().observe(mActivity, new Observer<Lcee<LiveRoomTypeInfo>>() {
            @Override
            public void onChanged(Lcee<LiveRoomTypeInfo> lcee) {
                updateLiveRoomType(lcee);
            }
        });

        liveViewModel.reLoadLiveRoomType(new RoomNoParam(roomNo));
    }

    private void updateLiveRoomType(Lcee<LiveRoomTypeInfo> lcee) {
        switch (lcee.status) {
            case Content:
                LiveRoomTypeInfo info = lcee.data.getData();
                int screenMode = info.getScreenMode();
                if (screenMode == 2) {
                    Intent intent = new Intent(mActivity, ShowFieldLiveRoomActivity.class);
                    intent.putExtra("show_param", info);
                    mActivity.startActivity(intent);
                }
                break;
            case Empty:
                break;
            case Error:
                ToastUtils.show(lcee.error.getMessage());
                break;
        }
    }
}
