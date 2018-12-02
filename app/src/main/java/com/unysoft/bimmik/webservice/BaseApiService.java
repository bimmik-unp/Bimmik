package com.unysoft.bimmik.webservice;

import com.unysoft.bimmik.model.ResponseDosen;
import com.unysoft.bimmik.model.ResponseMahasiswa;
import com.unysoft.bimmik.model.ResponseMatkul;
import com.unysoft.bimmik.model.ResponseUpdate;
import com.unysoft.bimmik.utils.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("api_login.php")
    Call<ResponseMahasiswa> cek_login(@Field("email") String em,
                                      @Field("pass") String pw);

    @FormUrlEncoded
    @POST("api_login_dosen.php")
    Call<ResponseDosen> cek_login_dosen(@Field("email") String em,
                                        @Field("pass") String pw);

    @FormUrlEncoded
    @POST("api_registrasi.php")
    Call<Value> registerRequest(@Field("id_mhs") String id_mhs,
                                @Field("nama") String nama,
                                @Field("email") String email,
                                @Field("pass") String pass,
                                @Field("id_dosen") String id_dosen);
    @FormUrlEncoded
    @POST("api_registrasi_dosen.php")
    Call<Value> registerDosen(@Field("id_dosen") String id_dosen,
                                @Field("nama") String nama,
                                @Field("email") String email,
                                @Field("pass") String pass);

    @FormUrlEncoded
    @POST("api_input_kegiatan.php")
    Call<Value> inputKegiatan(@Field("id_mhs") String id_mhs,
                                @Field("nama") String nama,
                              @Field("ket") String ket
                              );

    @GET("api_tampil_kegiatan.php")
    Call<Value> getKegiatan(@Query("id_mhs") String id_mhs);

    @GET("api_tampil_mhs.php")
    Call<ResponseUpdate> getMhs(@Query("id_mhs") String id_mhs);

    @FormUrlEncoded
    @POST("api_update_mhs.php")
    Call<ResponseUpdate> mhsUpdate(@Field("nama") String nama,
                                   @Field("email") String email,
                                   @Field("no_hp") String noHp,
                                   @Field("prodi") String prodi,
                                   @Field("id_dosen") String id_dosen,
                                   @Query("id_mhs") String id_mhs
                                   );


}
