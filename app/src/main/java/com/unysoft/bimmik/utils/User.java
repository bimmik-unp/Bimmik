package com.unysoft.bimmik.utils;

import com.google.gson.annotations.SerializedName;

public class User {

    private String nama;
    private String nim;
    private String noHp;
    private String dosenPA;
    private String email;
    private String password;

    public User(String nama, String nim, String email, String password) {
        this.nama = nama;
        this.nim = nim;
        this.email = email;
        this.password = password;
    }

    public User(String nama, String nim, String noHp, String dosenPA, String email, String password) {
        this.nama = nama;
        this.nim = nim;
        this.noHp = noHp;
        this.dosenPA = dosenPA;
        this.email = email;
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getDosenPA() {
        return dosenPA;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
