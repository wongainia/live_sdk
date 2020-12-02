package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.lib.showfield.R;
import com.lib.showfield.aop.SingleClick;
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
public final class UserPermissionDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private OnListener mListener;

        private boolean isSetupManager;

        private AppCompatImageView mIvAvatar;
        private AppCompatImageView mIvLevel;
        private AppCompatTextView mTvNickname;
        private AppCompatTextView mTvUserId;
        private AppCompatTextView mTvFansNum;
        private AppCompatImageView mIvRoom;
        private AppCompatImageView mIvRlFocus;

        private AppCompatTextView mTvFocus;
        private AppCompatTextView mTvManager;
        private AppCompatTextView mTvForbidden;

        private LinearLayoutCompat mLlBottom;
        private RelativeLayout mRlFocusBottom;

        private int mOpType;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_chat_permission);
            setAnimStyle(AnimAction.BOTTOM);

            mIvAvatar = findViewById(R.id.dialog_user_card_avatar);
            mIvLevel = findViewById(R.id.dialog_user_card_level);
            mTvNickname = findViewById(R.id.dialog_user_card_nickname);
            mTvUserId = findViewById(R.id.dialog_user_card_uid);
            mTvFansNum = findViewById(R.id.dialog_user_card_fans_num);
            mIvRoom = findViewById(R.id.dialog_user_card_room);
            mIvRlFocus = findViewById(R.id.iv_permission_bottom_focus);

            mTvFocus = findViewById(R.id.tv_permission_focus);
            mTvManager = findViewById(R.id.tv_permission_setup);
            mTvForbidden = findViewById(R.id.tv_permission_forbidden);

            mLlBottom = findViewById(R.id.ll_permission_bottom);
            mRlFocusBottom = findViewById(R.id.rl_focus_bottom);

            setOnClickListener(R.id.tv_permission_focus,
                    R.id.tv_permission_setup,
                    R.id.tv_permission_forbidden,
                    R.id.iv_permission_bottom_focus);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setData(UserCard userCard, int userType, int userId) {
            GlideApp.with(getContext())
                    .load(userCard.getAvatar())
                    .circleCrop()
                    .into(mIvAvatar);

            Log.d("zzy", "------userId: " + userId);

            mTvNickname.setText(userCard.getNickname());
            mTvUserId.setText(userCard.getRoomNo() + "");
            mTvFansNum.setText(userCard.getFansNum() + "");

            Log.d("zzy", "-----showUserCardDialog----mUserType-: " + userType);

            if (userType == 3) {
                mLlBottom.setVisibility(View.VISIBLE);
                mRlFocusBottom.setVisibility(View.GONE);
                mTvForbidden.setVisibility(View.VISIBLE);
                mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                        "icon_user_level" + userCard.getLevel()));

                if (userCard.isIsManager()) {
                    mIvRoom.setVisibility(View.VISIBLE);
                    mTvManager.setVisibility(View.VISIBLE);
                    mTvForbidden.setVisibility(View.GONE);
                    mTvManager.setText("解任房管");
                    isSetupManager = false;
                } else {
                    mTvManager.setVisibility(View.VISIBLE);
                    mIvRoom.setVisibility(View.GONE);
                    mTvManager.setText("任命房管");
                    isSetupManager = true;
                }

                if (userCard.isIsFollow()) {
                    mTvFocus.setText("已关注");
                    mOpType = 1;
                } else {
                    mTvFocus.setText("关注");
                    mOpType = 0;
                }

            } else if (userType == 1) {
                if (userCard.isIsManager()) {
                    mIvRoom.setVisibility(View.VISIBLE);
                    mLlBottom.setVisibility(View.GONE);
                    mRlFocusBottom.setVisibility(View.VISIBLE);

                    if (userCard.isIsFollow()) {
                        mOpType = 1;
                        mIvRlFocus.setImageResource(R.drawable.icon_focused_card);
                    } else {
                        mOpType = 0;
                        mIvRlFocus.setImageResource(R.drawable.icon_focus_yellow_card);
                    }

                    if (userId == userCard.getUserId()) {
                        mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_player_level" + userCard.getLevel()));
                    } else {
                        mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_user_level" + userCard.getLevel()));
                    }


                } else {
                    mIvRoom.setVisibility(View.GONE);
                    if (userCard.isIsFollow()) {
                        mTvFocus.setText("已关注");
                        mOpType = 1;
                    } else {
                        mTvFocus.setText("关注");
                        mOpType = 0;
                    }

                    if (userId == userCard.getUserId()) {
                        mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_player_level" + userCard.getLevel()));

                        mLlBottom.setVisibility(View.GONE);
                        mRlFocusBottom.setVisibility(View.VISIBLE);

                    } else {
                        mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                                "icon_user_level" + userCard.getLevel()));

                        mLlBottom.setVisibility(View.VISIBLE);
                        mRlFocusBottom.setVisibility(View.GONE);
                        mTvManager.setVisibility(View.GONE);

                        mTvForbidden.setText("禁言");
                        mTvForbidden.setEnabled(true);
                    }
                }

            } else if (userType == 0) {
                mLlBottom.setVisibility(View.GONE);
                mRlFocusBottom.setVisibility(View.VISIBLE);

                if (userCard.isIsManager()) {
                    mIvRoom.setVisibility(View.VISIBLE);
                } else {
                    mIvRoom.setVisibility(View.GONE);
                }

                if (userCard.isIsFollow()) {
                    mIvRlFocus.setImageResource(R.drawable.icon_focused_card);
                    mOpType = 1;
                } else {
                    mIvRlFocus.setImageResource(R.drawable.icon_focus_yellow_card);
                    mOpType = 0;
                }

                if (userId == userCard.getUserId()) {
                    mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                            "icon_player_level" + userCard.getLevel()));
                } else {
                    mIvLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + userCard.getLevel()));
                }
            }
            return this;
        }

        @SingleClick
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_permission_focus || id == R.id.iv_permission_bottom_focus) {
                if (mListener != null) {
                    mListener.onFocus(getDialog(), mOpType);
                }
            } else if (id == R.id.tv_permission_setup) {
                if (mListener != null) {
                    mListener.onSetupManager(isSetupManager, getDialog());
                }
            } else if (id == R.id.tv_permission_forbidden) {
                if (mListener != null) {
                    mListener.onForbidden(getDialog());
                }
            }
        }
    }


    public interface OnListener {

        /**
         * 关注
         */
        void onFocus(BaseDialog dialog, int opType);

        /**
         * 任命管理
         */
        void onSetupManager(boolean isSetupManager, BaseDialog dialog);

        /**
         * 禁言
         */
        void onForbidden(BaseDialog dialog);
    }
}
