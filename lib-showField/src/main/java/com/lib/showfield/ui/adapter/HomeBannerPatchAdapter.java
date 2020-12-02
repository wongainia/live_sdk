package com.lib.showfield.ui.adapter;

import android.view.View;

import com.lib.showfield.R;
import com.lib.showfield.bean.PatchAdBean;
import com.lib.showfield.ui.fragment.PatchAdBannerViewHolder;
import com.zhpan.bannerview.BaseBannerAdapter;

/**
 * Created by
 **/
public class HomeBannerPatchAdapter extends BaseBannerAdapter<PatchAdBean, PatchAdBannerViewHolder> {


    @Override
    protected void onBind(PatchAdBannerViewHolder holder, PatchAdBean data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public PatchAdBannerViewHolder createViewHolder(View itemView, int viewType) {
        return new PatchAdBannerViewHolder(itemView);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_banner_net;
    }
}
