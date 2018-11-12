package com.unysoft.bimmik.dosen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.unysoft.bimmik.R;

public class Dosen_detail_mhs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dosen_detail_mhs);

//        Toolbar toolbar = findViewById(R.id.dosen_toolbar_detail_mhs);
//        toolbar.setTitle("Detail mahasiswa");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.dosen_fab_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dosen_detail_mhs.this, Dosen_chat.class));
            }
        });
    }
}
