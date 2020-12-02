package com.lib.showfield.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.lib.showfield.R;
import com.lib.showfield.bean.PatchAdBean;
import com.lib.showfield.http.glide.GlideApp;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * <pre>
 *   Created by upingu
 *   Description:
 * </pre>
 */
public class PatchAdBannerViewHolder extends BaseViewHolder<PatchAdBean> {

    public PatchAdBannerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(PatchAdBean data, int position, int pageSize) {
        AppCompatImageView imageView = findView(R.id.banner_image);
        GlideApp.with(imageView).
                load(data.picUrl)
                .placeholder(R.drawable.icon_default_holder)
                .error(R.drawable.icon_default_holder)
                .centerCrop()
                .into(imageView);
    }
}
