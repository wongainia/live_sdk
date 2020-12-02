package com.lib.showfield.ui.view.tiktalk;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lib.showfield.R;
import com.lib.showfield.aop.SingleClick;
import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.bean.PatchAdBean;
import com.lib.showfield.bean.cmds.GiftMessage;
import com.lib.showfield.bean.gift.GiftDoubleCmd;
import com.lib.showfield.bean.gift.anim.AnimUtils;
import com.lib.showfield.bean.gift.anim.NumAnim;
import com.lib.showfield.common.Constants;
import com.lib.showfield.helper.KeyboardUtils;
import com.lib.showfield.http.glide.GlideApp;
import com.lib.showfield.helper.AddCustomViewHelper;
import com.lib.showfield.http.respones.live.ChatMessage;
import com.lib.showfield.http.respones.live.GiftResponse;
import com.lib.showfield.http.respones.live.LivePlayerInfo;
import com.lib.showfield.interfaces.AddCustomViewListener;
import com.lib.showfield.interfaces.OnClickBarrageListener;
import com.lib.showfield.interfaces.OnClickDoubleListener;
import com.lib.showfield.interfaces.OnClickFullChatListener;
import com.lib.showfield.interfaces.OnClickGiftListener;
import com.lib.showfield.interfaces.OnClickProjectionListener;
import com.lib.showfield.interfaces.OnClickReportListener;
import com.lib.showfield.interfaces.OnClickSendMsgListener;
import com.lib.showfield.interfaces.OnItemClickNickNameListener;
import com.lib.showfield.interfaces.OnJumpOtherLiveByRoomNoListener;
import com.lib.showfield.interfaces.OnSendGiftClickListener;
import com.lib.showfield.interfaces.OnShowFieldItemClickListener;
import com.lib.showfield.interfaces.OnShowFieldPreparedListener;
import com.lib.showfield.interfaces.OnShowFieldStopScrollListener;
import com.lib.showfield.interfaces.OnShowFieldUserCardOrIsMangerListener;
import com.lib.showfield.interfaces.OnShowOpenFullGiftListener;
import com.lib.showfield.interfaces.OnToggleListener;
import com.lib.showfield.interfaces.OnToggleScreenListener;
import com.lib.showfield.other.KeyboardWatcher;
import com.lib.showfield.ui.activty.EasyWebActivity;
import com.lib.showfield.ui.adapter.GiftFullAdapter;
import com.lib.showfield.ui.adapter.HomeBannerPatchAdapter;
import com.lib.showfield.ui.adapter.LiveAudienceAdapter;
import com.lib.showfield.ui.adapter.ShowFieldChatAdapter;
import com.lib.showfield.ui.fragment.PatchAdBannerViewHolder;
import com.lib.showfield.ui.view.HorizontalRecyclerViewV2;
import com.lib.showfield.ui.view.clearScreen.ClearScreenLayout;
import com.lib.showfield.ui.view.clearScreen.SlideDirection;
import com.lib.showfield.ui.view.widget.RegexEditText;
import com.lib.showfield.ui.view.widget.RewardLayout;
import com.lib.showfield.utils.FindDrawableByName;
import com.lib.showfield.utils.SystemUtils;
import com.lib.showfield.utils.UserLoginUtil;
import com.lib.showfield.utils.XFrameAnimation;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.indicator.DrawableIndicator;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import video.dkplayer_java.controller.GestureVideoController;
import video.dkplayer_java.player.VideoView;
import video.dkplayer_java.player.VideoViewManager;
import video.dkplayer_java.util.L;
import video.dkplayer_java.util.PlayerUtils;
import video.dkplayer_ui.component.GestureView;
import video.dkplayer_ui.component.MyDanmakuView;
import video.dkplayer_ui.component.TitleView;
import video.dkplayer_ui.utils.CenterSpaceImageSpan;

import static android.view.inputmethod.EditorInfo.IME_FLAG_NO_ENTER_ACTION;
import static com.blankj.utilcode.util.ResourceUtils.getDrawable;
import static com.lib.showfield.common.Constants.ANDROID3X16X16CURRENCYMONEYICONURL;
import static com.lib.showfield.common.Constants.COMMON_RESOURCE;
import static com.lib.showfield.common.Constants.CURRENCYMONEYNAME;
import static com.lib.showfield.common.Constants.PAYSWITCH;

public class ShowFieldLiveController extends GestureVideoController
        implements View.OnClickListener, TextView.OnEditorActionListener,
        KeyboardWatcher.SoftKeyboardStateListener, Animator.AnimatorListener,
        OnItemClickNickNameListener, OnClickGiftListener, OnClickBarrageListener,
        OnClickFullChatListener, OnClickSendMsgListener, OnClickReportListener,
        OnClickProjectionListener, AddCustomViewListener {

    private int softKeyboardHeight;

    private RelativeLayout mRlClearScreen;
    private ClearScreenLayout clearScreenLayout;

    private AppCompatImageView mIvMore;
    private AppCompatImageView mIvRed;
    private AppCompatImageView mIvGift;
    private AppCompatImageView mIvClose;
    private AppCompatImageView mIvFullIcon;

    private RelativeLayout mRlOpenChat;
    private RegexEditText mEdtOpenChat;
    private RegexEditText mEdtCloseChat;
    private LinearLayoutCompat mLlContribute;

    private ChildRecyclerView mRvChat;
    private ShowFieldChatAdapter mChatAdapter;
    private List<ChatMessage> mChatData;

    protected OnShowFieldItemClickListener mOnShowFieldItemClickListener;
    protected OnClickSendMsgListener mOnClickSendMsgListener;
    protected OnSendGiftClickListener mOnSendGiftClickListener;
    protected OnClickDoubleListener mOnClickDoubleListener;
    protected OnShowFieldUserCardOrIsMangerListener mOnShowFieldUserCardOrIsMangerListener;
    protected OnToggleScreenListener mOnToggleScreenListener;
    protected OnShowFieldStopScrollListener mOnShowFieldStopScrollListener;
    protected OnJumpOtherLiveByRoomNoListener mOnJumpOtherLiveByRoomNoListener;
    protected OnShowOpenFullGiftListener mOnShowOpenFullGiftListener;
    protected OnClickReportListener mOnClickReportListener;
    protected OnClickProjectionListener mOnClickProjectionListener;
    protected OnShowFieldPreparedListener mOnShowFieldPreparedListener;

    private AppCompatImageView mLottieView;
    private XFrameAnimation loadingDrawable;
    private AppCompatTextView mMtvChatNotice;

    private RelativeLayout mRlPlayerInfo;
    private AppCompatImageView mIvAvatar;
    private AppCompatTextView mTvNickname;
    private AppCompatTextView mTvHotNum;
    private AppCompatTextView mTvLiveNum;
    private AppCompatTextView mTvRoomNo;
    //private AppCompatTextView mTvFocusPlayer;
    private RelativeLayout mRlNoPull;

    private HorizontalRecyclerViewV2 mRvUserLive;
    private LiveAudienceAdapter mAudienceAdapter;
    private ArrayList<LiveAudienceBean.ListBean> mAudiences;

    //3秒内点击的次数
    private long firstTime = 0;
    private Timer delayTimer;
    private Handler handler;
    private TimerTask task;
    private int mDoubleClickCount = 0;
    private long interval = 3000;

    //用户ID
    private String mUserId;
    private long mCoins;
    private String mGiftId;
    private long mGiftPrice;
    private boolean isDoubled;

    //礼物相关
    private RewardLayout rewardLayout;
    private RewardLayout rewardLayoutFull;
    private String mAnchorNickName;
    private String mRoomNo;
    //跑马灯动画
    private Animation animation;
    private SVGAImageView mIvSvgSmall;
    private SVGAImageView mIvSvgFull;
    private SVGAParser svgaParser;
    private GiftMessage mGiftMessage;
    private HashMap<String, SVGAVideoEntity> mSvgaEntity = new HashMap<>();
    //所有的礼物数据
    private List<String> mGiftList;
    private AppCompatTextView mTvSvgaText;
    private AppCompatTextView mTvSvgaTextFull;

    protected TitleView titleView;
    protected ShowfieldLiveControlView liveControlView;
    protected MyDanmakuView mMyDanmakuView;
    protected GestureView gestureView;

    private BannerViewPager<PatchAdBean, PatchAdBannerViewHolder> mViewPager;
    private DrawableIndicator mIndicatorView;
    private RelativeLayout mGvd;

    //全屏礼物列表
    protected LinearLayout mPopupLayout;
    private RecyclerView mRvFullGift;
    private List<GiftResponse> mList;
    private GiftResponse mGiftResponse;
    private GiftFullAdapter mGiftFullAdapter;
    //横屏礼物余额
    private AppCompatTextView mTvFullCoins;
    private AppCompatTextView mSendGift;
    private AppCompatTextView mSendNoGift;
    private String moneyIcon;
    private AppCompatImageView mIvMoney;
    private LinearLayoutCompat mLlFullUpCoins;
    //当前选中的礼物下标
    private int mGiftCurrentIndex;
    //投屏状态
    public boolean isProjectionStatus;
    //锁屏键
    private ImageView mLockButton;

    private RegexEditText mEdtFullChat;

    //布局容器
    private RelativeLayout mRlContainer;

    public void setOnShowFieldItemClickListener(OnShowFieldItemClickListener listener) {
        this.mOnShowFieldItemClickListener = listener;
    }

    public void setOnClickSendMsgListener(OnClickSendMsgListener listener) {
        this.mOnClickSendMsgListener = listener;
    }

    public void setOnSendGiftClickListener(OnSendGiftClickListener listener) {
        this.mOnSendGiftClickListener = listener;
    }

    public void setOnClickDoubleListener(OnClickDoubleListener listener) {
        this.mOnClickDoubleListener = listener;
    }

    public void setOnShowFieldUserCardOrIsMangerListener(OnShowFieldUserCardOrIsMangerListener listener) {
        this.mOnShowFieldUserCardOrIsMangerListener = listener;
    }

    public void setOnToggleScreenListener(OnToggleScreenListener listener) {
        this.mOnToggleScreenListener = listener;
    }

    public void setOnShowFieldStopScrollListener(OnShowFieldStopScrollListener listener) {
        this.mOnShowFieldStopScrollListener = listener;
    }

    public void setOnJumpOtherLiveByRoomNoListener(OnJumpOtherLiveByRoomNoListener listener) {
        this.mOnJumpOtherLiveByRoomNoListener = listener;
    }

    public void setOnShowOpenFullGiftListener(OnShowOpenFullGiftListener listener) {
        this.mOnShowOpenFullGiftListener = listener;
    }

    public void setOnClickReportListener(OnClickReportListener listener) {
        this.mOnClickReportListener = listener;
    }

    public void setOnClickProjectionListener(OnClickProjectionListener mOnClickProjectionListener) {
        this.mOnClickProjectionListener = mOnClickProjectionListener;
    }

    public void setOnShowFieldPreparedListener(OnShowFieldPreparedListener listener) {
        this.mOnShowFieldPreparedListener = listener;
    }

    public ShowFieldLiveController(@NonNull Context context) {
        this(context, null);
        mActivity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
    }

    public ShowFieldLiveController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowFieldLiveController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.controller_showfield_live;
    }

    private int mScaledTouchSlop;
    private int mStartX, mStartY;

    /**
     * 解决点击和VerticalViewPager滑动冲突问题
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) event.getX();
                mStartY = (int) event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                int endX = (int) event.getX();
                int endY = (int) event.getY();
                if (Math.abs(endX - mStartX) < mScaledTouchSlop
                        && Math.abs(endY - mStartY) < mScaledTouchSlop) {
                    performClick();
                }
                break;
        }
        return false;
    }

    public void addDefaultControlComponent() {
        addControlComponent(new ShowFieldViewError(getContext()));//错误界面
        addControlComponent(new ShowFieldPrepareView(getContext()));
        mMyDanmakuView = new MyDanmakuView(getContext());
        titleView = new TitleView(getContext());
        liveControlView = new ShowfieldLiveControlView(getContext());
        gestureView = new GestureView(getContext());
        addControlComponent(mMyDanmakuView);
        setCanChangePosition(false);

        liveControlView.setOnClickGiftListener(this);
        liveControlView.setOnClickBarrageListener(this);
        liveControlView.setOnClickFullChatListener(this);
        liveControlView.setOnClickSendMsgListener(this);
        titleView.setOnClickReportListener(this);
        titleView.setOnClickProjectionListener(this);
    }

    @Override
    public void addCustomView(View view) {
        Log.d("zzyip", "--------add view--------");

        mRlContainer.addView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("HandlerLeak")
    @Override
    protected void initView() {
        super.initView();
        //键盘监听
        KeyboardWatcher.with(mActivity)
                .setListener(this);

        AddCustomViewHelper.getInstance().setAddCustomViewListener(this);

        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mList = new ArrayList<>();
        mChatData = new ArrayList<>();
        mGiftList = new ArrayList<>();
        mUserId = SPUtils.getInstance("user").getString(Constants.USER_ID);

        mIvMoney = findViewById(R.id.iv_full_money_icon);
        mLlFullUpCoins = findViewById(R.id.ll_full_up_coins);
        mEdtFullChat = findViewById(R.id.edt_show_field_full);
        mEdtFullChat.setOnEditorActionListener(this);

        //判断是否开启支付
        if (SPUtils.getInstance(COMMON_RESOURCE).getBoolean(PAYSWITCH, true)) {
            mLlFullUpCoins.setVisibility(VISIBLE);
        } else {
            mLlFullUpCoins.setVisibility(GONE);
        }

        moneyIcon = SPUtils.getInstance(COMMON_RESOURCE).getString(ANDROID3X16X16CURRENCYMONEYICONURL);
        Glide.with(getContext()).
                load(moneyIcon)
                .placeholder(R.drawable.icon_avatar)
                .circleCrop()
                .error(R.drawable.icon_avatar)
                .into(mIvMoney);

        mRlClearScreen = findViewById(R.id.rl_clear_screen);
        clearScreenLayout = findViewById(R.id.clear_screen);
        mRlNoPull = findViewById(R.id.rl_live_no_pull);

        mRlContainer = findViewById(R.id.rl_container);

        mIvMore = findViewById(R.id.item_iv_show_field_more);
        mIvRed = findViewById(R.id.item_iv_show_field_red);
        mIvGift = findViewById(R.id.item_iv_show_field_gift);
        mIvClose = findViewById(R.id.ll_show_field_close);
        mIvFullIcon = findViewById(R.id.iv_full_show_field);
        mIvFullIcon.setOnClickListener(this);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mIvFullIcon.getLayoutParams();
        int bottom = ScreenUtils.getScreenHeight() / 7;
        lp.setMargins(0, SystemUtils.dp2px(getContext(), bottom), 0, 0);
        mIvFullIcon.setLayoutParams(lp);

        mIvAvatar = findViewById(R.id.item_iv_show_field_avatar);
        mRlPlayerInfo = findViewById(R.id.rl_show_field_player_info);
        mRlPlayerInfo.setOnClickListener(this);
        mTvNickname = findViewById(R.id.item_iv_show_field_nickname);
        mTvHotNum = findViewById(R.id.item_iv_show_field_hotnum);
        mTvLiveNum = findViewById(R.id.item_iv_show_field_livenum);
        mTvLiveNum.setOnClickListener(this);
        mTvRoomNo = findViewById(R.id.item_iv_show_field_live_roomNo);
//        mTvFocusPlayer = findViewById(R.id.item_tv_show_field_focus);
//        mTvFocusPlayer.setOnClickListener(this);

        mRvUserLive = findViewById(R.id.hrv_user_live);
        LinearLayoutManager mRaceManager = new LinearLayoutManager(mActivity,
                RecyclerView.HORIZONTAL, false);
        mRvUserLive.setLayoutManager(mRaceManager);
        mAudiences = new ArrayList<>();
        mAudienceAdapter = new LiveAudienceAdapter(R.layout.item_live_audience, mAudiences);
        mRvUserLive.setAdapter(mAudienceAdapter);
        mAudienceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mOnShowFieldItemClickListener.onShowFieldItemClick(8, String.valueOf(mAudienceAdapter.getData().get(position).getUserId()));
            }
        });

        mLlContribute = findViewById(R.id.ll_show_field_contribute);

        mIvMore.setOnClickListener(this);
        mIvRed.setOnClickListener(this);
        mIvGift.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mLlContribute.setOnClickListener(this);

        mLockButton = findViewById(R.id.lock);
        mLockButton.setOnClickListener(this);

        mLottieView = findViewById(R.id.lottie_double_click);
        mLottieView.setVisibility(View.GONE);

        mMtvChatNotice = findViewById(R.id.tv_marquee);
        mIvSvgSmall = findViewById(R.id.iv_svg_choose);
        mIvSvgFull = findViewById(R.id.iv_svg_anim);
        mTvSvgaText = findViewById(R.id.tv_svg_text);
        mTvSvgaTextFull = findViewById(R.id.tv_svg_text_full);
        svgaParser = SVGAParser.Companion.shareParser();
        svgaParser.init(getContext());
        mIvSvgSmall.setLoops(1);
        mIvSvgSmall.setClearsAfterStop(true);
        mIvSvgFull.setLoops(1);
        mIvSvgFull.setClearsAfterStop(true);

        mIvSvgSmall.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
            }

            @Override
            public void onFinished() {
                mIvSvgSmall.stopAnimation(true);
                mIvSvgSmall.clearAnimation();
                mIvSvgSmall._$_clearFindViewByIdCache();

                if (mGiftList.size() > 1) {
                    mGiftList.remove(0);
                    loadAnimation(mGiftList.get(0), mGiftMessage);
                } else {
                    mGiftList.remove(0);
                    mTvSvgaText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeat() {
            }

            @Override
            public void onStep(int i, double v) {
            }
        });

        mIvSvgFull.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
            }

            @Override
            public void onFinished() {
                mIvSvgFull.stopAnimation(true);
                mIvSvgFull.clearAnimation();
                mIvSvgFull._$_clearFindViewByIdCache();

                if (mGiftList.size() > 1) {
                    mGiftList.remove(0);
                    loadAnimation(mGiftList.get(0), mGiftMessage);
                } else {
                    mGiftList.remove(0);
                    mTvSvgaTextFull.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeat() {
            }

            @Override
            public void onStep(int i, double v) {
            }
        });

        // 构建播放图片的XFrameAnimation
        loadingDrawable = new XFrameAnimation(3000, getRes(), getResources());
        mLottieView.setImageDrawable(loadingDrawable);
        loadingDrawable.getAnimator().addListener(this);
        mLottieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("zzy", "-----余额-------: " + mCoins);
                Log.d("zzy", "-----礼物价格-------: " + mGiftPrice);

                if (mCoins != 0 && mCoins >= mGiftPrice) {
                    long secondTime = System.currentTimeMillis();
                    // 判断每次点击的事件间隔是否符合连击的有效范围
                    // 不符合时，有可能是连击的开始，否则就仅仅是单击
                    if (secondTime - firstTime <= interval) {
                        mDoubleClickCount++;
                    } else {
                        mDoubleClickCount = 1;
                    }
                    // 延迟，用于判断用户的点击操作是否结束
                    delay();
                    firstTime = secondTime;
                    loadingDrawable.start();
                    mOnSendGiftClickListener.onClickSendGift(mGiftId, mGiftPrice, 0);
                } else {
                    mOnShowFieldStopScrollListener.showFieldStopScroll(true);
                    loadingDrawable.stop();
                    mLottieView.setVisibility(View.GONE);
                }
            }
        });

        // 点击事件结束后的事件处理
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (mDoubleClickCount > 0) {
                    isDoubled = true;
                    mOnClickDoubleListener.onClickDouble(mGiftId, mDoubleClickCount + 1);
                } else {
                    isDoubled = false;
                }
                //设置能够滑动
                mOnShowFieldStopScrollListener.showFieldStopScroll(true);
                delayTimer.cancel();
                mDoubleClickCount = 0;
                super.handleMessage(msg);
            }
        };

        mRvChat = findViewById(R.id.rv_show_field_chat);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        mRvChat.setLayoutManager(manager);
        mChatAdapter = new ShowFieldChatAdapter(mChatData);
        mRvChat.setAdapter(mChatAdapter);
        mChatAdapter.setOnItemClickNickNameListener(this);
        mRvChat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Log.d("zzyip", "-----SCROLL_STATE_DRAGGING----------");
                    mOnShowFieldStopScrollListener.showFieldStopScroll(false);
                } else {
                    mOnShowFieldStopScrollListener.showFieldStopScroll(true);
                }
            }
        });


        mRlOpenChat = findViewById(R.id.rl_open_chat);
        mEdtOpenChat = findViewById(R.id.edt_open_comment);
        mEdtCloseChat = findViewById(R.id.edt_close_comment);
        mEdtCloseChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserLoginUtil.checkLogin(getContext())) return;
                mOnShowFieldItemClickListener.onShowFieldItemClick(9, null);
            }
        });
        mEdtOpenChat.setOnEditorActionListener(this);
        mEdtOpenChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 30) {
                    Toast.makeText(getContext(), "您发言不能超过30个字", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clearScreenLayout.addClearViews(mRlClearScreen);
        clearScreenLayout.setSlideDirection(SlideDirection.RIGHT);

        rewardLayout = findViewById(R.id.rl_layout_gift);
        rewardLayout.setGiftAdapter(new RewardLayout.GiftAdapter<GiftResponse>() {
            @Override
            public View onInit(View view, GiftResponse bean) {
                AppCompatImageView giftAvatar = view.findViewById(R.id.iv_gift_anim_avatar);
                AppCompatImageView giftImage = view.findViewById(R.id.iv_gift_anim_img);
                final AppCompatTextView giftNum = view.findViewById(R.id.iv_gift_anim_count);
                AppCompatTextView userName = view.findViewById(R.id.iv_gift_anim_user_name);
                AppCompatTextView giftName = view.findViewById(R.id.iv_gift_anim_gift_name);

                // 初始化数据
                int showNum = bean.getTheSendGiftSize();
                giftNum.setText("x " + showNum);
                bean.setTheGiftCount(bean.getTheGiftCount() + bean.getTheSendGiftSize());

                GlideApp.with(mActivity)
                        .load(bean.getUserAvatar())
                        .circleCrop()
                        .into(giftAvatar);

                GlideApp.with(mActivity)
                        .load(bean.getDynamicGraphUrl())
                        .circleCrop()
                        .into(giftImage);

                userName.setText(bean.getUserName());
                giftName.setText("送出 " + bean.getGiftName());
                return view;
            }

            @Override
            public View onUpdate(View view, GiftResponse o, GiftResponse t) {
                AppCompatImageView giftAvatar = view.findViewById(R.id.iv_gift_anim_avatar);
                AppCompatImageView giftImage = view.findViewById(R.id.iv_gift_anim_img);
                AppCompatTextView giftNum = view.findViewById(R.id.iv_gift_anim_count);

                int showNum = (Integer) o.getTheGiftCount() + o.getTheSendGiftSize();
                // 刷新已存在的giftview界面数据
                giftNum.setText("x " + showNum);

                GlideApp.with(mActivity)
                        .load(o.getDefaultGraphUrl())
                        .circleCrop()
                        .into(giftImage);

                o.setDefaultGraphUrl(t.getDynamicGraphUrl());

                GlideApp.with(mActivity)
                        .load(t.getUserAvatar())
                        .circleCrop()
                        .into(giftAvatar);
                // 数字刷新动画
                new NumAnim().start(giftNum);
                // 更新累计礼物数量
                o.setTheGiftCount(showNum);
                // 更新其它自定义字段
                o.setUserName(t.getUserName());
                o.setGiftName("送出 " + t.getGiftName());
                return view;
            }

            @Override
            public void onKickEnd(GiftResponse bean) {
                Log.e("zzy", "onKickEnd:" + bean.getTheGiftId() + "," +
                        bean.getGiftName() + "," + bean.getUserName() + "," + bean.getTheGiftCount());
            }

            @Override
            public void onComboEnd(GiftResponse bean) {
                Log.e("zzy", "onComboEnd:" + bean.getTheGiftId() + "," +
                        bean.getGiftName() + "," + bean.getUserName() + "," + bean.getTheGiftCount());

                if (bean.getDoubleHit() == 1 && mUserId.equals(String.valueOf(bean.getTheUserId()))
                        && bean.getTheGiftCount() == 1 && !isDoubled) {
                    mOnClickDoubleListener.onClickDouble(mGiftId, bean.getTheGiftCount());
                }
            }

            @Override
            public void addAnim(final View view) {
                final AppCompatTextView textView = view.findViewById(R.id.iv_gift_anim_count);
                AppCompatImageView img = view.findViewById(R.id.iv_gift_anim_img);
                // 整个giftview动画
                Animation giftInAnim = AnimUtils.getInAnimation(mActivity);
                // 礼物图像动画
                Animation imgInAnim = AnimUtils.getInAnimation(mActivity);
                // 首次连击动画
                final NumAnim comboAnim = new NumAnim();
                imgInAnim.setStartTime(500);
                imgInAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        textView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.setVisibility(View.VISIBLE);
                        comboAnim.start(textView);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(giftInAnim);
                img.startAnimation(imgInAnim);
            }

            @Override
            public AnimationSet outAnim() {
                return AnimUtils.getOutAnimation(mActivity);
            }

            @Override
            public boolean checkUnique(GiftResponse o, GiftResponse t) {
                return o.getTheGiftId().equals(t.getTheGiftId()) && o.getTheUserId() == t.getTheUserId();
            }


            @Override
            public GiftResponse generateBean(GiftResponse bean) {
                try {
                    return (GiftResponse) bean.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        rewardLayoutFull = findViewById(R.id.rl_layout_gift_full);
        rewardLayoutFull.setGiftAdapter(new RewardLayout.GiftAdapter<GiftResponse>() {
            @Override
            public View onInit(View view, GiftResponse bean) {
                AppCompatImageView giftAvatar = view.findViewById(R.id.iv_gift_anim_avatar);
                AppCompatImageView giftImage = view.findViewById(R.id.iv_gift_anim_img);
                final AppCompatTextView giftNum = view.findViewById(R.id.iv_gift_anim_count);
                AppCompatTextView userName = view.findViewById(R.id.iv_gift_anim_user_name);
                AppCompatTextView giftName = view.findViewById(R.id.iv_gift_anim_gift_name);

                // 初始化数据
                int showNum = bean.getTheSendGiftSize();
                giftNum.setText("x " + showNum);
                bean.setTheGiftCount(bean.getTheGiftCount() + bean.getTheSendGiftSize());

                GlideApp.with(mActivity)
                        .load(bean.getUserAvatar())
                        .circleCrop()
                        .into(giftAvatar);

                GlideApp.with(mActivity)
                        .load(bean.getDynamicGraphUrl())
                        .circleCrop()
                        .into(giftImage);

                userName.setText(bean.getUserName());
                giftName.setText("送出 " + bean.getGiftName());
                return view;
            }

            @Override
            public View onUpdate(View view, GiftResponse o, GiftResponse t) {
                AppCompatImageView giftAvatar = view.findViewById(R.id.iv_gift_anim_avatar);
                AppCompatImageView giftImage = view.findViewById(R.id.iv_gift_anim_img);
                AppCompatTextView giftNum = view.findViewById(R.id.iv_gift_anim_count);

                int showNum = (Integer) o.getTheGiftCount() + o.getTheSendGiftSize();
                // 刷新已存在的giftview界面数据
                giftNum.setText("x " + showNum);

                GlideApp.with(mActivity)
                        .load(o.getDefaultGraphUrl())
                        .circleCrop()
                        .into(giftImage);

                o.setDefaultGraphUrl(t.getDynamicGraphUrl());

                GlideApp.with(mActivity)
                        .load(t.getUserAvatar())
                        .circleCrop()
                        .into(giftAvatar);
                // 数字刷新动画
                new NumAnim().start(giftNum);
                // 更新累计礼物数量
                o.setTheGiftCount(showNum);
                // 更新其它自定义字段
                o.setUserName(t.getUserName());
                o.setGiftName("送出 " + t.getGiftName());
                return view;
            }

            @Override
            public void onKickEnd(GiftResponse bean) {
                Log.e("zzy", "onKickEnd:" + bean.getTheGiftId() + "," +
                        bean.getGiftName() + "," + bean.getUserName() + "," + bean.getTheGiftCount());
            }

            @Override
            public void onComboEnd(GiftResponse bean) {
                Log.e("zzy", "onComboEnd:" + bean.getTheGiftId() + "," +
                        bean.getGiftName() + "," + bean.getUserName() + "," + bean.getTheGiftCount());

                if (bean.getDoubleHit() == 1 && mUserId.equals(String.valueOf(bean.getTheUserId()))
                        && bean.getTheGiftCount() == 1 && !isDoubled) {
                    mOnClickDoubleListener.onClickDouble(mGiftId, bean.getTheGiftCount());
                }
            }

            @Override
            public void addAnim(final View view) {
                final AppCompatTextView textView = view.findViewById(R.id.iv_gift_anim_count);
                AppCompatImageView img = view.findViewById(R.id.iv_gift_anim_img);
                // 整个giftview动画
                Animation giftInAnim = AnimUtils.getInAnimation(mActivity);
                // 礼物图像动画
                Animation imgInAnim = AnimUtils.getInAnimation(mActivity);
                // 首次连击动画
                final NumAnim comboAnim = new NumAnim();
                imgInAnim.setStartTime(500);
                imgInAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        textView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.setVisibility(View.VISIBLE);
                        comboAnim.start(textView);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(giftInAnim);
                img.startAnimation(imgInAnim);
            }

            @Override
            public AnimationSet outAnim() {
                return AnimUtils.getOutAnimation(mActivity);
            }

            @Override
            public boolean checkUnique(GiftResponse o, GiftResponse t) {
                return o.getTheGiftId().equals(t.getTheGiftId()) && o.getTheUserId() == t.getTheUserId();
            }


            @Override
            public GiftResponse generateBean(GiftResponse bean) {
                try {
                    return (GiftResponse) bean.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        //红包
//        mRedConstraintLayout = findViewById(R.id.cl_root);
//        mRedGroup = findViewById(R.id.group_line);
//        mGvd = findViewById(R.id.rl_ad);

        mViewPager = findViewById(R.id.banner_view);
        mIndicatorView = findViewById(R.id.indicator_view);
        mGvd = findViewById(R.id.rl_ad);

        //全屏控件初始化
        mPopupLayout = findViewById(R.id.ll_gift_bottom);
        mRvFullGift = findViewById(R.id.rv_full_gift);
        mTvFullCoins = findViewById(R.id.tv_full_coins);
        mSendGift = findViewById(R.id.tv_full_send_gift);
        mSendGift.setOnClickListener(this);
        mSendNoGift = findViewById(R.id.tv_full_no_send_gift);
        mRvFullGift.setLayoutManager(new LinearLayoutManager(mActivity,
                RecyclerView.HORIZONTAL, false));
    }

    public void setGiftData(List<GiftResponse> list) {
        if (!ObjectUtils.isEmpty(list)) {
            this.mList = list;
            mGiftFullAdapter = new GiftFullAdapter(mList, mActivity, moneyIcon);
            mRvFullGift.setAdapter(mGiftFullAdapter);
            mGiftCurrentIndex = 0;

            mGiftFullAdapter.setOnItemClickListener(new com.lib.showfield.interfaces.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    for (GiftResponse bean : mList) {
                        bean.setSelected(false);
                    }
                    mList.get(position).setSelected(true);
                    mGiftCurrentIndex = position;
                    mGiftFullAdapter.notifyDataSetChanged();
                    mGiftResponse = mList.get(position);

                    if (mList.get(position).getGiftPrice() > mCoins) {
                        mSendNoGift.setVisibility(VISIBLE);
                        mSendGift.setVisibility(GONE);
                    } else {
                        mSendNoGift.setVisibility(GONE);
                        mSendGift.setVisibility(VISIBLE);
                    }
                }
            });
        }
    }

    /**
     * 横竖屏切换
     */
    public void toggleFullScreen() {
        Activity activity = PlayerUtils.scanForActivity(getContext());
        mControlWrapper.toggleFullScreen(activity);
    }

    @Override
    protected void onVisibilityChanged(boolean isVisible, Animation anim) {
        super.onVisibilityChanged(isVisible, anim);
        if (mControlWrapper.isFullScreen()) {
            if (isVisible) {
                if (mLockButton.getVisibility() == GONE) {
                    mLockButton.setVisibility(VISIBLE);
                    if (anim != null) {
                        mLockButton.startAnimation(anim);
                    }
                }
                if (mPopupLayout.getVisibility() == VISIBLE) {
                    hideGiftView();
                }
            } else {
                mLockButton.setVisibility(GONE);
                if (anim != null) {
                    mLockButton.startAnimation(anim);
                }
            }
        }
    }

    @Override
    protected void onPlayerStateChanged(int playerState) {
        super.onPlayerStateChanged(playerState);
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                mOnToggleScreenListener.onToggleScreen(false);
                clearScreenLayout.setVisibility(VISIBLE);
                clearScreenLayout.addClearViews(mRlClearScreen);

                mLockButton.setVisibility(GONE);

                removeControlComponent(titleView);
                removeControlComponent(liveControlView);
                removeControlComponent(gestureView);

                hideDanMu();

                mIvSvgFull.stopAnimation(true);
                mIvSvgFull.clearAnimation();
                mIvSvgFull._$_clearFindViewByIdCache();
                mTvSvgaTextFull.setVisibility(View.GONE);

                rewardLayoutFull.setVisibility(GONE);
                rewardLayout.setVisibility(VISIBLE);

                if (mPopupLayout.getVisibility() == VISIBLE) {
                    hideGiftView();
                }

                if (mMtvChatNotice.getVisibility() == VISIBLE) {
                    mMtvChatNotice.setVisibility(GONE);
                }

                mOnShowFieldPreparedListener.showFieldPrepared(mControlWrapper.getVideoSize());

                break;
            case VideoView.PLAYER_FULL_SCREEN:

                removeControlComponent(titleView);
                removeControlComponent(liveControlView);
                removeControlComponent(gestureView);

                addControlComponent(titleView);
                addControlComponent(liveControlView);
                addControlComponent(gestureView);

                showDanMu();

                mOnToggleScreenListener.onToggleScreen(true);
                clearScreenLayout.removeAllClearViews();
                clearScreenLayout.setVisibility(GONE);

                if (isShowing()) {
                    mLockButton.setVisibility(VISIBLE);
                } else {
                    mLockButton.setVisibility(GONE);
                }

                mIvSvgSmall.stopAnimation(true);
                mIvSvgSmall.clearAnimation();
                mIvSvgSmall._$_clearFindViewByIdCache();
                mTvSvgaText.setVisibility(View.GONE);

                rewardLayout.setVisibility(GONE);
                rewardLayoutFull.setVisibility(VISIBLE);

                break;
        }

        if (mActivity != null && hasCutout()) {
            int orientation = mActivity.getRequestedOrientation();
            int dp24 = PlayerUtils.dp2px(getContext(), 24);
            int cutoutHeight = getCutoutHeight();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                FrameLayout.LayoutParams lblp = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                lblp.setMargins(dp24, 0, dp24, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                layoutParams.setMargins(dp24 + cutoutHeight, 0, dp24 + cutoutHeight, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                layoutParams.setMargins(dp24, 0, dp24, 0);
            }
        }
    }

    @Override
    protected void onPlayStateChanged(int playState) {
        super.onPlayStateChanged(playState);
        switch (playState) {
            case VideoView.STATE_IDLE:
                L.e("STATE_IDLE ");
                mLockButton.setSelected(false);
                break;
            case VideoView.STATE_PREPARING:
                Log.d("zzy", "----show-----STATE_PREPARING-------");
                mMtvChatNotice.setVisibility(GONE);
                mRlNoPull.setVisibility(GONE);
                mEdtCloseChat.setText("");
                break;
            case VideoView.STATE_PREPARED:
                mOnShowFieldPreparedListener.showFieldPrepared(mControlWrapper.getVideoSize());
                clearScreenLayout.setViewClickable(true);
                break;
            case VideoView.STATE_PLAYING:
                L.e("STATE_PLAYING " + hashCode());
                clearScreenLayout.restoreWithAnim();
                mRlNoPull.setVisibility(GONE);
                break;
            case VideoView.STATE_ERROR:
                Log.d("zzy", "----show-----STATE_ERROR-------");
                clearScreenLayout.setViewClickable(false);
                mRlNoPull.setVisibility(GONE);
                break;
            case VideoView.STATE_BUFFERING:
                Log.d("zzy", "----show-----STATE_BUFFERING-------");
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
                mLockButton.setVisibility(GONE);
                mLockButton.setSelected(false);
                break;
            case VideoView.STATE_START_ABORT:
                VideoViewManager.instance().setPlayOnMobileNetwork(true);
                mControlWrapper.start();
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (mPopupLayout.getVisibility() == VISIBLE) {
            hideGiftView();
        }

        if (isLocked()) {
            show();
            Toast.makeText(getContext(), R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show();
            return true;
        }

        if (mControlWrapper.isFullScreen()) {
            return stopFullScreen();
        }
        return super.onBackPressed();
    }

    public boolean isFullScreen() {
        return mControlWrapper.isFullScreen();
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.item_iv_show_field_more) {
            mOnShowFieldItemClickListener.onShowFieldItemClick(0, null);
        } else if (id == R.id.item_iv_show_field_red) {
            mOnShowFieldItemClickListener.onShowFieldItemClick(1, null);
        } else if (id == R.id.item_iv_show_field_gift) {
            mOnShowFieldItemClickListener.onShowFieldItemClick(2, null);
        } else if (id == R.id.ll_show_field_close) {
            mOnShowFieldItemClickListener.onShowFieldItemClick(3, null);
        } else if (id == R.id.ll_show_field_contribute) {
            mOnShowFieldItemClickListener.onShowFieldItemClick(4, null);
        }

        //else if (id == R.id.item_tv_show_field_focus) {
        //mOnShowFieldItemClickListener.onShowFieldItemClick(5, null);
        //  }

        else if (id == R.id.item_iv_show_field_livenum) {
            mOnShowFieldItemClickListener.onShowFieldItemClick(6, null);
        } else if (id == R.id.iv_full_show_field) {
            toggleFullScreen();
        } else if (id == R.id.rl_show_field_player_info) {
            mOnShowFieldItemClickListener.onShowFieldItemClick(7, null);
        } else if (id == R.id.tv_full_send_gift) {
            if (mGiftResponse.getDoubleHit() == 1) {
                mLottieView.setVisibility(VISIBLE);
                loadingDrawable.start();
            }

            mOnSendGiftClickListener.onClickSendGift(mGiftResponse.getTheGiftId(),
                    mGiftResponse.getGiftPrice(), 0);
            hideGiftView();

        } else if (id == R.id.tv_up_coins) {
            if (mControlWrapper.isFullScreen()) {
                hideGiftView();
                mControlWrapper.toggleFullScreen(mActivity);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //mOnClickJumpToAccount.onClickJumpToAccount();
                    }
                }, 500);
            }
        } else if (id == R.id.lock) {
            mControlWrapper.toggleLockState();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || actionId == IME_FLAG_NO_ENTER_ACTION) {
            String content = mEdtOpenChat.getText().toString().trim().replaceAll("\n", "");
            Log.d("zzy", "-------content-----: " + content);
            mOnClickSendMsgListener.onSendMsgClick(content);
            mEdtOpenChat.setText("");
            KeyboardUtils.hideKeyboard(mEdtOpenChat);
            return true;
        }
        return true;
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        if (!isFullScreen()) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRlOpenChat.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, keyboardHeight);//4个参数按顺序分别是左上右下
            mRlOpenChat.setLayoutParams(layoutParams);
            mEdtCloseChat.setText("");
            mRlOpenChat.setVisibility(View.VISIBLE);
            mEdtOpenChat.setFocusableInTouchMode(true);
            mEdtOpenChat.setFocusable(true);
            mEdtOpenChat.requestFocus();
        } else {
            stopFadeOut();
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        if (!isFullScreen()) {
            mEdtCloseChat.setFocusable(false);
            mEdtOpenChat.setFocusable(false);
            mRlOpenChat.setVisibility(View.GONE);
            mEdtCloseChat.setText("");
        } else {
            startFadeOut();
        }
    }

    //记录原始窗口高度
    private int mWindowHeight = 0;

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            //获取当前窗口实际的可见区域
            mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            int height = r.height();
            if (mWindowHeight == 0) {
                //一般情况下，这是原始的窗口高度
                mWindowHeight = height;
            } else {
                if (mWindowHeight != height) {
                    //两次窗口高度相减，就是软键盘高度
                    softKeyboardHeight = mWindowHeight - height;
                    System.out.println("SoftKeyboard height = " + softKeyboardHeight);
                }
            }
        }
    };

    //清屏
    public void doClearScreen() {
        clearScreenLayout.clearWithAnim();
    }

    public void setChatData(ChatMessage chatMessage) {
        mChatAdapter.addData(chatMessage);
        mChatAdapter.notifyDataSetChanged();
        mRvChat.scrollToPosition(mChatData.size() - 1);
    }

    public void setClearData() {
        mChatData.clear();
        mChatAdapter.notifyDataSetChanged();
    }

    public void showDoubleIcon(boolean isShow) {
        if (isShow) {
            mOnShowFieldStopScrollListener.showFieldStopScroll(false);
            mLottieView.setVisibility(View.VISIBLE);
            loadingDrawable.start();
        } else {
            mOnShowFieldStopScrollListener.showFieldStopScroll(true);
            mDoubleClickCount--;
            loadingDrawable.stop();
            mLottieView.setVisibility(View.GONE);
        }
    }

    public void sendGiftInfo(String giftId, long giftPrice, boolean isDoubled) {
        this.mGiftId = giftId;
        this.isDoubled = isDoubled;
        this.mGiftPrice = giftPrice;
    }

    public void showRewardLayout(GiftResponse giftResponse) {
        Log.d("zzyip", "--------------giftResponse--------------");
        if (!isFullScreen()) {
            rewardLayout.put(giftResponse);
        } else {
            rewardLayoutFull.put(giftResponse);
        }

    }

    private int[] getRes() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.c);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i < len; i++) {
            resId[i] = typedArray.getResourceId(i, -1);
        }
        typedArray.recycle();
        return resId;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mLottieView.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public void setCoins(long remainingSum) {
        this.mCoins = remainingSum;
        mTvFullCoins.setText(String.valueOf(mCoins));

        if (mList.get(mGiftCurrentIndex).getGiftPrice() > remainingSum) {
            mSendGift.setVisibility(GONE);
            mSendNoGift.setVisibility(VISIBLE);
        } else {
            mSendGift.setVisibility(VISIBLE);
            mSendNoGift.setVisibility(GONE);
        }
    }

    // 延迟时间是连击的时间间隔有效范围
    private void delay() {
        if (task != null)
            task.cancel();

        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                // message.what = 0;
                handler.sendMessage(message);
            }
        };
        delayTimer = new Timer();
        delayTimer.schedule(task, interval);
    }

    public void showMarquee(GiftMessage giftMsg, String roomNo) {
        this.mAnchorNickName = giftMsg.getAnchor().getNickName();
        this.mRoomNo = roomNo;
        mMtvChatNotice.setVisibility(View.VISIBLE);

        showChatGiftMarquee(giftMsg.getAnchor().getUid(), giftMsg.getUser().getNickName(),
                giftMsg.getAnchor().getNickName(),
                giftMsg.getUser().getUserLevel(),
                giftMsg.getGift().getName(),
                giftMsg.getGift().getQuantity());
    }

    public void showMarquee(GiftDoubleCmd giftMsg, String roomNo) {
        this.mAnchorNickName = giftMsg.getAnchor().getNickName();
        this.mRoomNo = roomNo;
        mMtvChatNotice.setVisibility(View.VISIBLE);

        showChatGiftMarquee(giftMsg.getAnchor().getUid(), giftMsg.getUser().getNickName(),
                giftMsg.getAnchor().getNickName(),
                giftMsg.getUser().getUserLevel(),
                giftMsg.getGift().getName(),
                giftMsg.getGift().getQuantity());
    }

    /**
     * 聊天区跑马灯
     */
    private void showChatGiftMarquee(String uid, String nickname, String anthorName,
                                     String level, String giftName, int giftNum) {
        SpannableStringBuilder builderTitle = new SpannableStringBuilder();
        builderTitle.append("\t");
        builderTitle.append("\t");
        builderTitle.append(nickname);
        builderTitle.append("\t");
        builderTitle.append("送给");
        builderTitle.append("\t");
        builderTitle.append(anthorName);
        builderTitle.append("\t");
        builderTitle.append(String.valueOf(giftNum));
        builderTitle.append("个");
        builderTitle.append(giftName);
        builderTitle.append("一起去围观吧");

        int userLevel = 0;
        if (!"0".equals(level) && !ObjectUtils.isEmpty(level)) {
            userLevel = FindDrawableByName.getDrawableRes(mActivity,
                    "icon_user_level" + level);
        }

        if (getContext() == null) {
            return;
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable drawable = mActivity.getResources().getDrawable(userLevel);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        CenterSpaceImageSpan imageSpan = new CenterSpaceImageSpan(drawable);
        builderTitle.setSpan(imageSpan,
                0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builderTitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.chat_join_text)),
                2, 2 + nickname.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builderTitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.chat_join_text)),
                nickname.length() + 6, mAnchorNickName.length() + nickname.length() + 7,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mMtvChatNotice.setText(builderTitle);
        float w = mMtvChatNotice.getPaint().measureText(builderTitle.toString());
        mMtvChatNotice.getLayoutParams().width = (int) w + 180;
        long duration = (long) (w * 10 / 2);
        animation = AnimationUtils.loadAnimation(mActivity, R.anim.trans_right_to_left);
        animation.setDuration(duration);
        animation.setRepeatCount(0);
        mMtvChatNotice.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("zzy", "-------animation---------");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mMtvChatNotice.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mMtvChatNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!uid.equals(mRoomNo)) {
                    mOnJumpOtherLiveByRoomNoListener.onJumpToOtherLive(uid);
                } else {
                    ToastUtils.showShort("您已在本房间");
                }

            }
        });
    }

    public void setSvgaGift(GiftMessage giftMessage) {
        if (!isFullScreen()) {
            mIvSvgSmall.clearAnimation();
            mIvSvgSmall._$_clearFindViewByIdCache();
        } else {
            mIvSvgFull.clearAnimation();
            mIvSvgFull._$_clearFindViewByIdCache();
        }

        this.mGiftMessage = giftMessage;
        if (mGiftList.isEmpty()) {
            mGiftList.add(giftMessage.getGift().getPlayEffectUrl());
            loadAnimation(mGiftList.get(0), giftMessage);
        } else {
            mGiftList.add(giftMessage.getGift().getPlayEffectUrl());
        }

    }

    /**
     * 播放SVGA
     */
    private void loadAnimation(String url, GiftMessage giftMessage) {
        if (!isFullScreen()) {
            if (mTvSvgaText.getVisibility() == View.GONE) {
                mTvSvgaText.setVisibility(View.VISIBLE);
            }
        } else {
            if (mTvSvgaTextFull.getVisibility() == View.GONE) {
                mTvSvgaTextFull.setVisibility(View.VISIBLE);
            }
        }

        Log.d("svga", "-------giftMessage-----: " + giftMessage.getUser().getNickName());
        showSvgaText(giftMessage);
        if (!mSvgaEntity.containsKey(url)) {
            try { // new URL needs try catch.
                svgaParser.decodeFromURL(
                        new URL(url),
                        new SVGAParser.ParseCompletion() {
                            @Override
                            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                                mSvgaEntity.put(url, videoItem);
                                if (!isFullScreen()) {
                                    mIvSvgSmall.setVideoItem(videoItem);
                                    mIvSvgSmall.startAnimation();
                                } else {
                                    mIvSvgFull.setVideoItem(videoItem);
                                    mIvSvgFull.startAnimation();
                                }
                            }

                            @Override
                            public void onError() {
                                Log.d("zzy", "-------svga-----onError-----");
                            }
                        });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            if (!isFullScreen()) {
                mIvSvgSmall.clearAnimation();
                mIvSvgSmall._$_clearFindViewByIdCache();
                mIvSvgSmall.setVideoItem(mSvgaEntity.get(url));
                mIvSvgSmall.startAnimation();
            } else {
                mIvSvgFull.clearAnimation();
                mIvSvgFull._$_clearFindViewByIdCache();
                mIvSvgFull.setVideoItem(mSvgaEntity.get(url));
                mIvSvgFull.startAnimation();
            }

        }
    }

    private void showSvgaText(GiftMessage giftMessage) {
        if (!isFullScreen()) {
            mTvSvgaText.setText(giftMessage.getUser().getNickName() + " 送给主播 " +
                    giftMessage.getGift().getQuantity() + " 个 " +
                    giftMessage.getGift().getName());
        } else {
            mTvSvgaTextFull.setText(giftMessage.getUser().getNickName() + " 送给主播 " +
                    giftMessage.getGift().getQuantity() + " 个 " +
                    giftMessage.getGift().getName());
        }

    }

    public void removeCathe() {
        //释放资源
        mIvSvgSmall.stopAnimation(true);
        mIvSvgSmall.clearAnimation();
        mIvSvgSmall._$_clearFindViewByIdCache();

        mIvSvgFull.stopAnimation(true);
        mIvSvgFull.clearAnimation();
        mIvSvgFull._$_clearFindViewByIdCache();

        mTvSvgaText.setVisibility(GONE);
        mTvSvgaTextFull.setVisibility(GONE);

//        if (rewardLayout != null) {
//            rewardLayout.onDestroy();
//        }
    }

    public void onResumeReward() {
        mViewPager.startLoop();
        if (rewardLayout != null) {
            rewardLayout.onResume();
        }
    }

    public void onPauseReward() {
        mViewPager.stopLoop();
        if (rewardLayout != null) {
            rewardLayout.onPause();
        }
    }

    //初始化红包开关
//    public void initViewModel(RedpackViewModel mViewModel, ShowFieldLiveRoomActivity activity) {
//        mRedConstraintLayout.initViewModel(mViewModel, activity);
//    }


    // 大红包飘屏
    private void showChatGrabMarquee(String roomNo, String nickname, String level, String roomName, int amount) {
        SpannableStringBuilder builderTitle = new SpannableStringBuilder();
        builderTitle.append("\t");
        builderTitle.append("\t");
        builderTitle.append(nickname);
        builderTitle.append("\t");
        builderTitle.append("在");
        builderTitle.append("\t");
        builderTitle.append(roomName);
        builderTitle.append("\t");
        builderTitle.append("的直播间发了");
        builderTitle.append(String.valueOf(amount));
        builderTitle.append(SPUtils.getInstance(COMMON_RESOURCE).getString(CURRENCYMONEYNAME));
        builderTitle.append("的红包呦一起去直播间抢红包吧！");

        int userLevel = 0;
        if (!"0".equals(level) && !ObjectUtils.isEmpty(level)) {
            userLevel = FindDrawableByName.getDrawableRes(mActivity,
                    "icon_user_level" + level);
        }
        Drawable drawable = getDrawable(userLevel);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        //等级icon 2 ok
        CenterSpaceImageSpan imageSpan = new CenterSpaceImageSpan(drawable);
        builderTitle.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //字体 2+nickname.length ok
        builderTitle.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.chat_join_text)),
                2, 2 + nickname.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builderTitle.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.chat_join_text)),
                nickname.length() + 2 + 3, nickname.length() + 2 + 3 + roomName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mMtvChatNotice.setText(builderTitle);
        float w = mMtvChatNotice.getPaint().measureText(builderTitle.toString());
        //mMtvChatNotice.getLayoutParams().width = SystemUtils.dp2px(getContext(), (int) w + 180);
//        mMtvChatNotice.getLayoutParams().width = (int) w + 180;
        long duration = (long) (w * 10 / 2);
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.trans_right_to_left);
        animation.setDuration(duration);
        animation.setRepeatCount(0);
        mMtvChatNotice.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mMtvChatNotice.setVisibility(View.GONE);
                mMtvChatNotice.clearAnimation();
                animation.cancel();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mMtvChatNotice.setOnClickListener(v -> {
            if (!roomNo.equals(mRoomNo)) {
                mOnJumpOtherLiveByRoomNoListener.onJumpToOtherLive(roomNo);
            } else {
                ToastUtils.showShort("您已在本房间");
            }
        });
    }

//    public void setNotifyView() {
//        mRedConstraintLayout.notifyView();
//    }
//
//    public void setNotifyListView(List<RedGradListBean> beans) {
//        mRedConstraintLayout.notifyDialogUserListView(beans);
//    }

    public void setRedConfig() {
        mIvRed.setVisibility(View.VISIBLE);
    }

    public void showRedMarquee(String roomNo, String nickName, String userLevel, String s, int amount) {
        mMtvChatNotice.setVisibility(VISIBLE);
        showChatGrabMarquee(roomNo, nickName, userLevel, s, amount);
    }

//    public void setRedGroupGone() {
//        mRedGroup.setVisibility(GONE);
//    }


    @Override
    public void onItemClickNickName(int position) {
        mOnShowFieldUserCardOrIsMangerListener.onShowFieldUserCardOrIsManger
                (mChatData.get(position).getUserId());
    }

    public void setLivingUserNumData(LiveAudienceBean data) {
        if (data != null) {
            mTvLiveNum.setText(data.getTotal() + "");
            mAudiences.clear();
            mAudiences.addAll(data.getList());
            mAudienceAdapter.notifyDataSetChanged();
        } else {
            mTvLiveNum.setText("0");
            mAudiences.clear();
            mAudienceAdapter.notifyDataSetChanged();
        }
    }

    public void setPlayerInfo(LivePlayerInfo data) {
        GlideApp.with(getContext())
                .load(data.getAvatar())
                .placeholder(R.drawable.icon_avatar)
                .error(R.drawable.icon_avatar)
                .circleCrop()
                .into(mIvAvatar);

        mTvNickname.setText(data.getNickname());
        mTvHotNum.setText(data.getHotNum() + "");
        mTvRoomNo.setText(data.getRoomNo());

        titleView.setHotNum(data.getHotNum() + "");
        titleView.setTitle(data.getTitle());
        titleView.setRoomNum(data.getRoomNo());
    }

//    public void setFocusImg(boolean isFocus) {
//        if (isFocus) {
//            mTvFocusPlayer.setVisibility(GONE);
//        } else {
//            mTvFocusPlayer.setVisibility(VISIBLE);
//            mTvFocusPlayer.setBackgroundResource(R.drawable.shape_yellow_conner15);
//            mTvFocusPlayer.setText("关注");
//            //mTvFocusPlayer.setTextColor(getResources().getColor(R.color.focus_txt));
//        }
//    }

    public void setAdData(List<PatchAdBean> data) {

        /**
         * 自定义指示器配置属性
         */
        mIndicatorView.setIndicatorGap(12);
        mIndicatorView.setIndicatorDrawable(R.drawable.icon_banner_normal, R.drawable.icon_banner_selected);
        mIndicatorView.setIndicatorSize(20, 20, 20, 20);
        mIndicatorView.setSlideMode(IndicatorSlideMode.WORM)
                .setIndicatorStyle(IndicatorStyle.CIRCLE);

        mViewPager
                .setInterval(3000)
                .setScrollDuration(1000)
                .setPageStyle(PageStyle.MULTI_PAGE)
                .setIndicatorVisibility(View.GONE)
                .setCanLoop(true)
                .setIndicatorView(mIndicatorView)
                .setAdapter(new HomeBannerPatchAdapter())
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {
                        EasyWebActivity.start(getContext(), mViewPager.getData().get(position).jumpUrl);
                    }
                }).create();
        mViewPager.refreshData(data);
    }

    public void setAdShowHide(boolean isShowAd) {
        mGvd.setVisibility(isShowAd ? VISIBLE : GONE);
    }

    public void setIsShowFullIcon(boolean isWeb) {
        mIvFullIcon.setVisibility(isWeb ? VISIBLE : GONE);
    }

    public void setEdtFocusable(boolean focusable, String time) {
        if (!focusable) {
            mEdtCloseChat.setFocusable(false);
            mEdtCloseChat.setText("您已被禁言至" + time);
        } else {

            mEdtCloseChat.setFocusable(true);
            mEdtCloseChat.setFocusableInTouchMode(true);
            mEdtCloseChat.setFocusable(true);
            mEdtCloseChat.requestFocus();
            KeyboardUtils.showKeyboard(mEdtCloseChat);
        }
    }

    public void showPlayerOff() {
        mRlNoPull.setVisibility(VISIBLE);
    }

    @Override
    public void onClickGift() {
        hide();
        if (!SPUtils.getInstance().getBoolean("IS_LOGIN")) {
            if (mControlWrapper.isFullScreen()) {
                mControlWrapper.stopFullScreen();
                //ActivityUtils.startActivity(LoginActivity.class);
            }
        } else {
            showGiftView();
        }
    }

    private void hideGiftView() {
        //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 1);
        ctrlAnimation.setDuration(400L);     //设置动画的过渡时间
        mPopupLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPopupLayout.setVisibility(View.GONE);
                mPopupLayout.startAnimation(ctrlAnimation);
            }
        }, 100);
    }

    private void showGiftView() {
        //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
        ctrlAnimation.setDuration(400L);     //设置动画的过渡时间
        mPopupLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPopupLayout.setVisibility(View.VISIBLE);
                mPopupLayout.startAnimation(ctrlAnimation);
            }
        }, 100);

        mOnShowOpenFullGiftListener.onShowFullGift();
    }

    @Override
    public void clickBarrage(boolean isOpened) {
        if (isOpened) {
            hideDanMu();
        } else {
            showDanMu();
        }
    }

    public void showDanMu() {
        mMyDanmakuView.show();
    }

    public void hideDanMu() {
        mMyDanmakuView.hide();
    }

    @Override
    public void onClickFullChat() {
        mEdtFullChat.setVisibility(VISIBLE);
    }

    @Override
    public void onSendMsgClick(String msg) {
        mOnClickSendMsgListener.onSendMsgClick(msg);
    }

    @Override
    public void onReportClick() {
        mOnClickReportListener.onReportClick();
    }

    @Override
    public void onClickProjection(boolean isFullScreen) {
        if (null == mOnClickProjectionListener) return;
        mOnClickProjectionListener.onClickProjection(isFullScreen);
    }

    public void setToggleListener(OnToggleListener listener) {
        if (liveControlView == null) return;
        liveControlView.setOnToggleListener(listener);
    }

    @Override
    protected void onLockStateChanged(boolean isLocked) {
        if (isLocked) {
            mLockButton.setSelected(true);
            Toast.makeText(getContext(), R.string.dkplayer_locked, Toast.LENGTH_SHORT).show();
        } else {
            mLockButton.setSelected(false);
            Toast.makeText(getContext(), R.string.dkplayer_unlocked, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendBarrage(boolean isSelf, String content) {
        mMyDanmakuView.addDanmaku(content, isSelf);
    }
}
