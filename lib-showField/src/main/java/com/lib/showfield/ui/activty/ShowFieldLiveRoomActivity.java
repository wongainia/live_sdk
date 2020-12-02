package com.lib.showfield.ui.activty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.lib.showfield.R;
import com.lib.showfield.aop.CheckNet;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.bean.EditEvent;
import com.lib.showfield.bean.ForbiddenParam;
import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.bean.MQTTBean;
import com.lib.showfield.bean.NotifySecurityParam;
import com.lib.showfield.bean.PatchAdBean;
import com.lib.showfield.bean.ReportParam;
import com.lib.showfield.bean.RoomNoItemIdParam;
import com.lib.showfield.bean.RoomNoMatchIdParam;
import com.lib.showfield.bean.RoomNoParam;
import com.lib.showfield.bean.SendMsgBean;
import com.lib.showfield.bean.SetupManagerParam;
import com.lib.showfield.bean.ToppicUrl;
import com.lib.showfield.bean.UserIdRoomNoParam;
import com.lib.showfield.bean.cmds.BanTalkCmd;
import com.lib.showfield.bean.cmds.CommissionManageCmd;
import com.lib.showfield.bean.cmds.GiftMessage;
import com.lib.showfield.bean.gift.GiftDoubleCmd;
import com.lib.showfield.bean.gift.bean.GiftListParam;
import com.lib.showfield.bean.gift.bean.GiftSendParam;
import com.lib.showfield.common.Constants;
import com.lib.showfield.common.MyActivity;
import com.lib.showfield.databinding.ActivityShowFieldLivingRoomBinding;
import com.lib.showfield.helper.WeakHandler;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.helper.ControllerInitHelper;
import com.lib.showfield.http.respones.live.AnchorStreamUrl;
import com.lib.showfield.http.respones.live.BanTalkResponse;
import com.lib.showfield.http.respones.live.ChatMessage;
import com.lib.showfield.http.respones.live.ChatResponse;
import com.lib.showfield.http.respones.live.DictDetailBean;
import com.lib.showfield.http.respones.live.FocusRecommendLives;
import com.lib.showfield.http.respones.live.GiftResponse;
import com.lib.showfield.http.respones.live.LivePlayerInfo;
import com.lib.showfield.http.respones.live.LiveRoomTypeInfo;
import com.lib.showfield.http.respones.live.PullUrl;
import com.lib.showfield.http.respones.live.UserCard;
import com.lib.showfield.http.respones.live.UserCoins;
import com.lib.showfield.http.respones.live.UserInfo;
import com.lib.showfield.http.respones.mqtt.TopicUrlBean;
import com.lib.showfield.interfaces.BaseHandlerCallBack;
import com.lib.showfield.interfaces.OnClickDoubleListener;
import com.lib.showfield.interfaces.OnClickProjectionListener;
import com.lib.showfield.interfaces.OnClickReportListener;
import com.lib.showfield.interfaces.OnClickSendMsgListener;
import com.lib.showfield.interfaces.OnContraollerPrectionListener;
import com.lib.showfield.interfaces.OnGoUpListClickListener;
import com.lib.showfield.interfaces.OnJumpOtherLiveByRoomNoListener;
import com.lib.showfield.interfaces.OnPrectionListener;
import com.lib.showfield.interfaces.OnSendGiftClickListener;
import com.lib.showfield.interfaces.OnShowFieldItemClickListener;
import com.lib.showfield.interfaces.OnShowFieldPreparedListener;
import com.lib.showfield.interfaces.OnShowFieldStopScrollListener;
import com.lib.showfield.interfaces.OnShowFieldUserCardOrIsMangerListener;
import com.lib.showfield.interfaces.OnShowOpenFullGiftListener;
import com.lib.showfield.interfaces.OnToggleListener;
import com.lib.showfield.interfaces.OnToggleScreenListener;
import com.lib.showfield.interfaces.OnUpdateUserCoinsListener;
import com.lib.showfield.livedata.LelinkSourceLiveData;
import com.lib.showfield.model.LelinkSourceViewModel;
import com.lib.showfield.model.LiveViewModel;
import com.lib.showfield.model.MqttViewModel;
import com.lib.showfield.other.IntentKey;
import com.lib.showfield.other.tinyTask.onebyone.BaseSyncTask;
import com.lib.showfield.other.tinyTask.onebyone.TinySyncExecutor;
import com.lib.showfield.service.MqttService;
import com.lib.showfield.ui.adapter.LiveShowFieldFullAdapter;
import com.lib.showfield.ui.fragment.LiveAudienceFragment;
import com.lib.showfield.ui.fragment.PushContributeFragment;
import com.lib.showfield.ui.fragment.live.gift.GiftSheetFragment;
import com.lib.showfield.ui.view.dialog.FullScreenProjectionDiaLog;
import com.lib.showfield.ui.view.dialog.MessageDialog;
import com.lib.showfield.ui.view.dialog.PickDialog;
import com.lib.showfield.ui.view.dialog.ProjectionDiaLog;
import com.lib.showfield.ui.view.dialog.ReportDiaLog;
import com.lib.showfield.ui.view.dialog.ShowFieldMoreDialog;
import com.lib.showfield.ui.view.dialog.UserPermissionDialog;
import com.lib.showfield.ui.view.dialog.UserSelfCardDialog;
import com.lib.showfield.ui.view.tiktalk.OnViewPagerListener;
import com.lib.showfield.ui.view.tiktalk.PrectionView;
import com.lib.showfield.ui.view.tiktalk.ShowFieldLiveController;
import com.lib.showfield.ui.view.tiktalk.ViewPagerLayoutManager;
import com.lib.showfield.ui.view.tiktalk.cache.PreloadManager;
import com.lib.showfield.utils.LiveDataBus;
import com.lib.showfield.utils.SystemUtils;
import com.lib.showfield.utils.UserLoginUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import video.dkplayer_java.player.VideoView;
import video.dkplayer_ui.utils.Utils;

import static com.lib.showfield.common.Constants.BAN_TALK;
import static com.lib.showfield.common.Constants.COMMISSION_ROOM_MANAGER;
import static com.lib.showfield.common.Constants.COMMON_RESOURCE;
import static com.lib.showfield.common.Constants.CURRENCYMONEYNAME;
import static com.lib.showfield.common.Constants.DISMISS_ROOM_MANAGER;
import static com.lib.showfield.common.Constants.ENTER_CHAT;
import static com.lib.showfield.common.Constants.FOLLOW_ANCHOR;
import static com.lib.showfield.common.Constants.GIFT_DOUBLE;
import static com.lib.showfield.common.Constants.GIVE_GIFT;
import static com.lib.showfield.common.Constants.OFFLINE;
import static com.lib.showfield.common.Constants.ONLINE;
import static com.lib.showfield.common.Constants.SEND_CHAT_MESSAGE;
import static video.dkplayer_java.player.VideoView.STATE_START_PRECTION;

public class ShowFieldLiveRoomActivity extends MyActivity implements
        OnShowFieldItemClickListener, MqttService.OnMessageListener,
        OnClickSendMsgListener, OnUpdateUserCoinsListener, OnSendGiftClickListener,
        OnClickDoubleListener, OnShowFieldUserCardOrIsMangerListener, OnToggleScreenListener,
        BaseHandlerCallBack, LiveAudienceFragment.OnSelectUserListener, OnGoUpListClickListener,
        OnShowFieldStopScrollListener, OnRefreshLoadMoreListener, OnJumpOtherLiveByRoomNoListener,
        OnShowOpenFullGiftListener, OnClickReportListener, OnClickProjectionListener,
        OnContraollerPrectionListener, OnShowFieldPreparedListener {

    private ActivityShowFieldLivingRoomBinding mBinding;
    private ShowFieldLiveController mController;
    private RecyclerView mRvShowField;
    private SmartRefreshLayout mSrlShowField;

    private static final String TAG = "SHOW_FIELD";
    protected VideoView mVideoView;
    private int mCurPos;

    private LiveShowFieldFullAdapter mAdapter;
    private List<LiveRoomTypeInfo> mVideoList = new ArrayList<>();
    public static final String INDEX_PARAM = "show_param";
    private ViewPagerLayoutManager layoutManager;

    //礼物弹框
    private GiftSheetFragment giftSheetFragment;
    //用户余额
    private long mCoins;
    //贡献榜
    private PushContributeFragment pushContributeFragment;

    //vm
    private MqttViewModel mqttViewModel;
    private LiveViewModel liveViewModel;
    protected PrectionView mPrectionView;

    //订阅主题
    private List<String> topicUrl;
    private String[] topicUrls;

    private int mPageNum = 1;

    private String mUserId;
    private String mRoomNo;
    private int mLiveStatus;
    private int mAnchorId;
    private String mChannelId;

    //礼物相关
    private long mGiftPrice;
    private String mGiftId;
    private boolean isDoubled;

    private String moneyName;
    //点击的是否是自己
    private boolean isClickSelf;
    //当前点击的聊天区item
    private int mCurrentClick;
    //当前用户类型
    private int mUserType;
    //是否关注
    private int isFollow;
    private String mClickNickname;
    private boolean isFollowPlayer;
    //是否初始化完毕
    private boolean isInitInfo = false;
    //是否是第一次进入直播间，查看禁言
    private boolean isFirstBanTalk = false;
    //推流平台
    private String mCurrentPlatform;

    private WeakHandler mFocusHandler = new WeakHandler(this);
    private WeakHandler mLiveAudienceHandler = new WeakHandler(this);
    private WeakHandler mHotNumHandler = new WeakHandler(this);

    //是否是提醒关注弹框
    private boolean isRemaindFocus;

    //当前播放的流地址
    private String mUrl;
    //直播间类型
    private int mRoomType;
    private String mMatchId;

    //举报
    private String mType = "";
    private String mContent;
    private List<DictDetailBean> mDicList = new ArrayList<>();
    private int mScreenHeight;


    @Override
    protected void getLayoutId() {
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_show_field_living_room);
        mRvShowField = mBinding.rvShowFieldDetail;
        mSrlShowField = mBinding.refreshLayout;
    }

    @Override
    protected void initView() {
        mScreenHeight = ScreenUtils.getScreenHeight();
        moneyName = SPUtils.getInstance(COMMON_RESOURCE).getString(CURRENCYMONEYNAME);
        mVideoView = new VideoView(this);
        //以下只能二选一，看你的需求
        //mVideoView.setRenderViewFactory(TikTokRenderViewFactory.create());
        //mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT);
        //mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_16_9);
        initRecyclerView();
        initReceiver();
        initController();
        loadAddData(true);
        //播放位置  待传入
        mRvShowField.scrollToPosition(mCurPos);

        LiveDataBus.getInstance().with("data", EditEvent.class)
                .observe(this, new Observer<EditEvent>() {
                    @Override
                    public void onChanged(EditEvent editEvent) {
                        if (editEvent.getCode() == Constants.TO_UN_SUBSCRIBE_TOPIC) {
                            if (liveViewModel == null) {
                                return;
                            }
                            //登录后要重新请求余额和礼物列表
                            if (ObjectUtils.isEmpty(mRoomNo)) return;
                            //请求礼物列表
                            liveViewModel.reloadGiftListValues(new GiftListParam(mRoomNo, 2));
                            liveViewModel.reloadEnterRoomCmd(new RoomNoParam(mRoomNo));
                            subscribeMQTT();
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        mqttViewModel = new ViewModelProvider(this).get(MqttViewModel.class);
        liveViewModel = new ViewModelProvider(this).get(LiveViewModel.class);
        mqttViewModel.getMqttTopic().observe(this, new Observer<Lcee<TopicUrlBean>>() {
            @Override
            public void onChanged(@Nullable Lcee<TopicUrlBean> data) {
                setTopicUrl(data);
            }
        });

        liveViewModel.getFocusRecommend().observe(this, new Observer<Lcee<FocusRecommendLives>>() {
            @Override
            public void onChanged(Lcee<FocusRecommendLives> lcee) {
                updateRecommendView(lcee);
            }
        });

        liveViewModel.getUser().observe(this, new Observer<Lcee<UserInfo>>() {
            @Override
            public void onChanged(@Nullable Lcee<UserInfo> lcee) {
                updateUserView(lcee);
            }
        });

        //直播间在线人数
        liveViewModel.getLiveAudiences().observe(this, data -> updateLiveAudience(data));

        liveViewModel.getSendMsg().observe(this, new Observer<Lcee<Object>>() {
            @Override
            public void onChanged(Lcee<Object> lcee) {
                updateSendMsgView(lcee);
            }
        });

        liveViewModel.doReport().observe(this, new Observer<Lcee<Boolean>>() {
            @Override
            public void onChanged(@Nullable Lcee<Boolean> lcee) {
                updateReportView(lcee);
            }
        });

        liveViewModel.dictDetail().observe(this, new Observer<Lcee<List<DictDetailBean>>>() {
            @Override
            public void onChanged(Lcee<List<DictDetailBean>> listLcee) {
                setDictDetail(listLcee);
            }
        });

//        liveViewModel.sendGift().observe(this, wnew Observer<Lcee<Object>>() {
//            @Override
//            public void onChanged(Lcee<Object> lcee) {
//                updateSendGiftView(lcee);
//            }
//        });

        liveViewModel.sendHitGift().observe(this, new Observer<Lcee<Object>>() {
            @Override
            public void onChanged(Lcee<Object> lcee) {
                updateSendHitGiftView(lcee);
            }
        });

//        liveViewModel.getUserCoins().observe(this, new Observer<Lcee<UserCoins>>() {
//            @Override
//            public void onChanged(Lcee<UserCoins> lcee) {
//                updateUserCoins(lcee);
//            }
//        });

        liveViewModel.getUserCard().observe(this, new Observer<Lcee<UserCard>>() {
            @Override
            public void onChanged(Lcee<UserCard> lcee) {
                updateUserCardView(lcee);
            }
        });

        liveViewModel.getIsRoomManager().observe(this, new Observer<Lcee<Boolean>>() {
            @Override
            public void onChanged(Lcee<Boolean> lcee) {
                updateUserIsManager(lcee);
            }
        });

//        liveViewModel.getFocusOnAnchor().observe(this, new Observer<Lcee<Object>>() {
//            @Override
//            public void onChanged(@Nullable Lcee<Object> lcee) {
//                updateFocus(lcee);
//            }
//        });

        liveViewModel.getSetupManager().observe(this, new Observer<Lcee<Boolean>>() {
            @Override
            public void onChanged(Lcee<Boolean> lcee) {
                updateSetupManager(lcee);
            }
        });

        liveViewModel.getUnSetupManager().observe(this, new Observer<Lcee<Boolean>>() {
            @Override
            public void onChanged(Lcee<Boolean> lcee) {
                updateUnSetupManager(lcee);
            }
        });

        liveViewModel.getChatMsg().observe(this, new Observer<Lcee<List<ChatResponse>>>() {
            @Override
            public void onChanged(Lcee<List<ChatResponse>> lcee) {
                updateHistoryMsg(lcee);
            }
        });

        liveViewModel.getEnterRoomCmd().observe(this, new Observer<Lcee<Object>>() {
            @Override
            public void onChanged(Lcee<Object> lcee) {
            }
        });

        liveViewModel.getForbidden().observe(this, new Observer<Lcee<Boolean>>() {
            @Override
            public void onChanged(Lcee<Boolean> lcee) {
                updateForbidden(lcee);
            }
        });

        liveViewModel.getAnchorStreamUrl().observe(this, new Observer<Lcee<AnchorStreamUrl>>() {
            @Override
            public void onChanged(@Nullable Lcee<AnchorStreamUrl> lcee) {
                updatePullUrl(lcee);
            }
        });

        liveViewModel.getLiveRoomType().observe(this, new Observer<Lcee<LiveRoomTypeInfo>>() {
            @Override
            public void onChanged(Lcee<LiveRoomTypeInfo> lcee) {
                updateLiveRoomType(lcee);
            }
        });

        liveViewModel.getGiftListValue().observe(this, new Observer<Lcee<List<GiftResponse>>>() {
            @Override
            public void onChanged(Lcee<List<GiftResponse>> lcee) {
                updateGiftListView(lcee);
            }
        });


        liveViewModel.getLivePlayerInfo().observe(this, new Observer<Lcee<LivePlayerInfo>>() {
            @Override
            public void onChanged(@Nullable Lcee<LivePlayerInfo> lcee) {
                updateInfoView(lcee);
            }
        });

        liveViewModel.getBanTalkStatus().observe(this, new Observer<Lcee<BanTalkResponse>>() {
            @Override
            public void onChanged(Lcee<BanTalkResponse> lcee) {
                updateBanTalkStatus(lcee);
            }
        });

        liveViewModel.getPullUrls().observe(this, new Observer<Lcee<PullUrl>>() {
            @Override
            public void onChanged(@Nullable Lcee<PullUrl> lcee) {
                updatePullUrls(lcee);
            }
        });

        // 监听贴片广告
        liveViewModel.getPatcAdLiveData().observe(this, new Observer<Lcee<List<PatchAdBean>>>() {
            @Override
            public void onChanged(Lcee<List<PatchAdBean>> listLcee) {
                updateAdPatchView(listLcee);
            }
        });

        liveViewModel.loadDictDetail(false);
        reloadRec(1);

    }

    @CheckNet
    private void reloadRec(int pageNum) {
        liveViewModel.reloadFocusRecommend(new NotifySecurityParam(1, pageNum, mChannelId));
    }

    private void updateAdPatchView(Lcee<List<PatchAdBean>> listLcee) {
        switch (listLcee.status) {
            case Content:
                mController.setAdShowHide(true);
                mController.setAdData(listLcee.data.getData());

                break;
            case Empty:
            case Error:
                mController.setAdShowHide(false);
                break;
        }
    }

    private void updatePullUrls(Lcee<PullUrl> lcee) {
        switch (lcee.status) {
            case Content:
                if (!ObjectUtils.isEmpty(lcee.data.getData().getList())) {
                    int pullSize = lcee.data.getData().getList().size();
                    mUrl = lcee.data.getData().getList().get(pullSize - 1).getPullUrl();
                    startPlay(mUrl);
                } else {
                    addVideoView();
                    mController.showPlayerOff();
                }
                break;
            case Empty:
                addVideoView();
                mController.showPlayerOff();
                break;
            case Error:
                toast(lcee.error.getMessage());
                break;
        }
    }

    private void updateBanTalkStatus(Lcee<BanTalkResponse> lcee) {
        switch (lcee.status) {
            case Content:
                if (!isResume) {
                    if (lcee.data.getData().getIsBan()) {
                        mController.setEdtFocusable(false, lcee.data.getData().getTime());
                        toast("您已被主播禁言");
                    } else {
                        if (isFirstBanTalk) {
                            mController.setEdtFocusable(true, null);
                        }
                        isFirstBanTalk = true;
                    }
                }

                break;
            case Empty:
                break;
            case Error:
                break;
        }
    }

    private void updateInfoView(Lcee<LivePlayerInfo> lcee) {
        switch (lcee.status) {
            case Content:
                mRoomNo = lcee.data.getData().getRoomNo();
                mLiveStatus = lcee.data.getData().getLiveStatus();
                isFollow = lcee.data.getData().getIsFollow();
                mAnchorId = lcee.data.getData().getAnchorId();
                mRoomType = lcee.data.getData().getRoomType();
                mMatchId = lcee.data.getData().getMatchId();

                if (String.valueOf(mAnchorId).equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
                    mUserType = 3;
                }

//                if (isFollow == 1) {
//                    mController.setFocusImg(true);
//                } else {
//                    mController.setFocusImg(false);
//                    showFocusDialog();
//                }

                if (mLiveStatus == 0) {
                    mVideoView.release();
                    mController.showPlayerOff();
                }

                mController.setPlayerInfo(lcee.data.getData());
                initInformation(mRoomNo);
                //请求礼物列表
                liveViewModel.reloadGiftListValues(new GiftListParam(mRoomNo, 2));
                liveViewModel.reLoadHistoryMsg(new RoomNoItemIdParam(mRoomNo, lcee.data.getData().getLiveId()));
                liveViewModel.reloadEnterRoomCmd(new RoomNoParam(mRoomNo));
                isInitInfo = true;

                if (mRoomType == 3) {
                    reloadUrls(new RoomNoMatchIdParam(mRoomNo, mMatchId));
                } else {
                    reloadPullUrls(new RoomNoParam(mRoomNo));
                }

                //开启在线用户轮训
                startLiveAudience();

                break;
            case Loading:
                break;
            case Error:
                toast(lcee.error.getMessage());
                break;
        }
    }

    private void reloadUrls(RoomNoMatchIdParam param) {
        liveViewModel.reLoadAnchorStreamUrl(param);
    }

    private void reloadPullUrls(RoomNoParam roomNoParam) {
        liveViewModel.reloadPullUrls(roomNoParam);
    }

    //提醒关注弹框
    private void showFocusDialog() {
        Message message = Message.obtain(mFocusHandler);
        message.what = 1;
        mFocusHandler.sendMessageDelayed(message, 1000 * 60);//通过延迟发送消息，每隔60秒发送一条消息
    }

    //在线主播接口轮训
    private void startLiveAudience() {
        Message message = Message.obtain(mLiveAudienceHandler);
        message.what = 2;
        mLiveAudienceHandler.sendMessageDelayed(message, 1000 * 30);//通过延迟发送消息，每隔30秒发送一条消息
    }

    private void initInformation(String roomNo) {
        liveViewModel.reload(false);
        reloadLiveAudience();
        giftSheetFragment = GiftSheetFragment.newInstance(mRoomNo);
        giftSheetFragment.setOnUpdateUserCoinsListener(this);
        giftSheetFragment.setOnSendGiftClickListener(this);
    }

    private void reloadLiveAudience() {
        Map map = new HashMap();
        map.put("roomNo", mRoomNo);
        map.put("pageNum", 1);
        liveViewModel.reLoadLiveAudienceList(map);
    }

    private void updatePullUrl(Lcee<AnchorStreamUrl> lcee) {
        switch (lcee.status) {
            case Content:
                mUrl = lcee.data.getData().getFlvUrls().get(0);
                Log.d("zzy", "-------url---------: " + mUrl);
                startPlay(mUrl);

                break;
            case Error:
                Log.d("zzy", "-------url---------: ");
                toast(lcee.error.getMessage());
                break;
            case Empty:
                addVideoView();
                mController.showPlayerOff();

                break;
        }
    }

    private void updateForbidden(Lcee<Boolean> lcee) {
        switch (lcee.status) {
            case Content:
                if (lcee.data.getData()) {
                    toast("您成功禁言「" + mClickNickname + "」");
                }
                break;
            case Error:
                break;
        }
    }

    private void updateGiftListView(Lcee<List<GiftResponse>> lcee) {
        switch (lcee.status) {
            case Content:
                mController.setGiftData(lcee.data.getData());
                //liveViewModel.reLoadUserCoins(false);
                break;
            case Empty:

                break;
        }
    }

    private void updateLiveRoomType(Lcee<LiveRoomTypeInfo> lcee) {
        switch (lcee.status) {
            case Content:
                LiveRoomTypeInfo info = lcee.data.getData();
                int screenMode = info.getScreenMode();
                if (screenMode == 2) {
                    Intent intent = new Intent(this, ShowFieldLiveRoomActivity.class);
                    intent.putExtra("show_param", info);
                    startActivity(intent);
                }
                finish();
                break;
            case Empty:
                break;
            case Error:
                toast(lcee.error.getMessage());
                break;
        }
    }

    private void updateHistoryMsg(Lcee<List<ChatResponse>> lcee) {
        switch (lcee.status) {
            case Content:
                for (int i = 0; i < lcee.data.getData().size(); i++) {
                    ChatResponse msg = lcee.data.getData().get(i);
                    ChatMessage chatMessage = new ChatMessage();
                    int userType = msg.getUser().getUserType();
                    chatMessage.setUserId(msg.getUser().getUserId());
                    chatMessage.setAnthorId(mAnchorId);
                    chatMessage.setNickName(msg.getUser().getNickName());
                    chatMessage.setCertifiedAnchorStatus(msg.getUser().getCertifiedAnchorStatus());
                    chatMessage.setContent(msg.getContent());
                    chatMessage.setSupportTeamLogo(msg.getUser().supportTeamLogo);


                    String userLevel = msg.getUser().getUserLevel();
                    String anchorLevel = msg.getUser().getAnchorLevel();

                    if (!"0".equals(userLevel) && !ObjectUtils.isEmpty(userLevel)) {
                        chatMessage.setUserLevel(userLevel);
                    } else {
                        chatMessage.setUserLevel("1");
                    }

                    if (!"0".equals(anchorLevel) && !ObjectUtils.isEmpty(anchorLevel)) {
                        chatMessage.setAnchorLevel(anchorLevel);
                    } else {
                        chatMessage.setAnchorLevel("1");
                    }


                    if (userType == 0) {
                        chatMessage.setMessageType(0);
                    } else if (userType == 3) {
                        chatMessage.setMessageType(0);
                    } else {
                        chatMessage.setMessageType(1);
                    }

                    mController.setChatData(chatMessage);
                }

                break;
            case Empty:
                break;
            case Error:
                break;
        }
    }

    private void updateUnSetupManager(Lcee<Boolean> lcee) {
        switch (lcee.status) {
            case Content:
                toast("解任房管成功");
                break;
            case Error:
                break;
        }
    }

    private void updateSetupManager(Lcee<Boolean> lcee) {
        switch (lcee.status) {
            case Content:
                toast("任命房管成功");
                break;
            case Error:
                break;
        }
    }

    private void reloadUserCard(UserIdRoomNoParam param) {
        liveViewModel.loadUserCard(param);
    }

    private void updateUserIsManager(Lcee<Boolean> lcee) {
        switch (lcee.status) {
            case Content:
                Log.d("zzy", "----是否为房管----: " + lcee.data.getData());
                if (lcee.data.getData()) {
                    mUserType = 1;
                } else {
                    mUserType = 0;
                }

                reloadUserCard(new UserIdRoomNoParam(mCurrentClick,
                        mRoomNo));
                break;
            case Error:
                break;
        }
    }

    private void updateSendHitGiftView(Lcee<Object> lcee) {
        switch (lcee.status) {
            case Content:


                break;
            case OCCURY:
                toast("余额不足");
                mController.showDoubleIcon(false);
//                if (SPUtils.getInstance(COMMON_RESOURCE).getBoolean(PAYSWITCH, true)) {
//                    startActivity(MyAccountActivity.class);
//                } else {
//                    startActivity(MyAccountOffActivity.class);
//                }
        }
    }

    private void updateLiveAudience(Lcee<LiveAudienceBean> lcee) {
        switch (lcee.status) {
            case Content:
                mController.setLivingUserNumData(lcee.data.getData());
                break;
            case Empty:
                mController.setLivingUserNumData(null);
                break;
        }
    }

    private void updateUserCardView(Lcee<UserCard> lcee) {
        switch (lcee.status) {
            case Content:
                Log.d("zzy", "----用户卡片----: " + lcee.data.getData().toString());
                UserCard card = lcee.data.getData();
                if (isRemaindFocus) {
//                    new ShowFieldFocusDialog.Builder(this)
//                            .setData(card, card.getUserId())
//                            .setListener(new ShowFieldFocusDialog.OnListener() {
//                                @Override
//                                public void onFocus(BaseDialog dialog, int opType) {
//                                    isFollow = 0;
//                                    if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
//                                        //关注
//                                        reloadFocus(new FocusChatAnchorParam(String.valueOf(card.getUserId()), 1,
//                                                1, mRoomNo));
//                                    } else {
//                                        startActivity(LoginActivity.class);
//                                    }
//
//                                    dialog.dismiss();
//                                }
//                            })
//                            .addOnShowListener(new BaseDialog.OnShowListener() {
//                                @Override
//                                public void onShow(BaseDialog dialog) {
//                                    mFocusHandler.removeCallBackMessages();
//                                }
//                            })
//                            .show();
                } else {
                    if (!isClickSelf) {
                        showUserCardDialog(card);
                    } else {
                        showUserCardSelfDialog(card);
                    }
                }
                break;
            case Empty:
                break;
            case Error:
                break;
        }
    }

    /**
     * 用户卡片弹窗(点击自己)
     */
    private void showUserCardSelfDialog(UserCard userCard) {
        new UserSelfCardDialog.Builder(this)
                .setData(userCard, mAnchorId)
                .setCanceledOnTouchOutside(true)
                .show();
    }

    /**
     * 用户卡片弹窗
     */
    private void showUserCardDialog(UserCard userCard) {
        this.mCurrentClick = userCard.getUserId();
        new UserPermissionDialog.Builder(this)
                .setCanceledOnTouchOutside(true)
                .setData(userCard, mUserType, mAnchorId)
                .setListener(new UserPermissionDialog.OnListener() {
                    @Override
                    public void onFocus(BaseDialog dialog, int opType) {
                        if (opType == 1) {
                            if (UserLoginUtil.checkLogin(ShowFieldLiveRoomActivity.this)) return;
//                            new FocusDialog.Builder(ShowFieldLiveRoomActivity.this)
//                                    .setMessage("确定取消关注吗？")
//                                    .setListener(new FocusDialog.OnListener() {
//                                        @Override
//                                        public void onConfirm(BaseDialog dialog) {
//                                            //取关
//                                            isFollow = 1;
//                                            reloadFocus(new FocusChatAnchorParam(String.valueOf(userCard.getUserId()), 0,
//                                                    1, mRoomNo));
//                                            dialog.dismiss();
//                                        }
//                                    }).show();
//
//                            dialog.dismiss();

                        } else {
                            isFollow = 0;
                            if (UserLoginUtil.checkLogin(ShowFieldLiveRoomActivity.this)) return;
                            //关注
//                            reloadFocus(new FocusChatAnchorParam(String.valueOf(userCard.getUserId()), 1,
//                                    1, mRoomNo));
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onSetupManager(boolean isSetupManager, BaseDialog dialog) {
                        dialog.dismiss();
                        if (isSetupManager) {
                            new MessageDialog.Builder(ShowFieldLiveRoomActivity.this)
                                    .setMessage("是否确定将「" + userCard.getNickname() + "」任命为房管")
                                    .setListener(new MessageDialog.OnListener() {
                                        @Override
                                        public void onConfirm(BaseDialog dialog) {
                                            if (UserLoginUtil.checkLogin(ShowFieldLiveRoomActivity.this))
                                                return;
                                            reloadSetupManager(new SetupManagerParam(mRoomNo,
                                                    userCard.getUserId(), mAnchorId));
                                        }
                                    }).show();

                        } else {
                            if (UserLoginUtil.checkLogin(ShowFieldLiveRoomActivity.this)) return;
                            reloadUnSetupManager(new UserIdRoomNoParam(userCard.getUserId(), mRoomNo));
                        }
                    }

                    @Override
                    public void onForbidden(BaseDialog dialog) {
                        dialog.dismiss();
                        showForbiddenDialog(userCard.getUserId());
                        mClickNickname = userCard.getNickname();
                    }
                })
                .show();
    }

    private void showForbiddenDialog(int userId) {
        ArrayList<String> lists = new ArrayList<>();
        lists.add("30分钟");
        lists.add("2小时");
        lists.add("1天");
        lists.add("3天");
        lists.add("7天");
        lists.add("30天");
        lists.add("永久");

        new PickDialog.Builder(this)
                .setTitle("禁言")
                .setDataList(lists)
                .setListener(new PickDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int data) {
                        dialog.dismiss();
                        if (UserLoginUtil.checkLogin(ShowFieldLiveRoomActivity.this)) return;
                        reloadForbidden(new ForbiddenParam(mRoomNo, mAnchorId,
                                userId, data));
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void reloadForbidden(ForbiddenParam param) {
        liveViewModel.loadForbidden(param);
    }

    private void reloadUnSetupManager(UserIdRoomNoParam param) {
        liveViewModel.loadUnSetupManager(param);
    }

    private void reloadSetupManager(SetupManagerParam param) {
        liveViewModel.loadSetupManager(param);
    }

    private void updateUserCoins(Lcee<UserCoins> lcee) {
        switch (lcee.status) {
            case Content:

                long remainingSum = lcee.data.getData().getRemainingSum();
                mController.setCoins(remainingSum);

                this.mCoins = remainingSum;

                break;
            case Error:
                Log.e("zzy", "获取余额失败");
                break;
        }
    }

    private void updateSendGiftView(Lcee<Object> lcee) {
        switch (lcee.status) {
            case Content:
                if (lcee.data.getCode() == 200) {
                    Log.d("zzy", "礼物发送成功");
                    if (giftSheetFragment != null && giftSheetFragment.isVisible()) {
                        giftSheetFragment.setCutCoins(mGiftPrice);
                    }
                    //mController.setCutCoins(mGiftPrice);
                    mCoins = mCoins - mGiftPrice;
                    Log.d("zzy", "-----mCoins-------: " + mCoins);
                }
                break;
            case OCCURY:
                toast("余额不足");
                if (giftSheetFragment != null && giftSheetFragment.isVisible()) {
                    giftSheetFragment.dismiss();
                }
                mController.showDoubleIcon(false);
                //startActivity(MyAccountActivity.class);
                break;
        }
    }

    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                .fullScreen(true)
                // 透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                .transparentNavigationBar();
    }

    private void setDictDetail(Lcee<List<DictDetailBean>> lcee) {
        switch (lcee.status) {
            case Content:
                mDicList = lcee.data.getData();
                break;
            case Empty:
                toast("获取举报原因为空");
                break;
            case Error:
                break;
        }
    }

    private void updateReportView(Lcee<Boolean> lcee) {
        switch (lcee.status) {
            case Content:
                hideDialog();
                toast("举报成功");
                break;
            case Loading:
                showDialog();
                break;
            case Error:
                hideDialog();
                toast(lcee.error.getMessage());
            case NOLOGIN:
                hideDialog();
                break;
        }
    }

    private void updateSendMsgView(Lcee<Object> lcee) {
        switch (lcee.status) {
            case Content:
                Log.d("zzy", lcee.data.getData().toString());
                //发送热力值
                //reloadHotValue();
                break;
            case Error:
                toast(lcee.error.getMessage());
                break;
            case ALEADYIN:
                toast("发言频率过高，歇会儿吧~");
                break;
        }
    }

    private void updateUserView(Lcee<UserInfo> lcee) {
        switch (lcee.status) {
            case Content:
                hideDialog();
                SPUtils.getInstance("user").put(IntentKey.AVATAR, lcee.data.getData().getAvatar());
                SPUtils.getInstance("user").put(Constants.USER_ID, lcee.data.getData().getUserId());
                SPUtils.getInstance("user").put(Constants.NICKNAME, lcee.data.getData().getNickName());

                mUserId = lcee.data.getData().getUserId();

                liveViewModel.reLoadBanTalkStatus(new UserIdRoomNoParam(Integer.parseInt(mUserId),
                        mRoomNo));

                break;
            case Empty:
                hideDialog();
                break;
            case Error:
                break;
        }
    }

    private void updateRecommendView(Lcee<FocusRecommendLives> lcee) {
        switch (lcee.status) {
            case Content:
                List<LiveRoomTypeInfo> realBeanList = new ArrayList<>();
                int size = lcee.data.getData().getRows().size();
                for (int i = 0; i < size; i++) {
                    FocusRecommendLives.RowsBean bean = lcee.data.getData().getRows().get(i);
                    if (!lcee.data.getData().getRows().get(i).getRoomNo().equals(mRoomNo)) {
                        LiveRoomTypeInfo info = new LiveRoomTypeInfo();
                        info.setCoverUrl(bean.getCover());
                        info.setRoomNo(bean.getRoomNo());
                        realBeanList.add(info);
                    }
                }

                int oldSize = mAdapter.getData().size();
                mVideoList.addAll(realBeanList);
                if (mPageNum > 1) {
                    mAdapter.notifyItemRangeInserted(oldSize, realBeanList.size());
                    mRvShowField.smoothScrollToPosition(oldSize);
                } else {
                    mAdapter.setData$com_github_CymChad_brvah(mVideoList);
                    mAdapter.notifyDataSetChanged();
                }

                break;
            case Empty:
                if (mPageNum > 1) {
                    toast("没有更多主播了！");
                }
                break;
            case Error:
                break;
        }
    }

    private void setTopicUrl(Lcee<TopicUrlBean> data) {
        switch (data.status) {
            case Content: {
                topicUrl = data.data.getData().getTopicUrls();
                //reloadInfo(new RoomNoParam(mRoomNo));
                subscribeMQTT();
                break;
            }
            case Error:
                toast("获取订阅主题失败");
                break;
            case TOKENLOST:
                //LoginActivity.start(this);
                finish();
                break;
        }
    }


    /**
     * 开始连接mqtt
     */
    public void subscribeMQTT() {
        Log.d("zzyip", "-------subscribeMQTT--------");
        if (!ObjectUtils.isEmpty(topicUrl)) {
            String[] topicUrls = new String[2];
            for (int i = 0; i < topicUrl.size(); i++) {
                topicUrls[i] = topicUrl.get(i);
            }

            this.topicUrls = topicUrls;
            Log.d(TAG, "subscribeTopic: topicUrls: " + topicUrls.toString());
            MqttService.subscribeTopic(topicUrls, this);//订阅主题，参数：主题、服务质量

            if (!isInitInfo) {
                mController.setClearData();
                ChatMessage message = new ChatMessage();
                message.setMessageType(7);
                mController.setChatData(message);
                reloadInfo(new RoomNoParam(mRoomNo));
            }
        }
    }

    @CheckNet
    private void reloadInfo(RoomNoParam param) {
        liveViewModel.reloadLivePlayerInfo(param);
    }


    private void initRecyclerView() {
        mSrlShowField.setOnRefreshLoadMoreListener(this);
        mAdapter = new LiveShowFieldFullAdapter(R.layout.item_show_field);
        layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);

        mRvShowField.setLayoutManager(layoutManager);
        mRvShowField.setAdapter(mAdapter);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                Log.d(TAG, "onInitComplete: ");
                //订阅
                mCurPos = 0;
                mCurrentPlatform = mAdapter.getData().get(mCurPos).getPlatform();
                mqttViewModel.loadTopicUrl(new ToppicUrl(mAdapter.getData().get(mCurPos).getRoomNo()));
                if (mCurPos > mAdapter.getData().size()) return;
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.d(TAG, "---onPageRelease-----" + "mCurPos: " + mCurPos + " position: " + position);
                if (mCurPos == position) {
                    //Log.d(TAG, "onPageRelease: " + position + " isnext: " + isNext);
                    mVideoView.release();
                    mController.removeCathe();
                    //取消订阅
                    MqttService.unSubscribeTopic(topicUrls);//订阅主题，参数：主题、服务质量
                    TinySyncExecutor.getInstance().remove();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.d(TAG, "onPageSelected: " + position + " isbottom: " + isBottom);
                if (mCurPos == position) return;
                mFocusHandler.removeCallBackMessages();
                mController.onResumeReward();
                mCurPos = position;
                isInitInfo = false;
                isFirstBanTalk = false;
                mCurrentPlatform = mAdapter.getData().get(mCurPos).getPlatform();
                mRoomNo = mAdapter.getData().get(mCurPos).getRoomNo();
                //初始化
                mqttViewModel.loadTopicUrl(new ToppicUrl(mRoomNo));
                if (position > mAdapter.getData().size()) return;
            }
        });
    }

    /**
     * 注册网络监听的广播
     */
    private void initReceiver() {
        IntentFilter timeFilter = new IntentFilter();
        timeFilter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
        timeFilter.addAction("android.net.ethernet.STATE_CHANGE");
        timeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        timeFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        timeFilter.addAction("android.net.wifi.STATE_CHANGE");
        timeFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(netReceiver, timeFilter);
    }

    private void initController() {
        mController = new ShowFieldLiveController(this);
        mController.addDefaultControlComponent();
        // 投屏展示层
        mPrectionView = new PrectionView(this).setListener(this);

        mController.setEnableOrientation(false);
        mController.setOnShowFieldItemClickListener(this);
        mController.setOnClickSendMsgListener(this);
        mController.setOnSendGiftClickListener(this);
        mController.setOnClickDoubleListener(this);
        mController.setOnShowFieldUserCardOrIsMangerListener(this);
        mController.setOnToggleScreenListener(this);
        mController.setOnShowFieldStopScrollListener(this);
        mController.setOnJumpOtherLiveByRoomNoListener(this);
        mController.setOnShowOpenFullGiftListener(this);
        mController.setOnClickReportListener(this);
        mController.setOnClickProjectionListener(this);
        mController.setOnShowFieldPreparedListener(this);
        mVideoView.setVideoController(mController);

        ControllerInitHelper.getInstance().initFinish();
    }

    private void loadAddData(boolean isLocalData) {
        if (isLocalData) {
            LiveRoomTypeInfo recommendLives = (LiveRoomTypeInfo) getIntent().getSerializableExtra(INDEX_PARAM);
            assert recommendLives != null;
            mRoomNo = recommendLives.getRoomNo();
            mChannelId = recommendLives.getChannelId();
            mVideoList.add(recommendLives);
            mAdapter.setData$com_github_CymChad_brvah(mVideoList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void callBack(Message msg) {
        if (msg.what == 1) {
            isRemaindFocus = true;
            isFollowPlayer = true;
            reloadUserCard(new UserIdRoomNoParam(mAnchorId,
                    mRoomNo));
        } else if (msg.what == 2) {
            reloadLiveAudience();
        } else {
            // TODO: 2020/10/29 热力值
        }
    }

    @Override
    public void onClickDouble(String giftId, int times) {
        reloadDoubleHitGift(new GiftSendParam(mAnchorId,
                mGiftId, times));
    }

    BaseDialog projectionDiaLog;
    BaseDialog fullprojectionDiaLog;
    FullScreenProjectionDiaLog.Builder fullBuilder;
    ProjectionDiaLog.Builder builder;

    @Override
    public void onClickProjection(boolean isFullScreen) {
        if (isFullScreen) {
            fullBuilder = new FullScreenProjectionDiaLog.Builder(
                    this,
                    mUrl)
                    .setListener(onPrejectionListener);
            fullprojectionDiaLog = fullBuilder.show();
        }
    }

    LelinkSourceViewModel mLelinkSourceViewModel;
    // 投屏成功后 回调该函数
    OnPrectionListener onPrejectionListener = new OnPrectionListener() {

        @Override
        public void onStartConnect() {
            showDialog();
        }

        @Override
        public void onConnectSuccess(LelinkServiceInfo selectInfo) {// 增加一个参数类型 代表来自全屏还是小屏触发事件
            Log.d(LelinkSourceLiveData.TAG, "onConnectSuccess: ");

            hideDialog();
            //ToastUtils.showShort("" + selectInfo.getName());
            if (fullprojectionDiaLog != null && fullprojectionDiaLog.isShowing())
                fullprojectionDiaLog.dismiss();
            if (projectionDiaLog != null && projectionDiaLog.isShowing())
                projectionDiaLog.dismiss();

            // sdk连接后  展示videoview投屏界面状态
            mController.showAlwaysComponentView();
            mController.setPlayState(STATE_START_PRECTION);
            mController.isProjectionStatus = true;
            mPrectionView.setTitle(selectInfo.getName());


            if (mLelinkSourceViewModel == null) {
                mLelinkSourceViewModel = new ViewModelProvider(ShowFieldLiveRoomActivity.this).get(LelinkSourceViewModel.class);
            }
            mController.setToggleListener(mToggleListener);
        }

        // 播放或者暂停
        OnToggleListener mToggleListener = new OnToggleListener() {
            @Override
            public void toggle(boolean isPlaying) {
                if (mLelinkSourceViewModel == null) return;
                mLelinkSourceViewModel.toggle(isPlaying);
            }
        };

        @Override
        public void onDisConnectFail() {
            hideDialog();
            ToastUtils.showShort("断开投屏");
            Log.d(LelinkSourceLiveData.TAG, "onDisConnectFail: ");
            mController.setPlayState(VideoView.STATE_START_CANCEL_PRECTION);
            mController.isProjectionStatus = false;
        }
    };

    @Override
    public void onReportClick() {
        showReportDialog();
    }

    private void showReportDialog() {
        if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            new ReportDiaLog.Builder(this)
                    .setReportReasonData(mDicList)
                    .setListener(new ReportDiaLog.OnListener() {
                        @Override
                        public void onConfirm(BaseDialog dialog) {
                            if (!ObjectUtils.isEmpty(mType)) {
                                reload(new ReportParam(mRoomNo, mType, mContent,
                                        1, Integer.parseInt(mUserId)));
                                dialog.dismiss();
                                mType = "";
                                mContent = "";
                            } else {
                                toast("请选择举报类型");
                            }
                        }

                        @Override
                        public void onReportType(String type) {
                            Log.d("zzy", "----report---content---: " + type);
                            mType = type;
                        }

                        @Override
                        public void onReportContent(String content) {
                            mContent = content;
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                            dialog.dismiss();
                            mType = "";
                            mContent = "";
                        }
                    })
                    .show();
        } else {
            //LoginActivity.start(this);
        }
    }

    @CheckNet
    private void reload(ReportParam param) {
        liveViewModel.reloadReport(param);
    }

    @Override
    public void onSendMsgClick(String msg) {
        sendMessage(msg);
    }

    /**
     * 发送聊天区消息
     */
    private void sendMessage(String content) {
        Log.d("zzy", "----showField---roomNo-sendMessage---: " + mRoomNo);
        SendMsgBean bean = new SendMsgBean();
        bean.setRoomNo(mRoomNo);
        //bean.setItemId(String.valueOf(mAdapter.getLiveId()));
        bean.setContent(content);
        liveViewModel.reloadSendMsg(bean);
    }

    @Override
    public void updateDevices() {
        onClickProjection(mController.isFullScreen());
    }

    @Override
    public void exitDevices() {
        mController.setPlayState(VideoView.STATE_START_CANCEL_PRECTION);
        mController.isProjectionStatus = false;
        mVideoView.setMute(false);
        mLelinkSourceViewModel.exitDevices();
    }

    @Override
    public void onGoUpClick() {
        pushContributeFragment.dismiss();
        giftSheetFragment.show(getSupportFragmentManager(), "gift");
    }

    @Override
    public void onJumpToOtherLive(String roomNo) {
        Log.d("zzyip", "----jump-----roomNo------- :" + roomNo);
        reloadRoomTypeInfo(new RoomNoParam(roomNo));
    }

    private void reloadRoomTypeInfo(RoomNoParam param) {
        liveViewModel.reLoadLiveRoomType(param);
    }


    @Override
    public void onClickSendGift(String giftId, long giftPrice, int sendType) {
        this.mGiftPrice = giftPrice;
        this.mGiftId = giftId;
        this.isDoubled = false;

        mController.sendGiftInfo(giftId, giftPrice, isDoubled);

//        if (sendType == 1) {
//            mController.showDoubleIcon(true);
//        }

        //成功后调用
        //SendGiftHelper.getInstance(this).sendGift(new GiftSendParam(mAnchorId, giftId, 1));
    }

    /**
     * 发送礼物
     */
    @CheckNet
    public void reloadSendGift(GiftSendParam param) {
        liveViewModel.loadGiftSend(param);
    }

    @CheckNet
    private void reloadDoubleHitGift(GiftSendParam param) {
        liveViewModel.loadHitGiftSend(param);
    }

    @Override
    public void onShowFieldItemClick(int type, String userId) {
        switch (type) {
            //更多
            case 0:
                showFieldMoreDialog();
                break;
            //红包
            case 1:
//                if (UserLoginUtil.checkLogin(this)) return;
//                if (TextUtils.isEmpty(mVideoList.get(mCurPos).getRoomNo())) {
//                    ToastUtils.showShort("缺少房间号");
//                    return;
//                }
//                RedSheetFragmentDialog.newInstance(mVideoList.get(mCurPos).getRoomNo())
//                        .setSendListener(mSendRedListener).show(getSupportFragmentManager(), "redDialog");
                break;
            //礼物
            case 2:
                if (UserLoginUtil.checkLogin(this)) return;
                setOpenGiftDialog();
                break;
            //关闭
            case 3:
                finish();
                break;
            //贡献榜
            case 4:
                pushContributeFragment = PushContributeFragment.newInstance(mRoomNo, mAnchorId, false);
                pushContributeFragment.setOnGoUpListClickListener(this);
                pushContributeFragment.show(getSupportFragmentManager(), "contribute");

                break;
            case 5:
                isFollowPlayer = true;
                if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN)) {
                    if (isFollow == 1) {
                        //取消关注
//                        new FocusDialog.Builder(this)
//                                .setMessage("确定取消关注吗？")
//                                .setListener(new FocusDialog.OnListener() {
//                                    @Override
//                                    public void onConfirm(BaseDialog dialog) {
//                                        reloadFocus(new FocusChatAnchorParam(String.valueOf(mAnchorId),
//                                                0, 1, mRoomNo));
//                                    }
//                                })
//                                .show();
                    } else {
                        if (!String.valueOf(mAnchorId)
                                .equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
                            //关注
//                            reloadFocus(new FocusChatAnchorParam(String.valueOf(mAnchorId),
//                                    1, 1, mRoomNo));
                        } else {
                            toast("不能关注自己");
                        }
                    }
                } else {
                    //LoginActivity.start(this);
                }
                break;
            case 6:
                reloadLiveAudience();
                //fragment.show(getSupportFragmentManager(), "liveAud");
                LiveAudienceFragment liveAudienceFragment = LiveAudienceFragment.newInstance(mRoomNo);
                liveAudienceFragment.show(getSupportFragmentManager(), "push_audiences");
                liveAudienceFragment.onShowUserCardListener(this);
                break;
            case 7:
                isFollowPlayer = true;
                onShowFieldUserCardOrIsManger(String.valueOf(mAnchorId));
                break;
            case 8:
                onShowFieldUserCardOrIsManger(userId);
                break;
            case 9:
                isResume = false;
                if (UserLoginUtil.checkLogin(this)) return;
                liveViewModel.reload(false);
                break;
        }
    }

    public void setOpenGiftDialog() {
        if (ObjectUtils.isEmpty(mRoomNo)) return;
        giftSheetFragment.show(getSupportFragmentManager(), "gift");
    }

    private void showFieldMoreDialog() {
        new ShowFieldMoreDialog.Builder(this)
                .setListener(new ShowFieldMoreDialog.OnListener() {
                    @Override
                    public void onShowMission(BaseDialog dialog) {

                    }

                    @Override
                    public void onShowClear(BaseDialog dialog) {
                        mController.doClearScreen();
                        dialog.dismiss();
                    }

                    @Override
                    public void onShareReport(BaseDialog dialog) {
                        dialog.dismiss();
                        showReportDialog();
                    }
                })
                .show();
    }

    @Override
    public void showFieldPrepared(int[] videoSize) {
        int mScreenWide = videoSize[0];
        int mScreenHeight = videoSize[1];

        Log.d("zzyip", "======mScreenWide====: " + mScreenWide);
        Log.d("zzyip", "======mScreenHeight====: " + mScreenHeight);

        mVideoView.setScreenScaleType(mScreenWide > mScreenHeight ? VideoView.SCREEN_SCALE_16_9 : VideoView.SCREEN_SCALE_CENTER_CROP);
        mVideoView.setPlayerBackgroundColor(getResources().getColor(R.color.transparent));
        mController.setIsShowFullIcon(mScreenWide > mScreenHeight);
        if (mScreenWide > mScreenHeight) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mVideoView.mRenderView.getView().getLayoutParams();
            int bottom = this.mScreenHeight / 15;
            Log.d("zzyip", "-------bottom------: " + bottom);
            lp.setMargins(0, 0, 0, SystemUtils.dp2px(this, bottom));
            mVideoView.mRenderView.getView().setLayoutParams(lp);
        } else if (mScreenWide < mScreenHeight) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mVideoView.mRenderView.getView().getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            mVideoView.mRenderView.getView().setLayoutParams(lp);
        }
    }

    public interface SimCallBack {
        void onComplete();
    }

    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth() + 10;
            if (event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }

//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
//                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
//                return false;
//            } else {
//                return true;
//            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void showFieldStopScroll(boolean isStop) {
        Log.d("zzy", "--------isStop-------" + isStop);
        layoutManager.setCanVerticalScroll(isStop);
        mSrlShowField.setEnableLoadMore(isStop);
    }

    @Override
    public void onShowFieldUserCardOrIsManger(String userId) {
        isRemaindFocus = false;
        mUserId = SPUtils.getInstance("user").getString(Constants.USER_ID);
        Log.d("zzy", "-----mUserType----: " + mUserType);
        if (!ObjectUtils.isEmpty(userId)) {
            this.mCurrentClick = Integer.parseInt(userId);
            if (userId.equals(mUserId)) {
                //点击自己
                isClickSelf = true;
                Log.d("zzy", "------点击了自己--------");
                reloadUserCard(new UserIdRoomNoParam(Integer.parseInt(userId),
                        mRoomNo));
            } else {
                isClickSelf = false;
                if (mUserType == 3) {
                    // 是本直播间的主播
                    reloadUserCard(new UserIdRoomNoParam(Integer.parseInt(userId),
                            mRoomNo));
                } else {
                    if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                        reloadIsRoomManager(Integer.parseInt(mUserId), mRoomNo);
                    } else {
                        reloadUserCard(new UserIdRoomNoParam(Integer.parseInt(userId),
                                mRoomNo));
                    }
                }
            }
        }
    }

    private void reloadIsRoomManager(int userId, String mRoomNo) {
        if (ObjectUtils.isEmpty(userId)) return;
        liveViewModel.loadIsRoomManager(new UserIdRoomNoParam(userId, mRoomNo));
    }

    @Override
    public void onShowFullGift() {
        // 请求用户余额
        //liveViewModel.reLoadUserCoins(false);
    }

    @Override
    public void onToggleScreen(boolean screenMode) {
        toggleScreen(screenMode);
    }

    public void toggleScreen(boolean isFull) {
        if (!isFull && "windows".equals(mAdapter.getData().get(mCurPos).getPlatform())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mVideoView.mRenderView.getView().getLayoutParams();
            int bottom = ScreenUtils.getScreenHeight() / 15;
            lp.setMargins(0, 0, 0, SystemUtils.dp2px(this, bottom));
            mVideoView.mRenderView.getView().setLayoutParams(lp);
        } else {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mVideoView.mRenderView.getView().getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            mVideoView.mRenderView.getView().setLayoutParams(lp);
        }
    }

    @Override
    public void onUpdateUserCoins(long coins) {
        this.mCoins = coins;
        mController.setCoins(coins);
    }

    @Override
    public void message(MQTTBean mqttBean) {
        mUserId = SPUtils.getInstance("user").getString(Constants.USER_ID);
        switch (mqttBean.getCmd()) {
            case SEND_CHAT_MESSAGE: //聊天
                ChatResponse msg = new Gson().fromJson(mqttBean.getText(), ChatResponse.class);
                ChatMessage chatMessage = new ChatMessage();

                int userType = msg.getUser().getUserType();
                chatMessage.setUserId(msg.getUser().getUserId());
                chatMessage.setNickName(msg.getUser().getNickName());
                chatMessage.setCertifiedAnchorStatus(msg.getUser().getCertifiedAnchorStatus());
                chatMessage.setContent(msg.getContent());
                chatMessage.setAnthorId(mAnchorId);
                chatMessage.setSupportTeamLogo(msg.getUser().supportTeamLogo);

                String userLevel = msg.getUser().getUserLevel();
                String anchorLevel = msg.getUser().getAnchorLevel();

                if (!"0".equals(userLevel) && !ObjectUtils.isEmpty(userLevel)) {
                    chatMessage.setUserLevel(userLevel);
                } else {
                    chatMessage.setUserLevel("1");
                }

                if (!"0".equals(anchorLevel) && !ObjectUtils.isEmpty(anchorLevel)) {
                    chatMessage.setAnchorLevel(anchorLevel);
                } else {
                    chatMessage.setAnchorLevel("1");
                }

                if (userType == 0) {
                    chatMessage.setMessageType(0);
                } else if (userType == 3) {
                    chatMessage.setMessageType(0);
                } else {
                    chatMessage.setMessageType(1);
                }

                mController.setChatData(chatMessage);
                mController.sendBarrage(false, msg.getContent());

                break;
            case ENTER_CHAT: //用户进入房间
                ChatResponse mMsgJoin = new Gson().fromJson(mqttBean.getText(), ChatResponse.class);
                ChatMessage mJoinMsg = new ChatMessage();
                mJoinMsg.setUserId(mMsgJoin.getUser().getUserId());
                mJoinMsg.setNickName(mMsgJoin.getUser().getNickName());
                mJoinMsg.setAnthorId(mAnchorId);
                mJoinMsg.setSupportTeamLogo(mMsgJoin.getUser().supportTeamLogo);
                mJoinMsg.setMessageType(6);

                mController.setChatData(mJoinMsg);

//                if (mMsgJoin.getIsShowAll() == 1) {
//                    mChatAdapter.addData(mJoinMsg);
//                    mChatAdapter.notifyDataSetChanged();
//                    mRvChat.scrollToPosition(mChatList.size() - 1);
//                } else if (mMsgJoin.getUser().getUserId().equals(mUserId)) {
//                    mChatAdapter.addData(mJoinMsg);
//                    mChatAdapter.notifyDataSetChanged();
//                    mRvChat.scrollToPosition(mChatList.size() - 1);
//                }

                break;
            case FOLLOW_ANCHOR://关注主播
                ChatResponse mMsgFocus = new Gson().fromJson(mqttBean.getText(), ChatResponse.class);
                ChatMessage mFocusMsg = new ChatMessage();
                mFocusMsg.setNickName(mMsgFocus.getUser().getNickName());

                String userLevelFollow = mMsgFocus.getUser().getUserLevel();
                String anchorLevelFollow = mMsgFocus.getUser().getAnchorLevel();

                if (!"0".equals(userLevelFollow) && !ObjectUtils.isEmpty(userLevelFollow)) {
                    mFocusMsg.setUserLevel(userLevelFollow);
                } else {
                    mFocusMsg.setUserLevel("1");
                }

                if (!"0".equals(anchorLevelFollow) && !ObjectUtils.isEmpty(anchorLevelFollow)) {
                    mFocusMsg.setAnchorLevel(anchorLevelFollow);
                } else {
                    mFocusMsg.setAnchorLevel("1");
                }

                mFocusMsg.setUserId(mMsgFocus.getUser().getUserId());
                mFocusMsg.setAnthorId(mAnchorId);
                mFocusMsg.setCertifiedAnchorStatus(mMsgFocus.getUser().getCertifiedAnchorStatus());
                mFocusMsg.setSupportTeamLogo(mMsgFocus.getUser().supportTeamLogo);

                int userTypeFocus = mMsgFocus.getUser().getUserType();

                if (userTypeFocus == 0) {
                    mFocusMsg.setMessageType(10);
                } else {
                    mFocusMsg.setMessageType(11);
                }

                mController.setChatData(mFocusMsg);

//                mChatAdapter.addData(mFocusMsg);
//                mChatAdapter.notifyDataSetChanged();
//                mRvChat.scrollToPosition(mChatList.size() - 1);

                break;
            case BAN_TALK://主播禁言
                BanTalkCmd mBanTalkMsg = new Gson().fromJson(mqttBean.getText(), BanTalkCmd.class);
                ChatMessage mBanTalkChat = new ChatMessage();
                mBanTalkChat.setNickName(mBanTalkMsg.getUser().getNickName());
                mBanTalkChat.setUserId(mBanTalkMsg.getUser().getUserId());
                mBanTalkChat.setAnthorId(mAnchorId);
                //mBanTalkChat.setSupportTeamLogo(mBanTalkMsg.getUser().getSupportTeamLogo());

                if (mBanTalkMsg.getUser().getUserId().equals(mUserId)) {
                    mBanTalkChat.setMessageType(5);
                    mBanTalkChat.setContent("你已被主播禁言" + mBanTalkMsg.getTime() + "分钟");

                } else {
                    mBanTalkChat.setContent("被主播禁言" + mBanTalkMsg.getTime() + "分钟");
                    mBanTalkChat.setMessageType(4);
                }

                mController.setChatData(mBanTalkChat);

//                mTvChatMsgSmall.setFocusable(false);
//
//
//                mChatAdapter.addData(mBanTalkChat);
//                mChatAdapter.notifyDataSetChanged();
//                mRvChat.scrollToPosition(mChatList.size() - 1);

                break;
            case COMMISSION_ROOM_MANAGER://任命房管
                CommissionManageCmd mManageCmd = new Gson().fromJson(mqttBean.getText(),
                        CommissionManageCmd.class);

                ChatMessage mManageChat = new ChatMessage();
                mManageChat.setNickName(mManageCmd.getUser().getNickName());
                mManageChat.setUserId(mManageCmd.getUser().getUserId());
                mManageChat.setAnthorId(mAnchorId);
                mManageChat.setContent("被任命房管");
                //mManageChat.setSupportTeamLogo(mManageCmd.getUser().getSupportTeamLogo());
                mManageChat.setMessageType(4);

                mController.setChatData(mManageChat);

//                mChatAdapter.addData(mManageChat);
//                mChatAdapter.notifyDataSetChanged();
//                mRvChat.scrollToPosition(mChatList.size() - 1);

                break;
            case DISMISS_ROOM_MANAGER://撤销房管
                CommissionManageCmd mDisManageCmd = new Gson().fromJson(mqttBean.getText(),
                        CommissionManageCmd.class);

                ChatMessage mDisManageChat = new ChatMessage();
                mDisManageChat.setNickName(mDisManageCmd.getUser().getNickName());
                mDisManageChat.setUserId(mDisManageCmd.getUser().getUserId());
                mDisManageChat.setAnthorId(mAnchorId);
                mDisManageChat.setContent("被解任房管身份");
                //mDisManageChat.setSupportTeamLogo(mDisManageCmd.getUser().getSupportTeamLogo());
                mDisManageChat.setMessageType(4);
                mController.setChatData(mDisManageChat);

//                mChatAdapter.addData(mDisManageChat);
//                mChatAdapter.notifyDataSetChanged();
//                mRvChat.scrollToPosition(mChatList.size() - 1);

                break;
            case GIVE_GIFT://送礼
                GiftMessage giftMsg = new Gson().fromJson(mqttBean.getText(), GiftMessage.class);
                ChatMessage giftMessage = new ChatMessage();
                GiftResponse giftResponse = new GiftResponse();

                int giftUserType = giftMsg.getUser().getUserType();
                giftMessage.setUserId(giftMsg.getUser().getUserId());
                giftMessage.setNickName(giftMsg.getUser().getNickName());
                giftMessage.setAvatar(giftMsg.getUser().getAvatar());
                giftMessage.setCertifiedAnchorStatus(giftMsg.getUser().getCertifiedAnchorStatus());
                giftMessage.setGiftNum(giftMsg.getGift().getQuantity());
                giftMessage.setGiftName(giftMsg.getGift().getName());
                giftMessage.setGiftIcon(giftMsg.getGift().getIcon());
                giftMessage.setAnthorId(mAnchorId);
                //giftMessage.setSupportTeamLogo(giftMsg.getUser().getSupportTeamLogo());

                String userLevelGift = giftMsg.getUser().getUserLevel();
                String anchorLevelGift = giftMsg.getUser().getAnchorLevel();

                if (!"0".equals(userLevelGift) && !ObjectUtils.isEmpty(userLevelGift)) {
                    giftMessage.setUserLevel(userLevelGift);
                } else {
                    giftMessage.setUserLevel("1");
                }

                if (!"0".equals(anchorLevelGift) && !ObjectUtils.isEmpty(anchorLevelGift)) {
                    giftMessage.setAnchorLevel(anchorLevelGift);
                } else {
                    giftMessage.setAnchorLevel("1");
                }

                if (giftUserType == 0) {
                    giftMessage.setMessageType(2);
                } else {
                    giftMessage.setMessageType(3);
                }

                giftResponse.setDefaultGraphUrl(giftMsg.getGift().getIcon());
                giftResponse.setGiftName(giftMsg.getGift().getName());
                giftResponse.setTheGiftId(giftMsg.getGift().getGiftId());
                giftResponse.setTheSendGiftSize(giftMsg.getGift().getQuantity());
                giftResponse.setTheUserId(Integer.parseInt(giftMsg.getUser().getUserId()));
                giftResponse.setUserAvatar(giftMsg.getUser().getAvatar());
                giftResponse.setUserName(giftMsg.getUser().getNickName());
                giftResponse.setGiftType(giftMsg.getGift().getGiftType());
                giftResponse.setPlayEffectUrl(giftMsg.getGift().getPlayEffectUrl());
                giftResponse.setFloatingStatus(giftMsg.getGift().getFloatingStatus());
                giftResponse.setDynamicGraphUrl(giftMsg.getGift().getDynamicGraphUrl());
                giftResponse.setDoubleHit(giftMsg.getGift().getDoubleHit());

                Log.d("LiveGiftDou", "---人名---: " + giftResponse.getUserName() + "礼物数" + giftResponse.getTheSendGiftSize());

                if (giftMsg.getGift().getDoubleHit() == 2) {
                    if (giftMsg.getGift().getGiftType() == 2) {
                        if (!giftMsg.getAnchor().getUid().
                                equals(mRoomNo) && giftMsg.getGift().getFloatingStatus() == 1) {

                            final BaseSyncTask task1 = new BaseSyncTask() {
                                @Override
                                public void doTask() {
                                    SimCallBack cb = new SimCallBack() {
                                        @Override
                                        public void onComplete() {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
//                                                        mMtvChatNotice.setVisibility(View.VISIBLE);
//
//                                                        showChatGiftMarquee(giftMsg.getAnchor().getUid(), giftMsg.getUser().getNickName(),
//                                                                giftMsg.getAnchor().getNickName(),
//                                                                giftMsg.getUser().getUserLevel(),
//                                                                giftMsg.getGift().getName(),
//                                                                giftMsg.getGift().getQuantity());

                                                    mController.showMarquee(giftMsg, mRoomNo);

                                                    TinySyncExecutor.getInstance().finish();
                                                }
                                            }, 4000);

                                        }
                                    };
                                    cb.onComplete();
                                }
                            };

                            TinySyncExecutor.getInstance().enqueue(task1);

                        } else {

                            if (giftMsg.getGift().getFloatingStatus() == 1) {

                                final BaseSyncTask task1 = new BaseSyncTask() {
                                    @Override
                                    public void doTask() {
                                        SimCallBack cb = new SimCallBack() {
                                            @Override
                                            public void onComplete() {
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
//                                                            mMtvChatNotice.setVisibility(View.VISIBLE);
//
//                                                            showChatGiftMarquee(giftMsg.getAnchor().getUid(), giftMsg.getUser().getNickName(),
//                                                                    giftMsg.getAnchor().getNickName(),
//                                                                    giftMsg.getUser().getUserLevel(),
//                                                                    giftMsg.getGift().getName(),
//                                                                    giftMsg.getGift().getQuantity());

                                                        mController.showMarquee(giftMsg, mRoomNo);
                                                        TinySyncExecutor.getInstance().finish();
                                                    }
                                                }, 4000);

                                            }
                                        };
                                        cb.onComplete();
                                    }
                                };

                                TinySyncExecutor.getInstance().enqueue(task1);
                            }

                            //mOnOpenSvgaListener.onOpenSvga(giftMsg);
                            mController.setSvgaGift(giftMsg);

                            mController.setChatData(giftMessage);
                        }
                    }
                    if (giftMsg.getGift().getGiftType() == 1) {
                        if (!giftMsg.getAnchor().getUid().
                                equals(mRoomNo) && giftMsg.getGift().getFloatingStatus() == 1) {

                            final BaseSyncTask task1 = new BaseSyncTask() {
                                @Override
                                public void doTask() {
                                    SimCallBack cb = new SimCallBack() {
                                        @Override
                                        public void onComplete() {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
//                                                        mMtvChatNotice.setVisibility(View.VISIBLE);
//
//                                                        showChatGiftMarquee(giftMsg.getAnchor().getUid(), giftMsg.getUser().getNickName(),
//                                                                giftMsg.getAnchor().getNickName(),
//                                                                giftMsg.getUser().getUserLevel(),
//                                                                giftMsg.getGift().getName(),
//                                                                giftMsg.getGift().getQuantity());
                                                    mController.showMarquee(giftMsg, mRoomNo);
                                                    TinySyncExecutor.getInstance().finish();
                                                }
                                            }, 4000);

                                        }
                                    };
                                    cb.onComplete();
                                }
                            };

                            TinySyncExecutor.getInstance().enqueue(task1);

                        } else {
                            mController.showRewardLayout(giftResponse);
                            if (giftMsg.getGift().getFloatingStatus() == 1) {
                                final BaseSyncTask task1 = new BaseSyncTask() {
                                    @Override
                                    public void doTask() {
                                        SimCallBack cb = new SimCallBack() {
                                            @Override
                                            public void onComplete() {
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
//                                                            mMtvChatNotice.setVisibility(View.VISIBLE);
//
//                                                            showChatGiftMarquee(giftMsg.getAnchor().getUid(), giftMsg.getUser().getNickName(),
//                                                                    giftMsg.getAnchor().getNickName(),
//                                                                    giftMsg.getUser().getUserLevel(),
//                                                                    giftMsg.getGift().getName(),
//                                                                    giftMsg.getGift().getQuantity());
                                                        mController.showMarquee(giftMsg, mRoomNo);
                                                        TinySyncExecutor.getInstance().finish();


                                                    }
                                                }, 4000);

                                            }
                                        };
                                        cb.onComplete();
                                    }
                                };

                                TinySyncExecutor.getInstance().enqueue(task1);
                            }

                            mController.setChatData(giftMessage);
                        }
                    }
                }

                if (giftMsg.getGift().getDoubleHit() == 1) {
                    if (giftMsg.getAnchor().getUid().
                            equals(mRoomNo)) {
                        mController.showRewardLayout(giftResponse);
                    }
                }

                break;
//                case GUESS_BEGIN://预言开始
//                    ProphesyStart start = new Gson().fromJson(mqttBean.getText(), ProphesyStart.class);
//                    //startTimeLoad(start.getGuessList().get(0).getTitle());
//                    break;
//                case GUESS_RESULT://预言结束
//                    ProphesyResult result = new Gson().fromJson(mqttBean.getText(), ProphesyResult.class);
//                    //reloadProphesyResult(new TopicItemParam(Integer.parseInt(result.getItemId())));
//                    break;
//                case SHARE_LIVE://分享直播间
//                    ShareCmd mShareCmd = new Gson().fromJson(mqttBean.getText(), ShareCmd.class);
//                    ChatMessage chatShare = new ChatMessage();
//
//                    chatShare.setNickName(mShareCmd.getUser().getNickName());
//                    chatShare.setUserLevel(mShareCmd.getUser().getUserLevel());
//                    chatShare.setAnchorLevel(mShareCmd.getUser().getAnchorLevel());
//                    chatShare.setAnthorId(mAnchorId);
//                    chatShare.setCertifiedAnchorStatus(mShareCmd.getUser().getCertifiedAnchorStatus());
//                    chatShare.setSupportTeamLogo(mShareCmd.getUser().getSupportTeamLogo());
//
//                    String userLevelShare = mShareCmd.getUser().getUserLevel();
//                    String anchorLevelShare = mShareCmd.getUser().getAnchorLevel();
//
//                    if (!"0".equals(userLevelShare) && !ObjectUtils.isEmpty(userLevelShare)) {
//                        chatShare.setUserLevel(userLevelShare);
//                    } else {
//                        chatShare.setUserLevel("1");
//                    }
//
//                    if (!"0".equals(anchorLevelShare) && !ObjectUtils.isEmpty(anchorLevelShare)) {
//                        chatShare.setAnchorLevel(anchorLevelShare);
//                    } else {
//                        chatShare.setAnchorLevel("1");
//                    }
//
//                    if (mShareCmd.getUser().getUserType() == 0) {
//                        chatShare.setMessageType(9);
//                    } else {
//                        chatShare.setMessageType(12);
//                    }
//
//                    postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                        mChatAdapter.addData(chatShare);
////                        mChatAdapter.notifyDataSetChanged();
////                        mRvChat.scrollToPosition(mChatList.size() - 1);
//                        }
//                    }, 2000);
//
//                    break;
            case GIFT_DOUBLE:

                GiftDoubleCmd giftDoubleCmd = new Gson().fromJson(mqttBean.getText(), GiftDoubleCmd.class);
                ChatMessage giftDoubleMessage = new ChatMessage();

                int giftDoubleUserType = giftDoubleCmd.getUser().getUserType();
                giftDoubleMessage.setUserId(giftDoubleCmd.getUser().getUserId());
                giftDoubleMessage.setNickName(giftDoubleCmd.getUser().getNickName());
                giftDoubleMessage.setAvatar(giftDoubleCmd.getUser().getAvatar());
                giftDoubleMessage.setCertifiedAnchorStatus(giftDoubleCmd.getUser().getCertifiedAnchorStatus());
                giftDoubleMessage.setGiftNum(giftDoubleCmd.getNum());
                giftDoubleMessage.setGiftName(giftDoubleCmd.getGift().getName());
                giftDoubleMessage.setGiftIcon(giftDoubleCmd.getGift().getIcon());
                giftDoubleMessage.setAnthorId(mAnchorId);
                //giftDoubleMessage.setSupportTeamLogo(giftDoubleCmd.getUser().getSupportTeamLogo());

                String userLevelGiftD = giftDoubleCmd.getUser().getUserLevel();
                String anchorLevelGiftD = giftDoubleCmd.getUser().getAnchorLevel();

                if (!"0".equals(userLevelGiftD) && !ObjectUtils.isEmpty(userLevelGiftD)) {
                    giftDoubleMessage.setUserLevel(userLevelGiftD);
                } else {
                    giftDoubleMessage.setUserLevel("1");
                }

                if (!"0".equals(anchorLevelGiftD) && !ObjectUtils.isEmpty(anchorLevelGiftD)) {
                    giftDoubleMessage.setAnchorLevel(anchorLevelGiftD);
                } else {
                    giftDoubleMessage.setAnchorLevel("1");
                }

                if (giftDoubleUserType == 0) {
                    giftDoubleMessage.setMessageType(2);
                } else {
                    giftDoubleMessage.setMessageType(3);
                }

                if (giftDoubleCmd.getAnchor().getUid().equals(mRoomNo)) {
                    if (giftDoubleCmd.getGift().getFloatingStatus() == 1) {
                        final BaseSyncTask task1 = new BaseSyncTask() {
                            @Override
                            public void doTask() {
                                SimCallBack cb = new SimCallBack() {
                                    @Override
                                    public void onComplete() {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
//                                            mMtvChatNotice.setVisibility(View.VISIBLE);
//                                            showChatGiftMarquee(giftDoubleCmd.getAnchor().getUid(), giftDoubleCmd.getUser().getNickName(),
//                                                    giftDoubleCmd.getAnchor().getNickName(),
//                                                    giftDoubleCmd.getUser().getUserLevel(),
//                                                    giftDoubleCmd.getGift().getName(),
//                                                    giftDoubleCmd.getNum());

                                                mController.showMarquee(giftDoubleCmd, mRoomNo);

                                                TinySyncExecutor.getInstance().finish();
                                            }
                                        }, 4000);

                                    }
                                };
                                cb.onComplete();
                            }
                        };
                        TinySyncExecutor.getInstance().enqueue(task1);
                    }

                    mController.setChatData(giftDoubleMessage);

                } else {
                    if (giftDoubleCmd.getGift().getFloatingStatus() == 1) {
                        final BaseSyncTask task1 = new BaseSyncTask() {
                            @Override
                            public void doTask() {
                                SimCallBack cb = new SimCallBack() {
                                    @Override
                                    public void onComplete() {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
//                                            mMtvChatNotice.setVisibility(View.VISIBLE);
//                                            showChatGiftMarquee(giftDoubleCmd.getAnchor().getUid(), giftDoubleCmd.getUser().getNickName(),
//                                                    giftDoubleCmd.getAnchor().getNickName(),
//                                                    giftDoubleCmd.getUser().getUserLevel(),
//                                                    giftDoubleCmd.getGift().getName(),
//                                                    giftDoubleCmd.getNum());
                                                mController.showMarquee(giftDoubleCmd, mRoomNo);
                                                TinySyncExecutor.getInstance().finish();
                                            }
                                        }, 4000);

                                    }
                                };
                                cb.onComplete();
                            }
                        };
                        TinySyncExecutor.getInstance().enqueue(task1);
                    }
                }

                if (mController.isFullScreen()) {
                    //mOnSendGiftDoubleInScreenListener.sendGift(giftDoubleCmd);
                }

                break;
//                case RED_LIVE://直播间发的红包
//                    if (mViewModel.getIsOn() != 1) return;
//                    parseRedInfo(mqttBean);
//                    break;
//                case GRAB_RED_PACKET://直播间领的红包
//                    if (mViewModel.getIsOn() != 1) return;
//                    parseRedGrabInfo(mqttBean);
//                    break;
//                case PK_SCORE://直播间礼物PK积分增加
//                    PKScoreCmd pkScoreCmd = new Gson().fromJson(mqttBean.getText(), PKScoreCmd.class);
//                    //livePKScoreAnimate.startAnimate(pkScoreCmd);
//                    break;
//                case LIVE_ROOM_BLOOD_RAIN://红包雨
//                    RedRainCmd redRainCmd = new Gson().fromJson(mqttBean.getText(), RedRainCmd.class);
////                mRedRainList.add(redRainCmd);
////                if (!isShowRedRain) {
////                    showRedRainCountDialog();
////                    isShowRedRain = true;
////                }
            //break;
            case OFFLINE:
                mVideoView.release();
                mController.showPlayerOff();
                break;
            case ONLINE:
                if (mRoomType == 3) {
                    reloadUrls(new RoomNoMatchIdParam(mRoomNo, mMatchId));
                } else {
                    reloadPullUrls(new RoomNoParam(mRoomNo));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSelectUserCard(String userId) {
        onShowFieldUserCardOrIsManger(userId);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageNum++;
        reloadRec(mPageNum);
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    BroadcastReceiver netReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type2 = networkInfo.getType();
                    String typeName = networkInfo.getTypeName();
                    Log.d("zzyip", "-------net--name-----: " + typeName + "----type---: " + type2);
                    switch (type2) {
                        //移动 网络    2G 3G 4G 都是一样的 实测 mix2s 联通卡
                        case 0:
                            //wifi网络
                        case 1:
                            //网线连接
                        case 9:
                            mVideoView.replay(false);
                            break;
                    }
                } else {// 无网络

                }
            }
        }
    };

    //是否resume
    private boolean isResume;

    @Override
    protected void onPause() {
        super.onPause();
        isResume = true;
        if (mVideoView != null) {
            mVideoView.pause();
        }
        mController.onPauseReward();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
        mController.onResumeReward();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
        //取消订阅
        MqttService.unSubscribeTopic(topicUrls);//订阅主题，参数：主题、服务质量
        TinySyncExecutor.getInstance().remove();

        mFocusHandler.removeCallBackMessages();
        mLiveAudienceHandler.removeCallBackMessages();

        if (netReceiver != null) {
            unregisterReceiver(netReceiver);
            netReceiver = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (mVideoView == null || !mVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }


    private void startPlay(String url) {
        View itemView = mRvShowField.getChildAt(0);
        BaseViewHolder viewHolder = (BaseViewHolder) itemView.getTag();
        mVideoView.release();
        Utils.removeViewFormParent(mVideoView);
        String playUrl = PreloadManager.getInstance(this).getPlayUrl(url);
        Log.d(TAG, "startPlay: " + "  url: " + playUrl);
        mVideoView.setUrl(playUrl);
        ((FrameLayout) viewHolder.getView(R.id.container)).addView(mVideoView, 0);
        mVideoView.start();
    }

    private void addVideoView() {
        View itemView = mRvShowField.getChildAt(0);
        BaseViewHolder viewHolder = (BaseViewHolder) itemView.getTag();
        mVideoView.release();
        Utils.removeViewFormParent(mVideoView);
        ((FrameLayout) viewHolder.getView(R.id.container)).addView(mVideoView, 0);
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return false;
    }

    @Override
    protected boolean isStatusBarEnabled() {
        return true;
    }
}
