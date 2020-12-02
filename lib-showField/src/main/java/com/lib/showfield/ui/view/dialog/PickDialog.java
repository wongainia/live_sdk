package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.showfield.R;
import com.lib.showfield.aop.SingleClick;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.base.action.AnimAction;
import com.lib.showfield.common.MyAdapter;
import com.lib.showfield.other.PickerLayoutManager;

import java.util.ArrayList;

/**
 * created by Ender on 2020/4/20
 * created by Joey Chow
 */
public final class PickDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> implements PickerLayoutManager.OnPickerListener {

        private RecyclerView mSexView;
        private AppCompatTextView mTvTitle;
        private PickerLayoutManager mSexManager;
        private PickerAdapter mYearAdapter;

        private String mTitle;
        private OnListener mListener;

        private Context mContext;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_picker);
            setAnimStyle(AnimAction.BOTTOM);
            this.mContext = context;
            mTvTitle = findViewById(R.id.tv_pick_title);
            mSexView = findViewById(R.id.sex_pick_view);
            mSexView.setLayoutManager(new LinearLayoutManager(context));
            mSexManager = new PickerLayoutManager.Builder(context).build();
            mSexView.setLayoutManager(mSexManager);
            mSexManager.setOnPickerListener(this);

            setOnClickListener(R.id.tv_pick_sex_commit, R.id.tv_pick_sex_cancel);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            mTvTitle.setText(mTitle);
            return this;
        }

        public Builder setDataList(ArrayList<String> data) {
            mYearAdapter = new PickerAdapter(mContext);
            mYearAdapter.setData(data);
            mSexView.setAdapter(mYearAdapter);

            return this;
        }

        @Override
        public void onPicked(RecyclerView recyclerView, int position) {
            mSexView.smoothScrollToPosition(position);
        }

        @SingleClick
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_pick_sex_commit) {
                if (mListener != null) {
                    mListener.onSelected(getDialog(), mSexManager.getPickedPosition() + 1);
                }
            } else if (id == R.id.tv_pick_sex_cancel) {
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            }
        }

        private static final class PickerAdapter extends MyAdapter<String> {

            private PickerAdapter(Context context) {
                super(context);
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder();
            }

            final class ViewHolder extends MyAdapter.ViewHolder {

                private final TextView mPickerView;

                ViewHolder() {
                    super(R.layout.item_picker);
                    mPickerView = (TextView) findViewById(R.id.tv_picker_name);
                }

                @Override
                public void onBindView(int position) {
                    mPickerView.setText(getItem(position));
                }
            }
        }
    }

    public interface OnListener {

        /**
         * 选择完日期后回调
         */
        void onSelected(BaseDialog dialog, int time);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}
