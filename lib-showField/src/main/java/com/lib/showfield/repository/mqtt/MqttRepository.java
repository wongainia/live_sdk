package com.lib.showfield.repository.mqtt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lib.showfield.bean.ToppicUrl;
import com.lib.showfield.common.Constants;
import com.lib.showfield.http.exception.ApiException;
import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.model.Hosts;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.http.model.Result;
import com.lib.showfield.http.respones.mqtt.TopicUrlBean;
import com.lib.showfield.repository.mqtt.remote.RemoteMqttDataSource;

/**
 * Created by JoeyChow
 * Date  2020/6/22 11:06
 * <p>
 * Description
 **/
public class MqttRepository {


    private static final MqttRepository instance = new MqttRepository();

    private MqttRepository() {
    }

    public static MqttRepository getInstance() {
        return instance;
    }

    private MqttDataSource remoteMqttDataSource = RemoteMqttDataSource.getInstance();

    public LiveData<Lcee<Object>> notifySecurity() {
        final MutableLiveData<Lcee<Object>> data = new MutableLiveData<>();
        remoteMqttDataSource.notifySecurity(new Result<BaseResponse<Object>>(){

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Object> responseData) {
                if (ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.empty());
                } else {
                    data.setValue(Lcee.content(responseData));
                }
            }


            @Override
            public void onFailed(Throwable t) {
                if (t instanceof ApiException) {
                    ApiException exception = (ApiException) t;
                    switch (exception.getCode()) {
                        case Hosts.RESP_CODE_ERROR:
                            data.setValue(Lcee.error(exception));
                            break;
                        case Hosts.RESP_NO_LOGIN:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(exception));
                            break;
                    }
                }else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<TopicUrlBean>> topicUrl(ToppicUrl toppicUrl) {
        final MutableLiveData<Lcee<TopicUrlBean>> data = new MutableLiveData<>();
        remoteMqttDataSource.topicUrl(toppicUrl,new Result<BaseResponse<TopicUrlBean>>(){

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<TopicUrlBean> responseData) {
                if (ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.empty());
                } else {
                    data.setValue(Lcee.content(responseData));
                }
            }


            @Override
            public void onFailed(Throwable t) {
                if (t instanceof ApiException) {
                    ApiException exception = (ApiException) t;
                    switch (exception.getCode()) {
                        case Hosts.RESP_CODE_ERROR:
                            data.setValue(Lcee.error(exception));
                            break;
                        case Hosts.RESP_NO_LOGIN:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(exception));
                            break;
                    }
                }else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }
}
