package com.lib.showfield.http.model;

import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.lib.showfield.common.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by JoeyChow
 * Date  2020/4/1 17:56
 * <p>
 * Description
 **/
public class HeadUrlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Log.d("zzy", "---token---: " + SPUtils.getInstance("token").getString(Constants.TOKEN));
        Log.d("zzy", "---appid---: " + SPUtils.getInstance("token").getString(Constants.APPID));

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json")
                .addHeader("clientVersion", "1.5.1")
                .addHeader("platform", "android")
                .addHeader("appId", SPUtils.getInstance().getString(Constants.APPID));

        if (!"".equals(SPUtils.getInstance("token").getString(Constants.TOKEN))) {
            builder.addHeader("X-ACCESS-TOKEN", SPUtils.getInstance("token").getString(Constants.TOKEN));
        }

        return chain.proceed(builder.build());
    }
}
