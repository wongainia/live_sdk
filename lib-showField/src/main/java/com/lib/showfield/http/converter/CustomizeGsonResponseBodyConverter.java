package com.lib.showfield.http.converter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.lib.showfield.BuildConfig;
import com.lib.showfield.http.exception.ApiException;
import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.model.Hosts;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by JoeyChow
 * Date  2020-05-07 12:27
 * <p>
 * Description
 **/
public class CustomizeGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomizeGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //把responsebody转为string
        String response = value.string();
        if (BuildConfig.DEBUG) {
            //打印后台数据
            Log.e("zzy", response);
        }
        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
        // 这里只是为了检测code是否!=200,所以只解析HttpStatus中的字段,因为只要code和message就可以了
        if (baseResponse.getCode() != Hosts.RESP_CODE_SUCCESS) {
            value.close();
            //抛出一个RuntimeException, 这里抛出的异常会到subscribe的onError()方法中统一处理
            throw new ApiException(baseResponse.getCode(), baseResponse.getMsg());
        }
        try {
            return adapter.fromJson(response);
        } finally {
            value.close();
        }
    }
}
