package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class ResponseMahasiswa {

    @SerializedName("id_mhs") String id_mhs;
    @SerializedName("nama") String nama;
    @SerializedName("email") String email;
    @SerializedName("no_hp") String no_hp;
    @SerializedName("prodi") String prodi;
    @SerializedName("pass") String pass;
    @SerializedName("nama_dosen") String nama_dosen;
    @SerializedName("id_dosen") String id_dosen;
    @SerializedName("foto") String foto;
    @SerializedName("value") String value;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId_dosen() {
        return id_dosen;
    }

    public void setId_dosen(String id_dosen) {
        this.id_dosen = id_dosen;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNama_dosen() {
        return nama_dosen;
    }

    public void setNama_dosen(String nama_dosen) {
        this.nama_dosen = nama_dosen;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
