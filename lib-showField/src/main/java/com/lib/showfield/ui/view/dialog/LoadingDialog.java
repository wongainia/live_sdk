package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;

import com.airbnb.lottie.LottieAnimationView;
import com.lib.showfield.R;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.base.action.AnimAction;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * Created by JoeyChow
 * Date  2020-04-15 16:58
 * <p>
 * Description
 **/
public class LoadingDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private final AppCompatTextView mMessageView;
        private final LottieAnimationView mAnimationView;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_loading);
            setAnimStyle(AnimAction.IOS);
            setBackgroundDimEnabled(false);
            setCancelable(false);

            mAnimationView = findViewById(R.id.loading_lottie);
            mAnimationView.playAnimation();
            mMessageView = findViewById(R.id.loading_text);
        }

        public LoadingDialog.Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }

        public LoadingDialog.Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            mMessageView.setVisibility(text == null ? View.GONE : View.VISIBLE);
            return this;
        }

        @Override
        public void dismiss() {
            super.dismiss();
            mAnimationView.cancelAnimation();
        }
    }
}
