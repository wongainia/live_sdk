package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.lib.showfield.R;
import com.lib.showfield.aop.SingleClick;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.http.respones.live.DictDetailBean;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by upingu
 * Date  2020-05-06 16:19
 * <p>
 * Description
 **/
public final class ReportDiaLog {

    public static final class Builder
            extends UIDialog.Builder<Builder> {

        private OnListener mListener;
        private PowerSpinnerView spinnerView;
        private AppCompatEditText mEdtContent;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_report);
            spinnerView = findViewById(R.id.spinner_report);
            mEdtContent = findViewById(R.id.edt_report_content);
            setCancelable(false);


            spinnerView.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
                @Override
                public void onItemSelected(int i, String s) {
                    mListener.onReportType(s);
                }
            });

            setOnClickListener(R.id.btn_report_cancel, R.id.btn_report_conform);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setReportReasonData(List<DictDetailBean> data) {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                strings.add(data.get(i).getVal());
            }
            spinnerView.setItems(strings);
            return this;
        }

        @SingleClick
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_report_cancel) {
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            } else if (id == R.id.btn_report_conform) {
                if (mListener != null) {
                    mListener.onReportContent(mEdtContent.getText().toString().trim());
                    mListener.onConfirm(getDialog());
                }
            }
        }
    }

    public interface OnListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog);

        /**
         * 举报类型
         *
         * @param type
         */
        void onReportType(String type);

        /**
         * 文字内容
         *
         * @param content
         */
        void onReportContent(String content);

        /**
         * 点击取消时回调
         */
        void onCancel(BaseDialog dialog);
    }

}
