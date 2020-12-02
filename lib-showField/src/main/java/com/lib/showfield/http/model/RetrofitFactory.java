package com.lib.showfield.http.model;


import com.blankj.utilcode.util.SPUtils;
import com.lib.showfield.common.Constants;
import com.lib.showfield.http.converter.CustomizeGsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by zzy
 */

public class RetrofitFactory {
    private static OkHttpClient client;
    private static Retrofit retrofit;

    private static final String BASE_URL = SPUtils.getInstance().getString(Constants.BASE_URL);

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .callTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HeadUrlInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(CustomizeGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return retrofit;
    }
}
