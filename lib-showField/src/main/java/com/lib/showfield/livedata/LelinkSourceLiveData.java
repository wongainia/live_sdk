package com.lib.showfield.livedata;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hpplay.sdk.source.api.LelinkSourceSDK;
import com.lib.showfield.repository.LelinkSourceRepository;

/**
 *
 */
public class LelinkSourceLiveData<T extends Boolean> extends MutableLiveData<T> {//LiveData<List<LelinkServiceInfo>> {
    public static final String TAG = "LelinkSourceLiveData_";

    private static LelinkSourceLiveData instance;

    private LelinkSourceLiveData() {

    }

    public static LelinkSourceLiveData getInstance() {
        if (instance == null) {
            instance = new LelinkSourceLiveData();
        }
        return instance;
    }


    @Override
    protected void onActive() {
        super.onActive();
        // 如果sdk生命周期全局，此代码注销
        if (!LelinkSourceRepository.isConnect) return;
        startBrowse();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "停止搜索: " + this);
        LelinkSourceSDK.getInstance().stopBrowse();
    }

    public void startBrowse() {
        Log.d(TAG, "开始搜索: " + this);
        LelinkSourceSDK.getInstance().startBrowse();
    }
}
