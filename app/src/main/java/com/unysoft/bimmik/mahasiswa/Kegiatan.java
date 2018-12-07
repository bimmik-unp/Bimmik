package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.adapter.KegiatanAdapter;
import com.unysoft.bimmik.model.Keg_item;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Kegiatan extends AppCompatActivity {

    private static final String URL = "http://teagardenapp.com/bimmikapp/api/";
    EditText nama, keterangan;
    ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    KegiatanAdapter kegiatanAdapter;
    List<Keg_item> keg_itemList = new ArrayList<>();

    BaseApiService baseApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_kegiatan);
        Toolbar toolbar = findViewById(R.id.toolbar_kegiatan);
        toolbar.setTitle("Input kegiatan");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        baseApiService = ApiClient.getClient().create(BaseApiService.class);

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        recyclerView = findViewById(R.id.kegiatan_rv);
        kegiatanAdapter = new KegiatanAdapter(keg_itemList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        AmbilKegiatan();


        findViewById(R.id.kegiatan_btn_simpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanKegiatan();
            }
        });
    }

    private void AmbilKegiatan() {

        progressDialog = ProgressDialog.show(this, null, "Mengambil data...", true, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseApiService baseApiService = retrofit.create(BaseApiService.class);

        Call<Value> call = baseApiService.getKegiatan(preferences.getString("ID_MHS",""));
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")) {
                    progressDialog.dismiss();
                    final List<Keg_item> keg_itemList = response.body().getKegiatan();
                    recyclerView.setAdapter(new KegiatanAdapter(keg_itemList, getApplicationContext()));
                    kegiatanAdapter.notifyDataSetChanged();
                } else {
                    progressDialog.dismiss();
                    FancyToast.makeText(getApplicationContext(), "Gagal mengambil data", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                FancyToast.makeText(getApplicationContext(), "Periksa jaringan Anda", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });

    }

    private void simpanKegiatan() {

        nama = findViewById(R.id.kegiatan_et_nm);
        keterangan = findViewById(R.id.kegiatan_et_ket);

        String na = nama.getText().toString().trim();
        final String ket = keterangan.getText().toString().trim();

        String idm = preferences.getString("ID_MHS","");

        nama.setError(null); keterangan.setError(null);

        if (TextUtils.isEmpty(na)){
            nama.setError("Field tidak boleh kosong");
        } else if (TextUtils.isEmpty(ket)) {
            keterangan.setError("Field tidak boleh kosong");
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Menyimpan data....");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            BaseApiService baseApiService = retrofit.create(BaseApiService.class);
            Call<Value> call = baseApiService.inputKegiatan(idm, na, ket);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    if (response.body().getValue().equals("1")) {
                        progressDialog.dismiss();
                        FancyToast.makeText(Kegiatan.this, "Berhasil menyimpan", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        AmbilKegiatan();
                        nama.setText(""); nama.requestFocus(); keterangan.setText("");
                    } else {
                        progressDialog.dismiss();
                        FancyToast.makeText(Kegiatan.this, "Gagal menyimpan", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {

                }
            });

        }
    }
}
