package com.unysoft.bimmik.utils;

import com.google.gson.annotations.SerializedName;

public class Value {

    private String value;
    private String message;

    public Value(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
