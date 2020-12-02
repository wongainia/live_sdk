package com.lib.showfield.model;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lib.showfield.bean.ToppicUrl;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.http.respones.mqtt.TopicUrlBean;
import com.lib.showfield.repository.mqtt.MqttRepository;

/**
 * Created by JoeyChow
 * Date  2020/6/22 11:12
 * <p>
 * Description
 **/
public class MqttViewModel extends ViewModel {

    private MqttRepository mqttRepository = MqttRepository.getInstance();

    private MutableLiveData<Boolean> mNotifySecurity;
    private LiveData<Lcee<Object>> ldmNotifySecurity;

    public LiveData<Lcee<Object>> notifySecurity() {

        if (null == ldmNotifySecurity) {
            mNotifySecurity = new MutableLiveData<>();
            ldmNotifySecurity = Transformations.switchMap(mNotifySecurity, new Function<Boolean, LiveData<Lcee<Object>>>() {
                @Override
                public LiveData<Lcee<Object>> apply(Boolean isSecurity) {
                    return mqttRepository.notifySecurity();
                }
            });
        }

        return ldmNotifySecurity;
    }

    public void loadNotifySecurity(boolean b) {
        mNotifySecurity.setValue(b);
    }


    private MutableLiveData<ToppicUrl> mTopicUrl;
    private LiveData<Lcee<TopicUrlBean>> ldmTopicUrl;

    public LiveData<Lcee<TopicUrlBean>> getMqttTopic() {

        if (null == ldmTopicUrl) {
            mTopicUrl = new MutableLiveData<>();
            ldmTopicUrl = Transformations.switchMap(mTopicUrl, new Function<ToppicUrl, LiveData<Lcee<TopicUrlBean>>>() {
                @Override
                public LiveData<Lcee<TopicUrlBean>> apply(ToppicUrl toppicUrl) {
                    return mqttRepository.topicUrl(toppicUrl);
                }
            });
        }

        return ldmTopicUrl;
    }

    public void loadTopicUrl(ToppicUrl toppicUrl) {
        mTopicUrl.setValue(toppicUrl);
    }

}
