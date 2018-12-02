package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.MainActivity;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.adapter.MatkulAdapter;
import com.unysoft.bimmik.adapter.SemesterAdapter;
import com.unysoft.bimmik.model.MatkulItem;
import com.unysoft.bimmik.model.SmtModel;
import com.unysoft.bimmik.webservice.AppController;
import com.unysoft.bimmik.webservice.Link;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputNilaiSemester extends AppCompatActivity {

    Spinner semester, idmatakuliah;
    EditText sks, nilai;
    String smt, mtkul, s;

    String tag_json_obj = "json_obj_req";

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_input_nilai_semester);
        Toolbar toolbar = findViewById(R.id.toolbar_ins);
        toolbar.setTitle("Input nilai");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loading = new ProgressDialog(InputNilaiSemester.this);
        loading.setMessage("Mengambil data...");
        loading.show();

        sks = findViewById(R.id.ins_et_sks);
        nilai = findViewById(R.id.ins_et_nilai);

        idmatakuliah = (Spinner) findViewById(R.id.ins_spin_id_matkul);
        semester = (Spinner) findViewById(R.id.ins_spin_smt);

        initSpinSmt();
        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                smt = semester.getSelectedItem().toString();
                initSpinMatkul();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        idmatakuliah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mtkul = idmatakuliah.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void initSpinSmt() {
        StringRequest strReq = new StringRequest(Request.Method.POST, Link.URL_SEMESTER, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    SmtModel modelSpinner = null;
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("results");
                    JSONArray jsonArray = new JSONArray(getObject);
                    ArrayList<SmtModel> list = new ArrayList<>();
                    if(jsonArray.length() != 0){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            modelSpinner = new SmtModel();
                            modelSpinner.setSmt(obj.getString("smt"));
                            list.add(modelSpinner);
                        }
                        SemesterAdapter adapter;
                        adapter = new SemesterAdapter(InputNilaiSemester.this, list);
                        semester.setAdapter(adapter);
                        loading.dismiss();
                    }
                } catch (JSONException e) {
                    loading.dismiss();
                    e.printStackTrace();

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                FancyToast.makeText(InputNilaiSemester.this, error.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq,tag_json_obj);
    }

    public void initSpinMatkul() {

        StringRequest strReq = new StringRequest(Request.Method.POST, Link.URL_MATKUL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    MatkulItem modelSpinner = null;
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("results");
                    JSONArray jsonArray = new JSONArray(getObject);
                    ArrayList<MatkulItem> list = new ArrayList<>();
                    if(jsonArray.length() != 0){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            modelSpinner = new MatkulItem();
                            modelSpinner.setId_matkul(obj.getString("id_matkul"));
                            modelSpinner.setNama(obj.getString("nama"));
                            list.add(modelSpinner);
                            loading.dismiss();
                        }
                    }
                    MatkulAdapter adapter;
                    adapter = new MatkulAdapter(InputNilaiSemester.this, list);
                    idmatakuliah.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FancyToast.makeText(InputNilaiSemester.this, error.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_smt", smt);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq,tag_json_obj);
    }
}
