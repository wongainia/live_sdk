package com.lib.showfield.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lib.showfield.R;
import com.lib.showfield.common.Constants;
import com.lib.showfield.http.glide.GlideApp;
import com.lib.showfield.http.respones.live.ChatMessage;
import com.lib.showfield.interfaces.OnItemClickNickNameListener;
import com.lib.showfield.utils.FindDrawableByName;
import com.lib.showfield.utils.URLImageParser;

import java.util.List;

import video.dkplayer_ui.utils.CenterSpaceImageSpan;

import static com.blankj.utilcode.util.ResourceUtils.getDrawable;
import static com.lib.showfield.common.Constants.COMMON_RESOURCE;
import static com.lib.showfield.common.Constants.CURRENCYMONEYNAME;

/**
 * Created by upingu
 * Date  2020-05-11 13:40
 * <p>
 * Description
 **/
public class ShowFieldChatAdapter extends BaseMultiItemQuickAdapter<ChatMessage, BaseViewHolder> {

    protected OnItemClickNickNameListener mOnItemClickNickNameListener;

    public void setOnItemClickNickNameListener(OnItemClickNickNameListener listener) {
        mOnItemClickNickNameListener = listener;
    }

    CenterSpaceImageSpan imageRoomManagerSpan;

    public ShowFieldChatAdapter(List<ChatMessage> data) {
        super(data);
        addItemType(0, R.layout.item_chat_msg_normal_v2);
        addItemType(1, R.layout.item_chat_msg_room_manager_v2);
        addItemType(2, R.layout.item_chat_msg_gift_v2);
        addItemType(3, R.layout.item_chat_msg_gift_room_v2);
        addItemType(4, R.layout.item_system_toast_v2);
        addItemType(5, R.layout.item_message_toast_v2);
        addItemType(6, R.layout.item_chat_join_v2);
        addItemType(7, R.layout.item_chat_system_v2);
        addItemType(8, R.layout.item_chat_prophesy_v2);
        addItemType(9, R.layout.item_chat_share_v2);
        addItemType(10, R.layout.item_chat_msg_foucs_v2);
        addItemType(11, R.layout.item_chat_msg_foucs_manager_v2);
        addItemType(12, R.layout.item_chat_share_rm_v2);
        addItemType(13, R.layout.item_chat_msg_send_red_v2);
        addItemType(14, R.layout.item_chat_msg_send_red_v2);

        Drawable roomIcon = getDrawable(R.drawable.icon_room_m);
        roomIcon.setBounds(0, 0, roomIcon.getMinimumWidth(), roomIcon.getMinimumHeight());
        //居中对齐imageSpan
        imageRoomManagerSpan = new CenterSpaceImageSpan(roomIcon);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ChatMessage item) {
        if (TextUtils.isEmpty(item.getUserLevel()) || "0".equals(item.getUserLevel()))
            item.setUserLevel("1");
        switch (helper.getItemViewType()) {
            case 0:
                Log.e("convert", helper.getItemViewType() + "======" + item.getContent());
//                layout_0(helper, item);
                layout_0_v2(helper, item);
                break;
            case 1:
                layout_1(helper, item);
                break;
            case 2:

                AppCompatTextView msg_gift = helper.getView(R.id.tv_item_chat_msg_text_gift);
                ImageView img = helper.getView(R.id.img_left);
                LinearLayoutCompat ll_bg = helper.findView(R.id.ll_chat_msg_gift);
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) ll_bg.getLayoutParams();
                ll_bg.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                msg_gift.setPadding(12, 0, 0, 15);
                lp.setMargins(12, 5, 0, 7);
                loadImg(item.getSupportTeamLogo(), img);

                int leftDrawable_gift = 0;
                if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
                    leftDrawable_gift = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_player_level" + item.getAnchorLevel());
                } else {
                    leftDrawable_gift = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + item.getUserLevel());
                }

                SpannableStringBuilder builder_gift = new SpannableStringBuilder();

                if (ObjectUtils.isEmpty(item.getSupportTeamLogo())) {

                    builder_gift.append("\t");
                    builder_gift.append("\t");
                    builder_gift.append(item.getNickName());
                    builder_gift.append("\t");
                    builder_gift.append("送出");
                    builder_gift.append(item.getGiftName());
                    builder_gift.append("\t");
                    builder_gift.append(String.valueOf(item.getGiftNum()));
                    builder_gift.append("个");
                    builder_gift.append("\t");
                    builder_gift.append("\t");
                    builder_gift.append("\t");
                    builder_gift.append("\t");

                    builder_gift.setSpan(new ImageSpan(getContext(), leftDrawable_gift),
                            0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builder_gift.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                            ds.setUnderlineText(false);
                        }

                    }, 2, item.getNickName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    String url = item.getGiftIcon();
                    //异步获取网络图片
                    Drawable drawableFromNet = new URLImageParser(msg_gift, getContext(), 50).getDrawable(url);
                    assert drawableFromNet != null;
                    drawableFromNet.setBounds(0, 0, drawableFromNet.getMinimumWidth(), drawableFromNet.getMinimumHeight());
                    //居中对齐imageSpan
                    CenterSpaceImageSpan iconSpan = new CenterSpaceImageSpan(drawableFromNet);
                    //设置网络图片
                    builder_gift.setSpan(iconSpan,
                            item.getNickName().length() + item.getGiftName().length() + 5,
                            item.getNickName().length() + item.getGiftName().length() + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else {

                    ////////////////////////////////球徽drawable//////////////////////////////
                    //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), msg);
                    Drawable drawable = img.getDrawable();
                    drawable.setBounds(0, 0, msg_gift.getLineHeight(), msg_gift.getLineHeight());
                    CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);

                    builder_gift.append("\t");
                    builder_gift.append("\t");

                    builder_gift.append("\t");
                    builder_gift.append("\t");
                    builder_gift.append(item.getNickName());
                    builder_gift.append("\t");
                    builder_gift.append("送出");
                    builder_gift.append(item.getGiftName());
                    builder_gift.append("\t");
                    builder_gift.append(String.valueOf(item.getGiftNum()));
                    builder_gift.append("个");
                    builder_gift.append("\t");
                    builder_gift.append("\t");
                    builder_gift.append("\t");
                    builder_gift.append("\t");


                    //球徽
                    builder_gift.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder_gift.setSpan(new ImageSpan(getContext(), leftDrawable_gift),
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builder_gift.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                            ds.setUnderlineText(false);
                        }

                    }, 4, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    String url = item.getGiftIcon();
                    //异步获取网络图片
                    Drawable drawableFromNet = new URLImageParser(msg_gift, getContext(), 50).getDrawable(url);
                    assert drawableFromNet != null;
                    drawableFromNet.setBounds(0, 0, drawableFromNet.getMinimumWidth(), drawableFromNet.getMinimumHeight());
                    //居中对齐imageSpan
                    CenterSpaceImageSpan iconSpan = new CenterSpaceImageSpan(drawableFromNet);
                    //设置网络图片
                    builder_gift.setSpan(iconSpan,
                            item.getNickName().length() + item.getGiftName().length() + 6,
                            item.getNickName().length() + item.getGiftName().length() + 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }

                msg_gift.setText(builder_gift);
                msg_gift.setMovementMethod(LinkMovementMethod.getInstance());

                break;
            case 3:

                AppCompatTextView msg_gift_room = helper.getView(R.id.tv_item_chat_msg_text_gift_room);
                ImageView img_room = helper.getView(R.id.img_left);
                LinearLayoutCompat ll_bg_gift_room = helper.findView(R.id.ll_chat_msg_gift_room);
                RecyclerView.LayoutParams lp_gift_room = (RecyclerView.LayoutParams) ll_bg_gift_room.getLayoutParams();
                ll_bg_gift_room.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                msg_gift_room.setPadding(12, 0, 12, 15);
                lp_gift_room.setMargins(12, 5, 12, 7);

                loadImg(item.getSupportTeamLogo(), img_room);

                int leftDrawable_gift_rm = 0;
                if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
                    leftDrawable_gift_rm = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_player_level" + item.getAnchorLevel());
                } else {
                    leftDrawable_gift_rm = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + item.getUserLevel());
                }

                SpannableStringBuilder builder_gift_rm = new SpannableStringBuilder();

                if (ObjectUtils.isEmpty(item.getSupportTeamLogo())) {
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append(item.getNickName());
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("送出");
                    builder_gift_rm.append(item.getGiftName());
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append(String.valueOf(item.getGiftNum()));
                    builder_gift_rm.append("个");

                    builder_gift_rm.setSpan(imageRoomManagerSpan,
                            0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builder_gift_rm.setSpan(new ImageSpan(getContext(), leftDrawable_gift_rm),
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//                builder_gift_rm.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.chat_join_text)),
//                        4, item.getNickName().length() + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builder_gift_rm.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                            ds.setUnderlineText(false);
                        }

                    }, 4, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    String url_rm = item.getGiftIcon();
                    //异步获取网络图片
                    Drawable drawableFromNetRm = new URLImageParser(msg_gift_room, getContext(), 50).getDrawable(url_rm);
                    assert drawableFromNetRm != null;
                    drawableFromNetRm.setBounds(0, 0, drawableFromNetRm.getMinimumWidth(), drawableFromNetRm.getMinimumHeight());
                    //居中对齐imageSpan
                    CenterSpaceImageSpan iconSpanRm = new CenterSpaceImageSpan(drawableFromNetRm);
                    //设置网络图片
                    builder_gift_rm.setSpan(iconSpanRm,
                            item.getNickName().length() + item.getGiftName().length() + 7,
                            item.getNickName().length() + item.getGiftName().length() + 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {

                    ////////////////////////////////球徽drawable//////////////////////////////
                    //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), msg);
                    Drawable drawable = img_room.getDrawable();
                    drawable.setBounds(0, 0, msg_gift_room.getLineHeight(), msg_gift_room.getLineHeight());
                    CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);

                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("\t");

                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append(item.getNickName());
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append("送出");
                    builder_gift_rm.append(item.getGiftName());
                    builder_gift_rm.append("\t");
                    builder_gift_rm.append(String.valueOf(item.getGiftNum()));
                    builder_gift_rm.append("个");

                    //球徽
                    builder_gift_rm.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder_gift_rm.setSpan(imageRoomManagerSpan,
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builder_gift_rm.setSpan(new ImageSpan(getContext(), leftDrawable_gift_rm),
                            4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builder_gift_rm.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                            ds.setUnderlineText(false);
                        }

                    }, 6, item.getNickName().length() + 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    String url_rm = item.getGiftIcon();
                    //异步获取网络图片
                    Drawable drawableFromNetRm = new URLImageParser(msg_gift_room, getContext(), 50).getDrawable(url_rm);
                    assert drawableFromNetRm != null;
                    drawableFromNetRm.setBounds(0, 0, drawableFromNetRm.getMinimumWidth(), drawableFromNetRm.getMinimumHeight());
                    //居中对齐imageSpan
                    CenterSpaceImageSpan iconSpanRm = new CenterSpaceImageSpan(drawableFromNetRm);
                    //设置网络图片
                    builder_gift_rm.setSpan(iconSpanRm,
                            item.getNickName().length() + item.getGiftName().length() + 8,
                            item.getNickName().length() + item.getGiftName().length() + 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }


                msg_gift_room.setText(builder_gift_rm);
                msg_gift_room.setMovementMethod(LinkMovementMethod.getInstance());

                break;
            case 4:
                AppCompatTextView toast_txt = helper.getView(R.id.tv_item_system_toast_txt);
                LinearLayout ll_bg_toast_txt = helper.findView(R.id.ll_system_toast_txt);
                RecyclerView.LayoutParams lp_toast = (RecyclerView.LayoutParams) ll_bg_toast_txt.getLayoutParams();
                ll_bg_toast_txt.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                toast_txt.setPadding(12, 10, 12, 10);
                lp_toast.setMargins(12, 5, 12, 7);
                SpannableStringBuilder builder_SysToast = new SpannableStringBuilder();

                builder_SysToast.append("系统提示:");
                builder_SysToast.append(item.getNickName());
                builder_SysToast.append(item.getContent());

                builder_SysToast.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                        ds.setUnderlineText(false);
                    }

                }, 5, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                toast_txt.setText(builder_SysToast);
                toast_txt.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case 5:

                AppCompatTextView message_txt = helper.getView(R.id.tv_item_message_toast_txt);
                LinearLayout ll_bg_message_toast_txt = helper.findView(R.id.ll_bg_message_toast);
                RecyclerView.LayoutParams lp_toast_txt = (RecyclerView.LayoutParams) ll_bg_message_toast_txt.getLayoutParams();
                ll_bg_message_toast_txt.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                lp_toast_txt.setMargins(12, 5, 12, 7);
                message_txt.setPadding(12, 10, 12, 10);

                SpannableStringBuilder builderTitleMessage = new SpannableStringBuilder();
                builderTitleMessage.append("消息提醒:");
                builderTitleMessage.append(item.getContent());

                message_txt.setText(builderTitleMessage);

                break;
            case 6:
                AppCompatTextView nickname = helper.getView(R.id.tv_item_chat_join_nick);
                LinearLayoutCompat ll_bg_join_nick = helper.findView(R.id.ll_bg_join_nick);
                RecyclerView.LayoutParams lp_join_nick = (RecyclerView.LayoutParams) ll_bg_join_nick.getLayoutParams();
                ll_bg_join_nick.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                lp_join_nick.setMargins(12, 5, 12, 7);
                nickname.setText(item.getNickName());

                nickname.setOnClickListener(v -> {
                    mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                });
                break;
            case 7:
                LinearLayoutCompat ll_bg_system = helper.findView(R.id.ll_system);
                RecyclerView.LayoutParams lp_system = (RecyclerView.LayoutParams) ll_bg_system.getLayoutParams();
                ll_bg_system.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                lp_system.setMargins(12, 5, 12, 7);
                break;
            case 8:
                AppCompatTextView prophesy_text = helper.getView(R.id.tv_item_prophesy_text);
                LinearLayout ll_bg_prophesy_text = helper.findView(R.id.ll_bg_prophesy_text);
                RecyclerView.LayoutParams lp_pro = (RecyclerView.LayoutParams) ll_bg_prophesy_text.getLayoutParams();
                ll_bg_prophesy_text.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                lp_pro.setMargins(12, 5, 12, 7);
                prophesy_text.setPadding(12, 0, 0, 15);
                SpannableStringBuilder builderTitleProphesy = new SpannableStringBuilder();

                builderTitleProphesy.append("预言:");
                builderTitleProphesy.append("恭喜你在主播预言中获得");
                builderTitleProphesy.append(item.getGiftNum() + "");
                builderTitleProphesy.append("个");
                builderTitleProphesy.append(SPUtils.getInstance(COMMON_RESOURCE).getString(CURRENCYMONEYNAME));

                builderTitleProphesy.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.chat_join_text)),
                        14, String.valueOf(item.getGiftNum()).length() + 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                prophesy_text.setText(builderTitleProphesy);

                break;

            case 9:
                AppCompatTextView tv_share = helper.getView(R.id.tv_item_share_text);
                ImageView img_share = helper.getView(R.id.img_left);
                LinearLayout ll_bg_share_text = helper.findView(R.id.ll_bg_share_text);
                RecyclerView.LayoutParams lp_share = (RecyclerView.LayoutParams) ll_bg_share_text.getLayoutParams();
                ll_bg_share_text.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                lp_share.setMargins(12, 5, 12, 5);
                tv_share.setPadding(12, 0, 0, 15);
                loadImg(item.getSupportTeamLogo(), img_share);

                int leftDrawableShare = 0;
                if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
                    leftDrawableShare = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_player_level" + item.getAnchorLevel());
                } else {
                    leftDrawableShare = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + item.getUserLevel());
                }

                SpannableStringBuilder builderTitleShare = new SpannableStringBuilder();

                if (ObjectUtils.isEmpty(item.getSupportTeamLogo())) {

                    builderTitleShare.append("\t");
                    builderTitleShare.append("\t");
                    builderTitleShare.append(item.getNickName());
                    builderTitleShare.append("分享了直播间");


                    builderTitleShare.setSpan(new ImageSpan(getContext(), leftDrawableShare),
                            0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleShare.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                            ds.setUnderlineText(false);
                        }

                    }, 2, item.getNickName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else {

                    builderTitleShare.append("\t");
                    builderTitleShare.append("\t");

                    builderTitleShare.append("\t");
                    builderTitleShare.append("\t");
                    builderTitleShare.append(item.getNickName());
                    builderTitleShare.append("分享了直播间");

                    ////////////////////////////////球徽drawable//////////////////////////////
                    //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), msg);
                    Drawable drawable = img_share.getDrawable();
                    drawable.setBounds(0, 0, tv_share.getLineHeight(), tv_share.getLineHeight());
                    CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);

                    //球徽
                    builderTitleShare.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleShare.setSpan(new ImageSpan(getContext(), leftDrawableShare),
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleShare.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                            ds.setUnderlineText(false);
                        }

                    }, 4, item.getNickName().length() + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }

                tv_share.setText(builderTitleShare);
                tv_share.setMovementMethod(LinkMovementMethod.getInstance());

                break;

            case 10:
                AppCompatTextView focus_txt = helper.getView(R.id.tv_item_chat_msg_text_focus);
                ImageView img_focus = helper.getView(R.id.img_left);
                LinearLayoutCompat ll_bg_chat_focus_text = helper.findView(R.id.ll_bg_chat_focus_text);
                RecyclerView.LayoutParams lp_focus_txt = (RecyclerView.LayoutParams) ll_bg_chat_focus_text.getLayoutParams();
                ll_bg_chat_focus_text.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                lp_focus_txt.setMargins(12, 5, 12, 7);
                focus_txt.setPadding(12, 0, 0, 15);
                loadImg(item.getSupportTeamLogo(), img_focus);

                int leftDrawableFocus = 0;
                if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
                    leftDrawableFocus = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_player_level" + item.getAnchorLevel());
                } else {
                    leftDrawableFocus = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + item.getUserLevel());
                }


                SpannableStringBuilder builderTitleFocus = new SpannableStringBuilder();

                if (ObjectUtils.isEmpty(item.getSupportTeamLogo())) {
                    builderTitleFocus.append("\t");
                    builderTitleFocus.append("\t");
                    builderTitleFocus.append(item.getNickName());
                    builderTitleFocus.append("关注了主播");
                    builderTitleFocus.append("\t");
                    builderTitleFocus.append("\t");


                    builderTitleFocus.setSpan(new ImageSpan(getContext(), leftDrawableFocus),
                            0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleFocus.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.white));
                            ds.setUnderlineText(false);
                        }

                    }, 2, item.getNickName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    builderTitleFocus.append("\t");
                    builderTitleFocus.append("\t");

                    builderTitleFocus.append("\t");
                    builderTitleFocus.append("\t");
                    builderTitleFocus.append(item.getNickName());
                    builderTitleFocus.append("关注了主播");
                    builderTitleFocus.append("\t");
                    builderTitleFocus.append("\t");

                    ////////////////////////////////球徽drawable//////////////////////////////
                    //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), msg);
                    Drawable drawable = img_focus.getDrawable();
                    drawable.setBounds(0, 0, focus_txt.getLineHeight(), focus_txt.getLineHeight());
                    CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);

                    //球徽
                    builderTitleFocus.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleFocus.setSpan(new ImageSpan(getContext(), leftDrawableFocus),
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleFocus.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.white));
                            ds.setUnderlineText(false);
                        }

                    }, 4, item.getNickName().length() + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                focus_txt.setText(builderTitleFocus);
                focus_txt.setMovementMethod(LinkMovementMethod.getInstance());

                break;
            case 11:

                AppCompatTextView focus_txt_manager = helper.getView(R.id.tv_item_chat_msg_text_focus_manager);
                ImageView img_focus_manager = helper.getView(R.id.img_left);
                LinearLayoutCompat ll_bg_chat_focus_manager = helper.findView(R.id.ll_bg_chat_focus_manager);
                RecyclerView.LayoutParams lp_focus_manager = (RecyclerView.LayoutParams) ll_bg_chat_focus_manager.getLayoutParams();
                ll_bg_chat_focus_manager.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                lp_focus_manager.setMargins(12, 5, 12, 12);
                focus_txt_manager.setPadding(12, 0, 0, 15);
                loadImg(item.getSupportTeamLogo(), img_focus_manager);

                int leftDrawableFocusManager = 0;
                if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
                    leftDrawableFocusManager = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_player_level" + item.getAnchorLevel());
                } else {
                    leftDrawableFocusManager = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + item.getUserLevel());
                }

                SpannableStringBuilder builderTitleFocusManager = new SpannableStringBuilder();

                if (ObjectUtils.isEmpty(item.getSupportTeamLogo())) {
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append(item.getNickName());
                    builderTitleFocusManager.append("关注了主播");
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");

                    builderTitleFocusManager.setSpan(imageRoomManagerSpan,
                            0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleFocusManager.setSpan(new ImageSpan(getContext(), leftDrawableFocusManager),
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleFocusManager.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.white));
                            ds.setUnderlineText(false);
                        }

                    }, 4, item.getNickName().length() + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {

                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");

                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append(item.getNickName());
                    builderTitleFocusManager.append("关注了主播");
                    builderTitleFocusManager.append("\t");
                    builderTitleFocusManager.append("\t");

                    ////////////////////////////////球徽drawable//////////////////////////////
                    //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), msg);
                    Drawable drawable = img_focus_manager.getDrawable();
                    drawable.setBounds(0, 0, focus_txt_manager.getLineHeight(), focus_txt_manager.getLineHeight());
                    CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);

                    //球徽
                    builderTitleFocusManager.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleFocusManager.setSpan(imageRoomManagerSpan,
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleFocusManager.setSpan(new ImageSpan(getContext(), leftDrawableFocusManager),
                            4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleFocusManager.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.white));
                            ds.setUnderlineText(false);
                        }

                    }, 6, item.getNickName().length() + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }


                focus_txt_manager.setMovementMethod(LinkMovementMethod.getInstance());
                focus_txt_manager.setText(builderTitleFocusManager);

                break;
            case 12:
                AppCompatTextView tv_share_rm = helper.getView(R.id.tv_item_share_text_rm);
                ImageView img_share_rm = helper.getView(R.id.img_left);
                LinearLayout ll_bg_chat_share_rm = helper.findView(R.id.ll_bg_chat_share_rm);
                RecyclerView.LayoutParams lp_share_rm = (RecyclerView.LayoutParams) ll_bg_chat_share_rm.getLayoutParams();
                ll_bg_chat_share_rm.setBackgroundResource(R.drawable.bg_live_anchor_shape);
                lp_share_rm.setMargins(12, 5, 12, 7);
                tv_share_rm.setPadding(12, 0, 0, 15);
                loadImg(item.getSupportTeamLogo(), img_share_rm);

                int leftDrawableShareRm = 0;

                if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
                    leftDrawableShareRm = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_player_level" + item.getAnchorLevel());
                } else {
                    leftDrawableShareRm = FindDrawableByName.getDrawableRes(getContext(),
                            "icon_user_level" + item.getUserLevel());
                }

                SpannableStringBuilder builderTitleShare_rm = new SpannableStringBuilder();

                if (ObjectUtils.isEmpty(item.getSupportTeamLogo())) {

                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append(item.getNickName());
                    builderTitleShare_rm.append("分享了直播间");

                    builderTitleShare_rm.setSpan(imageRoomManagerSpan,
                            0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleShare_rm.setSpan(new ImageSpan(getContext(), leftDrawableShareRm),
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleShare_rm.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                            ds.setUnderlineText(false);
                        }

                    }, 4, item.getNickName().length() + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else {

                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append("\t");

                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append("\t");
                    builderTitleShare_rm.append(item.getNickName());
                    builderTitleShare_rm.append("分享了直播间");

                    ////////////////////////////////球徽drawable//////////////////////////////
                    //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), msg);
                    Drawable drawable = img_share_rm.getDrawable();
                    drawable.setBounds(0, 0, tv_share_rm.getLineHeight(), tv_share_rm.getLineHeight());
                    CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);

                    //球徽
                    builderTitleShare_rm.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleShare_rm.setSpan(imageRoomManagerSpan,
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleShare_rm.setSpan(new ImageSpan(getContext(), leftDrawableShareRm),
                            4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builderTitleShare_rm.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                            ds.setUnderlineText(false);
                        }

                    }, 6, item.getNickName().length() + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }

                tv_share_rm.setMovementMethod(LinkMovementMethod.getInstance());
                tv_share_rm.setText(builderTitleShare_rm);

                break;

            case 13://发红包
                sendRedView(helper, item);
                break;


            case 14://领红包
                grabRedView(helper, item);

                break;
        }

    }

//    private void layout_0(BaseViewHolder helper, ChatMessage item) {
//        LinearLayoutCompat ll_bg = helper.getView(R.id.ll_item_chat_msg_normal);
//        AppCompatTextView msg = helper.getView(R.id.tv_item_chat_msg_text_normal);
//        ImageView img = helper.findView(R.id.img_left);
//        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) ll_bg.getLayoutParams();
//
//        if (mType == 1) {
//            ll_bg.setBackgroundResource(R.drawable.bg_live_anchor_shape);
//            msg.setPadding(12, 0, 12, 15);
//            lp.setMargins(12, 5, 12, 7);
//        } else {
//            msg.setPadding(0, 0, 0, 5);
//            lp.setMargins(12, 2, 12, 3);
//        }
//
//        loadImg(item.getSupportTeamLogo(), img);
//        int leftDrawable = 0;
//
//        if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
//            leftDrawable = FindDrawableByName.getDrawableRes(getContext(),
//                    "icon_player_level" + item.getAnchorLevel());
//        } else {
//            leftDrawable = FindDrawableByName.getDrawableRes(getContext(),
//                    "icon_user_level" + item.getUserLevel());
//        }
//
//        SpannableStringBuilder builderTitle = new SpannableStringBuilder();
//
//        if (TextUtils.isEmpty(item.getSupportTeamLogo())) {
//            builderTitle.append("\t");
//            builderTitle.append("\t");
//            builderTitle.append(item.getNickName());
//            builderTitle.append(": ");
//            builderTitle.append(item.getContent());
//            builderTitle.setSpan(new ImageSpan(getContext(), leftDrawable),
//                    0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
////            if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
////                builderTitle.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorAccent)),
////                        2, item.getNickName().length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////            } else {
////                builderTitle.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.light_gray)),
////                        2, item.getNickName().length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////            }
//
//            builderTitle.setSpan(new ClickableSpan() {
//                @Override
//                public void onClick(@NonNull View widget) {
//                    mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
//                }
//
//                @Override
//                public void updateDrawState(@NonNull TextPaint ds) {
//                    if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
//                        ds.setColor(getContext().getResources().getColor(R.color.colorAccent));
//                    } else {
//                        ds.setColor(getContext().getResources().getColor(R.color.light_gray));
//                    }
//
//                    ds.setUnderlineText(false);
//                }
//
//            }, 2, item.getNickName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//        } else {
//            ////////////////////////////////球徽drawable//////////////////////////////
//            //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), msg);
//            Drawable drawable = img.getDrawable();
//            drawable.setBounds(0, 0, msg.getLineHeight(), msg.getLineHeight());
//            CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);
//
//
//            builderTitle.append("\t");
//            builderTitle.append("\t");
//
//            builderTitle.append("\t");
//            builderTitle.append("\t");
//            builderTitle.append(item.getNickName());
//            builderTitle.append(": ");
//            builderTitle.append(item.getContent());
//
//            //球徽
//            builderTitle.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            builderTitle.setSpan(new ImageSpan(getContext(), leftDrawable),
//                    2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
////            if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
////                builderTitle.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorAccent)),
////                        4, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////            } else {
////                builderTitle.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.light_gray)),
////                        4, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////            }
//
//            builderTitle.setSpan(new ClickableSpan() {
//                @Override
//                public void onClick(@NonNull View widget) {
//                    mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
//                }
//
//                @Override
//                public void updateDrawState(@NonNull TextPaint ds) {
//                    if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
//                        ds.setColor(getContext().getResources().getColor(R.color.colorAccent));
//                    } else {
//                        ds.setColor(getContext().getResources().getColor(R.color.light_gray));
//                    }
//
//                    ds.setUnderlineText(false);
//                }
//
//            }, 4, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//
//        msg.setText(builderTitle);
//        msg.setMovementMethod(LinkMovementMethod.getInstance());
//    }

    private void layout_0_v2(BaseViewHolder helper, ChatMessage item) {
        LinearLayoutCompat ll_bg = helper.getView(R.id.ll_item_chat_msg_normal);
        ConstraintLayout clNormalMsg = helper.getView(R.id.cl_normal_msg);
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) ll_bg.getLayoutParams();

        ImageView ivSupportTeamIcon = helper.getView(R.id.iv_support_team_icon);
        ImageView ivUserLevel = helper.getView(R.id.iv_user_level);
        AppCompatTextView tvUserName = helper.getView(R.id.tv_user_name);
        AppCompatTextView tvUserContent = helper.getView(R.id.tv_user_content);
        ll_bg.setBackgroundResource(R.drawable.bg_live_anchor_shape);
        clNormalMsg.setPadding(5, 0, 10, 10);
        lp.setMargins(12, 5, 12, 7);
        int leftDrawable = 0;

        if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
            leftDrawable = FindDrawableByName.getDrawableRes(getContext(),
                    "icon_player_level" + item.getAnchorLevel());
        } else {
            leftDrawable = FindDrawableByName.getDrawableRes(getContext(),
                    "icon_user_level" + item.getUserLevel());
        }
        ivUserLevel.setImageDrawable(getDrawable(leftDrawable));
        if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
            tvUserName.setTextColor(Color.parseColor("#43ACFF"));
        } else {
            tvUserName.setTextColor(Color.parseColor("#9B9B9B"));
        }
        tvUserName.setText(item.getNickName()+" :");
        if (TextUtils.isEmpty(item.getSupportTeamLogo())) {
            ivSupportTeamIcon.setVisibility(View.GONE);
            SpannableStringBuilder span = new SpannableStringBuilder("缩进了吧吧吧" + item.getNickName()+ item.getContent());
            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 6 + item.getNickName().length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvUserContent.setText(span);
        } else {
            SpannableStringBuilder span = new SpannableStringBuilder("缩进了吧吧吧吧ba" + item.getNickName()+ item.getContent());
            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 8 + item.getNickName().length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvUserContent.setText(span);
            ivSupportTeamIcon.setVisibility(View.VISIBLE);
            loadImg(item.getSupportTeamLogo(), ivSupportTeamIcon);
        }
        tvUserContent.setTextColor(getContext().getResources().getColor(R.color.white));
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
            }
        });
    }

    private void layout_1(BaseViewHolder helper, ChatMessage item) {
        AppCompatTextView msg1 = helper.getView(R.id.tv_item_chat_msg_text_room_manager);
        ImageView img = helper.findView(R.id.img_left);
        LinearLayoutCompat ll_bg = helper.findView(R.id.ll_chat_msg_room_manager);
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) ll_bg.getLayoutParams();
        ll_bg.setBackgroundResource(R.drawable.bg_live_anchor_shape);
        msg1.setPadding(12, 0, 12, 15);
        lp.setMargins(12, 5, 12, 7);
        loadImg(item.getSupportTeamLogo(), img);

        int leftDrawable_room = 0;
        if (item.getCertifiedAnchorStatus() == 1 && String.valueOf(item.getAnthorId()).equals(item.getUserId())) {
            leftDrawable_room = FindDrawableByName.getDrawableRes(getContext(), "icon_player_level" + item.getAnchorLevel());
        } else {
            leftDrawable_room = FindDrawableByName.getDrawableRes(getContext(), "icon_user_level" + item.getUserLevel());
        }


        SpannableStringBuilder builderTitle1 = new SpannableStringBuilder();

        if (TextUtils.isEmpty(item.getSupportTeamLogo())) {
            builderTitle1.append("\t");
            builderTitle1.append("\t");
            builderTitle1.append("\t");
            builderTitle1.append("\t");
            builderTitle1.append(item.getNickName());
            builderTitle1.append(": ");
            builderTitle1.append(item.getContent());

            builderTitle1.setSpan(imageRoomManagerSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builderTitle1.setSpan(new ImageSpan(getContext(), leftDrawable_room), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
//                builderTitle1.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorAccent)),
//                        0, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            } else {
//                builderTitle1.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.light_gray)),
//                        0, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
            builderTitle1.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")),
                    7, item.getContent().length() + 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            builderTitle1.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
                        ds.setColor(getContext().getResources().getColor(R.color.colorAccent));
                    } else {
                        ds.setColor(getContext().getResources().getColor(R.color.light_gray));
                    }

                    ds.setUnderlineText(false);
                }

            }, 0, item.getNickName().length() + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        } else {
            ////////////////////////////////球徽drawable//////////////////////////////
            //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), msg1);
            Drawable drawable = img.getDrawable();
            drawable.setBounds(0, 0, msg1.getLineHeight(), msg1.getLineHeight());
            CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);

            builderTitle1.append("\t");
            builderTitle1.append("\t");

            builderTitle1.append("\t");
            builderTitle1.append("\t");
            builderTitle1.append("\t");
            builderTitle1.append("\t");
            builderTitle1.append(item.getNickName());
            builderTitle1.append(": ");
            builderTitle1.append(item.getContent());

            //球徽
            builderTitle1.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builderTitle1.setSpan(imageRoomManagerSpan, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builderTitle1.setSpan(new ImageSpan(getContext(), leftDrawable_room), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//            if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
//                builderTitle1.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorAccent)),
//                        6, item.getNickName().length() + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            } else {
//                builderTitle1.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.light_gray)),
//                        6, item.getNickName().length() + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }

            builderTitle1.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")),
                    9, item.getContent().length() + 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            builderTitle1.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    if (item.getUserId().equals(SPUtils.getInstance("user").getString(Constants.USER_ID))) {
                        ds.setColor(getContext().getResources().getColor(R.color.colorAccent));
                    } else {
                        ds.setColor(getContext().getResources().getColor(R.color.light_gray));
                    }

                    ds.setUnderlineText(false);
                }

            }, 6, item.getNickName().length() + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        msg1.setText(builderTitle1);
        msg1.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void sendRedView(BaseViewHolder helper, ChatMessage item) {
        TextView tv_send_red = helper.getView(R.id.tv_send_red);
        ImageView img = helper.findView(R.id.img_left);
        LinearLayoutCompat ll_bg_send_red = helper.findView(R.id.ll_bg_send_red);
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) ll_bg_send_red.getLayoutParams();
        ll_bg_send_red.setBackgroundResource(R.drawable.bg_live_anchor_shape);
        lp.setMargins(12, 5, 12, 7);
        tv_send_red.setPadding(12, 0, 0, 15);
        loadImg(item.getSupportTeamLogo(), img);


        ////////////////////////////////球徽drawable//////////////////////////////
        //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), tv_send_red);
        Drawable drawable = img.getDrawable();
        drawable.setBounds(0, 0, tv_send_red.getLineHeight(), tv_send_red.getLineHeight());
        CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);


        ////////////////////////////////等级drawable//////////////////////////////
        int userLevel = FindDrawableByName.getDrawableRes(getContext(), "icon_user_level" + item.getUserLevel());
        Drawable locaDrawable = getDrawable(userLevel);
        locaDrawable.setBounds(0, 0, locaDrawable.getMinimumWidth(), locaDrawable.getMinimumHeight());
        CenterSpaceImageSpan locaImageSpans = new CenterSpaceImageSpan(locaDrawable);


        ////////////////////////////////拼接//////////////////////////////
        SpannableStringBuilder builderSendRed = new SpannableStringBuilder();

        if (TextUtils.isEmpty(item.getSupportTeamLogo())) {
            builderSendRed.append("\t");
            builderSendRed.append("\t");
            builderSendRed.append(item.getNickName());
            builderSendRed.append(item.getContent());

            //球徽
            //builderSendRed.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //等级
            builderSendRed.setSpan(locaImageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //字体设置
            builderSendRed.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                    ds.setUnderlineText(false);
                }

            }, 2, item.getNickName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else {
            builderSendRed.append("\t");
            builderSendRed.append("\t");

            builderSendRed.append("\t");
            builderSendRed.append("\t");
            builderSendRed.append(item.getNickName());
            builderSendRed.append(item.getContent());
            //球徽
            builderSendRed.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //等级
            builderSendRed.setSpan(locaImageSpans, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //字体设置
            builderSendRed.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Log.d("zzy", "--------click-------");
                    mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                    ds.setUnderlineText(false);
                }

            }, 4, item.getNickName().length() + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tv_send_red.setText(builderSendRed);
        tv_send_red.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void grabRedView(BaseViewHolder helper, ChatMessage item) {
        TextView tv_grab_red = helper.getView(R.id.tv_send_red);
        ImageView img = helper.findView(R.id.img_left);
        LinearLayoutCompat
                ll_bg_grab_red = helper.findView(R.id.ll_bg_send_red);
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) ll_bg_grab_red.getLayoutParams();
        ll_bg_grab_red.setBackgroundResource(R.drawable.bg_live_anchor_shape);
        lp.setMargins(12, 5, 12, 7);
        loadImg(item.getSupportTeamLogo(), img);

        // 球徽drawable
        //UrlImageSpan urlImageSpan = new UrlImageSpan(getContext(), R.drawable.icon_logo, item.getSupportTeamLogo(), tv_send_red);
        Drawable drawable = img.getDrawable();
        drawable.setBounds(0, 0, tv_grab_red.getLineHeight(), tv_grab_red.getLineHeight());
        CenterSpaceImageSpan imageSpans = new CenterSpaceImageSpan(drawable);


        // 获取等级
        int userGrabLevel = FindDrawableByName.getDrawableRes(getContext(), "icon_user_level" + item.getUserLevel());
        Drawable grabDrawable = getDrawable(userGrabLevel);
        grabDrawable.setBounds(0, 0, grabDrawable.getMinimumWidth(), grabDrawable.getMinimumHeight());
        CenterSpaceImageSpan imageGrabSpans = new CenterSpaceImageSpan(grabDrawable);


        SpannableStringBuilder builderGrabRed = new SpannableStringBuilder();


        if (TextUtils.isEmpty(item.getSupportTeamLogo())) {
            builderGrabRed.append("\t");
            builderGrabRed.append("\t");
            builderGrabRed.append(item.getNickName());
            builderGrabRed.append(!TextUtils.isEmpty(item.getSenderNickName()) ? item.getSenderNickName() : "");
            builderGrabRed.append(item.getContent());

            //球徽
            //builderGrabRed.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //等级
            builderGrabRed.setSpan(imageGrabSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (!TextUtils.isEmpty(item.getSenderNickName())) {
                // 字体设置
                builderGrabRed.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                        ds.setUnderlineText(false);
                    }

                }, 0, item.getNickName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

            builderGrabRed.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.chat_join_text)),
                    item.getNickName().length() + 2, item.getSenderNickName().length() + item.getNickName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else {
            builderGrabRed.append("\t");
            builderGrabRed.append("\t");
            builderGrabRed.append("\t");
            builderGrabRed.append("\t");
            builderGrabRed.append(item.getNickName());
            builderGrabRed.append(!TextUtils.isEmpty(item.getSenderNickName()) ? item.getSenderNickName() : "");
            builderGrabRed.append(item.getContent());


            //球徽
            builderGrabRed.setSpan(imageSpans, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //等级
            builderGrabRed.setSpan(imageGrabSpans, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (!TextUtils.isEmpty(item.getSenderNickName())) {
                // 字体设置
                builderGrabRed.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        mOnItemClickNickNameListener.onItemClickNickName(helper.getPosition());
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setColor(getContext().getResources().getColor(R.color.chat_join_text));
                        ds.setUnderlineText(false);
                    }

                }, 0, item.getNickName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            builderGrabRed.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.chat_join_text)),
                    item.getNickName().length() + 2, item.getSenderNickName().length() + item.getNickName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tv_grab_red.setText(builderGrabRed);
        tv_grab_red.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void loadImg(String url, ImageView view) {
        GlideApp.with(getContext())
                .load(url)
                .apply(options)
                .into(view);
    }

    //图片加载策略
    private RequestOptions options =
            new RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.icon_team_default)
                    .error(R.drawable.icon_team_default)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

}