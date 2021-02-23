package com.melbsoft.teacherplatform.web.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String name) {
        super(name);
    }

    public InvalidInputException(String name, Throwable e) {
        super(name, e);
    }
}
