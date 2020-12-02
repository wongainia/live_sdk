package com.lib.showfield.bean;

/**
 * Created by JoeyChow
 * Date  2020-07-27 14:21
 * <p>
 * Description
 **/
public class LoginParam {

    private String appId;
    private String userId;
    private String nickName;
    private String avatar;

    public LoginParam(String appId, String userId, String nickName, String avatar) {
        this.appId = appId;
        this.userId = userId;
        this.nickName = nickName;
        this.avatar = avatar;
    }
}
