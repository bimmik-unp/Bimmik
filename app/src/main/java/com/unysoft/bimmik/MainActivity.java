package com.unysoft.bimmik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unysoft.bimmik.dosen.Dosen_dashboard;
import com.unysoft.bimmik.mahasiswa.Dashboard;
import com.unysoft.bimmik.mahasiswa.Register;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.BaseApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://teagardenapp.com/bimmikapp/api/";

    TextView regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regis = findViewById(R.id.login_txt_regis);
        findViewById(R.id.login_btn_msk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesLogin();
            }
        });
        findViewById(R.id.login_dosen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Selamat datang (nama dosen)", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Dosen_dashboard.class));
            }
        });

    }

    private void prosesLogin() {

        EditText ETemail = findViewById(R.id.login_et_email);
        EditText ETpwd = findViewById(R.id.login_et_pwd);
        final String em = ETemail.getText().toString();
        final String pw = ETpwd.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
        Call<Value> call = baseApiService.cek_login(em,pw);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String error = response.body().getValue();
                String message = response.body().getMessage();
                if (error.equals("1")) {
                    Log.d("onResponse=1", message);
                    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString("email", em);
                    editor.putString("password", pw);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("onResponse=0", message);
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Log.d("onFailure", "Jaringan Error!");
                Toast.makeText(MainActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void regis(View view) {
        startActivity(new Intent(MainActivity.this, Register.class));
    }

}
