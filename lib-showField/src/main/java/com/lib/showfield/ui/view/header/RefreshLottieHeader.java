package com.lib.showfield.ui.view.header;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.airbnb.lottie.LottieAnimationView;
import com.lib.showfield.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by JoeyChow
 * Date  2020-04-14 17:42
 * <p>
 * Description
 **/
public class RefreshLottieHeader extends LinearLayout implements RefreshHeader {

    /**
     * The M animation view.
     */
    LottieAnimationView mAnimationView;

    AppCompatTextView mStatusText;

    /**
     * Instantiates a new My refresh lottie header.
     *
     * @param context the context
     */
    public RefreshLottieHeader(Context context) {
        super(context);
        initView(context);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        mAnimationView.playAnimation();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mAnimationView.cancelAnimation();
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout,
                               @NonNull RefreshState oldState,
                               @NonNull RefreshState newState) {

        Log.d("zzy", "--oldState--: " + oldState);
        Log.d("zzy", "--newState--: " + newState);

        if (newState.equals(RefreshState.PullDownToRefresh)) {
            mStatusText.setText("正在下拉");
        } else if (newState.equals(RefreshState.ReleaseToRefresh)) {
            mStatusText.setText("松开进行刷新");
        } else if (newState.equals(RefreshState.Refreshing)) {
            mStatusText.setText("加载中");
        } else if (newState.equals(RefreshState.RefreshFinish)) {
            mStatusText.setText("刷新完成");
        }
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.loading_lottie_refrush, this);
        mStatusText = view.findViewById(R.id.tv_refresh_status);
        mAnimationView = view.findViewById(R.id.loading_lottie);
    }

    /**
     * Set animation view json.
     *
     * @param animName json文件名
     */
    public void setAnimationViewJson(String animName) {
        mAnimationView.setAnimation(animName);
    }

    /**
     * Set animation view json.
     *
     * @param anim the anim
     */
    public void setAnimationViewJson(Animation anim) {
        mAnimationView.setAnimation(anim);
    }
}
