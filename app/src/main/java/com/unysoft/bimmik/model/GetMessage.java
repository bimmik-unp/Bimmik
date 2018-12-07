package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMessage {

    @SerializedName("value") private String value;
    @SerializedName("output") private String output;
    @SerializedName("semuapesan") private List<MessageItem> semuapesan;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<MessageItem> getSemuapesan() {
        return semuapesan;
    }

    public void setSemuapesan(List<MessageItem> semuapesan) {
        this.semuapesan = semuapesan;
    }
}
