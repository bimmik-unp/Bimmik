package com.unysoft.bimmik.utils;

import com.google.gson.annotations.SerializedName;
import com.unysoft.bimmik.model.Keg_item;
import com.unysoft.bimmik.model.NilaiItem;

import java.util.ArrayList;
import java.util.List;

public class Value {

    @SerializedName("value") private String value;
    @SerializedName("message") private String message;
    @SerializedName("user") private User user;
    @SerializedName("result") String id_mhs;
    @SerializedName("email") String email;
    @SerializedName("Gresult") private List<Keg_item> keg_items;
    @SerializedName("semuanilai") private List<NilaiItem> nilaiItems;
    @SerializedName("total_sks") String total_sks;

    public List<NilaiItem> getNilaiItems() {
        return nilaiItems;
    }

    public void setNilaiItems(List<NilaiItem> nilaiItems) {
        this.nilaiItems = nilaiItems;
    }

    public void setKeg_items(List<Keg_item> keg_items) {
        this.keg_items = keg_items;
    }

    public String getTotal_sks() {
        return total_sks;
    }

    public void setTotal_sks(String total_sks) {
        this.total_sks = total_sks;
    }

    public List<Keg_item> getKegiatan() {
        return keg_items;
    }

    public Value(String value, String message, User user, String id_mhs) {
        this.value = value;
        this.message = message;
        this.user = user;
        this.id_mhs=id_mhs;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public void setId_mhs(String id_mhs) {
        this.id_mhs = id_mhs;
    }

    public String getId_mhs() {

        return id_mhs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {

        return user;
    }

}
