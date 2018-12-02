package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class ResponseMahasiswa {

    @SerializedName("id_mhs")
    String id_mhs;
    @SerializedName("nama")
    String nama;
    @SerializedName("email")
    String email;
    @SerializedName("no_hp")
    String no_hp;
    @SerializedName("prodi")
    String prodi;
    @SerializedName("pass")
    String pass;
    @SerializedName("id_dosen")
    String id_dosen;

    public String getId_dosen() {
        return id_dosen;
    }

    public void setId_dosen(String id_dosen) {
        this.id_dosen = id_dosen;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @SerializedName("value")
    String value;

    public String getValue() {

        return value;
    }

    public String getId_mhs() {
        return id_mhs;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public String getProdi() {
        return prodi;
    }
}
