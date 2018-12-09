package com.unysoft.bimmik.webservice;

import com.unysoft.bimmik.model.DosenModel;
import com.unysoft.bimmik.model.GetMahasiswa;
import com.unysoft.bimmik.model.GetMatkul;
import com.unysoft.bimmik.model.GetMessage;
import com.unysoft.bimmik.model.Keg_item;
import com.unysoft.bimmik.model.NilaiItem;
import com.unysoft.bimmik.model.ResponseDosen;
import com.unysoft.bimmik.model.ResponseInputNilai;
import com.unysoft.bimmik.model.ResponseMahasiswa;
import com.unysoft.bimmik.model.ResponsePassword;
import com.unysoft.bimmik.model.ResponseSKS;
import com.unysoft.bimmik.model.ResponseUpdate;
import com.unysoft.bimmik.model.GetSemester;
import com.unysoft.bimmik.utils.Value;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("api_login.php")
    Call<ResponseMahasiswa> cek_login(@Field("email") String em,
                                      @Field("pass") String pw);

    @FormUrlEncoded
    @POST("api_login_dosen.php")
    Call<DosenModel> cek_login_dosen(@Field("email") String em,
                                     @Field("pass") String pw);

    @FormUrlEncoded
    @POST("api_registrasi.php")
    Call<Value> registerRequest(@Field("id_mhs") String id_mhs,
                                @Field("nama") String nama,
                                @Field("email") String email,
                                @Field("pass") String pass,
                                @Field("id_dosen") String id_dosen,
                                @Field("nama_dosen") String nama_dosen);
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

    @GET("api_tampil_kegiatan2.php")
    Call<Value> getKegiatan2(@Query("id_mhs") String id_mhs);

    @GET("api_tampil_kegiatan.php")
    Call<Value> getKegiatan(@Query("id_mhs") String id_mhs);



    @GET("api_tampil_nilai.php")
    Call<Value> getNilai(@Query("id_mhs") String id_mhs);
    @GET("api_tampil_nilai_smt.php")
    Call<Value> detailGetNilai(@Query("id_smt") String id_smt,
                               @Query("id_mhs") String id_mhs);

    @GET("api_tampil_mhs.php")
    Call<ResponseUpdate> getMhs(@Query("id_mhs") String id_mhs);

    @FormUrlEncoded
    @POST("api_update_mhs.php")
    Call<ResponseUpdate> mhsUpdate(@Field("nama") String nama,
                                   @Field("email") String email,
                                   @Field("no_hp") String noHp,
                                   @Field("prodi") String prodi,
                                   @Field("foto") String foto,
                                   @Field("id_mhs") String id_mhs );

    @GET("semester.php")
    Call<GetSemester> getSemester();

    @GET("matkul.php")
    Call<GetMatkul>getMatkul(@Query("id_smt") String id_smt);

    @FormUrlEncoded
    @POST("api_input_nilai.php")
    Call<ResponseInputNilai> inputNilai(@Field("id_mhs") String id_mhs,
                                        @Field("id_matkul") String id_matkul,
                                        @Field("nama") String nama,
                                        @Field("nilai") String nilai,
                                        @Field("sks") String sks,
                                        @Field("id_smt")String id_smt);

    @GET("api_tampil_dosen.php")
    Call<ResponseDosen> getSemuaDosen();

    @FormUrlEncoded
    @POST("api_update_pass.php")
    Call<ResponsePassword>updatePassword(@Field("id_mhs") String id_mhs,
                                         @Field("pass") String pass);
    @FormUrlEncoded
    @POST("api_update_pass2.php")
    Call<ResponsePassword>updatePassword2(@Field("id_dosen") String id_dosen,
                                         @Field("pass") String pass);

    @GET("api_tampil_mhs_dosen.php")
    Call<GetMahasiswa>getMahasiswaBimbingan(@Query("id_dosen") String id_dosen);



    @GET("api_profile_dosen.php")
    Call<DosenModel>getProfileDosen(@Query("id_dosen") String id_dosen);

    @GET("api_hapus_kegiatan.php")
    Call<Value>deleteKegiatan(@Query("id") String id);

    @GET("api_hapus_nilai.php")
    Call<Value>deleteNilai(@Query("id") String id);

    @FormUrlEncoded
    @POST("api_update_dsn.php")
    Call<DosenModel>updateDosen(@Field("nama") String nama,
                                @Field("email") String email,
                                @Field("no_hp") String no_hp,
                                @Field("foto") String foto,
                                @Field("id_dosen") String id_dosen);
    @Multipart
    @POST("update_pic_dosen.php")
    Call<Value> uploadPicDsn(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @Multipart
    @POST("update_pic_mhs.php")
    Call<Value> uploadPicMhs(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @FormUrlEncoded
    @POST("api_upd_kegiatan.php")
    Call<Keg_item>updateKegiatan(@Field("id") String id,
                                 @Field("nama") String nama,
                                 @Field("ket") String ket);

    @FormUrlEncoded
    @POST("api_upd_nilai.php")
    Call<NilaiItem>updateNilai(@Field("id") String id,
                               @Field("id_matkul") String id_matkul,
                               @Field("nama") String nama,
                               @Field("nilai") String nilai,
                               @Field("sks") String sks,
                               @Field("id_smt") String id_smt);

    @FormUrlEncoded
    @POST("api_kirim_pesan.php")
    Call<GetMessage>kirimPesan(@Field("from_user_id") String from_user_id,
                               @Field("to_user_id") String to_user_id,
                               @Field("title") String title,
                               @Field("message") String message);

    @GET("api_tampil_pesan.php")
    Call<GetMessage>getPesan(@Query("from_user_id") String id);

    @GET("api_tampil_pesan_mhs.php")
    Call<GetMessage>getPesanMhs(@Query("from_user_id") String from_id,
                                @Query("to_user_id") String to_id);


}
