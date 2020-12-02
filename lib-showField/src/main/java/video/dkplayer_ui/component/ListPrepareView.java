package video.dkplayer_ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.showfield.R;

import video.dkplayer_java.controller.ControlWrapper;
import video.dkplayer_java.controller.IControlComponent;
import video.dkplayer_java.player.VideoView;

/**
 * Created by JoeyChow
 * Date  2020-05-28 13:31
 * <p>
 * Description
 **/
public class ListPrepareView extends FrameLayout implements IControlComponent {

    private ControlWrapper mControlWrapper;
    private ImageView mThumb;

    public ListPrepareView(@NonNull Context context) {
        super(context);
    }

    public ListPrepareView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListPrepareView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        LayoutInflater.from(getContext()).inflate(R.layout.dkplayer_layout_list_autovideo, this, true);
        mThumb = findViewById(R.id.thumb);
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
                mThumb.setVisibility(View.VISIBLE);
                break;
            case VideoView.STATE_START_ABORT:
                setVisibility(VISIBLE);
                mThumb.setVisibility(View.VISIBLE);
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
