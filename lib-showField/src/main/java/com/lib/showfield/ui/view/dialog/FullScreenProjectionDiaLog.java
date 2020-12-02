package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
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
 * 横屏（全屏）状态弹出dialog
 **/
public final class FullScreenProjectionDiaLog {
    public static final class Builder extends CommonProjectionDiaLog implements StatusAction {

        private HintLayout mHintLayout;
        private ProjectionAdapter mAdapter;
        private RecyclerView mListView;

        public Builder(Context context, String url) {
            super(context, url);
            setContentView(R.layout.dialog_full_projection);
            setAnimStyle(AnimAction.RIGHT);
            setGravity(Gravity.RIGHT);
            //setHeight(height);
            setWidth(ScreenUtils.getScreenWidth() / 3);
            initView();
            //  showLayout(R.drawable.icon_empty_comment, R.string.hint_layout_empty_devices, v -> { }, false);
        }

        public FullScreenProjectionDiaLog.Builder setListener(OnPrectionListener listener) {
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
            if (v.getId() == R.id.tv_tip) {//ToastUtils.showShort("全屏状态——放图");
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
    }
}
