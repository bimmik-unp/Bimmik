package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class Keg_item {

    @SerializedName("id") private String id;
    @SerializedName("id_mhs") private String id_mhs;
    @SerializedName("nama") private String nama;
    @SerializedName("ket") private String ket;
    @SerializedName("value") private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId_mhs() {
        return id_mhs;
    }

    public void setId_mhs(String id_mhs) {
        this.id_mhs = id_mhs;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }
}
