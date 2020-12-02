package com.lib.showfield.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.gyf.immersionbar.ImmersionBar;
import com.lib.showfield.R;
import com.lib.showfield.base.BaseActivity;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.base.action.SwipeAction;
import com.lib.showfield.base.action.ToastAction;
import com.lib.showfield.ui.view.dialog.LoadingDialog;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目中的 Activity 基类
 */
public abstract class MyActivity extends BaseActivity
        implements ToastAction,
        SwipeAction {

    private ImmersionBar mImmersionBar;
    /**
     * 加载对话框
     */
    private BaseDialog mDialog;
    /**
     * 对话框数量
     */
    private int mDialogTotal;


    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return mDialog != null && mDialog.isShowing();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        showDialogCustom(R.string.common_loading);
    }

    public void showDialogCustom(@StringRes int id){
        if (mDialog == null) {
            mDialog = new LoadingDialog.Builder(this)
                    .setCancelable(true)
                    .setMessage(id)
                    .create();
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        mDialogTotal++;
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (mDialogTotal == 1) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
        if (mDialogTotal > 0) {
            mDialogTotal--;
        }
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        initImmersion();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersion() {
        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            createStatusBarConfig().init();
        }
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 状态栏字体深色模式
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected ImmersionBar createStatusBarConfig() {
        // 在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont());
        return mImmersionBar;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @Nullable
    public ImmersionBar getStatusBarConfig() {
        return mImmersionBar;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
    }

    @Override
    protected void onDestroy() {
        if (isShowDialog()) {
            mDialog.dismiss();
        }
        mDialog = null;
        super.onDestroy();
    }
}