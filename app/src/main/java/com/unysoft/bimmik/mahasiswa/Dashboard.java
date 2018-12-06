package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.MainActivity;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.adapter.ProfileDosen;
import com.unysoft.bimmik.model.DosenModel;
import com.unysoft.bimmik.model.ResponseDosen;
import com.unysoft.bimmik.utils.GLOBAL;
import com.unysoft.bimmik.utils.SharedPrefManager;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity  {

    private static final String URL = "http://teagardenapp.com/bimmikapp/api/";

    SharedPrefManager sharedPrefManager;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String idDosen, idMhs;
    TextView nama, nm_dosen;
    FloatingActionButton chat;
    CircleImageView image;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zzz);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();


        nama = findViewById(R.id.zzz_nama);
        nama.setText(preferences.getString("NAMA_MHS", ""));
        nm_dosen = findViewById(R.id.zzz_nama_dosenPA);
        nm_dosen.setText(preferences.getString("NAMA_DOSEN", ""));
        idDosen = preferences.getString("ID_DOSEN","");

        idDosen = preferences.getString("ID_DOSEN","");
        idMhs = preferences.getString("ID_MHS", "");


        findViewById(R.id.zzz_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Profile.class));
            }
        });
        findViewById(R.id.zzz_input_nilai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, InputNilaiSemester.class));
            }
        });
        findViewById(R.id.zzz_input_kegiatan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Kegiatan.class));
            }
        });
        findViewById(R.id.zzz_cetak_laporan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                builder.setTitle("Cetak laporan");
                String[] p = {"Laporan kegiatan","Laporan nilai"};
                builder.setItems(p, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //cetak kegiatan
                                break;
                            case 1:
                                //cetak nilai
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        findViewById(R.id.zzz_pilihan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        findViewById(R.id.zzz_profile_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Dashboard.this)
                        .setMessage("Logout dari aplikasi ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GLOBAL.id_mhs = "";
                                editor.putString("STATUS_LOGIN", "FALSE");
                                editor.clear();
                                editor.apply();
                                Intent i = new Intent(Dashboard.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });




    }

    private void showDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Lihat profile",
                "Kirim pesan",
                "Ganti dosen pembimbing"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0:




                                Bundle bundle = new Bundle();
                                bundle.putString("idosen", idDosen);
                                bundle.putString("ndosen", preferences.getString("NAMA_DOSEN",""));
                                ProfileDosen profileDosen = new ProfileDosen().newInstance();
                                profileDosen.show(getSupportFragmentManager(), "profile_dosen");
                                profileDosen.setArguments(bundle);

                                break;
                            case 1:
                                FancyToast.makeText(Dashboard.this, "Dalam proses", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                                break;
                            case 2:
                                FancyToast.makeText(Dashboard.this, "Dalam proses", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Apa anda ingin keluar dari aplikasi ?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

}
