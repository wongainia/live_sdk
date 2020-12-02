package com.lib.showfield.helper;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.SPUtils;
import com.lib.showfield.common.Constants;
import com.lib.showfield.http.model.Lcee;
import com.lib.showfield.http.respones.live.UserInfo;
import com.lib.showfield.model.LiveViewModel;
import com.lib.showfield.other.IntentKey;

/**
 * Created by JoeyChow
 * Date  2020-07-28 20:05
 * <p>
 * Description
 **/
public class UserLoginExitHelper {

    private String mUserId;
    private FragmentActivity mActivity;
    private LiveViewModel liveViewModel;

    private static UserLoginExitHelper userLoginExitHelper;

    private UserLoginExitHelper(FragmentActivity activity) {
        this.mActivity = activity;
        liveViewModel = new ViewModelProvider(activity).get(LiveViewModel.class);
    }

    public static synchronized UserLoginExitHelper getInstance(FragmentActivity activity) {

        if (userLoginExitHelper == null) {
            userLoginExitHelper = new UserLoginExitHelper(activity);
        }
        return userLoginExitHelper;
    }

    public void getUserInfo() {
        liveViewModel.getUser().observe(mActivity, new Observer<Lcee<UserInfo>>() {
            @Override
            public void onChanged(@Nullable Lcee<UserInfo> lcee) {
                updateUserView(lcee);
            }
        });

        liveViewModel.reload(false);
    }

    private void updateUserView(Lcee<UserInfo> lcee) {
        switch (lcee.status) {
            case Content:
                SPUtils.getInstance("user").put(IntentKey.AVATAR, lcee.data.getData().getAvatar());
                SPUtils.getInstance("user").put(Constants.USER_ID, lcee.data.getData().getUserId());
                SPUtils.getInstance("user").put(Constants.NICKNAME, lcee.data.getData().getNickName());

                mUserId = lcee.data.getData().getUserId();
                SPUtils.getInstance("user").put(Constants.USER_ID, mUserId);
                break;
            case Error:
                break;
        }

    }

    /**
     * 登录
     */
    public void getLogin(String token) {

        //保存token数据
        SPUtils.getInstance("token").put(Constants.TOKEN, token);
        SPUtils.getInstance().put(Constants.IS_LOGIN, true);

        getUserInfo();
    }

    /**
     * 退出登录
     */
    public void exitLogin() {
        SPUtils.getInstance("token").clear();
        SPUtils.getInstance().put(Constants.IS_LOGIN, false);
    }
}
