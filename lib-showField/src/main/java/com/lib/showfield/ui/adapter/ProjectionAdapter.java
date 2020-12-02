package com.lib.showfield.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.lib.showfield.R;
import com.lib.showfield.interfaces.OnAdapterClickListener;

import java.util.List;

/**
 * 投屏设备适配
 */
public class ProjectionAdapter extends BaseQuickAdapter<LelinkServiceInfo, BaseViewHolder> {
    //private OnPrectionListener mListener;
    private OnAdapterClickListener mListener;

    public void setListener(OnAdapterClickListener listener) {
        mListener = listener;
    }

    public ProjectionAdapter(Context context, List<LelinkServiceInfo> data, RecyclerView mListView) {
        super(R.layout.item_projection, data);
        mListView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void convert(BaseViewHolder helper, LelinkServiceInfo entity) {
        helper.itemView.setOnClickListener(v -> {
            mListener.onItemClick(helper.itemView,helper.getLayoutPosition());
        });

        TextView name = helper.getView(R.id.tv_name);
        name.setText(entity.getName() + "-" + (entity.isOnLine() ? "在线" : "离线"));
    }

    public void updateDatas(List<LelinkServiceInfo> infos) {
        if (null != infos) {
            getData().clear();
            addData(infos);
        }
    }
}