package com.lib.showfield.ui.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lib.showfield.R;
import com.lib.showfield.http.glide.GlideApp;
import com.lib.showfield.http.respones.live.ContributeList;
import com.lib.showfield.utils.FindDrawableByName;

import java.util.List;

/**
 * Created by upingu
 * Date  2020-06-05 10:31
 * <p>
 * Description
 **/
public class ContributeAdapter extends BaseQuickAdapter<ContributeList.RankListBean, BaseViewHolder> {

    public ContributeAdapter(int layoutResId, @Nullable List<ContributeList.RankListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ContributeList.RankListBean item) {
        AppCompatTextView tv_position = helper.getView(R.id.tv_contribute_position);
        AppCompatImageView iv_avatar = helper.getView(R.id.tv_contribute_avatar);
        AppCompatTextView tv_nickname = helper.getView(R.id.tv_contribute_nickname);
        AppCompatTextView tv_num = helper.getView(R.id.tv_contribute_num);
        AppCompatImageView iv_level = helper.getView(R.id.iv_contribute_level);
        AppCompatTextView tv_text = helper.getView(R.id.tv_profit_text);

        Log.d("zzy", "helper.getPosition()----: " + helper.getPosition());

        if (helper.getPosition() < 8) {
            tv_position.setText(String.valueOf(helper.getAdapterPosition() + 3));

            if (!ObjectUtils.isEmpty(item.getNickname())) {
                tv_nickname.setText(item.getNickname());
            } else {
                tv_nickname.setText("虚位以待");
            }

            if (!ObjectUtils.isEmpty(item.getContribution())) {
                tv_text.setText("贡献值");
                tv_num.setText(item.getContribution());
            }

            GlideApp.with(getContext())
                    .load(item.getAvatar())
                    .placeholder(R.drawable.icon_profit_emty)
                    .error(R.drawable.icon_profit_emty)
                    .circleCrop()
                    .into(iv_avatar);

            iv_level.setImageResource(FindDrawableByName.getDrawableRes(getContext(),
                    "icon_user_level" + item.getLevel()));
        } else {
            tv_nickname.setText("");
            tv_text.setText("");
            tv_num.setText("");
            tv_position.setText("");
        }
    }
}
