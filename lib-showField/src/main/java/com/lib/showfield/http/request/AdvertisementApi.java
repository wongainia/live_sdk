package com.lib.showfield.http.request;

import com.lib.showfield.bean.PatchAdBean;
import com.lib.showfield.http.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by
 * 开屏广告
 **/
public interface AdvertisementApi {

    //贴片广告
    @POST
    Observable<BaseResponse<List<PatchAdBean>>> queryPatchAd(@Url String url);

}
