package com.coffee.entity;

import java.io.Serializable;

public class Result implements Serializable {

    private boolean success;

    private Object message;

    public Result(boolean success, Object message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
