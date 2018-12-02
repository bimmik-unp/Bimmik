package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDosen {

    @SerializedName("id_dosen")
    String id_dosen;
    @SerializedName("nama")
    String nama;
    @SerializedName("email")
    String email;
    @SerializedName("pass")
    String pass;
    @SerializedName("no_hp")
    String no_hp;
    @SerializedName("value")
    String value;

    public String getValue() {
        return value;
    }

    public String getId_dosen() {
        return id_dosen;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public ResponseDosen(String id_dosen, String nama, String email, String pass, String no_hp) {
        this.id_dosen = id_dosen;
        this.nama = nama;
        this.email = email;
        this.pass = pass;
        this.no_hp = no_hp;
    }
}
