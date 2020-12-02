package com.lib.showfield.ui.view.tiktalk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.lib.showfield.R;

import video.dkplayer_java.controller.ControlWrapper;
import video.dkplayer_java.controller.IControlComponent;
import video.dkplayer_java.player.VideoView;

public class ShowFieldPrepareView extends FrameLayout implements IControlComponent {

    private ControlWrapper mControlWrapper;

    private LottieAnimationView mIvLoading;

    public ShowFieldPrepareView(@NonNull Context context) {
        super(context);
    }

    public ShowFieldPrepareView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowFieldPrepareView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(R.layout.dkplayer_layout_show_field_prepare_view,
                this, true);
        mIvLoading = findViewById(R.id.iv_prepare_loading);
    }

    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {

    }



    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoView.STATE_PREPARING:
                setVisibility(VISIBLE);
                mIvLoading.playAnimation();
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
            case VideoView.STATE_ERROR:
            case VideoView.STATE_PLAYING:
                mIvLoading.cancelAnimation();
                setVisibility(GONE);
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLocked) {

    }
}
