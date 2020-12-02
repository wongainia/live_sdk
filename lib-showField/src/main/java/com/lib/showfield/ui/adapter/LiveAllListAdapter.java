package com.lib.showfield.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lib.showfield.R;
import com.lib.showfield.http.respones.live.DynamicMatchBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LiveAllListAdapter extends BaseQuickAdapter<DynamicMatchBean, BaseViewHolder> {

    public LiveAllListAdapter(int layoutResId, @Nullable List<DynamicMatchBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, DynamicMatchBean item) {
        AppCompatImageView ivMatchBg = helper.findView(R.id.iv_home_recommend_bg);
        AppCompatTextView tvMatchTitle = helper.findView(R.id.tv_home_recommend_title);
        AppCompatTextView tvMatchLeague = helper.findView(R.id.tv_home_recommend_league);
        AppCompatImageView ivHeadIcon = helper.findView(R.id.iv_home_recommend_avatar);
        AppCompatTextView tvAnchorName = helper.findView(R.id.tv_home_recommend_name);
        AppCompatTextView tvHotDegree = helper.findView(R.id.tv_home_recommend_hot_num);


        Glide.with(getContext()).
                load(item.getCover())
                .into(ivMatchBg);

        Glide.with(getContext()).
                load(item.getAvatar())
                .placeholder(R.drawable.icon_avatar)
                .circleCrop()
                .error(R.drawable.icon_avatar)
                .into(ivHeadIcon);

        if (null != item.getTitle() && !TextUtils.isEmpty(item.getTitle())) {
            tvMatchTitle.setText(item.getTitle());
        } else {
            tvMatchTitle.setText(item.getNickname() + "的直播间");
        }

        if (item.getSportType() == 0) {
            tvMatchLeague.setVisibility(View.GONE);
        } else {
            tvMatchLeague.setVisibility(View.VISIBLE);
            tvMatchLeague.setText(item.getEventName());
        }

        tvAnchorName.setText(item.getNickname());
        tvHotDegree.setText(item.getHotNum() + "");
    }
}
