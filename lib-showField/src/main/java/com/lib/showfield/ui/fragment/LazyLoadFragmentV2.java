package com.lib.showfield.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * Created by upingu
 * Date  2020-07-10 21:05
 * <p>
 * Description
 **/
public abstract class LazyLoadFragmentV2 extends Fragment {

    protected boolean isViewInitiated;

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
        if (getUserVisibleHint() && isViewInitiated ) {
            requestData();
            return true;
        }
        return false;
    }

}
