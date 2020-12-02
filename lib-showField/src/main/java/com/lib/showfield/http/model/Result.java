package com.lib.showfield.http.model;

/**
 * Created by JoeyChow
 * Date  2020-05-22 12:49
 * <p>
 * Description
 **/
public interface Result<T> {

    void onLoading();

    void onSuccess(T responseData);

    void onFailed(Throwable t);
}
