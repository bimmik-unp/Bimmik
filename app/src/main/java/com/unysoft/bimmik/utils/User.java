package com.unysoft.bimmik.utils;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String nim;

    public User(String name, String email, String password, String nim) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nim = nim;
    }

    public User(int id, String name, String email, String gender){
        this.id = id;
        this.name = name;
        this.email = email;
        this.nim = gender;
    }

    public User(int id, String name, String email, String password, String nim) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nim = nim;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getNim() {
        return nim;
    }

}
