package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMatkul {

    @SerializedName("Gresult")
    private List<MatkulItem> gResult;

    @SerializedName("value")
    private String value;

    @SerializedName("message")
    private String message;

    public void setSemuadosen(List<MatkulItem> gResult){
        this.gResult = gResult;
    }

    public List<MatkulItem> getgResult(){
        return gResult;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "ResponseDosen{" +
                        "Gresult = '" + gResult + '\'' +
                        ",value = '" + value + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }

}
