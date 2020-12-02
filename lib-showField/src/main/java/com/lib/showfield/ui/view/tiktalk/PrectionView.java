package com.lib.showfield.ui.view.tiktalk;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.showfield.R;
import com.lib.showfield.interfaces.OnContraollerPrectionListener;

import video.dkplayer_java.controller.ControlWrapper;
import video.dkplayer_java.controller.IControlComponent;
import video.dkplayer_java.player.VideoView;


/**
 * 投屏成功后展示的界面
 */
public class PrectionView extends FrameLayout implements IControlComponent, View.OnClickListener {

    private ControlWrapper mControlWrapper;
    private OnContraollerPrectionListener mListener;
    private TextView mTvTitle;

    public PrectionView(@NonNull Context context) {
        super(context);
    }

    public PrectionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PrectionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(R.layout.item_prection, this, true);
        setClickable(true);
        findViewById(R.id.tv_exit).setOnClickListener(this);
        findViewById(R.id.tv_update_devices).setOnClickListener(this);
        mTvTitle = findViewById(R.id.tv_prection_title);

    }

    public PrectionView setListener(OnContraollerPrectionListener mListener) {
        this.mListener = mListener;
        return this;
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
        Log.d("prection_status", "" + playState);

        switch (playState) {
            // 投屏
            case VideoView.STATE_START_PRECTION:
                setVisibility(VISIBLE);
                break;
            // 取消投屏
            case VideoView.STATE_START_CANCEL_PRECTION:
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
    public void onLockStateChanged(boolean isLock) {

    }

    @Override
    public void onClick(View v) {
        if (mListener == null) return;
        if (v.getId() == R.id.tv_exit) {
            //Toast.makeText(getContext(), "退出", Toast.LENGTH_LONG).show();
            mListener.exitDevices();
        } else if (v.getId() == R.id.tv_update_devices) {
            //Toast.makeText(getContext(), "更换", Toast.LENGTH_LONG).show();
            mListener.updateDevices();
        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }
}
