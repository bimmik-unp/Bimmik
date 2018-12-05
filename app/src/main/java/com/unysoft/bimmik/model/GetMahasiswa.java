package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMahasiswa {

    @SerializedName("value") private String value;
    @SerializedName("message") private String message;
    @SerializedName("Mahasiswa") private List<ResponseMahasiswa> semuamahasiswa;

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

    public List<ResponseMahasiswa> getSemuamahasiswa() {
        return semuamahasiswa;
    }

    public void setSemuamahasiswa(List<ResponseMahasiswa> semuamahasiswa) {
        this.semuamahasiswa = semuamahasiswa;
    }
}
