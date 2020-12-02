package com.lib.showfield.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lib.showfield.R;
import com.lib.showfield.base.action.StatusAction;
import com.lib.showfield.bean.RoomNoParam;
import com.lib.showfield.common.MyFragment;
import com.lib.showfield.helper.LiveInfoHelper;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.helper.AddCustomViewHelper;
import com.lib.showfield.http.respones.Config;
import com.lib.showfield.helper.ControllerInitHelper;
import com.lib.showfield.http.respones.LoginInfo;
import com.lib.showfield.helper.UserLoginExitHelper;
import com.lib.showfield.http.respones.live.DynamicMatchBean;
import com.lib.showfield.http.respones.live.LiveRoomTypeInfo;
import com.lib.showfield.interfaces.OnControllerInitListener;
import com.lib.showfield.model.LiveViewModel;
import com.lib.showfield.ui.activty.ShowFieldLiveRoomActivity;
import com.lib.showfield.ui.adapter.LiveAllListAdapter;
import com.lib.showfield.ui.view.header.RefreshLottieHeader;
import com.lib.showfield.ui.view.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.lib.showfield.common.Constants.ANDROID3X16X16CURRENCYMONEYICONURL;
import static com.lib.showfield.common.Constants.ANDROID3X26X26CURRENCYSILKICONURL;
import static com.lib.showfield.common.Constants.ANDROID3X26X26CURRENCYZHUBOICONURL;
import static com.lib.showfield.common.Constants.ANDROID3X50X50LOGOIMAGEURL;
import static com.lib.showfield.common.Constants.ANDROID3X68X68LOGOIMAGEURL;
import static com.lib.showfield.common.Constants.ANDROID3X80X80LOGOIMAGEURL;
import static com.lib.showfield.common.Constants.BRANDNAME;
import static com.lib.showfield.common.Constants.BRANDUSERNICKNAME;
import static com.lib.showfield.common.Constants.COMMON_RESOURCE;
import static com.lib.showfield.common.Constants.CSCODE;
import static com.lib.showfield.common.Constants.CURRENCYMONEYNAME;
import static com.lib.showfield.common.Constants.CURRENCYSILKNAME;
import static com.lib.showfield.common.Constants.CURRENCYZHUBONAME;
import static com.lib.showfield.common.Constants.ICP;
import static com.lib.showfield.common.Constants.ONLINESERVICE;
import static com.lib.showfield.common.Constants.PAYSWITCH;
import static com.lib.showfield.common.Constants.lIVETOOLDOWNURL;

public class LiveFragment extends MyFragment
        implements StatusAction, OnRefreshLoadMoreListener, OnControllerInitListener {

    private RecyclerView mRvAllLive;
    private HintLayout mHintLayout;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RefreshLottieHeader mRefreshLottieHeader;
    private LiveAllListAdapter mLiveAllListAdapter;
    private List<DynamicMatchBean> mLiveAllList;

    private LiveViewModel liveViewModel;

    private int pageNum = 1;

    private String mAppId;
    private String mUserId;
    private String mNickname;
    private String mAvatar;

    public static LiveFragment getInstance(String appId, String userId, String nickName, String avatar) {
        LiveFragment fragment = new LiveFragment();
        Bundle bundle = new Bundle();
        bundle.putString("appId", appId);
        bundle.putString("userId", userId);
        bundle.putString("nickName", nickName);
        bundle.putString("avatar", avatar);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static LiveFragment getInstance(String appId, String userId, String nickName) {
        LiveFragment fragment = new LiveFragment();
        Bundle bundle = new Bundle();
        bundle.putString("appId", appId);
        bundle.putString("userId", userId);
        bundle.putString("nickName", nickName);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void initView() {
        mRvAllLive = (RecyclerView) findViewById(R.id.rv_all_live);
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_lives);
        mHintLayout = (HintLayout) findViewById(R.id.hl_status_hint);

        mAppId = Objects.requireNonNull(getBundle()).getString("appId");
        mUserId = Objects.requireNonNull(getBundle()).getString("userId");
        mNickname = Objects.requireNonNull(getBundle()).getString("nickName");
        mAvatar = Objects.requireNonNull(getBundle()).getString("avatar");

        mLiveAllList = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(getAttachActivity(), 2);
        mRvAllLive.setLayoutManager(manager);

        mLiveAllListAdapter = new LiveAllListAdapter(R.layout.item_home_recommend, mLiveAllList);
        mRvAllLive.setAdapter(mLiveAllListAdapter);

        mLiveAllListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                String roomNo = mLiveAllList.get(position).getRoomNo();

                LiveInfoHelper.getInstance(getAttachActivity()).getLiveInfo(roomNo);
            }
        });

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLottieHeader = new RefreshLottieHeader(getAttachActivity());
        setHeader(mRefreshLottieHeader);

        ControllerInitHelper.getInstance().setOnControllerInitListener(this);
    }

    private void setHeader(RefreshLottieHeader header) {
        mSmartRefreshLayout.setRefreshHeader(header);
    }

    private void reloadRoomTypeInfo(RoomNoParam param) {
        liveViewModel.reLoadLiveRoomType(param);
    }

    @Override
    protected void initData() {
        liveViewModel = new ViewModelProvider(this).get(LiveViewModel.class);
        liveViewModel.getLiveMatchListByTab().observe(this, new Observer<Lcee<List<DynamicMatchBean>>>() {
            @Override
            public void onChanged(Lcee<List<DynamicMatchBean>> listLcee) {
                updateAllLiveView(listLcee);
            }
        });

        liveViewModel.getLiveRoomType().observe(this, new Observer<Lcee<LiveRoomTypeInfo>>() {
            @Override
            public void onChanged(Lcee<LiveRoomTypeInfo> lcee) {
                updateLiveRoomType(lcee);
            }
        });

        liveViewModel.getConfig().observe(this, new Observer<Lcee<Config>>() {
            @Override
            public void onChanged(Lcee<Config> lcee) {
                updateConfig(lcee);
            }
        });

        liveViewModel.reLoadConfig(false);

        //调用登录接口
        if (!ObjectUtils.isEmpty(mAppId) && !ObjectUtils.isEmpty(mUserId) &&
                !ObjectUtils.isEmpty(mNickname)) {

            UserLoginExitHelper.getInstance(getAttachActivity()).
                    getLogin("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNzI5NTMiLCJyb29tTm8iOiI2ODYyNTUiLCJtZXJjaGFudElkIjoiMTAwMDAiLCJyYW5kb21WYWx1ZSI6NTk1MywiaXNzIjoibGl2ZS10cnVtcCIsImV4cCI6MTYwNzMzODUwNiwiaWF0IjoxNjA2NzMzNzA2fQ.CtbThd-DgQjhkgNu32BAO-1Cuj5tZvv5YoYCQob5cBA");
        }

        reload(0);
    }

    private void updateConfig(Lcee<Config> lcee) {
        switch (lcee.status) {
            case Content:
                Config data = lcee.data.getData();
                if (null != data) {
                    restoreCommonResource(data);
                }
                break;
            case Error:
                liveViewModel.reLoadConfig(false);
                break;
        }
    }

    private void restoreCommonResource(Config data) {
        SPUtils.getInstance(COMMON_RESOURCE).put(BRANDNAME, data.getBrandName());
        SPUtils.getInstance(COMMON_RESOURCE).put(ANDROID3X50X50LOGOIMAGEURL, data.getAndroid3X50X50LogoImageUrl());
        SPUtils.getInstance(COMMON_RESOURCE).put(ANDROID3X68X68LOGOIMAGEURL, data.getAndroid3X68X68LogoImageUrl());
        SPUtils.getInstance(COMMON_RESOURCE).put(ANDROID3X80X80LOGOIMAGEURL, data.getAndroid3X80X80LogoImageUrl());
        SPUtils.getInstance(COMMON_RESOURCE).put(BRANDUSERNICKNAME, data.getBrandUserNickname());
        SPUtils.getInstance(COMMON_RESOURCE).put(CURRENCYMONEYNAME, data.getCurrencyMoneyName());
        SPUtils.getInstance(COMMON_RESOURCE).put(ANDROID3X16X16CURRENCYMONEYICONURL, data.getAndroid3X16X16CurrencyMoneyIconUrl());
        SPUtils.getInstance(COMMON_RESOURCE).put(CURRENCYZHUBONAME, data.getCurrencyZhuboName());
        SPUtils.getInstance(COMMON_RESOURCE).put(ANDROID3X26X26CURRENCYZHUBOICONURL, data.getAndroid3X26X26CurrencyZhuboIconUrl());
        SPUtils.getInstance(COMMON_RESOURCE).put(CURRENCYSILKNAME, data.getCurrencySilkName());
        SPUtils.getInstance(COMMON_RESOURCE).put(ANDROID3X26X26CURRENCYSILKICONURL, data.getAndroid3X26X26CurrencySilkIconUrl());
        SPUtils.getInstance(COMMON_RESOURCE).put(CSCODE, data.getCsCode());
        SPUtils.getInstance(COMMON_RESOURCE).put(ICP, data.getIcp());
        SPUtils.getInstance(COMMON_RESOURCE).put(lIVETOOLDOWNURL, data.getLiveToolDownUrl());
        SPUtils.getInstance(COMMON_RESOURCE).put(ONLINESERVICE, data.getOnLineService());
        SPUtils.getInstance(COMMON_RESOURCE).put(PAYSWITCH, data.isPaySwitch());
    }

    private void updateLoginInfo(Lcee<LoginInfo> lcee) {

    }

    private void updateAllLiveView(Lcee<List<DynamicMatchBean>> lcee) {
        switch (lcee.status) {
            case Content:
                showComplete();
                if (pageNum == 1) {
                    mLiveAllList.clear();
                    mLiveAllList.addAll(lcee.data.getData());
                } else {
                    mLiveAllList.addAll(lcee.data.getData());
                }
                mLiveAllListAdapter.notifyDataSetChanged();
                if (lcee.data.getData().size() < 20) {
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                break;
            case Empty:
                if (pageNum == 1) {
                    if (getContext() == null)
                        return;
                    showEmptyLayout(getDrawable(R.drawable.icon_zhanwu), getString(R.string.live_match_no_data), null);
                    mSmartRefreshLayout.setEnableLoadMore(false);
                } else {
                    showComplete();
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                break;
            case Error:
                toast(lcee.error.getMessage());
                pageNum = 1;
                showError(view -> {
                    reload(0);
                });
                break;
        }
    }


    private void updateLiveRoomType(Lcee<LiveRoomTypeInfo> lcee) {
        switch (lcee.status) {
            case Content:
                LiveRoomTypeInfo info = lcee.data.getData();
                int screenMode = info.getScreenMode();
                if (screenMode == 2) {
                    Intent intent = new Intent(getAttachActivity(), ShowFieldLiveRoomActivity.class);
                    intent.putExtra("show_param", info);
                    startActivity(intent);
                }
                break;
            case Empty:
                break;
            case Error:
                toast(lcee.error.getMessage());
                break;
        }
    }


    private void reload(int subChannelId) {
        Map<String, Integer> map = new ArrayMap<>();
        map.put("channelId", 75);
        map.put("subChannelId", subChannelId);
        map.put("pageNum", pageNum);
        liveViewModel.reloadLiveMatchListByTab(map);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        reload(0);
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        reload(0);
        mSmartRefreshLayout.finishRefresh();
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void initFinish() {
        AddCustomViewHelper.getInstance().addView(new Button(getContext()));
    }
}
