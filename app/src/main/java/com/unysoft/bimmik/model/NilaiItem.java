package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class NilaiItem {

    @SerializedName("id")
    private String id;
    @SerializedName("id_mhs")
    private String id_mhs;
    @SerializedName("id_matkul")
    private String id_matkul;
    @SerializedName("nama")
    private String nama;
    @SerializedName("nilai")
    private String nilai;
    @SerializedName("sks")
    private String sks;
    @SerializedName("id_smt")
    private String id_smt;
    @SerializedName("value")private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_mhs() {
        return id_mhs;
    }

    public void setId_mhs(String id_mhs) {
        this.id_mhs = id_mhs;
    }

    public String getId_matkul() {
        return id_matkul;
    }

    public void setId_matkul(String id_matkul) {
        this.id_matkul = id_matkul;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }

    public String getId_smt() {
        return id_smt;
    }

    public void setId_smt(String id_smt) {
        this.id_smt = id_smt;
    }
}
