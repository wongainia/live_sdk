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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.angcyo.tablayout.DslTabLayout;
import com.angcyo.tablayout.delegate.ViewPager1Delegate;
import com.lib.showfield.R;
import com.lib.showfield.interfaces.OnGoUpListClickListener;
import com.lib.showfield.ui.adapter.FmPagerAdapter;
import com.lib.showfield.ui.fragment.live.contribute.DailyListFragment;
import com.lib.showfield.ui.fragment.live.contribute.GeneralListFragment;
import com.lib.showfield.ui.fragment.live.contribute.WeeklyListFragment;
import com.lib.showfield.utils.SystemUtils;

import java.util.ArrayList;

import biz.laenger.android.vpbs.ViewPagerBottomSheetBehavior;
import biz.laenger.android.vpbs.ViewPagerBottomSheetDialogFragment;

/**
 * Created by Roc
 * Date  2020/10/15 13:04
 * <p>
 * Description
 **/
public class PushContributeFragment extends ViewPagerBottomSheetDialogFragment implements OnGoUpListClickListener {

    private ViewPager mViewPager;
    public DslTabLayout mDslTabLayout;
    private String mRoomNo;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private DailyListFragment dailyListFragment;
    private WeeklyListFragment weeklyListFragment;
    private GeneralListFragment generalListFragment;
    private int anchorId = 0;
    private boolean isLiving;

    private Dialog dialog;

    protected OnGoUpListClickListener mOnGoUpListClickListener;

    public void setOnGoUpListClickListener(OnGoUpListClickListener listener) {
        this.mOnGoUpListClickListener = listener;
    }

    public static PushContributeFragment newInstance(String mRoomNo, int anchorId, Boolean isLiving) {
        PushContributeFragment livePushQualityFragment = new PushContributeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomNo", mRoomNo);
        bundle.putBoolean("isLiving", isLiving);
        bundle.putInt("anchorId", anchorId);
        livePushQualityFragment.setArguments(bundle);
        return livePushQualityFragment;
    }

    public PushContributeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.fragment_sheet_push_contribute_list, container, false);
        mViewPager = mView.findViewById(R.id.vp_contribute);
        mDslTabLayout = mView.findViewById(R.id.dtl_contribute_list);
        isLiving = getArguments().getBoolean("isLiving");
        anchorId = getArguments().getInt("anchorId");
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRoomNo = requireArguments().getString("roomNo");
        dailyListFragment = DailyListFragment.newInstance(mRoomNo, isLiving);
        weeklyListFragment = WeeklyListFragment.newInstance(mRoomNo, isLiving);
        generalListFragment = GeneralListFragment.newInstance(mRoomNo, isLiving);

        fragments.add(dailyListFragment);
        fragments.add(weeklyListFragment);
        fragments.add(generalListFragment);

        dailyListFragment.setParams(anchorId);
        weeklyListFragment.setParams(anchorId);
        generalListFragment.setParams(anchorId);

        FmPagerAdapter pagerAdapter = new FmPagerAdapter(getChildFragmentManager(),
                0,
                fragments, null);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(1);


        mDslTabLayout.setupViewPager(new ViewPager1Delegate(mViewPager, mDslTabLayout));
        mDslTabLayout.setTabDefaultIndex(0);

        dailyListFragment.setOnGoUpListClickListener(this);
        weeklyListFragment.setOnGoUpListClickListener(this);
        generalListFragment.setOnGoUpListClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = SystemUtils.dp2px(getContext(), 435);
            //ScreenUtils.getScreenHeight();
            //(int) (ScreenUtils.getScreenHeight() - ScreenUtils.getScreenWidth() * 0.9 / 1.6 - SystemUtils.dp2px(getContext(), 125));

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
    public void onGoUpClick() {
        mOnGoUpListClickListener.onGoUpClick();
    }
}
