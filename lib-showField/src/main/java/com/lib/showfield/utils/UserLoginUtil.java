package com.lib.showfield.utils;

import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lib.showfield.common.Constants;

/**
 * 用户登录状态
 */
public class UserLoginUtil {

    /**
     * 带跳转动作
     * true: 未登陆
     * false:已登陆
     */
    public static boolean checkLogin(Context context) {
        if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            return false;
        } else {
            ToastUtils.showShort("请登录");
            return true;
        }
    }
}
