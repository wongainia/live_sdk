package com.lib.showfield.ui.adapter;

import android.view.View;

import com.lib.showfield.R;
import com.lib.showfield.http.respones.live.GiftResponse;
import com.lib.showfield.interfaces.OnSelectGiftListener;
import com.lib.showfield.ui.fragment.live.gift.GiftBannerViewHolder;
import com.zhpan.bannerview.BaseBannerAdapter;

import java.util.List;

/**
 * Created by upingu
 * Date  2020/6/9 11:14
 * <p>
 * Description
 **/
public class GiftBannerAdapter extends BaseBannerAdapter<List<GiftResponse>, GiftBannerViewHolder>
        implements OnSelectGiftListener {

    public GiftBannerViewHolder mViewHolder;

    protected OnSelectGiftListener mOnSelectGiftListener;

    public void setOnSelectGiftListener(OnSelectGiftListener listener) {
        this.mOnSelectGiftListener = listener;
    }

    private int mSelectPos;

    public GiftBannerAdapter(int pos) {
        this.mSelectPos = pos;
    }

    @Override
    protected void onBind(GiftBannerViewHolder holder, List<GiftResponse> data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public GiftBannerViewHolder createViewHolder(View itemView, int viewType) {
        mViewHolder = new GiftBannerViewHolder(itemView, mSelectPos);
        mViewHolder.setOnSelectGiftListener(this);
        return mViewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_banner_gift;
    }

    @Override
    public void onClickSelectGift(int position, boolean isSelected) {
        mOnSelectGiftListener.onClickSelectGift(position, isSelected);
    }
}
