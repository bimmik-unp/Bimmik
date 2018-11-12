package com.unysoft.bimmik.adapter;

public class ImageModel {

    private String nmMhs;
    private int imgMhs;

    public ImageModel(String nmMhs, int imgMhs) {
        this.nmMhs = nmMhs;
        this.imgMhs = imgMhs;
    }

    public String getNmMhs() {
        return nmMhs;
    }

    public void setNmMhs(String nmMhs) {
        this.nmMhs = nmMhs;
    }

    public int getImgMhs() {
        return imgMhs;
    }

    public void setImgMhs(int imgMhs) {
        this.imgMhs = imgMhs;
    }
}
