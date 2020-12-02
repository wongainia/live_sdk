package com.lib.showfield.repository.live;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
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
import com.lib.showfield.common.Constants;
import com.lib.showfield.http.exception.ApiException;
import com.lib.showfield.http.model.BaseResponse;
import com.lib.showfield.http.model.Hosts;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.http.model.Result;
import com.lib.showfield.http.respones.Config;
import com.lib.showfield.http.respones.live.AnchorStreamUrl;
import com.lib.showfield.http.respones.live.BanTalkResponse;
import com.lib.showfield.http.respones.live.ChatResponse;
import com.lib.showfield.http.respones.live.CoinsPanels;
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
import com.lib.showfield.repository.live.remote.RemoteLiveDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by JoeyChow
 * Date  2020-04-14 10:16
 * <p>
 * Description
 **/
public class LiveRepository {

    private static final LiveRepository instance = new LiveRepository();

    private LiveRepository() {
    }

    public static LiveRepository getInstance() {
        return instance;
    }

    private LiveDataSource remoteLiveDataSource = RemoteLiveDataSource.getInstance();

    public LiveData<Lcee<List<DynamicMatchBean>>> getAllLiveMatchByTab(Map param) {
        final MutableLiveData<Lcee<List<DynamicMatchBean>>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryMatchDataByTabId(param, new Result<BaseResponse<List<DynamicMatchBean>>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<List<DynamicMatchBean>> responseData) {
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
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }

    public LiveData<Lcee<LiveRoomTypeInfo>> getLiveRoomTypeInfo(RoomNoParam param) {
        final MutableLiveData<Lcee<LiveRoomTypeInfo>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryLiveTypeInfoByRoomNo(param, new Result<BaseResponse<LiveRoomTypeInfo>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<LiveRoomTypeInfo> recommendList) {
                if (ObjectUtils.isEmpty(recommendList.getData())) {
                    data.setValue(Lcee.empty());
                } else {
                    data.setValue(Lcee.content(recommendList));
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        default:
                            data.setValue(Lcee.error(exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }


    public LiveData<Lcee<FocusRecommendLives>> getFocusRecommend(NotifySecurityParam param) {
        final MutableLiveData<Lcee<FocusRecommendLives>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryIndexFocusRecommend(param, new Result<BaseResponse<FocusRecommendLives>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<FocusRecommendLives> recommendList) {
                if (ObjectUtils.isEmpty(recommendList.getData().getRows())) {
                    data.setValue(Lcee.empty());
                } else {
                    data.setValue(Lcee.content(recommendList));
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        default:
                            data.setValue(Lcee.error(exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }

//    public LiveData<Lcee<UserCoins>> getUserCoins() {
//        final MutableLiveData<Lcee<UserCoins>> data = new MutableLiveData<>();
//        remoteLiveDataSource.queryUserCoinsByToken(new Result<BaseResponse<UserCoins>>() {
//            @Override
//            public void onLoading() {
//                data.setValue(Lcee.loading());
//            }
//
//            @Override
//            public void onSuccess(BaseResponse<UserCoins> responseData) {
//                if (!ObjectUtils.isEmpty(responseData.getData())) {
//                    data.setValue(Lcee.content(responseData));
//                } else {
//                    data.setValue(Lcee.empty(responseData));
//                }
//            }
//
//            @Override
//            public void onFailed(Throwable t) {
//                if (t instanceof ApiException) {
//                    ApiException exception = (ApiException) t;
//                    switch (exception.getCode()) {
//                        case Hosts.RESP_CODE_ERROR:
//                            data.setValue(Lcee.error(exception));
//                            break;
//                        case Hosts.RESP_CODE_FAIL:
//                            data.setValue(Lcee.noLogin(exception));
//                            break;
//                        case Hosts.RESP_CODE_REPEAT:
//                            SPUtils.getInstance("token").clear();
//                            SPUtils.getInstance("idCard").clear();
//                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
//                            data.setValue(Lcee.repeatLogin(exception));
//                            break;
//                        case Hosts.RESP_CODE_TOKEN_LOST:
//                            SPUtils.getInstance("token").put(Constants.TOKEN,
//                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
//                            data.setValue(Lcee.tokenLost(exception));
//                            break;
//                        case Hosts.RESP_TOKEN_BAN:
//                            ToastUtils.showShort("你已被封禁");
//                            SPUtils.getInstance("token").clear();
//                            SPUtils.getInstance("idCard").clear();
//                            SPUtils.getInstance("user").clear();
//                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
//                            break;
//                        default:
//                            data.setValue(Lcee.error(null, exception));
//                            break;
//                    }
//                } else {
//                    data.setValue(Lcee.error(t));
//                }
//            }
//        });
//        return data;
//    }

    public LiveData<Lcee<List<GiftResponse>>> getGiftList(GiftListParam param) {
        final MutableLiveData<Lcee<List<GiftResponse>>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryGiftListByRoomNo(param, new Result<BaseResponse<List<GiftResponse>>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<List<GiftResponse>> responseData) {
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<LiveAudienceBean>> getLiveAudienceList(Map map) {
        final MutableLiveData<Lcee<LiveAudienceBean>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryLiveAudienctList(map, new Result<BaseResponse<LiveAudienceBean>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<LiveAudienceBean> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData().getList())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
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
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<UserInfo>> getUser() {
        final MutableLiveData<Lcee<UserInfo>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryUserByToken(new Result<BaseResponse<UserInfo>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<UserInfo> responseData) {
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }

    public LiveData<Lcee<Object>> getSendMsg(SendMsgBean param) {
        final MutableLiveData<Lcee<Object>> data = new MutableLiveData<>();
        remoteLiveDataSource.querySendMsgByRoomNo(param, new Result<BaseResponse<Object>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Object> responseData) {
                data.setValue(Lcee.content(responseData));
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        case Hosts.RESP_SEND_NO_TIMES:
                            data.setValue(Lcee.aleadyIn(exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }


    public LiveData<Lcee<Boolean>> doReport(ReportParam param) {
        final MutableLiveData<Lcee<Boolean>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryLiveReportByToken(param, new Result<BaseResponse<Boolean>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Boolean> response) {
                data.setValue(Lcee.content(response));
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }


    public LiveData<Lcee<List<DictDetailBean>>> getDictDetail() {
        final MutableLiveData<Lcee<List<DictDetailBean>>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryDictDetail(new Result<BaseResponse<List<DictDetailBean>>>() {

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<List<DictDetailBean>> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty());
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<Object>> getSendGift(GiftSendParam param) {
        final MutableLiveData<Lcee<Object>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryGiftSendById(param, new Result<BaseResponse<Object>>() {

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Object> responseData) {
                if (ObjectUtils.isEmpty(responseData)) {
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        case Hosts.RESP_NOT_HAVE_CHARGE:
                            data.setValue(Lcee.occupy(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<UserCard>> getUserCard(UserIdRoomNoParam param) {
        final MutableLiveData<Lcee<UserCard>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryUserCardById(param, new Result<BaseResponse<UserCard>>() {

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<UserCard> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }


    public LiveData<Lcee<Boolean>> getIsRoomManager(UserIdRoomNoParam param) {
        final MutableLiveData<Lcee<Boolean>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryIsRoomManagerById(param, new Result<BaseResponse<Boolean>>() {

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Boolean> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }


    public LiveData<Lcee<Object>> getHitSendGift(GiftSendParam param) {
        final MutableLiveData<Lcee<Object>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryHitGiftSendByGiftId(param, new Result<BaseResponse<Object>>() {

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Object> responseData) {
                if (ObjectUtils.isEmpty(responseData)) {
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        case Hosts.RESP_NOT_HAVE_CHARGE:
                            data.setValue(Lcee.occupy(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }


    public LiveData<Lcee<Boolean>> getSetupManager(SetupManagerParam param) {
        final MutableLiveData<Lcee<Boolean>> data = new MutableLiveData<>();
        remoteLiveDataSource.querySetupUserManagerById(param, new Result<BaseResponse<Boolean>>() {

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Boolean> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<Boolean>> getUnSetupManager(UserIdRoomNoParam param) {
        final MutableLiveData<Lcee<Boolean>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryUnSetupUserManagerById(param, new Result<BaseResponse<Boolean>>() {

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Boolean> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }


    public LiveData<Lcee<List<ChatResponse>>> getHistoryMsg(RoomNoItemIdParam param) {
        final MutableLiveData<Lcee<List<ChatResponse>>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryHistoryChatMsgByRoomNo(param, new Result<BaseResponse<List<ChatResponse>>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<List<ChatResponse>> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }


    public LiveData<Lcee<Object>> getEnterRoomCmd(RoomNoParam param) {
        final MutableLiveData<Lcee<Object>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryEnterRoomCmdByRoomNo(param, new Result<BaseResponse<Object>>() {
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }


    public LiveData<Lcee<Boolean>> getForbidden(ForbiddenParam param) {
        final MutableLiveData<Lcee<Boolean>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryForbiddenUserById(param, new Result<BaseResponse<Boolean>>() {

            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Boolean> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<AnchorStreamUrl>> getAnchorStreamUrl(RoomNoMatchIdParam param) {
        final MutableLiveData<Lcee<AnchorStreamUrl>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryAnchorStreamUrlByRoomNo(param, new Result<BaseResponse<AnchorStreamUrl>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<AnchorStreamUrl> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData().getPullUrls())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
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
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<LivePlayerInfo>> getLivePlayerInfo(RoomNoParam param) {
        final MutableLiveData<Lcee<LivePlayerInfo>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryLivePlayerInfoById(param, new Result<BaseResponse<LivePlayerInfo>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<LivePlayerInfo> responseData) {
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }


    public LiveData<Lcee<BanTalkResponse>> getBanTalkStatus(UserIdRoomNoParam param) {
        final MutableLiveData<Lcee<BanTalkResponse>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryUserIsBanTalkById(param, new Result<BaseResponse<BanTalkResponse>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<BanTalkResponse> responseData) {
                if (!ObjectUtils.isEmpty(responseData.getData())) {
                    data.setValue(Lcee.content(responseData));
                } else {
                    data.setValue(Lcee.empty(responseData));
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
                        case Hosts.RESP_CODE_FAIL:
                            data.setValue(Lcee.noLogin(exception));
                            break;
                        case Hosts.RESP_CODE_REPEAT:
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        default:
                            data.setValue(Lcee.error(null, exception));
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });
        return data;
    }

    public LiveData<Lcee<PullUrl>> getPullUrls(RoomNoParam param) {
        final MutableLiveData<Lcee<PullUrl>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryPullUrlsByPullId(param, new Result<BaseResponse<PullUrl>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<PullUrl> responseData) {
                if (ObjectUtils.isEmpty(responseData.getData().getList())) {
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
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            data.setValue(Lcee.repeatLogin(exception));
                            break;
                        case Hosts.RESP_TOKEN_BAN:
                            ToastUtils.showShort("你已被封禁");
                            SPUtils.getInstance("token").clear();
                            SPUtils.getInstance("idCard").clear();
                            SPUtils.getInstance("user").clear();
                            SPUtils.getInstance().put(Constants.IS_LOGIN, false);
                            break;
                        case Hosts.RESP_CODE_TOKEN_LOST:
                            SPUtils.getInstance("token").put(Constants.TOKEN,
                                    SPUtils.getInstance("token").getString(Constants.REFRESH_TOKEN));
                            data.setValue(Lcee.tokenLost(exception));
                            break;
                        case Hosts.RESP_PULL_URL_NOT_EXIST:
                            data.setValue(Lcee.empty());
                            break;
                        default:
                            data.setValue(Lcee.empty());
                            break;
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }

    public LiveData<Lcee<Config>> getAppCommonResources() {
        final MutableLiveData<Lcee<Config>> data = new MutableLiveData<>();
        remoteLiveDataSource.queryAppCommonResources(new Result<BaseResponse<Config>>() {
            @Override
            public void onLoading() {
                data.setValue(Lcee.loading());
            }

            @Override
            public void onSuccess(BaseResponse<Config> responseData) {
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
                    if (exception.getCode() == Hosts.RESP_CODE_ERROR) {
                        data.setValue(Lcee.error(exception));
                    }
                } else {
                    data.setValue(Lcee.error(t));
                }
            }
        });

        return data;
    }
}
