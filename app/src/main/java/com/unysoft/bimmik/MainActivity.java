package com.unysoft.bimmik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.dosen.Dosen_dashboard;
import com.unysoft.bimmik.mahasiswa.Dashboard;
import com.unysoft.bimmik.model.DosenModel;
import com.unysoft.bimmik.model.ResponseDosen;
import com.unysoft.bimmik.model.ResponseMahasiswa;
import com.unysoft.bimmik.utils.GLOBAL;
import com.unysoft.bimmik.utils.SharedPrefManager;
import com.unysoft.bimmik.webservice.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://teagardenapp.com/bimmikapp/api/";

    boolean backExit = false;

    ProgressDialog progressDialog;
    TextView regis;
    EditText ETemail,ETpwd;

    String em, pw;
    int id_rb;
    RadioGroup radioGroup;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //COMMIT KOMENTAR
        //COMMIT KOMENTAR
        //COMMIT KOMENTAR

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();
        cekLogin();

        regis = findViewById(R.id.login_txt_regis);
        findViewById(R.id.login_btn_msk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesLogin();
            }
        });

    }

    private void cekLogin() {
        if (preferences.getString("STATUS_LOGIN", null) != null) {
            if (preferences.getString("STATUS_LOGIN", null).equals("TRUE")) {
                GLOBAL.nama_mhs = preferences.getString("NAMA_MHS", null);
                startActivity(new Intent(MainActivity.this, Dashboard.class));
                finish();
            }
        }

        if (preferences.getString("STATUS_LOGIN_DOSEN", null) != null) {
            if (preferences.getString("STATUS_LOGIN_DOSEN", null).equals("TRUE")) {
                GLOBAL.nama_dosen = preferences.getString("NAMA_DOSEN", null);
                startActivity(new Intent(MainActivity.this, Dosen_dashboard.class));
                finish();
            }
        }
    }

    private void cekParam(){
        ETemail = findViewById(R.id.login_et_email);
        ETpwd = findViewById(R.id.login_et_pwd);

        em = ETemail.getText().toString().trim();
        pw = ETpwd.getText().toString().trim();
        radioGroup = findViewById(R.id.radioGroup);
        id_rb = radioGroup.getCheckedRadioButtonId();

    }

    private void loginMhs(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang masuk...");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
        Call<ResponseMahasiswa> call = baseApiService.cek_login(em,pw);
        call.enqueue(new Callback<ResponseMahasiswa>() {
            @Override
            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                progressDialog.dismiss();
                if (response.body().getValue().equals("1")) {
                    GLOBAL.id_mhs = response.body().getId_mhs();
                    editor.putString("STATUS_LOGIN", "TRUE");
                    editor.putString("NAMA_MHS", response.body().getNama());
                    editor.putString("ID_MHS", response.body().getId_mhs());
                    editor.putString("EMAIL_MHS", response.body().getEmail());
                    editor.putString("NO_HP", response.body().getNo_hp());
                    editor.putString("PRODI", response.body().getProdi());
                    editor.putString("PASS_MHS", response.body().getPass());
                    editor.putString("NAMA_DOSEN", response.body().getNama_dosen());
                    editor.putString("ID_DOSEN", response.body().getId_dosen());
                    editor.commit();
                    startActivity(new Intent(MainActivity.this, Dashboard.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                    FancyToast.makeText(MainActivity.this, "Selamat datang " + response.body().getNama(), FancyToast.LENGTH_SHORT, FancyToast.DEFAULT, false).show();
                } else {
                    progressDialog.dismiss();
                    FancyToast.makeText(MainActivity.this, "Username atau password salah", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                }
            }
            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {
                progressDialog.dismiss();
                FancyToast.makeText(MainActivity.this, t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void loginDosen(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang masuk...");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
        Call<DosenModel> call_ds = baseApiService.cek_login_dosen(em,pw);
        call_ds.enqueue(new Callback<DosenModel>() {
            @Override
            public void onResponse(Call<DosenModel> call, Response<DosenModel> response) {
                progressDialog.dismiss();
                if (response.body().getValue().equals("1")) {
                    GLOBAL.id_dosen = response.body().getId_dosen();
                    editor.putString("STATUS_LOGIN_DOSEN", "TRUE");
                    editor.putString("NAMA_DOSEN", response.body().getNama());
                    editor.putString("EMAIL_DOSEN", response.body().getEmail());
                    editor.putString("ID_DOSEN", response.body().getId_dosen());
                    editor.putString("PASS_DOSEN", response.body().getPass());
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, Dosen_dashboard.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                    FancyToast.makeText(MainActivity.this, "Selamat datang "+response.body().getNama(), FancyToast.LENGTH_SHORT, FancyToast.DEFAULT, false).show();
                } else {
                    progressDialog.dismiss();
                    FancyToast.makeText(MainActivity.this, "Username atau password salah", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }
            @Override
            public void onFailure(Call<DosenModel> call, Throwable t) {
                progressDialog.dismiss();
                FancyToast.makeText(MainActivity.this, t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void prosesLogin() {
        cekParam();
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            FancyToast.makeText(MainActivity.this, "Level belum dipilih", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
        } else if (em.isEmpty()) {
            ETemail.setError("Email tidak boleh kosong");
        } else if (pw.isEmpty()) {
            ETpwd.setError("Password tidak boleh kosong");
        } else {
            switch (id_rb) {
                case R.id.login_rb_mhs:
                    loginMhs();
                    break;
                case R.id.login_rb_dosen:
                    loginDosen();
                    break;
                default:
                    break;
            }
        }
    }

    public void regis(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (backExit){
            super.onBackPressed();
            return;
        }
        this.backExit = true;
        FancyToast.makeText(MainActivity.this, "Tekan lagi untuk keluar", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backExit = false;
            }
        }, 2000);

    }
}