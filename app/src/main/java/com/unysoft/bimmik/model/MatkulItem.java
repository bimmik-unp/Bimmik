package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class MatkulItem {

    @SerializedName("id")
    private String id;
    @SerializedName("id_matkul")
    private String id_matkul;
    @SerializedName("nama")
    private String nama;
    @SerializedName("semester")
    private String semester;
    @SerializedName("sks")
    private String sks;
    @SerializedName("id_smt")
    private String id_smt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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
