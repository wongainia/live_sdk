package com.lib.showfield.ui.view.tiktalk;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.lib.showfield.R;
import com.lib.showfield.helper.KeyboardUtils;
import com.lib.showfield.interfaces.OnClickBarrageListener;
import com.lib.showfield.interfaces.OnClickFullChatListener;
import com.lib.showfield.interfaces.OnClickGiftListener;
import com.lib.showfield.interfaces.OnClickSendMsgListener;
import com.lib.showfield.interfaces.OnToggleListener;
import com.lib.showfield.other.KeyboardWatcher;

import video.dkplayer_java.controller.ControlWrapper;
import video.dkplayer_java.controller.IControlComponent;
import video.dkplayer_java.player.VideoView;
import video.dkplayer_java.util.PlayerUtils;

import static android.view.inputmethod.EditorInfo.IME_FLAG_NO_ENTER_ACTION;

/**
 * 直播底部控制栏
 */
public class ShowfieldLiveControlView extends FrameLayout implements IControlComponent, View.OnClickListener,
        TextView.OnEditorActionListener, KeyboardWatcher.SoftKeyboardStateListener {

    private ControlWrapper mControlWrapper;
    private Context mContext;

    private ImageView mPlayButtonFull;
    private ImageView barrage;
    private AppCompatEditText mEdtMsg;

    private AppCompatImageView mTvGift;

    protected OnClickBarrageListener mOnClickBarrageListener;
    protected OnClickGiftListener mOnClickGiftListener;
    protected OnClickFullChatListener mOnClickFullChatListener;
    protected OnToggleListener mOnToggleListener;
    protected OnClickSendMsgListener mOnClickSendMsgListener;

    private boolean isBarrage;

    public void setOnClickSendMsgListener(OnClickSendMsgListener listener) {
        this.mOnClickSendMsgListener = listener;
    }

    public ShowfieldLiveControlView(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public ShowfieldLiveControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowfieldLiveControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(R.layout.dkplayer_layout_show_field_live_control_view, this, true);
        mPlayButtonFull = findViewById(R.id.iv_play);
        mPlayButtonFull.setOnClickListener(this);
        mPlayButtonFull.setSelected(true);
        barrage = findViewById(R.id.iv_barrage);
        barrage.setOnClickListener(this);
        mEdtMsg = findViewById(R.id.tv_edit_start);
        mEdtMsg.setOnEditorActionListener(this);

        mTvGift = findViewById(R.id.iv_full_gift);
        mTvGift.setOnClickListener(this);

        barrage.setSelected(false);
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

        if (isVisible) {
            if (getVisibility() == GONE) {
                setVisibility(VISIBLE);
                if (anim != null) {
                    startAnimation(anim);
                }
            }
        } else {
            if (getVisibility() == VISIBLE) {
                setVisibility(GONE);
                if (anim != null) {
                    startAnimation(anim);
                }
            }
        }
    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoView.STATE_PREPARING:
                Log.d("zzy", "------STATE_PREPARING-------");
                break;
            case VideoView.STATE_ERROR:
                Log.d("zzy", "------STATE_ERROR-----");
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
                setVisibility(GONE);
                break;
            case VideoView.STATE_PLAYING:
                Log.d("zzy", "------STATE_PLAYING-----");
                mPlayButtonFull.setSelected(true);
                break;
            case VideoView.STATE_PAUSED:
                mPlayButtonFull.setSelected(false);
                break;
            case VideoView.STATE_BUFFERING:
                Log.d("zzy", "------STATE_BUFFERING-----");
                mPlayButtonFull.setSelected(mControlWrapper.isPlaying());
                break;
            case VideoView.STATE_BUFFERED:
                Log.d("zzy", "------STATE_BUFFERED-----");
                mPlayButtonFull.setSelected(mControlWrapper.isPlaying());
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                mControlWrapper.hide();
                setVisibility(GONE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                setVisibility(VISIBLE);
                if (mControlWrapper.isPlaying())
                    mPlayButtonFull.setSelected(true);
                break;
        }
    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLocked) {
        onVisibilityChanged(!isLocked, null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fullscreen) {
            toggleFullScreen();
        } else if (id == R.id.iv_play) {
            mControlWrapper.togglePlay();
            if (null != mOnToggleListener)
                mOnToggleListener.toggle(mControlWrapper.isPlaying() ? true : false);
        } else if (id == R.id.iv_barrage) {

            if (!isBarrage) {
                barrage.setSelected(true);
                isBarrage = true;
            } else {
                barrage.setSelected(false);
                isBarrage = false;
            }
            mOnClickBarrageListener.clickBarrage(isBarrage);
        } else if (id == R.id.iv_full_gift) {
            mOnClickGiftListener.onClickGift();
            //mOnClickFullChatListener.onClickFullChat();
        }
    }

    /**
     * 横竖屏切换
     */
    public void toggleFullScreen() {
        Activity activity = PlayerUtils.scanForActivity(getContext());
        mControlWrapper.toggleFullScreen(activity);
    }

    public void setOnClickBarrageListener(OnClickBarrageListener listener) {
        this.mOnClickBarrageListener = listener;
    }

    public void setOnClickGiftListener(OnClickGiftListener listener) {
        this.mOnClickGiftListener = listener;
    }

    public void setOnClickFullChatListener(OnClickFullChatListener listener) {
        this.mOnClickFullChatListener = listener;
    }

    public void setOnToggleListener(OnToggleListener mOnToggleListener) {
        this.mOnToggleListener = mOnToggleListener;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || actionId == IME_FLAG_NO_ENTER_ACTION) {
            String content = mEdtMsg.getText().toString().trim().replaceAll("\n", "");
            mOnClickSendMsgListener.onSendMsgClick(content);
            mEdtMsg.setText("");
            KeyboardUtils.hideKeyboard(mEdtMsg);
            return true;
        }
        return true;
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        Log.d("zzyiii", "------opened------");
    }

    @Override
    public void onSoftKeyboardClosed() {
        Log.d("zzyiii", "------closed------");
    }
}
