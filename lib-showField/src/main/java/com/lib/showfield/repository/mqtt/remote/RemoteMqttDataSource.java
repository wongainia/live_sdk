package com.lib.showfield.repository.mqtt.remote;

import com.google.gson.Gson;
import com.lib.showfield.bean.NotifySecurityParam;
import com.lib.showfield.bean.ToppicUrl;
import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.model.Result;
import com.lib.showfield.http.model.RetrofitFactory;
import com.lib.showfield.http.request.ChatMqttApi;
import com.lib.showfield.http.respones.mqtt.TopicUrlBean;
import com.lib.showfield.repository.mqtt.MqttDataSource;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by JoeyChow
 * Date  2020/6/22 11:08
 * <p>
 * Description
 **/
public class RemoteMqttDataSource implements MqttDataSource {

    private static final RemoteMqttDataSource instance = new RemoteMqttDataSource();

    private RemoteMqttDataSource() {
    }

    public static RemoteMqttDataSource getInstance() {
        return instance;
    }

    private ChatMqttApi mqttApi = RetrofitFactory.getInstance().create(ChatMqttApi.class);


    @Override
    public void notifySecurity(Result<BaseResponse<Object>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                new Gson().toJson(new NotifySecurityParam()));
        mqttApi.notifySecurity(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Object> listBaseResponse) {
                        result.onSuccess(listBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.onFailed(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void topicUrl(ToppicUrl toppicUrl, Result<BaseResponse<TopicUrlBean>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                new Gson().toJson(toppicUrl));
        mqttApi.topicUrl(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<TopicUrlBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<TopicUrlBean> listBaseResponse) {
                        result.onSuccess(listBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.onFailed(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
