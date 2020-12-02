package com.lib.showfield.http.model;

/**
 * Created by JoeyChow
 * Date  2020/4/1 17:38
 * <p>
 * Description
 **/
public class Hosts {

    public static final String APPID = "APP1594644083423";//测试
    public static String BASE_URL = "https://gateway-test.upingu.cn";
    public static String H5_BASE_URL = "https://h5-test.upingu.cn";

    /**
     * code码
     */
    public static final int RESP_CODE_SUCCESS = 200;//成功

    public static final int RESP_CODE_FAIL = 400;//失败

    public static final int RESP_CODE_ERROR = 99999;//（异常）

    public static final int RESP_CODE_TOKEN_LOST = 1303;//（token失效）

    public static final int RESP_CODE_REPEAT = 1301;//（重复登录）

    public static final int RESP_TOKEN_BAN = 1309;//(封禁)

    public static final int RESP_BAD_APPID = 1307;//（appId不合法）

    public static final int RESP_NO_LOGIN = 1308;//（未登录,token缺失）

    public static final int RESP_PULL_URL_NOT_EXIST = 1405;//（拉流地址不存在）

    public static final int RESP_NOT_HAVE_CHARGE = 1904; //余额不足

    public static final int RESP_SEND_NO_TIMES = 1941; //发言过于频繁
}
