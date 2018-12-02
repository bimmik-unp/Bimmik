package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdate {

    @SerializedName("nama")
    String nama;
    @SerializedName("email")
    String email;
    @SerializedName("no_hp")
    String noHp;
    @SerializedName("prodi")
    String prodi;
    @SerializedName("id_dosen")
    String dosen_id_dosen;
    @SerializedName("id_mhs")
    String id_mhs;
    @SerializedName("value")
    String value;

    public String getValue() {
        return value;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getProdi() {
        return prodi;
    }

    public String getDosen_id_dosen() {
        return dosen_id_dosen;
    }

    public String getId_mhs() {
        return id_mhs;
    }
}
