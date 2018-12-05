package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class ResponsePassword {

    @SerializedName("newpass")private String newpass;
    @SerializedName("value")private String value;
    @SerializedName("pass")private String pass;
    @SerializedName("message")private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
