package com.lib.showfield.helper;

import android.view.View;

import com.lib.showfield.interfaces.AddCustomViewListener;

public class AddCustomViewHelper {

    private static AddCustomViewHelper addCustomViewHelper;

    private AddCustomViewHelper () {

    }

    public static synchronized AddCustomViewHelper getInstance() {

        if (addCustomViewHelper == null) {
            addCustomViewHelper = new AddCustomViewHelper();
        }
        return addCustomViewHelper;
    }

    protected AddCustomViewListener mAddCustomViewListener;

    public void setAddCustomViewListener(AddCustomViewListener listener) {
        this.mAddCustomViewListener = listener;
    }

    public void addView(View view){
        mAddCustomViewListener.addCustomView(view);
    }
}
