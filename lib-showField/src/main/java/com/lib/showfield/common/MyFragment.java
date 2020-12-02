package com.lib.showfield.common;

import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.lib.showfield.base.BaseFragment;
import com.lib.showfield.base.action.ToastAction;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目中 Fragment 懒加载基类
 */
public abstract class MyFragment<A extends MyActivity> extends BaseFragment<A>
        implements ToastAction {

    @Override
    protected void initFragment() {
        super.initFragment();
    }

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return getAttachActivity().isShowDialog();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        getAttachActivity().showDialog();
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        getAttachActivity().hideDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}