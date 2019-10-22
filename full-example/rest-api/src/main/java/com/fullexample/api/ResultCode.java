package com.fullexample.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultCode {
    private int code;
    private String message;

    public ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @JsonProperty
    public int getCode() {
        return code;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }
}
