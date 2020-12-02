package com.lib.showfield.http.exception;

import java.io.IOException;

/**
 * Created by JoeyChow
 * Date  2020-05-07 12:29
 * <p>
 * Description
 **/
public class ApiException extends IOException {

    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
