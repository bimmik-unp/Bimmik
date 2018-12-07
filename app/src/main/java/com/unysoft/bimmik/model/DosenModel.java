package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class DosenModel {

    @SerializedName("id") private String id;
    @SerializedName("id_dosen") private String id_dosen;
    @SerializedName("nama") private String nama;
    @SerializedName("email") private String email;
    @SerializedName("pass") private String pass;
    @SerializedName("no_hp") private String no_hp;
    @SerializedName("value") private String value;
    @SerializedName("message") private String message;
    @SerializedName("foto")private String foto;
//    @SerializedName("Gresult") private String getProfile;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public String getGetProfile() {
//        return getProfile;
//    }
//
//    public void setGetProfile(String getProfile) {
//        this.getProfile = getProfile;
//    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_dosen() {
        return id_dosen;
    }

    public void setId_dosen(String id_dosen) {
        this.id_dosen = id_dosen;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
