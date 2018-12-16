package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.adapter.KegiatanAdapter;
import com.unysoft.bimmik.mahasiswa.fragment.EditNilai;
import com.unysoft.bimmik.model.GetMatkul;
import com.unysoft.bimmik.model.GetSemester;
import com.unysoft.bimmik.model.Keg_item;
import com.unysoft.bimmik.model.MatkulItem;
import com.unysoft.bimmik.model.NilaiItem;
import com.unysoft.bimmik.model.ResponseInputNilai;
import com.unysoft.bimmik.model.ResponseSKS;
import com.unysoft.bimmik.model.SemesterItem;
import com.unysoft.bimmik.utils.GLOBAL;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputNilaiSemester extends AppCompatActivity {

    String[] mutu = {
            "A","B+","B","B-","C+","C","C-","D","E"
    };

    private static final String URL = "http://bimmikapps-unp.com/api/";

    public static InputNilaiSemester in;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Spinner semester, idmatakuliah, spin_mutu;
    EditText ETsks, ETnilai;
    TextView tot,tvips,tvipk,tvPoint;
    String idM, na, nil, sk, idS;
    String idSemester, idMatkul, nilSks;
    ProgressDialog loading;

    RecyclerView recyclerView;
    NilaiSemesterAdapter nilaiSemesterAdapter;
    List<NilaiItem> nilaiItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_input_nilai_semester);

        in = this;
//        Toolbar toolbar = findViewById(R.id.toolbar_ins);
//        toolbar.setTitle("Input nilai");
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        preferences = this.getSharedPreferences("MySaving",Context.MODE_PRIVATE);
        editor = preferences.edit();
        idM = preferences.getString("ID_MHS", "");

        recyclerView = findViewById(R.id.ins_rv);
        nilaiSemesterAdapter= new NilaiSemesterAdapter(nilaiItems, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loading = new ProgressDialog(InputNilaiSemester.this);
        loading.setMessage("Mengambil data...");
        loading.show();

        ETsks = findViewById(R.id.ins_et_sks);
//        ETnilai = findViewById(R.id.ins_et_nilai);
        tot=findViewById(R.id.tvSKS);
        tvips=findViewById(R.id.tvIPS);
        tvipk=findViewById(R.id.tvIPK);
        tvPoint=findViewById(R.id.ins_tvPoint);
        spin_mutu = findViewById(R.id.ins_spinMutu);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mutu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_mutu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        tvPoint.setText("4");
                        break;
                    case 1:
                        tvPoint.setText("3,6");
                        break;
                    case 2:
                        tvPoint.setText("3,3");
                        break;
                    case 3:
                        tvPoint.setText("3");
                        break;
                    case 4:
                        tvPoint.setText("2,6");
                        break;
                    case 5:
                        tvPoint.setText("2,3");
                        break;
                    case 6:
                        tvPoint.setText("2");
                        break;
                    case 7:
                        tvPoint.setText("1,6");
                        break;
                    case 8:
                        tvPoint.setText("1");
                        break;
                    case 9:
                        tvPoint.setText("0");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_mutu.setAdapter(adapter);
        idmatakuliah = (Spinner) findViewById(R.id.ins_spin_id_matkul);
        semester = (Spinner) findViewById(R.id.ins_spin_smt);

        findViewById(R.id.ins_btn_simpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesSimpan();
            }
        });
        initSpinSmt();
    }

    public void initSpinSmt() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
        Call<GetSemester> call = baseApiService.getSemester();
        call.enqueue(new Callback<GetSemester>() {
            @Override
            public void onResponse(Call<GetSemester> call, retrofit2.Response<GetSemester> response) {
                if (response.body().getValue().equals("1")){
                    loading.dismiss();
                    final List<SemesterItem> semesterItems = response.body().getSemuaSemester();
                    final List<String> listSpinner = new ArrayList<String>();
                    for (int i=0; i < semesterItems.size(); i++) {
                        listSpinner.add(semesterItems.get(i).getSmt());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        semester.setAdapter(adapter);
                        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                                String selectedName = parent.getItemAtPosition(position).toString();
                                idSemester = semesterItems.get(position).getId_smt();
                                Log.d("id_smt", idSemester);
                                if (selectedName.equals("Pilih Semester")){
                                    idmatakuliah.setEnabled(false);
                                    spin_mutu.setEnabled(false);
                                } else {
                                    idmatakuliah.setEnabled(true);
                                    spin_mutu.setEnabled(true);
                                    ambilNilai();
                                    initSpinMatkul(idSemester);
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
            public void onFailure(Call<GetSemester> call, Throwable t) {
                loading.dismiss();
                FancyToast.makeText(getApplicationContext(), t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    public void initSpinMatkul(String id_smt) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
        Call<GetMatkul> call = baseApiService.getMatkul(id_smt);
        call.enqueue(new Callback<GetMatkul>() {
            @Override
            public void onResponse(Call<GetMatkul> call, retrofit2.Response<GetMatkul> response) {
                if (response.body().getValue().equals("1")){
                    loading.dismiss();
                    final List<MatkulItem> semesterItems = response.body().getDaftar_matkul();
                    final List<String> listSpinner = new ArrayList<String>();
                    for (int i=0; i < semesterItems.size(); i++) {
                        listSpinner.add(semesterItems.get(i).getNama());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        idmatakuliah.setAdapter(adapter);
                        idmatakuliah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                                String selectedName = parent.getItemAtPosition(position).toString();
                                idMatkul = semesterItems.get(position).getId_matkul();
                                nilSks = semesterItems.get(position).getSks();
                                na = semesterItems.get(position).getNama();
                                Log.d("id_matkul", idMatkul);
                                if (selectedName == null){
                                    spin_mutu.setEnabled(false);
                                    ETsks.setText("");
                                } else {
                                    ETsks.setText(nilSks);
                                    spin_mutu.setEnabled(true);
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
            public void onFailure(Call<GetMatkul> call, Throwable t) {
                loading.dismiss();
                FancyToast.makeText(getApplicationContext(), t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void prosesSimpan() {

        nil = spin_mutu.getSelectedItem().toString();

        if (nil.isEmpty()){
            ETnilai.setError("Field tidak boleh kosong");
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            BaseApiService baseApiService = retrofit.create(BaseApiService.class);
            Call<ResponseInputNilai> call = baseApiService.inputNilai(idM, idMatkul, na, nil, nilSks, idSemester);
            call.enqueue(new Callback<ResponseInputNilai>() {
                @Override
                public void onResponse(Call<ResponseInputNilai> call, Response<ResponseInputNilai> response) {
                    if (response.body().getValue().equals("1")){
                        loading.dismiss();
                        FancyToast.makeText(InputNilaiSemester.this, "Berhasil menyimpan", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        ambilNilai();
                    } else {
                        loading.dismiss();
                        FancyToast.makeText(InputNilaiSemester.this, "Gagal menyimpan", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseInputNilai> call, Throwable t) {
                    loading.dismiss();
                    FancyToast.makeText(InputNilaiSemester.this, "Kesalahan jaringan: "+t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            });
        }
    }

    public void ambilNilai() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseApiService baseApiService = retrofit.create(BaseApiService.class);

        Call<Value> call = baseApiService.detailGetNilai(idSemester,idM);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                final String total_sks =response.body().getTotal_sks();
                final String ips=response.body().getIps();
                final String ipk=response.body().getIpk();
                tot.setText(total_sks);
                tvips.setText(ips);
                tvipk.setText(ipk);
                if (response.body().getValue().equals("1")) {
                    loading.dismiss();
                    final List<NilaiItem> nilaiItemList= response.body().getNilaiItems();
                    recyclerView.setAdapter(new NilaiSemesterAdapter(nilaiItemList, getApplicationContext()));
                    nilaiSemesterAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    FancyToast.makeText(getApplicationContext(), "Gagal mengambil data", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                FancyToast.makeText(getApplicationContext(), "Kesalahan jaringan: "+t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });
    }

}

class NilaiSemesterAdapter extends RecyclerView.Adapter <NilaiSemesterAdapter.NilaiHolder> {

    List<NilaiItem> nilaiItems;
    Context context;

    public NilaiSemesterAdapter(List<NilaiItem> nilaiItemList, Context context) {
        this.nilaiItems = nilaiItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public NilaiSemesterAdapter.NilaiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zzz_inputnilai, parent, false);
        return new NilaiSemesterAdapter.NilaiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NilaiSemesterAdapter.NilaiHolder holder, int position) {
        final NilaiItem nilaiItem = nilaiItems.get(position);
        holder.mt.setText(nilaiItem.getNama());
        holder.id.setText(nilaiItem.getId_matkul());
        //holder.smt.setText(nilaiItem.getId_smt());
//        holder.smt.setText(nilaiItem.getId_smt());
        holder.sks.setText("SKS "+nilaiItem.getSks());
        holder.nil.setText(nilaiItem.getNilai());
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = nilaiItem.getId();
                hapus(id);
            }
        });
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, EditNilai.class);
//                intent.putExtra("idnilai",nilaiItem.getId());
//                intent.putExtra("nmMt",nilaiItem.getNama());
//                intent.putExtra("idMt",nilaiItem.getId_matkul());
//                intent.putExtra("sks",nilaiItem.getSks());
//                intent.putExtra("nilai",nilaiItem.getNilai());
//                intent.putExtra("idSmt", nilaiItem.getId_smt());
//                context.startActivity(intent);
//            }
//        });
    }

    private void hapus(String id) {
        BaseApiService baseApiService = ApiClient.getClient().create(BaseApiService.class);
        baseApiService.deleteNilai(id).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")){
                    InputNilaiSemester.in.ambilNilai();
                    FancyToast.makeText(context,"Data berhasil dihapus",FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return nilaiItems.size();
    }

    public class NilaiHolder extends RecyclerView.ViewHolder {

        TextView id,mt,smt,sks,nil;
        ImageButton edit,hapus;

        public NilaiHolder(View view) {
            super(view);
            id =view.findViewById(R.id.tvIdMatkul);
            mt = view.findViewById(R.id.ins_tb_matkul);
            //smt = view.findViewById(R.id.ins_tb_smt);
            sks = view.findViewById(R.id.tvSks);
            nil = view.findViewById(R.id.tvGrade);
//            edit = view.findViewById(R.id.ins_edit);
            hapus = view.findViewById(R.id.ins_delete);

        }
    }
}
