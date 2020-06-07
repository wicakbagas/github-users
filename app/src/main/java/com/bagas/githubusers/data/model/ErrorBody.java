package com.bagas.githubusers.data.model;

import java.io.Serializable;

public class ErrorBody implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
