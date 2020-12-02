package com.lib.showfield.model;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.ObjectUtils;
import com.lib.showfield.aop.CheckNet;
import com.lib.showfield.bean.ForbiddenParam;
import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.bean.NotifySecurityParam;
import com.lib.showfield.bean.PatchAdBean;
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
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.http.model.RetrofitFactory;
import com.lib.showfield.http.request.AdvertisementApi;
import com.lib.showfield.http.respones.Config;
import com.lib.showfield.http.respones.live.AnchorStreamUrl;
import com.lib.showfield.http.respones.live.BanTalkResponse;
import com.lib.showfield.http.respones.live.ChatResponse;
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
import com.lib.showfield.repository.live.LiveRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.airbnb.lottie.L.TAG;


/**
 * Created by JoeyChow
 * Date  2020-04-14 10:23
 * <p>
 * Description
 **/
public class LiveViewModel extends ViewModel {

    private LiveRepository liveRepository = LiveRepository.getInstance();

    /**
     * 根据tab的channId动态获取
     */
    private MutableLiveData<Map> mLiveMatchTab;
    private LiveData<Lcee<List<DynamicMatchBean>>> ldMatchTab;

    public LiveData<Lcee<List<DynamicMatchBean>>> getLiveMatchListByTab() {
        if (null == ldMatchTab) {
            mLiveMatchTab = new MutableLiveData<>();
            ldMatchTab = Transformations.switchMap(mLiveMatchTab, new Function<Map, LiveData<Lcee<List<DynamicMatchBean>>>>() {
                @Override
                public LiveData<Lcee<List<DynamicMatchBean>>> apply(Map param) {
                    return liveRepository.getAllLiveMatchByTab(param);
                }
            });
        }
        return ldMatchTab;
    }

    public void reloadLiveMatchListByTab(Map param) {
        mLiveMatchTab.setValue(param);
    }


    private MutableLiveData<RoomNoParam> mLiveRoomType;
    private LiveData<Lcee<LiveRoomTypeInfo>> ldLiveRoomType;

    public LiveData<Lcee<LiveRoomTypeInfo>> getLiveRoomType() {
        if (null == ldLiveRoomType) {
            mLiveRoomType = new MutableLiveData<>();
            ldLiveRoomType = Transformations.switchMap(mLiveRoomType, new Function<RoomNoParam, LiveData<Lcee<LiveRoomTypeInfo>>>() {
                @Override
                public LiveData<Lcee<LiveRoomTypeInfo>> apply(RoomNoParam map) {
                    return liveRepository.getLiveRoomTypeInfo(map);
                }
            });
        }

        return ldLiveRoomType;
    }

    public void reLoadLiveRoomType(RoomNoParam map) {
        mLiveRoomType.setValue(map);
    }


    /**
     * 关注推荐直播
     */
    private MutableLiveData<NotifySecurityParam> mFocusRecommend;
    private LiveData<Lcee<FocusRecommendLives>> ldFocusRecommend;

    public LiveData<Lcee<FocusRecommendLives>> getFocusRecommend() {
        if (null == ldFocusRecommend) {
            mFocusRecommend = new MutableLiveData<>();
            ldFocusRecommend = Transformations.switchMap(mFocusRecommend, new Function<NotifySecurityParam, LiveData<Lcee<FocusRecommendLives>>>() {
                @Override
                public LiveData<Lcee<FocusRecommendLives>> apply(NotifySecurityParam param) {
                    return liveRepository.getFocusRecommend(param);
                }
            });
        }

        return ldFocusRecommend;
    }

    public void reloadFocusRecommend(NotifySecurityParam p) {
        mFocusRecommend.setValue(p);
    }


//    private MutableLiveData<Boolean> mUserCoins;
//    private LiveData<Lcee<UserCoins>> ldUserCoins;
//
//    public LiveData<Lcee<UserCoins>> getUserCoins() {
//        if (null == ldUserCoins) {
//            mUserCoins = new MutableLiveData<>();
//            ldUserCoins = Transformations.switchMap(mUserCoins, new Function<Boolean,
//                    LiveData<Lcee<UserCoins>>>() {
//                @Override
//                public LiveData<Lcee<UserCoins>> apply(Boolean b) {
//                    return liveRepository.getUserCoins();
//                }
//            });
//        }
//
//        return ldUserCoins;
//    }wwwwwwwwwwwwwwww
//
//    public void reLoadUserCoins(boolean b) {
//        mUserCoins.setValue(b);
//    }

    private MutableLiveData<GiftListParam> mGiftListValue;
    private LiveData<Lcee<List<GiftResponse>>> ldGiftListValue;

    public LiveData<Lcee<List<GiftResponse>>> getGiftListValue() {

        if (null == ldGiftListValue) {
            mGiftListValue = new MutableLiveData<>();
            ldGiftListValue = Transformations.switchMap(mGiftListValue, new Function<GiftListParam, LiveData<Lcee<List<GiftResponse>>>>() {
                @Override
                public LiveData<Lcee<List<GiftResponse>>> apply(GiftListParam param) {
                    return liveRepository.getGiftList(param);
                }
            });
        }

        return ldGiftListValue;
    }

    public void reloadGiftListValues(GiftListParam param) {
        mGiftListValue.setValue(param);
    }


    private MutableLiveData<Map> mLiveAudience;
    private LiveData<Lcee<LiveAudienceBean>> ldLiveAudienct;

    public LiveData<Lcee<LiveAudienceBean>> getLiveAudiences() {
        if (null == ldLiveAudienct) {
            mLiveAudience = new MutableLiveData<>();
            ldLiveAudienct = Transformations.switchMap(mLiveAudience, new Function<Map, LiveData<Lcee<LiveAudienceBean>>>() {
                @Override
                public LiveData<Lcee<LiveAudienceBean>> apply(Map map) {
                    return liveRepository.getLiveAudienceList(map);
                }
            });
        }

        return ldLiveAudienct;
    }

    public void reLoadLiveAudienceList(Map map) {
        mLiveAudience.setValue(map);
    }


    public LiveData<Lcee<List<PatchAdBean>>> getPatcAdLiveData() {
        final MutableLiveData<Lcee<List<PatchAdBean>>> data = new MutableLiveData<>();

        RetrofitFactory.getInstance().create(AdvertisementApi.class).queryPatchAd(Hosts.BASE_URL + "/app/ad/patchAd")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<PatchAdBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<PatchAdBean>> indexHotBaseResponse) {
                        Log.e(TAG, "贴片广告-返回成功");
                        if (ObjectUtils.isEmpty(indexHotBaseResponse.getData())) {
                            data.setValue(Lcee.empty());
                        } else {
                            data.setValue(Lcee.content(indexHotBaseResponse));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "贴片广告-返回错误");
                        data.setValue(Lcee.error(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

    private MutableLiveData<Boolean> ldUsername;
    private LiveData<Lcee<UserInfo>> ldUser;

    public LiveData<Lcee<UserInfo>> getUser() {
        if (null == ldUser) {
            ldUsername = new MutableLiveData<>();
            ldUser = Transformations.switchMap(ldUsername, new Function<Boolean, LiveData<Lcee<UserInfo>>>() {
                @Override
                public LiveData<Lcee<UserInfo>> apply(Boolean b) {
                    return liveRepository.getUser();
                }
            });
        }
        return ldUser;
    }

    @CheckNet
    public void reload(boolean b) {
        ldUsername.setValue(b);
    }


    private MutableLiveData<SendMsgBean> mSendMsgValue;
    private LiveData<Lcee<Object>> ldSendMsgValue;

    public LiveData<Lcee<Object>> getSendMsg() {

        if (null == ldSendMsgValue) {
            mSendMsgValue = new MutableLiveData<>();
            ldSendMsgValue = Transformations.switchMap(mSendMsgValue, new Function<SendMsgBean, LiveData<Lcee<Object>>>() {
                @Override
                public LiveData<Lcee<Object>> apply(SendMsgBean param) {
                    return liveRepository.getSendMsg(param);
                }
            });
        }

        return ldSendMsgValue;
    }

    public void reloadSendMsg(SendMsgBean param) {
        mSendMsgValue.setValue(param);
    }


    private MutableLiveData<ReportParam> mLiveReport;
    private LiveData<Lcee<Boolean>> ldReport;

    public LiveData<Lcee<Boolean>> doReport() {

        if (null == ldReport) {
            mLiveReport = new MutableLiveData<>();
            ldReport = Transformations.switchMap(mLiveReport, new Function<ReportParam, LiveData<Lcee<Boolean>>>() {
                @Override
                public LiveData<Lcee<Boolean>> apply(ReportParam param) {
                    return liveRepository.doReport(param);
                }
            });
        }

        return ldReport;
    }

    public void reloadReport(ReportParam param) {
        mLiveReport.setValue(param);
    }


    private MutableLiveData<Boolean> mDictDetail;
    private LiveData<Lcee<List<DictDetailBean>>> ldmDictDetail;

    public LiveData<Lcee<List<DictDetailBean>>> dictDetail() {

        if (null == ldmDictDetail) {
            mDictDetail = new MutableLiveData<>();
            ldmDictDetail = Transformations.switchMap(mDictDetail, new Function<Boolean, LiveData<Lcee<List<DictDetailBean>>>>() {
                @Override
                public LiveData<Lcee<List<DictDetailBean>>> apply(Boolean b) {
                    return liveRepository.getDictDetail();
                }
            });
        }

        return ldmDictDetail;
    }

    public void loadDictDetail(boolean b) {
        mDictDetail.setValue(b);
    }


    private MutableLiveData<GiftSendParam> mGiftSend;
    private LiveData<Lcee<Object>> ldGiftSend;

    public LiveData<Lcee<Object>> sendGift() {

        if (null == ldGiftSend) {
            mGiftSend = new MutableLiveData<>();
            ldGiftSend = Transformations.switchMap(mGiftSend, new Function<GiftSendParam, LiveData<Lcee<Object>>>() {
                @Override
                public LiveData<Lcee<Object>> apply(GiftSendParam b) {
                    return liveRepository.getSendGift(b);
                }
            });
        }

        return ldGiftSend;
    }

    public void loadGiftSend(GiftSendParam b) {
        mGiftSend.setValue(b);
    }


    private MutableLiveData<UserIdRoomNoParam> mUserCard;
    private LiveData<Lcee<UserCard>> ldUserCard;

    public LiveData<Lcee<UserCard>> getUserCard() {

        if (null == ldUserCard) {
            mUserCard = new MutableLiveData<>();
            ldUserCard = Transformations.switchMap(mUserCard, new Function<UserIdRoomNoParam, LiveData<Lcee<UserCard>>>() {
                @Override
                public LiveData<Lcee<UserCard>> apply(UserIdRoomNoParam b) {
                    return liveRepository.getUserCard(b);
                }
            });
        }

        return ldUserCard;
    }

    public void loadUserCard(UserIdRoomNoParam b) {
        mUserCard.setValue(b);
    }


    private MutableLiveData<UserIdRoomNoParam> mIsRoomManager;
    private LiveData<Lcee<Boolean>> ldIsRoomManager;

    public LiveData<Lcee<Boolean>> getIsRoomManager() {

        if (null == ldIsRoomManager) {
            mIsRoomManager = new MutableLiveData<>();
            ldIsRoomManager = Transformations.switchMap(mIsRoomManager, new Function<UserIdRoomNoParam, LiveData<Lcee<Boolean>>>() {
                @Override
                public LiveData<Lcee<Boolean>> apply(UserIdRoomNoParam b) {
                    return liveRepository.getIsRoomManager(b);
                }
            });
        }

        return ldIsRoomManager;
    }

    public void loadIsRoomManager(UserIdRoomNoParam b) {
        mIsRoomManager.setValue(b);
    }


    private MutableLiveData<GiftSendParam> mHitGiftSend;
    private LiveData<Lcee<Object>> ldHitGiftSend;

    public LiveData<Lcee<Object>> sendHitGift() {

        if (null == ldHitGiftSend) {
            mHitGiftSend = new MutableLiveData<>();
            ldHitGiftSend = Transformations.switchMap(mHitGiftSend, new Function<GiftSendParam, LiveData<Lcee<Object>>>() {
                @Override
                public LiveData<Lcee<Object>> apply(GiftSendParam b) {
                    return liveRepository.getHitSendGift(b);
                }
            });
        }

        return ldHitGiftSend;
    }

    public void loadHitGiftSend(GiftSendParam b) {
        mHitGiftSend.setValue(b);
    }


    private MutableLiveData<SetupManagerParam> mSetupManager;
    private LiveData<Lcee<Boolean>> ldSetupManager;

    public LiveData<Lcee<Boolean>> getSetupManager() {

        if (null == ldSetupManager) {
            mSetupManager = new MutableLiveData<>();
            ldSetupManager = Transformations.switchMap(mSetupManager, new Function<SetupManagerParam, LiveData<Lcee<Boolean>>>() {
                @Override
                public LiveData<Lcee<Boolean>> apply(SetupManagerParam b) {
                    return liveRepository.getSetupManager(b);
                }
            });
        }

        return ldSetupManager;
    }

    public void loadSetupManager(SetupManagerParam b) {
        mSetupManager.setValue(b);
    }


    private MutableLiveData<UserIdRoomNoParam> mUnSetupManager;
    private LiveData<Lcee<Boolean>> ldUnSetupManager;

    public LiveData<Lcee<Boolean>> getUnSetupManager() {

        if (null == ldUnSetupManager) {
            mUnSetupManager = new MutableLiveData<>();
            ldUnSetupManager = Transformations.switchMap(mUnSetupManager, new Function<UserIdRoomNoParam, LiveData<Lcee<Boolean>>>() {
                @Override
                public LiveData<Lcee<Boolean>> apply(UserIdRoomNoParam b) {
                    return liveRepository.getUnSetupManager(b);
                }
            });
        }

        return ldUnSetupManager;
    }

    public void loadUnSetupManager(UserIdRoomNoParam b) {
        mUnSetupManager.setValue(b);
    }


    private MutableLiveData<RoomNoItemIdParam> mHistoryMsg;
    private LiveData<Lcee<List<ChatResponse>>> ldHistoryMsg;

    public LiveData<Lcee<List<ChatResponse>>> getChatMsg() {
        if (null == ldHistoryMsg) {
            mHistoryMsg = new MutableLiveData<>();
            ldHistoryMsg = Transformations.switchMap(mHistoryMsg, new Function<RoomNoItemIdParam,
                    LiveData<Lcee<List<ChatResponse>>>>() {
                @Override
                public LiveData<Lcee<List<ChatResponse>>> apply(RoomNoItemIdParam b) {
                    return liveRepository.getHistoryMsg(b);
                }
            });
        }

        return ldHistoryMsg;
    }

    public void reLoadHistoryMsg(RoomNoItemIdParam b) {
        mHistoryMsg.setValue(b);
    }


    private MutableLiveData<RoomNoParam> mEnterRoomCmd;
    private LiveData<Lcee<Object>> ldEnterRoomCmd;

    public LiveData<Lcee<Object>> getEnterRoomCmd() {

        if (null == ldEnterRoomCmd) {
            mEnterRoomCmd = new MutableLiveData<>();
            ldEnterRoomCmd = Transformations.switchMap(mEnterRoomCmd, new Function<RoomNoParam, LiveData<Lcee<Object>>>() {
                @Override
                public LiveData<Lcee<Object>> apply(RoomNoParam param) {
                    return liveRepository.getEnterRoomCmd(param);
                }
            });
        }

        return ldEnterRoomCmd;
    }

    public void reloadEnterRoomCmd(RoomNoParam param) {
        mEnterRoomCmd.setValue(param);
    }


    private MutableLiveData<ForbiddenParam> mForbidden;
    private LiveData<Lcee<Boolean>> ldForbidden;

    public LiveData<Lcee<Boolean>> getForbidden() {

        if (null == ldForbidden) {
            mForbidden = new MutableLiveData<>();
            ldForbidden = Transformations.switchMap(mForbidden, new Function<ForbiddenParam, LiveData<Lcee<Boolean>>>() {
                @Override
                public LiveData<Lcee<Boolean>> apply(ForbiddenParam b) {
                    return liveRepository.getForbidden(b);
                }
            });
        }

        return ldForbidden;
    }

    public void loadForbidden(ForbiddenParam b) {
        mForbidden.setValue(b);
    }


    private MutableLiveData<RoomNoMatchIdParam> mAnchorStreamUrl;
    private LiveData<Lcee<AnchorStreamUrl>> ldAnchorStreamUrl;

    public LiveData<Lcee<AnchorStreamUrl>> getAnchorStreamUrl() {
        if (null == ldAnchorStreamUrl) {
            mAnchorStreamUrl = new MutableLiveData<>();
            ldAnchorStreamUrl = Transformations.switchMap(mAnchorStreamUrl, new Function<RoomNoMatchIdParam, LiveData<Lcee<AnchorStreamUrl>>>() {
                @Override
                public LiveData<Lcee<AnchorStreamUrl>> apply(RoomNoMatchIdParam input) {
                    return liveRepository.getAnchorStreamUrl(input);
                }
            });
        }

        return ldAnchorStreamUrl;
    }

    public void reLoadAnchorStreamUrl(RoomNoMatchIdParam map) {
        mAnchorStreamUrl.setValue(map);
    }


    private MutableLiveData<RoomNoParam> mLivePlayerInfo;
    private LiveData<Lcee<LivePlayerInfo>> ldPlayerInfo;

    public LiveData<Lcee<LivePlayerInfo>> getLivePlayerInfo() {

        if (null == ldPlayerInfo) {
            mLivePlayerInfo = new MutableLiveData<>();
            ldPlayerInfo = Transformations.switchMap(mLivePlayerInfo, new Function<RoomNoParam, LiveData<Lcee<LivePlayerInfo>>>() {
                @Override
                public LiveData<Lcee<LivePlayerInfo>> apply(RoomNoParam param) {
                    return liveRepository.getLivePlayerInfo(param);
                }
            });
        }

        return ldPlayerInfo;
    }

    public void reloadLivePlayerInfo(RoomNoParam param) {
        mLivePlayerInfo.setValue(param);
    }


    private MutableLiveData<UserIdRoomNoParam> mBanTalkStatus;
    private LiveData<Lcee<BanTalkResponse>> ldBanTalkStatus;

    public LiveData<Lcee<BanTalkResponse>> getBanTalkStatus() {
        if (null == ldBanTalkStatus) {
            mBanTalkStatus = new MutableLiveData<>();
            ldBanTalkStatus = Transformations.switchMap(mBanTalkStatus, new Function<UserIdRoomNoParam,
                    LiveData<Lcee<BanTalkResponse>>>() {
                @Override
                public LiveData<Lcee<BanTalkResponse>> apply(UserIdRoomNoParam b) {
                    return liveRepository.getBanTalkStatus(b);
                }
            });
        }

        return ldBanTalkStatus;
    }

    public void reLoadBanTalkStatus(UserIdRoomNoParam b) {
        mBanTalkStatus.setValue(b);
    }


    private MutableLiveData<RoomNoParam> mPullUrls;
    private LiveData<Lcee<PullUrl>> ldPullUrls;

    public LiveData<Lcee<PullUrl>> getPullUrls() {

        if (null == ldPullUrls) {
            mPullUrls = new MutableLiveData<>();
            ldPullUrls = Transformations.switchMap(mPullUrls, new Function<RoomNoParam, LiveData<Lcee<PullUrl>>>() {
                @Override
                public LiveData<Lcee<PullUrl>> apply(RoomNoParam param) {
                    return liveRepository.getPullUrls(param);
                }
            });
        }

        return ldPullUrls;
    }

    public void reloadPullUrls(RoomNoParam param) {
        mPullUrls.setValue(param);
    }


    private MutableLiveData<Boolean> mConfigParam;
    private LiveData<Lcee<Config>> ldConfig;

    public LiveData<Lcee<Config>> getConfig() {
        if (null == ldConfig) {
            mConfigParam = new MutableLiveData<>();
            ldConfig = Transformations.switchMap(mConfigParam, new Function<Boolean,
                    LiveData<Lcee<Config>>>() {
                @Override
                public LiveData<Lcee<Config>> apply(Boolean b) {
                    return liveRepository.getAppCommonResources();
                }
            });
        }

        return ldConfig;
    }

    public void reLoadConfig(boolean b) {
        mConfigParam.setValue(b);
    }
}
