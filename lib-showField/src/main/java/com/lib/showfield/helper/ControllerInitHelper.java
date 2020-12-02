package com.lib.showfield.helper;

import com.lib.showfield.interfaces.OnControllerInitListener;

public class ControllerInitHelper {

    private static ControllerInitHelper controllerInitHelper;

    private ControllerInitHelper() {

    }

    protected OnControllerInitListener mOnControllerInitListener;

    public static synchronized ControllerInitHelper getInstance() {

        if (controllerInitHelper == null) {
            controllerInitHelper = new ControllerInitHelper();
        }
        return controllerInitHelper;
    }

    public void setOnControllerInitListener(OnControllerInitListener listener) {
        this.mOnControllerInitListener = listener;
    }

    public void initFinish() {
        mOnControllerInitListener.initFinish();
    }
}
