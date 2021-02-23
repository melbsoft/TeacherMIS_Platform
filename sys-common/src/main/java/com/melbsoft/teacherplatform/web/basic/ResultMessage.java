package com.melbsoft.teacherplatform.web.basic;

import lombok.Data;

@Data
public class ResultMessage<T> {

    public static final ResultMessage SUCCESS = new ResultMessage("00000", "ok");
    public static final ResultMessage FAIL = new ResultMessage("10000", "fail");
    public static final ResultMessage ERROR = new ResultMessage("00003", "SYSTEM ERROR");
    public static final ResultMessage UN_AUTH = new ResultMessage("00002", "UNAUTHORIZED");
    public static final ResultMessage INVALID = new ResultMessage("00001", "INVALID REQUEST");

    private final String code;
    private final String message;
    private T data;

    public ResultMessage(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ResultMessage(String code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static <T> ResultMessage<T> success(T data) {
        return new ResultMessage(SUCCESS.code, SUCCESS.message, data);
    }

    public static ResultMessage fail(String message) {
        return new ResultMessage(FAIL.code, message);
    }
}
