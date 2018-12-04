package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDosen {

    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("Gresult")
    private List<DosenModel> semuadosen;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DosenModel> getSemuadosen() {
        return semuadosen;
    }

    public void setSemuadosen(List<DosenModel> semuadosen) {
        this.semuadosen = semuadosen;
    }
}
