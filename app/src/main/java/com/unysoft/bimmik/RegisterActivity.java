package com.unysoft.bimmik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.model.DosenModel;
import com.unysoft.bimmik.model.ResponseDosen;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.BaseApiService;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    public static final String URL = "http://bimmikapps-unp.com/api/";

    ProgressDialog progressDialog;

    EditText ETnim, ETnama, ETpwd, ETemail;
    Button regis;
    Spinner spin_dosen;

    TextInputLayout textInputLayout;

    String ni,na,pw,em;
    String tag_json_obj = "json_obj_req";
    String idDosen, nmDosen;

    RadioGroup radioGroup;
    RadioButton rbDosen, rbMhs;
    int rbId;

    private ArrayList<String> dosen;
    private JSONArray result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dosen = new ArrayList<String>();
        spin_dosen = findViewById(R.id.regis_spinner);
        spin_dosen.setVisibility(View.GONE);

        textInputLayout = findViewById(R.id.regis_nim_textinputlayout);
        textInputLayout.setHint("Kode dosen/Nomor induk");
        radioGroup = findViewById(R.id.radioGroup2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.regis_rb_dosen:
                        spin_dosen.setVisibility(View.GONE);
                        textInputLayout.setHint("Kode dosen");
                        break;
                    case R.id.regis_rb_mhs:
                        spin_dosen.setVisibility(View.VISIBLE);
                        textInputLayout.setHint("Nomor induk");
                        break;
                    default:
                        break;
                }
            }
        });

        regis = findViewById(R.id.regis_btn);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesRegister();
            }
        });


        ambilDataDosen();
        spin_dosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void ambilDataDosen() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
        Call<ResponseDosen> call = baseApiService.getSemuaDosen();
        call.enqueue(new Callback<ResponseDosen>() {
            @Override
            public void onResponse(Call<ResponseDosen> call, retrofit2.Response<ResponseDosen> response) {
                if (response.body().getValue().equals("1")){
                    final List<DosenModel> dosenModels = response.body().getSemuadosen();
                    final List<String> listSpinner = new ArrayList<String>();
                    for (int i=0; i < dosenModels.size(); i++) {
                        listSpinner.add(dosenModels.get(i).getNama());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spin_dosen.setAdapter(adapter);
                        spin_dosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedName = parent.getItemAtPosition(position).toString();
                                idDosen = dosenModels.get(position).getId_dosen();
                                nmDosen = dosenModels.get(position).getNama();
                                Log.d("id_dosen", idDosen+"-"+nmDosen);
                                if (selectedName == null){

                                } else {

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDosen> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }


    private void cekParam(){
        ETnim = findViewById(R.id.regis_et_nim);
        ETnama = findViewById(R.id.regis_et_nama);
        ETpwd = findViewById(R.id.regis_et_pwd);
        ETemail = findViewById(R.id.regis_et_email);

        rbDosen = findViewById(R.id.regis_rb_dosen);
        rbMhs = findViewById(R.id.regis_rb_mhs);
        rbId = radioGroup.getCheckedRadioButtonId();

        ni = ETnim.getText().toString().trim();
        na = ETnama.getText().toString().trim();
        pw = ETpwd.getText().toString().trim();
        em = ETemail.getText().toString().trim();

        ETnim.setError(null); ETnama.setError(null);
        ETpwd.setError(null); ETemail.setError(null);
    }

    private void prosesRegister() {
        cekParam();
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            FancyToast.makeText(RegisterActivity.this, "Level belum dipilih", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
        } else if (TextUtils.isEmpty(ni)) {
            ETnim.setError("Nomor induk tidak boleh kosong");
        } else if (TextUtils.isEmpty(na)) {
            ETnama.setError("Nama tidak boleh kosong");
        } else if (TextUtils.isEmpty(em)) {
            ETemail.setError("Email tidak boleh kosong");
        } else if (TextUtils.isEmpty(pw)) {
            ETpwd.setError("Password tidak boleh kosong");
        } else if (pw.length() < 6){
            ETpwd.setError("Password harus lebih dari 6 karakter");
        } else {
            switch (rbId) {
                case R.id.regis_rb_mhs:
                    regisMhs();
                    break;
                case R.id.regis_rb_dosen:
                    spin_dosen.setEnabled(false);
                    regisDosen();
                    break;
            }
        }
    }

    private void regisDosen() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Menyimpan data....");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiService api = retrofit.create(BaseApiService.class);

        Call<Value> call = api.registerDosen(ni, na, em, pw);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")) {
                    progressDialog.dismiss();
                    FancyToast.makeText(RegisterActivity.this, "Akun berhasil dibuat, login untuk melanjutkan", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    finish();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    progressDialog.dismiss();
                    FancyToast.makeText(RegisterActivity.this, "Gagal menyimpan", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                FancyToast.makeText(RegisterActivity.this, "Kesalahan " + t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void regisMhs() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Menyimpan data....");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiService api = retrofit.create(BaseApiService.class);

        Call<Value> call = api.registerRequest(ni,na,em,pw,idDosen, nmDosen);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")) {
                progressDialog.dismiss();
                    FancyToast.makeText(RegisterActivity.this, "Akun berhasil dibuat, silahkan login untuk melanjutkan", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    finish();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                progressDialog.dismiss();
                    FancyToast.makeText(RegisterActivity.this, "Gagal menyimpan "+response.body().getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
            progressDialog.dismiss();
                FancyToast.makeText(RegisterActivity.this, "Kesalahan "+t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(RegisterActivity.this,MainActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}