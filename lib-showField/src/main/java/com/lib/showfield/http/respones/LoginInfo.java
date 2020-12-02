package com.lib.showfield.http.respones;


/**
 * Created by JoeyChow
 * Date  2020-04-14 11:20
 * <p>
 * Description
 **/
public class LoginInfo {

    /**
     * accessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMCIsInN1YiI6IjE4NTEyMTc3NzcwIiwiaXNzIjoibGl2ZS10cnVtcCIsImV4cCI6MTU4NjgzNTIwNSwiaWF0IjoxNTg2ODMzNDA1fQ.ELMn8eqS4osAwoiRgZ1nQfMPxKfkTmNoP5T6syu68gU
     * refreshToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMCIsInN1YiI6IjE4NTEyMTc3NzcwIiwiaXNzIjoibGl2ZS10cnVtcCIsImV4cCI6MTU4NzQzODIwNSwiaWF0IjoxNTg2ODMzNDA1fQ.7Aw8tLaAQsgitT6cCcoiEfHTIL0vF588jh379Ak9_bk
     * expiredTime : 1800000
     */

    private String accessToken;
    private String refreshToken;
    private int expiredTime;

    public int getIsAnchor() {
        return isAnchor;
    }

    public void setIsAnchor(int isAnchor) {
        this.isAnchor = isAnchor;
    }

    private int isAnchor;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiredTime=" + expiredTime +
                ", isAnchor=" + isAnchor +
                '}';
    }
}
