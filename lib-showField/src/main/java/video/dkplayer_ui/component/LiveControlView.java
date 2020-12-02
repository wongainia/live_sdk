package video.dkplayer_ui.component;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.lib.showfield.R;
import com.lib.showfield.interfaces.OnClickBarrageListener;
import com.lib.showfield.interfaces.OnClickFullChatListener;
import com.lib.showfield.interfaces.OnClickGiftListener;
import com.lib.showfield.interfaces.OnClickSendMsgListener;

import video.dkplayer_java.controller.ControlWrapper;
import video.dkplayer_java.controller.IControlComponent;
import video.dkplayer_java.player.VideoView;
import video.dkplayer_java.util.PlayerUtils;

/**
 * 直播底部控制栏
 */
public class LiveControlView extends FrameLayout implements IControlComponent, View.OnClickListener, TextView.OnEditorActionListener {

    private ControlWrapper mControlWrapper;
    private Context mContext;

    private ImageView mFullScreen;
    private RelativeLayout mBottomContainerNormal;
    private LinearLayout mBottomContainerFull;
    private ImageView mPlayButtonNormal;
    private ImageView mPlayButtonFull;
    private ImageView barrage;
    private AppCompatEditText mEdtMsg;
    private AppCompatTextView mTitle;

    private AppCompatTextView mTvRoomNum;
    private AppCompatTextView mTvHotNum;
    private AppCompatImageView mTvGift;

    private boolean isBarrage;

    protected OnClickBarrageListener mOnClickBarrageListener;
    protected OnClickGiftListener mOnClickGiftListener;
    protected OnClickFullChatListener mOnClickFullChatListener;
    protected OnClickSendMsgListener mOnClickSendMsgListener;

    public void setOnClickSendMsgListener(OnClickSendMsgListener listener) {
        this.mOnClickSendMsgListener = listener;
    }

    public LiveControlView(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public LiveControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(R.layout.dkplayer_layout_live_control_view, this, true);
        mFullScreen = findViewById(R.id.fullscreen);
        mFullScreen.setOnClickListener(this);
        mBottomContainerNormal = findViewById(R.id.bottom_container_normal);
        mBottomContainerFull = findViewById(R.id.bottom_container_full);
        mPlayButtonFull = findViewById(R.id.iv_play);
        mPlayButtonFull.setOnClickListener(this);
        mPlayButtonNormal = findViewById(R.id.iv_play_normal);
        mPlayButtonNormal.setOnClickListener(this);
        barrage = findViewById(R.id.iv_barrage);
        barrage.setOnClickListener(this);
        mEdtMsg = findViewById(R.id.tv_edit_start);
        mEdtMsg.setOnEditorActionListener(this);

        mTitle = findViewById(R.id.tv_play_title);

        mTvGift = findViewById(R.id.iv_full_gift);
        mTvGift.setOnClickListener(this);

        barrage.setSelected(false);
        isBarrage = true;

        mTvRoomNum = findViewById(R.id.tv_room_num_normal);
        mTvHotNum = findViewById(R.id.tv_hot_num_normal);
    }

    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    public void setRoomNum(String roomNum) {
        mTvRoomNum.setText(roomNum);
    }

    public void setHotNum(String hotNum) {
        mTvHotNum.setText(hotNum);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
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
            case VideoView.STATE_IDLE:
            case VideoView.STATE_START_ABORT:
            case VideoView.STATE_PREPARING:
            case VideoView.STATE_PREPARED:
            case VideoView.STATE_ERROR:
            case VideoView.STATE_PLAYBACK_COMPLETED:
                setVisibility(GONE);
                break;
            case VideoView.STATE_PLAYING:
                mPlayButtonNormal.setSelected(true);
                mPlayButtonFull.setSelected(true);
                break;
            case VideoView.STATE_PAUSED:
                mPlayButtonNormal.setSelected(false);
                mPlayButtonFull.setSelected(false);
                break;
            case VideoView.STATE_BUFFERING:
            case VideoView.STATE_BUFFERED:
                mPlayButtonNormal.setSelected(mControlWrapper.isPlaying());
                mPlayButtonFull.setSelected(mControlWrapper.isPlaying());
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                mBottomContainerNormal.setVisibility(VISIBLE);
                mBottomContainerFull.setVisibility(GONE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                mBottomContainerNormal.setVisibility(GONE);
                mBottomContainerFull.setVisibility(VISIBLE);
                break;
        }

        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity != null && mControlWrapper.hasCutout()) {
            int orientation = activity.getRequestedOrientation();
            int cutoutHeight = mControlWrapper.getCutoutHeight();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                mBottomContainerNormal.setPadding(0, 0, 0, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                mBottomContainerNormal.setPadding(cutoutHeight, 0, 0, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                mBottomContainerNormal.setPadding(0, 0, cutoutHeight, 0);
            }
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
        } else if (id == R.id.iv_play_normal) {
            mControlWrapper.togglePlay();
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            String content = mEdtMsg.getText().toString().trim().replaceAll("\n", "")
                    .replaceAll(" ", "");
            mOnClickSendMsgListener.onSendMsgClick(content);
            mEdtMsg.setText("");
            return true;
        }
        return false;
    }
}
