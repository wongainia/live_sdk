package com.lib.showfield.repository.mqtt;

import com.lib.showfield.bean.ToppicUrl;
import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.model.Result;
import com.lib.showfield.http.respones.mqtt.TopicUrlBean;

/**
 * Created by JoeyChow
 * Date  2020/6/22 11:05
 * <p>
 * Description
 **/
public interface MqttDataSource {

    void notifySecurity(Result<BaseResponse<Object>> result);

    void topicUrl(ToppicUrl toppicUrl, Result<BaseResponse<TopicUrlBean>> result);
}
