package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.MainActivity;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.fragment.ProfileDosen;
import com.unysoft.bimmik.utils.GLOBAL;
import com.unysoft.bimmik.utils.SharedPrefManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity  {

    private static final String URL = "http://teagardenapp.com/bimmikapp/api/";
    private static final String URLP = "http://teagardenapp.com/bimmikapp/cetak.php?";


    SharedPrefManager sharedPrefManager;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String idDosen, idMhs, foto,namax;
    TextView nama, nm_dosen;
    FloatingActionButton chat;
    CircleImageView image;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zzz);

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        idDosen = preferences.getString("ID_DOSEN","");
        idMhs = preferences.getString("ID_MHS", "");
        foto = preferences.getString("FOTO_MHS", "");
        namax=preferences.getString("NAMA_MHS","");
        final String URLX =URLP+"id_mhs="+idMhs+"&nama="+namax;

        nama = findViewById(R.id.zzz_nama);
            nama.setText(preferences.getString("NAMA_MHS", ""));
        nm_dosen = findViewById(R.id.zzz_nama_dosenPA);
            nm_dosen.setText(preferences.getString("NAMA_DOSEN", ""));
        image = findViewById(R.id.dashboard_img);

        if  (foto.isEmpty()){
            Glide.with(Dashboard.this).load(R.drawable.user).into(image);
        } else {
            Glide.with(Dashboard.this).load(foto).into(image);
        }

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
                //startActivity(new Intent(Dashboard.this, Print.class));
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(URLX));
                startActivity(browserIntent);

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

    private void cetak() {

    }

    private void showDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Lihat profile",
                "Lihat pesan"};
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
                                startActivity(new Intent(Dashboard.this, Message.class));
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
