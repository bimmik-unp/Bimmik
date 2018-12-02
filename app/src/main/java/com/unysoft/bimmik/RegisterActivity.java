package com.unysoft.bimmik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.adapter.DosenAdapter;
import com.unysoft.bimmik.model.DosenModel;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.AppController;
import com.unysoft.bimmik.webservice.BaseApiService;
import com.unysoft.bimmik.webservice.Link;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    public static final String URL = "http://teagardenapp.com/bimmikapp/api/";

    ProgressDialog progressDialog;

    EditText ETnim, ETnama, ETpwd, ETemail;
    Button regis;
    Spinner spin_dosen;

    String ni,na,pw,em;
    String tag_json_obj = "json_obj_req";
    String datadosen;

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

        regis = findViewById(R.id.regis_btn);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesRegister();
            }
        });

        spin_dosen = findViewById(R.id.regis_spinner);
        ambilDataDosen();
        spin_dosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = getId_dosen(position);
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void ambilDataDosen() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Link.URL_SEMUADOSEN, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("results");
                    getDosen(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
//        StringRequest strReq = new StringRequest(Request.Method.POST, Link.URL_SEMUADOSEN, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    DosenModel modelSpinner = null;
//                    JSONObject jObj = new JSONObject(response);
//                    String getObject = jObj.getString("results");
//                    JSONArray jsonArray = new JSONArray(getObject);
//                    ArrayList<DosenModel> list = new ArrayList<>();
//                    if(jsonArray.length() != 0){
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject obj = jsonArray.getJSONObject(i);
//                            modelSpinner = new DosenModel();
//                            modelSpinner.setId_dosen(obj.getString("id_dosen"));
//                            modelSpinner.setNama(obj.getString("nama"));
//                            list.add(modelSpinner);
//                        }
//                    }
//                    DosenAdapter adapter;
//                    adapter = new DosenAdapter(RegisterActivity.this, list);
//                    spin_dosen.setAdapter(adapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                FancyToast.makeText(RegisterActivity.this, error.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                return params;
//            }
//        };
//        AppController.getInstance().addToRequestQueue(strReq,tag_json_obj);
    }

    private void getDosen(JSONArray jsonArray) {
        for (int i=0; i<jsonArray.length();i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dosen.add(jsonObject.getString("nama"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spin_dosen.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dosen));
    }
    private String getId_dosen(int position){
        try {
            JSONObject jsonObject = result.getJSONObject(position);
            datadosen = jsonObject.getString("id_dosen");
        } catch (JSONException e){
            e.printStackTrace();
        }
        return datadosen;
    }

    private void cekParam(){
        ETnim = findViewById(R.id.regis_et_nim);
        ETnama = findViewById(R.id.regis_et_nama);
        ETpwd = findViewById(R.id.regis_et_pwd);
        ETemail = findViewById(R.id.regis_et_email);

        rbDosen = findViewById(R.id.regis_rb_dosen);
        rbMhs = findViewById(R.id.regis_rb_mhs);
        radioGroup = findViewById(R.id.radioGroup2);
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

        Call<Value> call = api.registerRequest(ni,na,em,pw,datadosen);
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
}