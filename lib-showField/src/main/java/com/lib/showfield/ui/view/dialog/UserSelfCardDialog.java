package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.lib.showfield.R;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.base.action.AnimAction;
import com.lib.showfield.http.glide.GlideApp;
import com.lib.showfield.http.respones.live.UserCard;
import com.lib.showfield.utils.FindDrawableByName;

/**
 * Created by upingu
 * Date  2020/6/11 10:21
 * <p>
 * Description
 **/
public final class UserSelfCardDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private AppCompatImageView mIvAvatar;
        private AppCompatImageView mIvLevel;
        private AppCompatTextView mTvNickname;
        private AppCompatTextView mTvUserId;
        private AppCompatTextView mTvFansNum;
        private AppCompatImageView mIvRoom;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_chat_self_card);
            setAnimStyle(AnimAction.BOTTOM);

            mIvAvatar = findViewById(R.id.dialog_self_card_avatar);
            mIvLevel = findViewById(R.id.dialog_self_card_level);
            mTvNickname = findViewById(R.id.dialog_self_card_nickname);
            mTvUserId = findViewById(R.id.dialog_self_card_uid);
            mTvFansNum = findViewById(R.id.dialog_self_card_fans_num);
            mIvRoom = findViewById(R.id.dialog_self_card_room);
        }

        public Builder setData(UserCard userCard, int userId) {
            GlideApp.with(getContext())
                    .load(userCard.getAvatar())
                    .circleCrop()
                    .into(mIvAvatar);

            mTvNickname.setText(userCard.getNickname());
            mTvUserId.setText(userCard.getRoomNo() + "");
            mTvFansNum.setText(userCard.getFansNum() + "");


            if (userId == userCard.getUserId()) {
                mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                        "icon_player_level" + userCard.getLevel()));
            } else {
                mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                        "icon_user_level" + userCard.getLevel()));
            }

            if (userCard.isIsManager()) {
                mIvRoom.setVisibility(View.VISIBLE);
            } else {
                mIvRoom.setVisibility(View.GONE);
            }

            return this;
        }
    }
}
