package com.lib.showfield.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPUtils;
import com.hpplay.sdk.source.api.IBindSdkListener;
import com.hpplay.sdk.source.api.LelinkPlayerInfo;
import com.hpplay.sdk.source.api.LelinkSourceSDK;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;

/**
 *
 * 管理投屏sdk对接
 */
public class LelinkSourceRepository {
    private static final String TAG = "LelinkSourceLiveData_";
    static String APP_ID;// = "14548";
    static String APP_SECRET;//= "b9d2428eb0ec8eae64a62289023ccf85";
    public static boolean isConnect;
    private final MediatorLiveData<String> resultLiveData = new MediatorLiveData<>();

    private static final LelinkSourceRepository instance = new LelinkSourceRepository();

    private LelinkSourceRepository() {
        APP_ID = SPUtils.getInstance("config").getString("lebo_appKey");
        APP_SECRET = SPUtils.getInstance("config").getString("lebo_appSec");
    }

    public static LelinkSourceRepository getInstance() {
        return instance;
    }

    public LiveData<Boolean> launchLelinkSdk(Context context, MutableLiveData<Boolean> liveData) {
        if (isConnect) {
            LelinkSourceSDK.getInstance().startBrowse();
            return liveData;
        }
        //  sdk初始化状态监听当onBindCallback回调true之后 SDK可正常使用
        LelinkSourceSDK.getInstance().bindSdk(context.getApplicationContext(), APP_ID, APP_SECRET, new IBindSdkListener() {
            @Override
            public void onBindCallback(boolean b) {
                Log.d(TAG, "onBindCallback: " + b);
                LelinkSourceSDK.getInstance().setDebugMode(true);
                if (!b) return;
                isConnect = b;
                // 通知view 可以监听处理
                liveData.postValue(b);
                LelinkSourceSDK.getInstance().startBrowse();
            }
        });
        return liveData;
    }

    /**
     * 连接设备
     *
     * @param serviceInfo
     */
    public void connect(LelinkServiceInfo serviceInfo, String url) {
        // LelinkSourceSDK.getInstance().connect(serviceInfo);


        LelinkPlayerInfo lelinkPlayerInfo = new LelinkPlayerInfo();
        lelinkPlayerInfo.setUrl(url);
        lelinkPlayerInfo.setType(LelinkSourceSDK.MEDIA_TYPE_VIDEO);
        lelinkPlayerInfo.setLelinkServiceInfo(serviceInfo);
        LelinkSourceSDK.getInstance().startPlayMedia(lelinkPlayerInfo);


//        // 竟象
//        lelinkPlayerInfo.setOption(IAPI.OPTION_31, true);
//        lelinkPlayerInfo.setLelinkServiceInfo(serviceInfo);
//        lelinkPlayerInfo.setBitRateLevel(LelinkSourceSDK.BITRATE_HIGH);
//        lelinkPlayerInfo.setResolutionLevel(LelinkSourceSDK.RESOLUTION_HIGH);
//       // lelinkPlayerInfo.setOption(IAPI.OPTION_6, mScreencode);
//        lelinkPlayerInfo.setMirrorAudioEnable(true);
//        LelinkSourceSDK.getInstance().startMirror(lelinkPlayerInfo);
    }

    public void exitDevices() {
        LelinkSourceSDK.getInstance().stopPlay();
    }

    public void toggleDevices(boolean isPlaying) {
        Log.d(TAG, "toggleDevices: " + isPlaying);
        if (isPlaying) {
            LelinkSourceSDK.getInstance().resume();
        } else {
            LelinkSourceSDK.getInstance().pause();
        }
    }


    public void play(String url) {

    }

    public void unBindLinkSdk() {
        isConnect = false;
        LelinkSourceSDK.getInstance().stopPlay();
        LelinkSourceSDK.getInstance().unBindSdk();
    }
}
