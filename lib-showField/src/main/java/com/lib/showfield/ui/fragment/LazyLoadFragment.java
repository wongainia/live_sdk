package com.lib.showfield.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import com.lib.showfield.common.MyFragment;

/**
 * Created by JoeyChow
 * Date  2020-07-10 21:05
 * <p>
 * Description
 **/
public abstract class LazyLoadFragment extends MyFragment {

    protected boolean isViewInitiated;

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareRequestData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewInitiated = isVisibleToUser;
        prepareRequestData();
    }

    public abstract void requestData();

    public boolean prepareRequestData() {
        Log.d("zzy", "----prepareRequestData----: " + getUserVisibleHint());
        if (getUserVisibleHint() && isViewInitiated) {
            requestData();
            return true;
        }
        return false;
    }
}