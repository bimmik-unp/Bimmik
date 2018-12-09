package com.unysoft.bimmik.mahasiswa.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.InputNilaiSemester;
import com.unysoft.bimmik.mahasiswa.Kegiatan;
import com.unysoft.bimmik.model.GetMatkul;
import com.unysoft.bimmik.model.GetSemester;
import com.unysoft.bimmik.model.MatkulItem;
import com.unysoft.bimmik.model.NilaiItem;
import com.unysoft.bimmik.model.SemesterItem;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNilai extends AppCompatActivity {

    Spinner smt, matkul;
    EditText sks,nilai;
    Intent intent;
    BaseApiService baseApiService;
    String intentId, intentIdMatkul, intentNama, intentNilai, intentSks, intentIdSmt;
    String idSmt, idMatkul, sk, na;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_edit_nilai);

        sks = findViewById(R.id.ins_edit_et_sks);
        nilai = findViewById(R.id.ins_edit_et_nilai);

        smt = findViewById(R.id.ins_edit_spin_smt);
        matkul = findViewById(R.id.ins_edit_spin_id_matkul);

        intent = getIntent();
        intentId = intent.getStringExtra("idnilai");
        intentIdMatkul = intent.getStringExtra("idMt");
        intentNama = intent.getStringExtra("nmMt");
        intentNilai = intent.getStringExtra("nilai");
        intentSks = intent.getStringExtra("sks");

        nilai.setText(intentNilai);
        sks.setText(intentSks);
        
        findViewById(R.id.ins_edit_btn_simpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up();
            }
        });

        baseApiService = ApiClient.getClient().create(BaseApiService.class);
        initSpinsmt();

    }

    private void initSpinsmt() {
        baseApiService.getSemester().enqueue(new Callback<GetSemester>() {
            @Override
            public void onResponse(Call<GetSemester> call, Response<GetSemester> response) {
                final List<SemesterItem> semesterItems = response.body().getSemuaSemester();
                final List<String> listSpinner = new ArrayList<String>();
                for (int i = 0; i < semesterItems.size(); i++) {
                    listSpinner.add(semesterItems.get(i).getSmt());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    smt.setAdapter(adapter);
                    smt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                            String selectedName = parent.getItemAtPosition(position).toString();
                            idSmt = semesterItems.get(position).getId_smt();
                            Log.d("id_smt", idSmt);
                            if (selectedName.equals("Pilih Semester")) {
                                matkul.setEnabled(false);
                            } else {
                                matkul.setEnabled(true);
                                initSpinMatkul(idSmt);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetSemester> call, Throwable t) {

            }
        });
    }

    private void initSpinMatkul(String idSmt) {
        baseApiService.getMatkul(idSmt).enqueue(new Callback<GetMatkul>() {
            @Override
            public void onResponse(Call<GetMatkul> call, Response<GetMatkul> response) {
                final List<MatkulItem> semesterItems = response.body().getDaftar_matkul();
                final List<String> listSpinner = new ArrayList<String>();
                for (int i=0; i < semesterItems.size(); i++) {
                    listSpinner.add(0, intentNama);
                    listSpinner.add(semesterItems.get(i).getNama());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    matkul.setAdapter(adapter);
                    matkul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                            String selectedName = parent.getItemAtPosition(position).toString();
                            idMatkul = semesterItems.get(position).getId_matkul();
                            sk = semesterItems.get(position).getSks();
                            na = semesterItems.get(position).getNama();
                            Log.d("id_matkul", idMatkul);
                            if (selectedName == null){
                                sks.setText("");
                            } else {
                                sks.setText(sk);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetMatkul> call, Throwable t) {

            }
        });
    }

    private void up() {

        String zzNilai = nilai.getText().toString().trim();

        if (zzNilai.isEmpty()){
            nilai.setError("Field tidak boleh kosong");
        } else {
            baseApiService.updateNilai(intentId,idMatkul,na,zzNilai,sk,idSmt).enqueue(new Callback<NilaiItem>() {
                @Override
                public void onResponse(Call<NilaiItem> call, Response<NilaiItem> response) {
                    if (response.body().getValue().equals("1")){
                        FancyToast.makeText(EditNilai.this, "Berhasil edit data", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        startActivity(new Intent(EditNilai.this, InputNilaiSemester.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<NilaiItem> call, Throwable t) {

                }
            });
        }
    }

}
