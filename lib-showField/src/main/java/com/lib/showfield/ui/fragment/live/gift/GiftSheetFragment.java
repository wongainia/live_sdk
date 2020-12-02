package com.lib.showfield.ui.fragment.live.gift;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.lib.showfield.R;
import com.lib.showfield.base.action.StatusAction;
import com.lib.showfield.bean.gift.bean.GiftListParam;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.helper.CheckCoinsHelper;
import com.lib.showfield.http.respones.live.GiftResponse;
import com.lib.showfield.interfaces.OnSelectGiftListener;
import com.lib.showfield.interfaces.OnSendGiftClickListener;
import com.lib.showfield.interfaces.OnUpdateUserCoinsListener;
import com.lib.showfield.model.LiveViewModel;
import com.lib.showfield.ui.adapter.GiftBannerAdapter;
import com.lib.showfield.ui.view.widget.HintLayout;
import com.lib.showfield.utils.SystemUtils;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.indicator.DrawableIndicator;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.List;

import biz.laenger.android.vpbs.ViewPagerBottomSheetBehavior;
import biz.laenger.android.vpbs.ViewPagerBottomSheetDialogFragment;

import static com.lib.showfield.common.Constants.ANDROID3X16X16CURRENCYMONEYICONURL;
import static com.lib.showfield.common.Constants.COMMON_RESOURCE;
import static com.lib.showfield.common.Constants.PAYSWITCH;

/**
 * Created by upingu
 * Date  2020/6/9 16:37
 * <p>
 * Description
 **/
public class GiftSheetFragment extends ViewPagerBottomSheetDialogFragment
        implements View.OnClickListener, OnSelectGiftListener, StatusAction {

    private BannerViewPager<List<GiftResponse>, GiftBannerViewHolder> mBannerViewPager;
    private DrawableIndicator mIndicatorView;
    private AppCompatTextView mTvSend;
    private AppCompatTextView mTvNoSend;
    private AppCompatTextView mTvUserCoins;
    private LinearLayoutCompat mLlGotoChargeText;
    private Dialog dialog;
    private HintLayout mHintLayout;

    private List<List<GiftResponse>> mList;
    private long mCoinsNum;

    //单页的礼物数据
    private List<GiftResponse> mSingleList;
    private GiftBannerAdapter mGiftBannerAdapter;
    private int mSelectPosition = 0;

    private boolean isSelect;
    //vm
    private LiveViewModel liveViewModel;
    private String mRoomNo;

//    private int mDoubleHit;
//    private int sendType;


    protected OnSendGiftClickListener mOnSendGiftClickListener;
    protected OnUpdateUserCoinsListener mOnUpdateUserCoinsListener;

    private AppCompatImageView ivMoneyIcon;

    public void setOnSendGiftClickListener(OnSendGiftClickListener listener) {
        this.mOnSendGiftClickListener = listener;
    }

    public void setOnUpdateUserCoinsListener(OnUpdateUserCoinsListener listener) {
        this.mOnUpdateUserCoinsListener = listener;
    }

    public static GiftSheetFragment newInstance(String roomNo) {
        GiftSheetFragment fragment = new GiftSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomNo", roomNo);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GiftSheetFragment newInstance(String roomNo, long coins) {
        GiftSheetFragment fragment = new GiftSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomNo", roomNo);
        bundle.putLong("coins", coins);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.fragment_sheet_gift, container, false);
        mBannerViewPager = mView.findViewById(R.id.bvp_gift);
        mIndicatorView = mView.findViewById(R.id.indicator_gift);
        mTvSend = mView.findViewById(R.id.tv_sheet_gift_send);
        mTvUserCoins = mView.findViewById(R.id.tv_sheet_gift_user_coins);
        mHintLayout = mView.findViewById(R.id.hl_gift);
        ivMoneyIcon = mView.findViewById(R.id.iv_money_icon);
        mTvNoSend = mView.findViewById(R.id.tv_sheet_gift_no_send);
        mLlGotoChargeText = mView.findViewById(R.id.ll_go_to_charge);
        //动态配置单位图标
        String icon = SPUtils.getInstance(COMMON_RESOURCE).getString(ANDROID3X16X16CURRENCYMONEYICONURL);

        Glide.with(getContext()).
                load(icon)
                .circleCrop()
                .error(R.drawable.icon_avatar)
                .into(ivMoneyIcon);

        if (SPUtils.getInstance(COMMON_RESOURCE).getBoolean(PAYSWITCH, true)) {
            mLlGotoChargeText.setVisibility(View.VISIBLE);
        } else {
            mLlGotoChargeText.setVisibility(View.GONE);
        }

        mTvSend.setOnClickListener(this);

        liveViewModel = new ViewModelProvider(this).get(LiveViewModel.class);

        liveViewModel.getGiftListValue().observe(this, new Observer<Lcee<List<GiftResponse>>>() {
            @Override
            public void onChanged(Lcee<List<GiftResponse>> lcee) {
                updateGiftListView(lcee);
            }
        });

        //liveViewModel.reLoadUserCoins(false);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager();
    }

    private void setupViewPager() {

        mList = new ArrayList<>();

        /**
         * 自定义指示器配置属性
         */
        mIndicatorView.setIndicatorGap(12);
        mIndicatorView.setIndicatorDrawable(R.drawable.icon_banner_normal,
                R.drawable.icon_banner_selected);
        mIndicatorView.setIndicatorSize(20, 20,
                20, 20);
        mIndicatorView.setSlideMode(IndicatorSlideMode.WORM)
                .setIndicatorStyle(IndicatorStyle.CIRCLE);

        mGiftBannerAdapter = new GiftBannerAdapter(mSelectPosition);
        mGiftBannerAdapter.setOnSelectGiftListener(this);

        /**
         * banner
         */
        mBannerViewPager
                .setScrollDuration(1000)
                .setCanLoop(false)
                .setAutoPlay(false)
                .setPageStyle(PageStyle.NORMAL)
                .setIndicatorVisibility(View.GONE)
                .setIndicatorView(mIndicatorView)
                .setAdapter(mGiftBannerAdapter)
                .create();

        mRoomNo = getArguments().getString("roomNo");
        mCoinsNum = getArguments().getLong("coins");

        mTvUserCoins.setText(String.valueOf(mCoinsNum));
        mOnUpdateUserCoinsListener.onUpdateUserCoins(mCoinsNum);

        Log.d("zzy", "---giftsheet-----mRoomNo-----: " + mRoomNo);

        mLlGotoChargeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getContext(), MyAccountActivity.class);
                //startActivity(intent);
                ToastUtils.showShort("去充值");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //请求礼物列表
                liveViewModel.reloadGiftListValues(new GiftListParam(mRoomNo, 2));
            }
        }, 200);

        mBannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mSingleList = new ArrayList<>();
                mSingleList = mList.get(position);
            }
        });
    }

    /**
     * 礼物列表回调
     *
     * @param lcee
     */
    private void updateGiftListView(Lcee<List<GiftResponse>> lcee) {
        switch (lcee.status) {
            case Content:
                showComplete();
                List<List<GiftResponse>> beans = fixedGrouping(lcee.data.getData(), 8);
                this.mList = beans;
                mBannerViewPager.refreshData(beans);
                Log.d("zzy", "------余额---: " + mCoinsNum);
                if (mCoinsNum != 0 && mCoinsNum >= beans.get(0).get(mSelectPosition).getGiftPrice()) {
                    mTvSend.setVisibility(View.VISIBLE);
                    mTvNoSend.setVisibility(View.GONE);
                } else {
                    mTvSend.setVisibility(View.GONE);
                    mTvNoSend.setVisibility(View.VISIBLE);
                }

                break;
            case Empty:
                showLayout(R.drawable.ic_hint_empty, R.string.no_gift_text, null, false);
                break;
        }
    }

//    private void updateUserCoins(Lcee<UserCoins> lcee) {
//        switch (lcee.status) {
//            case Content:
//                mCoinsNum = lcee.data.getData().getRemainingSum();
//                mTvUserCoins.setText(String.valueOf(lcee.data.getData().getRemainingSum()));
//                mOnUpdateUserCoinsListener.onUpdateUserCoins(mCoinsNum);
//                break;
//            case Error:
//                Log.e("zzy", "获取余额失败");
//                break;
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = SystemUtils.dp2px(getContext(), 320);

            dialog.getWindow().findViewById(R.id.design_bottom_sheet).
                    setBackground(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setDimAmount(0.6f);
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
    public void onClick(View view) {
        if (view.getId() == R.id.tv_sheet_gift_send) {
            //发送礼物
//            if (mSingleList.get(mSelectPosition).getDoubleHit() == 1) {
//                mOnSendGiftClickListener.onClickSendGift(mSingleList.get(mSelectPosition).getTheGiftId(),
//                        mSingleList.get(mSelectPosition).getGiftPrice(), 1);
//
//            } else {
//                mOnSendGiftClickListener.onClickSendGift(mSingleList.get(mSelectPosition).getTheGiftId(),
//                        mSingleList.get(mSelectPosition).getGiftPrice(), 0);
//            }


            CheckCoinsHelper.getInstance()
                    .checkUserCoins(mSingleList.get(mSelectPosition).getGiftPrice(),
                            mSingleList.get(mSelectPosition).getTheGiftId());

            dialog.dismiss();
//                } else {
//                    dialog.dismiss();
//                    Intent intent = new Intent(getContext(), MyAccountActivity.class);
//                    startActivity(intent);
//                }
//            } else {
//                Toast.makeText(getContext(), "请选择礼物", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    @Override
    public void onClickSelectGift(int position, boolean isSelected) {
        isSelect = isSelected;
        this.mSelectPosition = position;
        if (!ObjectUtils.isEmpty(mSingleList)) {
            Log.d("zzy", "-----礼物金额------: " + mSingleList.get(position).getGiftPrice());
            Log.d("zzy", "-----是否选中------: " + isSelected);
            if (isSelected) {
                if (mCoinsNum != 0 && mCoinsNum >= mSingleList.get(position).getGiftPrice()) {
                    Log.d("zzy", "---------sssssssssdddssssssss--");
                    mTvSend.setVisibility(View.VISIBLE);
                    mTvNoSend.setVisibility(View.GONE);
                } else {
                    mTvSend.setVisibility(View.GONE);
                    mTvNoSend.setVisibility(View.VISIBLE);
                }
            } else {
                mTvSend.setVisibility(View.GONE);
                mTvNoSend.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    public void setCutCoins(long mGiftPrice) {
        long mNowPrice = Long.parseLong(mTvUserCoins.getText().toString()) - mGiftPrice;
        mTvUserCoins.setText(String.valueOf(mNowPrice));
    }


    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;

        List<List<T>> result = new ArrayList<List<T>>();

        int sourceSize = source.size();
        int size;
        if (sourceSize % n != 0) {
            size = (sourceSize / n) + 1;
        } else {
            size = (sourceSize / n);
        }


        for (int i = 0; i < size; i++) {
            List<T> subset = new ArrayList<T>();
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }
}
