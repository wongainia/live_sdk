package com.lib.showfield.ui.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lib.showfield.R;
import com.lib.showfield.aop.SingleClick;
import com.lib.showfield.base.action.StatusAction;
import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.model.LiveViewModel;
import com.lib.showfield.ui.adapter.LiveAudienctdapter;
import com.lib.showfield.ui.view.header.RefreshLottieHeader;
import com.lib.showfield.ui.view.widget.HintLayout;
import com.lib.showfield.utils.SystemUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import biz.laenger.android.vpbs.ViewPagerBottomSheetBehavior;
import biz.laenger.android.vpbs.ViewPagerBottomSheetDialogFragment;

import static com.lib.showfield.http.model.Status.Content;

/**
 * Created by Roc
 * Date  2020/10/10 13:04
 * <p>
 * Description
 **/
public class LiveAudienceFragment extends ViewPagerBottomSheetDialogFragment implements StatusAction, OnRefreshListener, OnLoadMoreListener {

    private RecyclerView rvAudienctList;
    private LiveAudienctdapter mAdapter;
    private ArrayList<LiveAudienceBean.ListBean> mData = new ArrayList<>();
    private SmartRefreshLayout mSmartRefreshLayout;
    private RefreshLottieHeader mRefreshLottieHeader;
    private int pageNum = 1;
    private String roomNo;
    private LiveViewModel liveViewModel;
    private View mView;

    public static LiveAudienceFragment newInstance(String roomNo) {
        LiveAudienceFragment livePushQualityFragment = new LiveAudienceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomNo", roomNo);
        livePushQualityFragment.setArguments(bundle);
        return livePushQualityFragment;
    }

    public LiveAudienceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sheet_push_audience_list, container, false);
        mView.findViewById(R.id.rl_return).setOnClickListener(v -> dismiss());
        mSmartRefreshLayout = mView.findViewById(R.id.refreshLayout);
        rvAudienctList = mView.findViewById(R.id.rv_audience_list);
        initRefreHeader();
        roomNo = getArguments().getString("roomNo");
        return mView;
    }

    private void initRefreHeader() {
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLottieHeader = new RefreshLottieHeader(getContext());
        mSmartRefreshLayout.setRefreshHeader(mRefreshLottieHeader);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvAudienctList.setLayoutManager(mLinearLayoutManager);
        mAdapter = new LiveAudienctdapter(R.layout.item_live_audience_list, mData);
        rvAudienctList.setAdapter(mAdapter);
        liveViewModel = new ViewModelProvider(this).get(LiveViewModel.class);
        liveViewModel.getLiveAudiences().observe(this, lcee -> updateLiveAudience(lcee));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (onSelectUserListener!=null)
                    onSelectUserListener.onSelectUserCard(String.valueOf(mData.get(position).getUserId()));
            }
        });
        reLoadLiveGiftList(1);
    }

    private void reLoadLiveGiftList(int pageNum) {
        Map map = new HashMap();
        map.put("roomNo", roomNo);
        map.put("pageNum", pageNum);
        liveViewModel.reLoadLiveAudienceList(map);
    }
    private OnSelectUserListener onSelectUserListener;
    public void onShowUserCardListener(OnSelectUserListener onSelectUserListener){
        this.onSelectUserListener = onSelectUserListener;
    }
    private void updateLiveAudience(Lcee<LiveAudienceBean> lcee) {
        switch (lcee.status) {
            case Content:
                showComplete();
                if (pageNum == 1) {
                    mData.clear();
                    mData.addAll(lcee.data.getData().getList());
                    mAdapter.setData$com_github_CymChad_brvah(mData);
                } else {
                    mData.addAll(lcee.data.getData().getList());
                }
                if (lcee.data.getData().getList().size() < 20) {
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                mAdapter.notifyDataSetChanged();
                break;
            case Empty:
                if (pageNum == 1) {
                    showLayout(R.drawable.icon_zhanwu, R.string.live_match_no_data, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pageNum = 1;
                            reLoadLiveGiftList(pageNum);
                        }
                    }, false);
                    mSmartRefreshLayout.setEnableLoadMore(false);
                } else {
                    showComplete();
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                break;
            case Loading:
                showLoading();
                break;
            case Error:
                showError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pageNum = 1;
                        reLoadLiveGiftList(pageNum);
                    }
                });
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = SystemUtils.dp2px(getContext(), 435);
            dialog.getWindow().findViewById(R.id.design_bottom_sheet).
                    setBackground(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setDimAmount(0f);
            dialog.setCanceledOnTouchOutside(true);
        }
        final View view = getView();
        assert view != null;
        view.post(() -> {
            View parent = (View) view.getParent();
            CoordinatorLayout.LayoutParams params =
                    (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
            ViewPagerBottomSheetBehavior behavior = (ViewPagerBottomSheetBehavior) params.
                    getBehavior();
            behavior.setHideable(false);
            behavior.setPeekHeight(view.getMeasuredHeight());
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        reLoadLiveGiftList(pageNum);
        mSmartRefreshLayout.resetNoMoreData();
        mSmartRefreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        reLoadLiveGiftList(pageNum);
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public HintLayout getHintLayout() {
        return mView.findViewById(R.id.hl_status_hint);
    }

    public interface OnSelectUserListener{
        void onSelectUserCard(String userId);
    }
}
