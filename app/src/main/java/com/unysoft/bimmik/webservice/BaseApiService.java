package com.unysoft.bimmik.webservice;

import com.unysoft.bimmik.utils.Value;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {

    // Fungsi ini untuk memanggil API http://teagardenapp.com/BimmikApp/login.php
    @FormUrlEncoded
    @POST("cek_login.php")
    Call<Value> cek_login(@Field("email") String email,
                          @Field("password") String password);

    // Fungsi ini untuk memanggil API http:/teagardenapp.com/BimmikApp/register.php
    @FormUrlEncoded
    @POST("register")
    Call<Value> registerRequest(@Field("nim") String nim,
                                @Field("nama") String nama,
                                @Field("password") String password,
                                @Field("email") String email,
                                @Field("noHp") String noHp,
                                @Field("prodi") String prodi,
                                @Field("dosen_PA") String dosen_PA);

}
