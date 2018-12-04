package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSemester {

    @SerializedName("semuasemester")
    private List<SemesterItem> semuaSemester;
    @SerializedName("value")
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public List<SemesterItem> getSemuaSemester() {
        return semuaSemester;
    }

    public void setSemuaSemester(List<SemesterItem> semuaSemester) {
        this.semuaSemester = semuaSemester;
    }

    @Override
    public String toString(){
        return "ResponseSemester {" + "semuasemester = " + semuaSemester + '\'' + ",value = '" + value + "}";
    }


}
