package com.lib.showfield.ui.view.tiktalk;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.showfield.R;

import video.dkplayer_java.controller.ControlWrapper;
import video.dkplayer_java.controller.IControlComponent;
import video.dkplayer_java.player.VideoView;

public class ShowFieldViewError extends FrameLayout implements IControlComponent {

    private ControlWrapper mControlWrapper;
    private float mDownX;
    private float mDownY;
    private TextView tvRetry;

    public ShowFieldViewError(@NonNull Context context) {
        super(context);
    }

    public ShowFieldViewError(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowFieldViewError(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(R.layout.controller_view_show_field_error, this, true);
        tvRetry = findViewById(R.id.show_status_btn);
        tvRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zzy", "--------click---------");
                setVisibility(GONE);
                mControlWrapper.replay(false);
            }
        });

        setClickable(true);
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
            case VideoView.STATE_ERROR:
                setVisibility(VISIBLE);
                break;
            case VideoView.STATE_IDLE:
            case VideoView.STATE_PREPARED:
            case VideoView.STATE_PLAYING:
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                // True if the child does not want the parent to intercept touch events.
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float absDeltaX = Math.abs(ev.getX() - mDownX);
                float absDeltaY = Math.abs(ev.getY() - mDownY);
                if (absDeltaX > ViewConfiguration.get(getContext()).getScaledTouchSlop() ||
                        absDeltaY > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
