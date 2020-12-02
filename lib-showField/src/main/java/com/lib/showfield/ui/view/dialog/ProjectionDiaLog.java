package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.lib.showfield.R;
import com.lib.showfield.aop.SingleClick;
import com.lib.showfield.base.action.AnimAction;
import com.lib.showfield.base.action.StatusAction;
import com.lib.showfield.interfaces.OnPrectionListener;
import com.lib.showfield.ui.adapter.ProjectionAdapter;
import com.lib.showfield.ui.view.widget.HintLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * 垂直状态弹出dialog
 **/
public final class ProjectionDiaLog {
    public static final class Builder extends CommonProjectionDiaLog implements StatusAction {

        private HintLayout mHintLayout;
        private ProjectionAdapter mAdapter;
        private RecyclerView mListView;

        public Builder(Context context, int height, String url) {
            super(context, url);
            setContentView(R.layout.dialog_projection);
            setAnimStyle(AnimAction.BOTTOM);
            setHeight(height);
            initView();
        }

        public ProjectionDiaLog.Builder setListener(OnPrectionListener listener) {
            mOnPrectionListener = listener;
            return this;
        }

        private void initView() {
            mHintLayout = findViewById(R.id.hl_status_hint);
            mListView = findViewById(R.id.rv_list);
            findViewById(R.id.tv_tip).setOnClickListener(this);
            mAdapter = new ProjectionAdapter(getContext(), new ArrayList<>(), mListView);
            mListView.setAdapter(mAdapter);
            startAnimaition(findViewById(R.id.img_search));
            mAdapter.setListener((view, position) -> {
                setSelectInfo(mAdapter.getData().get(position));
            });

            starConnect();
        }

        @Override
        protected void updateAdapter(List<LelinkServiceInfo> list) {
            mAdapter.updateDatas(list);
        }


        @SingleClick
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tv_tip) {
                initTip();
            }
        }

        @Override
        public HintLayout getHintLayout() {
            return mHintLayout;
        }


        private float mStartDegrees = 0f;
        private float mEndDegrees = 360f;

        private void startAnimaition(View view) {
            RotateAnimation rotate = new RotateAnimation(mStartDegrees, mEndDegrees,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            LinearInterpolator lin = new LinearInterpolator();
            rotate.setInterpolator(lin);
            rotate.setDuration(1000);//设置动画持续周期
            rotate.setRepeatCount(Animation.INFINITE);//设置重复次数
            rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
            view.setAnimation(rotate);

            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            view.startAnimation(rotate);

        }

        @Override
        public void dismiss() {
            findViewById(R.id.img_search).clearAnimation();
            super.dismiss();
        }

        public void initTip() {
            BottomSheetDialog dialog = new BottomSheetDialog(getContext());
            NestedScrollView view = (NestedScrollView) LayoutInflater.from(getContext()).inflate(R.layout.item_proction_tip, null);
            dialog.setContentView(view);
            FrameLayout bottomSheet = dialog.getDelegate().findViewById(com.google.android.material.R.id.design_bottom_sheet);
            //bottomSheet.setBackgroundColor(Color.parseColor("#00000000"));

            ViewGroup.LayoutParams originLayoutParams = bottomSheet.getLayoutParams();
            originLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            bottomSheet.setLayoutParams(originLayoutParams);


            BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from(bottomSheet);
            mDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            dialog.show();
            AppCompatImageView bar = view.findViewById(R.id.title_bar);
            bar.setOnClickListener(v -> {
                dialog.dismiss();
            });
        }
    }
}
