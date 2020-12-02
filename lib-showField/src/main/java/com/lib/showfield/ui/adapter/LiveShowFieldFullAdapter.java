package com.lib.showfield.ui.adapter;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lib.showfield.R;
import com.lib.showfield.http.glide.GlideApp;
import com.lib.showfield.http.respones.live.LiveRoomTypeInfo;
import com.lib.showfield.ui.view.tiktalk.cache.PreloadManager;
import com.zhouwei.blurlibrary.EasyBlur;

import org.jetbrains.annotations.NotNull;

public class LiveShowFieldFullAdapter extends
        BaseQuickAdapter<LiveRoomTypeInfo, BaseViewHolder> {

    public LiveShowFieldFullAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("zzy", "------onViewDetachedFromWindow-----");
        LiveRoomTypeInfo item = getData().get(holder.getLayoutPosition());
        PreloadManager.getInstance(holder.itemView.getContext()).removePreloadTask(item.getPullUrl());
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, LiveRoomTypeInfo item) {
        helper.itemView.setTag(helper);

        AppCompatImageView mIvThumb = helper.getView(R.id.iv_show_field_bg);

        GlideApp.with(getContext())
                .asBitmap()
                .load(item.getCoverUrl())
                .placeholder(android.R.color.black)
                .error(android.R.color.black)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap finalBitmap = EasyBlur.with(getContext())
                                .bitmap(resource) //要模糊的图片
                                .radius(1)//模糊半径
                                .blur();

                        mIvThumb.setImageBitmap(finalBitmap);
                    }
                });
    }
}
