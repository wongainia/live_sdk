package video.dkplayer_ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.lib.showfield.R;

import video.dkplayer_java.controller.ControlWrapper;
import video.dkplayer_java.controller.IControlComponent;
import video.dkplayer_java.player.VideoView;
import video.dkplayer_java.player.VideoViewManager;

/**
 * 准备播放界面
 */
public class PrepareView extends FrameLayout implements IControlComponent {

    private ControlWrapper mControlWrapper;
    
    private ImageView mThumb;
    private ImageView mStartPlay;
    private LottieAnimationView mLoading;
    private FrameLayout mNetWarning;
    private LinearLayout mLlLoading;

    public PrepareView(@NonNull Context context) {
        super(context);
    }

    public PrepareView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PrepareView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    {
        LayoutInflater.from(getContext()).inflate(R.layout.dkplayer_layout_prepare_view, this, true);
        mThumb = findViewById(R.id.thumb);
        mStartPlay = findViewById(R.id.start_play);
        mLlLoading = findViewById(R.id.ll_loading);
        mLoading = findViewById(R.id.loading);
        mNetWarning = findViewById(R.id.net_warning_layout);
        findViewById(R.id.status_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mNetWarning.setVisibility(GONE);
                VideoViewManager.instance().setPlayOnMobileNetwork(true);
                mControlWrapper.start();
            }
        });
    }

    /**
     * 设置点击此界面开始播放
     */
    public void setClickStart() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mControlWrapper.start();
            }
        });
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
                bringToFront();
                setVisibility(VISIBLE);
                mStartPlay.setVisibility(View.GONE);
                mNetWarning.setVisibility(GONE);
                mLlLoading.setVisibility(View.VISIBLE);
                mLoading.playAnimation();
                break;
            case VideoView.STATE_PLAYING:
            case VideoView.STATE_PAUSED:
            case VideoView.STATE_ERROR:
            case VideoView.STATE_BUFFERING:
            case VideoView.STATE_BUFFERED:
            case VideoView.STATE_PLAYBACK_COMPLETED:
                setVisibility(GONE);
                break;
            case VideoView.STATE_IDLE:
                setVisibility(VISIBLE);
                bringToFront();
                mLlLoading.setVisibility(View.GONE);
                mLoading.cancelAnimation();
                mNetWarning.setVisibility(GONE);
                mStartPlay.setVisibility(View.VISIBLE);
                mThumb.setVisibility(View.VISIBLE);
                break;
            case VideoView.STATE_START_ABORT:
                setVisibility(VISIBLE);
                mNetWarning.setVisibility(VISIBLE);
                mNetWarning.bringToFront();
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
