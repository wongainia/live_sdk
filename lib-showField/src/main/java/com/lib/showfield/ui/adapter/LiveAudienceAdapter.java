package com.lib.showfield.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lib.showfield.R;
import com.lib.showfield.bean.LiveAudienceBean;
import com.lib.showfield.http.glide.GlideApp;

import java.util.List;

/**
 * Created by upingu
 * Date  2020-04-10 14:21
 * <p>
 * Description
 **/
public class LiveAudienceAdapter extends BaseQuickAdapter<LiveAudienceBean.ListBean, BaseViewHolder> {

    public LiveAudienceAdapter(int layoutResId, @Nullable List<LiveAudienceBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LiveAudienceBean.ListBean item) {
        if (item!=null){
            AppCompatImageView ivAudienceIcon = helper.getView(R.id.iv_audience_icon);
            GlideApp.with(getContext())
                    .load(item.getAvatar())
                    .circleCrop()
                    .into(ivAudienceIcon);
        }
    }
}
