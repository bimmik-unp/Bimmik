package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMatkul {

    @SerializedName("semuamatkul")
    private List<MatkulItem> daftar_matkul;

    @SerializedName("value")
    private String value;

    @SerializedName("message")
    private String message;

    public List<MatkulItem> getDaftar_matkul() {
        return daftar_matkul;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        return
                "ResponseMatkul{" +
                        "semuamatkul = '" + daftar_matkul + '\'' +
                        ",value = '" + value + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }

}
