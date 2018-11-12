package com.unysoft.bimmik.mahasiswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.unysoft.bimmik.R;

public class InputNilaiSemester extends AppCompatActivity {

    String[] smt = { "--Pilih semester--", "Semester 1", "Semester 2", "Semester 3", "Semester 4",
      "Semester 5", "Semester 6", "Semester 7", "Semester 8" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_input_nilai_semester);
        Toolbar toolbar = findViewById(R.id.toolbar_ins);
        toolbar.setTitle("Nama mahasiswa");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
        Spinner spinner = findViewById(R.id.ins_spin_smt);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, smt));


//        findViewById(R.id.ins_btn_kembali).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        findViewById(R.id.ins_btn_simpan).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(InputNilaiSemester.this);
//                builder.setTitle("Pemberitahuan!");
//                builder.setMessage("Data yang sudah disimpan tidak dapat diubah, mohon periksa kembali sebelum menyimpan data.");
//                builder.setIcon(R.drawable.ic_alert_circled);
//                builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(InputNilaiSemester.this, "Berhasil simpan data nilai.", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(InputNilaiSemester.this, GetNilaiSemester.class));
//                    }
//                })
//                .setNegativeButton("Periksa lagi", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });

    }
}
