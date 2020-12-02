package com.lib.showfield.http.request;

import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.respones.mqtt.TopicUrlBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by JoeyChow
 * Date  2020/6/22 11:00
 * <p>
 * Description
 **/
public interface ChatMqttApi {

    //聊天室获取MQTT连接信息
    @POST("/app/emq/config/connect")
    Observable<BaseResponse<Object>> notifySecurity(@Body RequestBody body);

    //获取MQTT主题
    @POST("/app/chat/live/topic")
    Observable<BaseResponse<TopicUrlBean>> topicUrl(@Body RequestBody body);

}
