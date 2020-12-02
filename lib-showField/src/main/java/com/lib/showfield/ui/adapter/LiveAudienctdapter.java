package com.lib.showfield.ui.adapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lib.showfield.R;
import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.utils.FindDrawableByName;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Roc
 * Date  2020-09-028 17:07
 * <p>
 * Description
 **/
public class LiveAudienctdapter extends BaseQuickAdapter<LiveAudienceBean.ListBean, BaseViewHolder> {

    public LiveAudienctdapter(int layoutResId, @Nullable List<LiveAudienceBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, LiveAudienceBean.ListBean item) {
        AppCompatImageView ivAudienceIcon = helper.getView(R.id.iv_audience_icon);
        AppCompatImageView ivAudienceLevel = helper.getView(R.id.iv_audience_level);
        AppCompatTextView tvAudienceName = helper.getView(R.id.tv_audience_name);
        Glide.with(getContext())
                .load(item.getAvatar())
                .placeholder(R.drawable.icon_avatar)
                .error(R.drawable.icon_avatar)
                .circleCrop()
                .into(ivAudienceIcon);
        ivAudienceLevel.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                "icon_user_level" + item.getUserLevel()));
        tvAudienceName.setText(item.getNickname());
    }
}
