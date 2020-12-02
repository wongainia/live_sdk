package com.lib.showfield.model;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.lib.showfield.livedata.LelinkSourceLiveData;
import com.lib.showfield.repository.LelinkSourceRepository;

/**
 *
 * 投屏
 */
public class LelinkSourceViewModel extends ViewModel {

    private MutableLiveData<Context> mutableLiveData;
    private LiveData<Boolean> lelinkLiveData;

    public LiveData<Boolean> getLeLinkLiveData() {
        if (lelinkLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            lelinkLiveData = Transformations.switchMap(mutableLiveData, new Function<Context, LiveData<Boolean>>() {
                @Override
                public LiveData<Boolean> apply(Context input) {
                    return LelinkSourceRepository.getInstance().launchLelinkSdk(input, LelinkSourceLiveData.getInstance());
                }
            });
        }
        return lelinkLiveData;
    }


    // 等待view触发 连接sdk
    public void connectLiveData(Context context) {
        mutableLiveData.setValue(context);
    }

    // 连接设备
    public void connect(LelinkServiceInfo serviceInfo, String url) {
        LelinkSourceRepository.getInstance().connect(serviceInfo,url);
    }

    // 退出投屏
    public void exitDevices() {
        LelinkSourceRepository.getInstance().exitDevices();
    }

    public void toggle(boolean isPlaying){
        LelinkSourceRepository.getInstance().toggleDevices(isPlaying);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(LelinkSourceLiveData.TAG, "onCleared: ");
        LelinkSourceRepository.getInstance().unBindLinkSdk();
    }
}
