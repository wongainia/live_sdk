package com.lib.showfield.ui.fragment.live.gift;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.lib.showfield.R;
import com.lib.showfield.http.glide.GlideApp;
import com.lib.showfield.http.respones.live.GiftResponse;
import com.lib.showfield.interfaces.OnSelectGiftListener;
import com.zhpan.bannerview.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import static com.lib.showfield.common.Constants.ANDROID3X16X16CURRENCYMONEYICONURL;
import static com.lib.showfield.common.Constants.COMMON_RESOURCE;

/**
 * Created by upingu
 * Date  2020/6/9 11:48
 * <p>
 * Description
 **/
public class GiftBannerViewHolder extends BaseViewHolder<List<GiftResponse>> {

    protected OnSelectGiftListener mOnSelectGiftListener;
    private View mItemView;

    public void setOnSelectGiftListener(OnSelectGiftListener listener) {
        this.mOnSelectGiftListener = listener;
    }

    private int mSelectPos;

    public GiftBannerViewHolder(@NonNull View itemView, int pos) {
        super(itemView);
        this.mItemView = itemView;
        this.mSelectPos = pos;
    }

    @Override
    public void bindData(List<GiftResponse> data, int position, int pageSize) {

        TagFlowLayout mTagFlowLayout = findView(R.id.tfl_gift_banner);

        final LayoutInflater mInflater = LayoutInflater.from(itemView.getContext());
        mTagFlowLayout.setMaxLine(2);
        mTagFlowLayout.setAdapter(new TagAdapter<GiftResponse>(data) {
            @Override
            public View getView(FlowLayout parent, int position, GiftResponse bean) {

                LinearLayoutCompat view = (LinearLayoutCompat) mInflater.
                        inflate(R.layout.item_gift, mTagFlowLayout, false);

                AppCompatImageView ivImage = view.findViewById(R.id.iv_gift_image);
                AppCompatTextView tvName = view.findViewById(R.id.iv_gift_name);
                AppCompatTextView tvPrice = view.findViewById(R.id.iv_gift_price);
                //动态图标设置
                AppCompatImageView ivMoneyIcon = view.findViewById(R.id.iv_money_icon);
                String icon = SPUtils.getInstance(COMMON_RESOURCE).getString(ANDROID3X16X16CURRENCYMONEYICONURL);
                Glide.with(parent.getContext()).
                        load(icon)
                        .circleCrop()
                        .placeholder(R.drawable.icon_avatar)
                        .error(R.drawable.icon_avatar)
                        .into(ivMoneyIcon);

                GlideApp.with(parent.getContext())
                        .load(bean.getDefaultGraphUrl())
                        .placeholder(R.drawable.icon_defult_gift)
                        .error(R.drawable.icon_defult_gift)
                        .circleCrop()
                        .into(ivImage);

                tvName.setText(bean.getGiftName());
                tvPrice.setText(bean.getGiftPrice() + "");

                return view;
            }
        });

        mTagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size() != 0) {
                    for (int index : selectPosSet) {
                        Log.d("zzy", "---------selected-----: " + index);
                        mOnSelectGiftListener.onClickSelectGift(index, true);
                        GlideApp.with(mItemView.getContext())
                                .load(data.get(index).getDynamicGraphUrl())
                                .error(R.drawable.icon_defult_gift)
                                .placeholder(R.drawable.icon_defult_gift)
                                .into((ImageView) mTagFlowLayout.getChildAt(index).findViewById(R.id.iv_gift_image));


                        for (int i = 0; i < data.size(); i++) {
                            if (i != index) {
                                GlideApp.with(mItemView.getContext())
                                        .load(data.get(i).getDefaultGraphUrl())
                                        .error(R.drawable.icon_defult_gift)
                                        .placeholder(R.drawable.icon_defult_gift)
                                        .into((ImageView) mTagFlowLayout.getChildAt(i).findViewById(R.id.iv_gift_image));
                            }
                        }
                    }
                } else {
                    mOnSelectGiftListener.onClickSelectGift(0, false);
                }
            }
        });

        if (mSelectPos < data.size()) {
            mTagFlowLayout.getAdapter().setSelectedList(mSelectPos);
            mOnSelectGiftListener.onClickSelectGift(mSelectPos, true);
            GlideApp.with(mItemView.getContext())
                    .load(data.get(mSelectPos).getDynamicGraphUrl())
                    .placeholder(R.drawable.icon_defult_gift)
                    .error(R.drawable.icon_defult_gift)
                    .into((ImageView) mTagFlowLayout.getChildAt(mSelectPos).findViewById(R.id.iv_gift_image));
        }
    }
}
