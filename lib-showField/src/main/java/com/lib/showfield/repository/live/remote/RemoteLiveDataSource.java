package com.lib.showfield.repository.live.remote;


import com.google.gson.Gson;
import com.lib.showfield.bean.ContributeListParam;
import com.lib.showfield.bean.ForbiddenParam;
import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.bean.NotifySecurityParam;
import com.lib.showfield.bean.ReportParam;
import com.lib.showfield.bean.RoomNoItemIdParam;
import com.lib.showfield.bean.RoomNoMatchIdParam;
import com.lib.showfield.bean.RoomNoParam;
import com.lib.showfield.bean.SendMsgBean;
import com.lib.showfield.bean.SetupManagerParam;
import com.lib.showfield.bean.UserIdRoomNoParam;
import com.lib.showfield.bean.gift.bean.GiftListParam;
import com.lib.showfield.bean.gift.bean.GiftSendParam;
import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.model.Hosts;
import com.lib.showfield.http.model.Result;
import com.lib.showfield.http.model.RetrofitFactory;
import com.lib.showfield.http.request.LiveApi;
import com.lib.showfield.http.respones.Config;
import com.lib.showfield.http.respones.live.AnchorStreamUrl;
import com.lib.showfield.http.respones.live.BanTalkResponse;
import com.lib.showfield.http.respones.live.ChatResponse;
import com.lib.showfield.http.respones.live.ContributeList;
import com.lib.showfield.http.respones.live.DictDetailBean;
import com.lib.showfield.http.respones.live.DynamicMatchBean;
import com.lib.showfield.http.respones.live.FocusRecommendLives;
import com.lib.showfield.http.respones.live.GiftResponse;
import com.lib.showfield.http.respones.live.LivePlayerInfo;
import com.lib.showfield.http.respones.live.LiveRoomTypeInfo;
import com.lib.showfield.http.respones.live.PullUrl;
import com.lib.showfield.http.respones.live.UserCard;
import com.lib.showfield.http.respones.live.UserCoins;
import com.lib.showfield.http.respones.live.UserInfo;
import com.lib.showfield.repository.live.LiveDataSource;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by JoeyChow
 * Date  2020-04-14 10:19
 * <p>
 * Description
 **/
public class RemoteLiveDataSource implements LiveDataSource {

    private static final RemoteLiveDataSource instance = new RemoteLiveDataSource();

    private RemoteLiveDataSource() {
    }

    public static RemoteLiveDataSource getInstance() {
        return instance;
    }

    public LiveApi liveApi = RetrofitFactory.getInstance().create(LiveApi.class);

    @Override
    public void queryMatchDataByTabId(Map param, Result<BaseResponse<List<DynamicMatchBean>>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));
        liveApi.queryMatchDataByTabId(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<DynamicMatchBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<List<DynamicMatchBean>> securityTokenBaseResponse) {
                        result.onSuccess(securityTokenBaseResponse);
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
    public void queryLiveTypeInfoByRoomNo(RoomNoParam param, Result<BaseResponse<LiveRoomTypeInfo>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryLiveTypeInfoByRoomNo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LiveRoomTypeInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<LiveRoomTypeInfo> listBaseResponse) {
                        result.onSuccess(listBaseResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        result.onFailed(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void queryIndexFocusRecommend(NotifySecurityParam param, Result<BaseResponse<FocusRecommendLives>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryIndexFocusRecommend(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<FocusRecommendLives>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<FocusRecommendLives> listBaseResponse) {
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
    public void queryGiftListByRoomNo(GiftListParam param, Result<BaseResponse<List<GiftResponse>>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryGiftListByRoomNo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<GiftResponse>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<List<GiftResponse>> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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

//    @Override
//    public void queryUserCoinsByToken(Result<BaseResponse<UserCoins>> result) {
//        liveApi.queryUserCoinsByToken()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseResponse<UserCoins>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        result.onLoading();
//                    }
//
//                    @Override
//                    public void onNext(BaseResponse<UserCoins> objectBaseResponse) {
//                        result.onSuccess(objectBaseResponse);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        result.onFailed(e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

    @Override
    public void queryContributeListByRoomNo(ContributeListParam param, Result<BaseResponse<ContributeList>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryContributeByRoomNo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ContributeList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<ContributeList> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void queryUserCardById(UserIdRoomNoParam param, Result<BaseResponse<UserCard>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryUserCardById(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UserCard>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<UserCard> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void queryIsRoomManagerById(UserIdRoomNoParam param, Result<BaseResponse<Boolean>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryIsRoomManagerById(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Boolean> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void queryForbiddenUserById(ForbiddenParam param, Result<BaseResponse<Boolean>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryForbiddenUserById(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Boolean> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void querySetupUserManagerById(SetupManagerParam param, Result<BaseResponse<Boolean>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.querySetupUserManagerById(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Boolean> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void queryUnSetupUserManagerById(UserIdRoomNoParam param, Result<BaseResponse<Boolean>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryUnSetupUserManagerById(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Boolean> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void queryLiveAudienctList(Map map, Result<BaseResponse<LiveAudienceBean>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(map));

        liveApi.queryLiveAudienctList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LiveAudienceBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<LiveAudienceBean> response) {
                        result.onSuccess(response);
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
    public void queryUserByToken(Result<BaseResponse<UserInfo>> result) {
        liveApi.queryUserByToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UserInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<UserInfo> response) {
                        result.onSuccess(response);
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
    public void querySendMsgByRoomNo(SendMsgBean param, Result<BaseResponse<Object>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.querySendMsgByRoomNo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Object> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void queryLiveReportByToken(ReportParam param, Result<BaseResponse<Boolean>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryLiveReportByToken(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Boolean> response) {
                        result.onSuccess(response);
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
    public void queryGiftSendById(GiftSendParam param, Result<BaseResponse<Object>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                new Gson().toJson(param));
        liveApi.queryGiftSendByGiftId(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Object>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Object> roomLoadBaseResponse) {
                        result.onSuccess(roomLoadBaseResponse);
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
    public void queryDictDetail(Result<BaseResponse<List<DictDetailBean>>> result) {
        liveApi.queryLiveDictDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<DictDetailBean>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<List<DictDetailBean>> roomLoadBaseResponse) {
                        result.onSuccess(roomLoadBaseResponse);
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
    public void queryHitGiftSendByGiftId(GiftSendParam param, Result<BaseResponse<Object>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                new Gson().toJson(param));

        liveApi.queryHitGiftSendByGiftId(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Object>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Object> roomLoadBaseResponse) {
                        result.onSuccess(roomLoadBaseResponse);
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
    public void queryHistoryChatMsgByRoomNo(RoomNoItemIdParam param, Result<BaseResponse<List<ChatResponse>>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryHistoryChatMsgByRoomNo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ChatResponse>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<List<ChatResponse>> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void queryEnterRoomCmdByRoomNo(RoomNoParam param, Result<BaseResponse<Object>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryEnterRoomCmdByRoomNo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Object> livePlayerInfoBaseResponse) {
                        result.onSuccess(livePlayerInfoBaseResponse);
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
    public void queryAnchorStreamUrlByRoomNo(RoomNoMatchIdParam param, Result<BaseResponse<AnchorStreamUrl>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryAnchorStreamUrlByRoomNo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<AnchorStreamUrl>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<AnchorStreamUrl> response) {
                        result.onSuccess(response);
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
    public void queryLivePlayerInfoById(RoomNoParam param, Result<BaseResponse<LivePlayerInfo>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryLivePlayerInfoById(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LivePlayerInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<LivePlayerInfo> livePlayerInfoBaseResponse) {
                        result.onSuccess(livePlayerInfoBaseResponse);
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
    public void queryUserIsBanTalkById(UserIdRoomNoParam param, Result<BaseResponse<BanTalkResponse>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryUserIsBanTalkById(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<BanTalkResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<BanTalkResponse> objectBaseResponse) {
                        result.onSuccess(objectBaseResponse);
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
    public void queryPullUrlsByPullId(RoomNoParam param, Result<BaseResponse<PullUrl>> result) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(param));

        liveApi.queryPullUrlsByPullId(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<PullUrl>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<PullUrl> pullUrlBaseResponse) {
                        result.onSuccess(pullUrlBaseResponse);
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
    public void queryAppCommonResources(Result<BaseResponse<Config>> result) {
        liveApi.queryAppCommonResources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Config>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        result.onLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<Config> response) {
                        result.onSuccess(response);
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
