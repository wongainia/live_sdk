package com.lib.showfield.app;


import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.SPUtils;
import com.lib.showfield.BuildConfig;
import com.lib.showfield.common.Constants;
import com.lib.showfield.helper.MqttInitHelper;

/**
 * author : JoeyChow
 * time   : 2020/04/01
 * desc   : App 配置管理类
 */
public final class AppConfig {

    /**
     * 当前是否为 Debug 模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static void initSdk(FragmentActivity activity, String baseUrl, String h5_base_url, String appId) {

        SPUtils.getInstance().put(Constants.APPID, appId);
        SPUtils.getInstance().put(Constants.BASE_URL, baseUrl);
        SPUtils.getInstance().put(Constants.H5_BASE_URL, h5_base_url);

        MqttInitHelper.getInstance(activity).startMqtt();
    }
}