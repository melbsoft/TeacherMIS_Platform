package com.melbsoft.teacherplatform.web.basic;

public class MessageException extends Exception {

    public MessageException(String name) {
        super(name);
    }

    public MessageException(String name, Throwable e) {
        super(name, e);
    }
}
