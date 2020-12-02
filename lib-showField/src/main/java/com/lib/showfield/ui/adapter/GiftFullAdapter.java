package com.lib.showfield.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lib.showfield.R;
import com.lib.showfield.http.respones.live.GiftResponse;
import com.lib.showfield.interfaces.OnItemClickListener;

import java.util.List;

/**
 * Created by upingu
 * Date  2020/6/10 15:13
 * <p>
 * Description
 **/
public class GiftFullAdapter extends RecyclerView.Adapter<GiftFullAdapter.GiftViewHolder> {

    private List<GiftResponse> mList;
    private Activity mActivity;
    private String iconUrl;
    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public GiftFullAdapter(List<GiftResponse> mList, Activity activity, String iconUrl) {
        this.mList = mList;
        this.mActivity = activity;
        this.iconUrl = iconUrl;
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(mActivity).
                inflate(R.layout.item_gift_full, null, false);

        return new GiftViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        //动态图标设置
        Glide.with(holder.itemView.getContext()).
                load(iconUrl)
                .circleCrop()
                .into(holder.ivMoneyIcon);

        boolean selected = mList.get(position).isSelected();
        if (selected) {
            holder.llBg.setBackgroundResource(R.drawable.shape_gift_checked_full);
        } else {
            holder.llBg.setBackgroundResource(R.drawable.shape_gift_normal_full);
        }

        holder.price.setText(mList.get(position).getGiftPrice() + "");
        holder.name.setText(mList.get(position).getGiftName());

        Glide.with(holder.itemView.getContext())
                .load(mList.get(position).getDefaultGraphUrl())
                .placeholder(R.drawable.icon_gift)
                .error(R.drawable.icon_gift)
                .into(holder.icon);

        holder.llBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GiftViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat llBg;
        AppCompatTextView price;
        AppCompatImageView icon;
        AppCompatTextView name;
        AppCompatImageView ivMoneyIcon;

        public GiftViewHolder(View itemView) {
            super(itemView);
            llBg = itemView.findViewById(R.id.ll_full_gift_bg);
            price = itemView.findViewById(R.id.tv_full_gift_price);
            icon = itemView.findViewById(R.id.iv_full_gift_icon);
            name = itemView.findViewById(R.id.tv_full_gift_name);
            ivMoneyIcon = itemView.findViewById(R.id.iv_full_money_icon);
        }
    }
}
