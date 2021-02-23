package com.melbsoft.teacherplatform.web.exception;

public class FailOperationException extends RuntimeException {

    public FailOperationException(String name) {
        super(name);
    }

    public FailOperationException(String name, Throwable e) {
        super(name, e);
    }
}
