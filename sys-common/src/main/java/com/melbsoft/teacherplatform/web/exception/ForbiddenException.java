package com.melbsoft.teacherplatform.web.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String name) {
        super(name);
    }

    public ForbiddenException(String name, Throwable e) {
        super(name, e);
    }
}
