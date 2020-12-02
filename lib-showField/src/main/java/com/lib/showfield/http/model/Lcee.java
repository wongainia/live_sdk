package com.lib.showfield.http.model;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/12/07
 * desc   : 统一接口数据结构
 */
public class Lcee<T> {

    public final Status status;
    public BaseResponse<T> data;
    public final Throwable error;

    public Lcee(Status status, BaseResponse<T> data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Lcee<T> content(BaseResponse<T> data) {
        return new Lcee<>(Status.Content, data, null);
    }

    public static <T> Lcee<T> error(BaseResponse<T> data, Throwable error) {
        return new Lcee<>(Status.Error, data, error);
    }

    public static <T> Lcee<T> error(Throwable error) {
        return error(null, error);
    }

    public static <T> Lcee<T> empty(BaseResponse<T> data) {
        return new Lcee<>(Status.Empty, data, null);
    }

    public static <T> Lcee<T> empty() {
        return empty(null);
    }

    public static <T> Lcee<T> loading(BaseResponse<T> data) {
        return new Lcee<>(Status.Loading, data, null);
    }

    public static <T> Lcee<T> loading() {
        return loading(null);
    }

    public static <T> Lcee<T> noLogin(Throwable error) {
        return new Lcee<>(Status.NOLOGIN, null, error);
    }

    public static <T> Lcee<T> repeatLogin(Throwable error) {
        return new Lcee<>(Status.REPEATLOGIN, null, error);
    }

    public static <T> Lcee<T> tokenLost(Throwable error) {
        return new Lcee<>(Status.TOKENLOST, null, error);
    }
    public static <T> Lcee<T> occupy(Throwable error) {
        return new Lcee<>(Status.OCCURY,null, error);
    }

    public static <T> Lcee<T> nonet(Throwable error) {
        return new Lcee<>(Status.NONET,null, error);
    }

    public static <T> Lcee<T> entryClose(Throwable error) {
        return new Lcee<>(Status.ENTRYCLOSE,null, error);
    }

    public static <T> Lcee<T> full(Throwable error) {
        return new Lcee<>(Status.FULL,null, error);
    }

    public static <T> Lcee<T> aleadyIn(Throwable error) {
        return new Lcee<>(Status.ALEADYIN,null, error);
    }
}