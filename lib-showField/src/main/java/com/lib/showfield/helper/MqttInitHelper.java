package com.lib.showfield.helper;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.lib.showfield.bean.EditEvent;
import com.lib.showfield.common.Constants;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.http.respones.mqtt.NotifySecurityBean;
import com.lib.showfield.model.MqttViewModel;
import com.lib.showfield.service.MqttService;
import com.lib.showfield.utils.Base64Util;
import com.lib.showfield.utils.LiveDataBus;

public class MqttInitHelper implements MqttService.OnListener {

    private FragmentActivity mActivity;
    private MqttViewModel mqttViewModel;
    private static MqttInitHelper mqttInitHelper;

    public static synchronized MqttInitHelper getInstance(FragmentActivity activity) {

        if (mqttInitHelper == null) {
            mqttInitHelper = new MqttInitHelper(activity);
        }
        return mqttInitHelper;
    }

    public MqttInitHelper(FragmentActivity mActivity) {
        this.mActivity = mActivity;
        mqttViewModel = new ViewModelProvider(mActivity).get(MqttViewModel.class);
    }

    public void startMqtt() {
        startMqttConnecting();
    }

    private void startMqttConnecting() {
        MqttService.disconnect(mActivity);

        mqttViewModel.notifySecurity().observe(mActivity, new Observer<Lcee<Object>>() {
            @Override
            public void onChanged(Lcee<Object> lcee) {
                updateMqttNotifySecurity(lcee);
            }
        });
        mqttViewModel.loadNotifySecurity(false);
    }

    private NotifySecurityBean notifySecurityBean;

    private void updateMqttNotifySecurity(Lcee<Object> lcee) {
        switch (lcee.status) {
            case Content:
                String data1 = (String) lcee.data.getData();
                String decode = Base64Util.decode(data1);
                notifySecurityBean = new Gson().fromJson(decode, NotifySecurityBean.class);
                startMQTT();
                break;
            case Empty:
                break;
            case Error:
                break;
        }
    }

    private void startMQTT() {
        MqttService.getInstance(mActivity).startMQ(notifySecurityBean, this);
    }

    @Override
    public void clientSuccess(int num) {
        LiveDataBus.getInstance().with("data", EditEvent.class).postValue(new EditEvent(Constants.TO_UN_SUBSCRIBE_TOPIC, "success"));
    }

    @Override
    public void clientIng() {

    }

    @Override
    public void clientFailure() {

    }
}
