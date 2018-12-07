package com.unysoft.bimmik.mahasiswa.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.Kegiatan;
import com.unysoft.bimmik.model.Keg_item;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditKegiatan extends AppCompatActivity {

    EditText nama,ket;
    BaseApiService baseApiService;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_edit_kegiatan);

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        baseApiService = ApiClient.getClient().create(BaseApiService.class);

        nama = findViewById(R.id.mhs_editKeg_nama);
        ket = findViewById(R.id.mhs_editKeg_ket);

        final Intent intent = getIntent();
        nama.setText(intent.getStringExtra("nk"));
        ket.setText(intent.getStringExtra("kk"));

        findViewById(R.id.mhs_editKeg_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idmhs = preferences.getString("ID_MHS","");
                String nm = nama.getText().toString().trim();
                String k = ket.getText().toString().trim();
                baseApiService.updateKegiatan(idmhs,nm,k).enqueue(new Callback<Keg_item>() {
                    @Override
                    public void onResponse(Call<Keg_item> call, Response<Keg_item> response) {
                        if (response.body().getValue().equals("1")){
                            FancyToast.makeText(EditKegiatan.this, "Berhasil edit data", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            startActivity(new Intent(EditKegiatan.this, Kegiatan.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Keg_item> call, Throwable t) {

                    }
                });
            }
        });

    }

}
