package com.unysoft.bimmik.dosen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.adapter.DosenListMhs;
import com.unysoft.bimmik.adapter.ImageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dosen_dashboard extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    private ListView lv;
    List<ImageModel> list;

    String[] nmMhs= {
        "Mahasiswa 1",
        "Mahasiswa 1",
        "Mahasiswa 1",
        "Mahasiswa 1",
        "Mahasiswa 1",
        "Mahasiswa 1",
        "Mahasiswa 1",
        "Mahasiswa 1",
        "Mahasiswa 1",
        "Mahasiswa 1",
    };

    int[] imgMhs = {
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dosen_dashboard);

        list = new ArrayList<>();
        lv = (ListView) findViewById(R.id.dosen_list_mhs);

        list.add(new ImageModel(nmMhs[0], imgMhs[0]));
        list.add(new ImageModel(nmMhs[1], imgMhs[1]));
        list.add(new ImageModel(nmMhs[2], imgMhs[2]));
        list.add(new ImageModel(nmMhs[3], imgMhs[3]));
        list.add(new ImageModel(nmMhs[4], imgMhs[4]));
        list.add(new ImageModel(nmMhs[5], imgMhs[5]));
        list.add(new ImageModel(nmMhs[6], imgMhs[6]));
        list.add(new ImageModel(nmMhs[7], imgMhs[7]));
        list.add(new ImageModel(nmMhs[8], imgMhs[8]));
        list.add(new ImageModel(nmMhs[9], imgMhs[9]));

        DosenListMhs adapter = new DosenListMhs(this, R.layout.dosen_list_mhs, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Dosen_dashboard.this, Dosen_detail_mhs.class));
            }
        });

        findViewById(R.id.dosen_dashboard_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dosen_dashboard.this, Dosen_profile.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
