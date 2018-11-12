package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unysoft.bimmik.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity  {

    TextView nama;
    FloatingActionButton chat;
    CircleImageView image;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        findViewById(R.id.dashboard_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Profile.class));
            }
        });

        findViewById(R.id.dashboard_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, InputNilaiSemester.class));
            }
        });
        findViewById(R.id.dashboard_ik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Kegiatan.class));
            }
        });
        findViewById(R.id.dashboard_cl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Dashboard.this, Print.class));
            }
        });

//        progressDialog = new ProgressDialog(Dashboard.this);
//        progressDialog.setTitle("Mohon tunggu...");
//        progressDialog.setMessage("Sedang mengambil data...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();

        chat = (FloatingActionButton) findViewById(R.id.dashboard_fab_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Chat.class));
            }
        });

//        Bundle extras = getIntent().getExtras();
//
//        nama = findViewById(R.id.dashboard_nm_mhs);
//        image = findViewById(R.id.dashboard_img_profile);
//        findViewById(R.id.dashboard_edit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Ke layout profile mahasiswa & kirim id mahasiswa
////                startActivity(new Intent(Dashboard.this, Profile.class));
//                Toast.makeText(Dashboard.this, "Ke layout profile mahasiswa(CRUD)", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}
