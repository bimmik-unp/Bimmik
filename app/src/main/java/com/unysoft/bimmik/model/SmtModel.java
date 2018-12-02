package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class SmtModel {

//    @SerializedName("id")
    private String id;
//    @SerializedName("id_smt")
    private String id_smt;
//    @SerializedName("smt")
    private String smt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_smt() {
        return id_smt;
    }

    public void setId_smt(String id_smt) {
        this.id_smt = id_smt;
    }

    public String getSmt() {
        return smt;
    }

    public void setSmt(String smt) {
        this.smt = smt;
    }
}
