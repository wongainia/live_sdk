package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.view.View;

import com.lib.showfield.R;
import com.lib.showfield.aop.SingleClick;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.base.action.AnimAction;

/**
 * Created by upingu
 * Date  2020-05-12 10:18
 * <p>
 * Description
 **/
public final class ShowFieldMoreDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {

        private OnListener mListener;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_show_field_more);
            setAnimStyle(AnimAction.BOTTOM);

            setOnClickListener(R.id.ll_show_field_more_clear, R.id.ll_show_field_more_report,
                    R.id.iv_show_dialog_close);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @SingleClick
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.ll_show_field_more_clear) {
                if (mListener != null) {
                    mListener.onShowClear(getDialog());
                }
            } else if (id == R.id.ll_show_field_more_report) {
                if (mListener != null) {
                    mListener.onShareReport(getDialog());
                }
            } else if (id == R.id.iv_show_dialog_close) {
                dismiss();
            }
        }
    }

    public interface OnListener {

        void onShowMission(BaseDialog dialog);

        void onShowClear(BaseDialog dialog);

        void onShareReport(BaseDialog dialog);
    }
}
