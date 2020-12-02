package video.dkplayer_players.exo;

import android.content.Context;

import video.dkplayer_java.player.PlayerFactory;


public class ExoMediaPlayerFactory extends PlayerFactory<ExoMediaPlayer> {

    public static ExoMediaPlayerFactory create() {
        return new ExoMediaPlayerFactory();
    }

    @Override
    public ExoMediaPlayer createPlayer(Context context) {
        return new ExoMediaPlayer(context);
    }
}
