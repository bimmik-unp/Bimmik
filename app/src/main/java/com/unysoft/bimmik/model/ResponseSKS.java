package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class ResponseSKS {

    @SerializedName("sks")
    private String sks;
    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }

    public String getSks() {
        return sks;
    }
}
