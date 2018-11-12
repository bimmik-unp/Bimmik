package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.unysoft.bimmik.MainActivity;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.utils.User;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.BaseApiService;
import com.unysoft.bimmik.webservice.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    EditText nim, nama, pwd, email;
    Button regis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_register);
        Toolbar toolbar = findViewById(R.id.toolbar_regis);
        toolbar.setTitle("Register");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nim = findViewById(R.id.regis_et_nim);
        nama = findViewById(R.id.regis_et_nama);
        pwd = findViewById(R.id.regis_et_pwd);
        email = findViewById(R.id.regis_et_email);
        regis = findViewById(R.id.regis_btn);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this, "Berhasil register", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, MainActivity.class));

            }
        });

    }

}