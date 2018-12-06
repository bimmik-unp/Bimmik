package com.unysoft.bimmik.dosen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.adapter.KegiatanAdapter;
import com.unysoft.bimmik.model.GetSemester;
import com.unysoft.bimmik.model.Keg_item;
import com.unysoft.bimmik.model.NilaiItem;
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

public class Dosen_detail_mhs extends AppCompatActivity {

    private static final String URL = "http://teagardenapp.com/bimmikapp/api/";
    public String idSemester;

    TextView tvNama, tvNim, tvEmail, tvNohp, tvProdi,tvTotal,tvIps,tvIpk;
    String idMhs, namaMhs, emailMhs, nohpMhs, prodiMhs;
    Spinner semester;

    RecyclerView rvNilai, rvKegiatan;

    KegiatanAdapter kegiatanAdapter;
    List<Keg_item> keg_itemList = new ArrayList<>();


    NilaiAdapter nilaiSemesterAdapter;
    List<NilaiItem> nilaiItems = new ArrayList<>();

    ProgressDialog loading;
    BaseApiService baseApiService;

    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;

    public static Dosen_detail_mhs ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dosen_detail_mhs);

        recyclerView = (RecyclerView)findViewById(R.id.dosen_detailMhs_rvKegiatan);
        recyclerView2 = (RecyclerView)findViewById(R.id.dosen_detailMhs_rvNilai);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager2 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView2.setLayoutManager(mLayoutManager2);
        ds=this;

        baseApiService = ApiClient.getClient().create(BaseApiService.class);

        tvNama = findViewById(R.id.dosen_profileMhs_tvNama);
        tvNim = findViewById(R.id.dosen_profileMhs_tvNim);
        tvEmail = findViewById(R.id.dosen_profileMhs_tvEmail);
        tvNohp = findViewById(R.id.dosen_profileMhs_tvNohp);
        tvProdi = findViewById(R.id.dosen_profileMhs_tvProdi);
        tvTotal=findViewById(R.id.tvTotalSKS);
        tvIps=findViewById(R.id.tvIps);
        tvIpk=findViewById(R.id.tvIPK);

//        rvKegiatan = findViewById(R.id.dosen_detailMhs_rvKegiatan);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        rvKegiatan.setLayoutManager(layoutManager);
//        rvKegiatan.setItemAnimator(new DefaultItemAnimator());


//        rvNilai = findViewById(R.id.dosen_detailMhs_rvNilai);
//        nilaiSemesterAdapter= new NilaiAdapter(nilaiItems, getApplicationContext());
//        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
//        rvNilai.setLayoutManager(layoutManager2);
//        rvNilai.setItemAnimator(new DefaultItemAnimator());
//        ambilNilai();

        Intent Intent = getIntent();
        idMhs = Intent.getStringExtra("id_mhs");
        namaMhs = Intent.getStringExtra("nama_mhs");
        emailMhs = Intent.getStringExtra("email_mhs");
        nohpMhs = Intent.getStringExtra("nohp_mhs");
        prodiMhs = Intent.getStringExtra("prodi_mhs");

        tvNama.setText(namaMhs);
        tvNim.setText(idMhs);
        tvEmail.setText(emailMhs);
        tvNohp.setText(nohpMhs);
        tvProdi.setText(prodiMhs);


        semester =(Spinner) findViewById(R.id.spSemester);

        AmbilKegiatan();
        initSpinSmt();

        loading = new ProgressDialog(this);
        loading.setMessage("Mengambil data...");
        loading.setCancelable(false);
        loading.show();
    }

    public void initSpinSmt() {
        baseApiService.getSemester().enqueue(new Callback<GetSemester>() {
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
                                GLOBAL.id_smt = semesterItems.get(position).getId_smt();
                                Log.d("id_smt", idSemester);
                                if (selectedName.equals("- Pilih Semester -")){

                                } else {
                                    ambilNilai();
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

    private void ambilNilai() {

        baseApiService.detailGetNilai(idSemester,idMhs).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                final String total_sks =response.body().getTotal_sks();
                final String ips=response.body().getIps();
                final String ipk=response.body().getIpk();
                tvTotal.setText(total_sks);
                tvIps.setText(ips);
                tvIpk.setText(ipk);

                if (response.body().getValue().equals("1")) {
                    loading.dismiss();
                    final List<NilaiItem> nilaiItemList= response.body().getNilaiItems();
                    Log.d("Retrofit GET","Nilai"+String.valueOf(nilaiItemList.size()));
                    mAdapter = new NilaiAdapter(nilaiItemList,getApplicationContext());
                    recyclerView2.setAdapter(mAdapter);
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

    private void AmbilKegiatan() {

        baseApiService.getKegiatan2(idMhs).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")) {
                    loading.dismiss();
                    List<Keg_item> keg_itemList = response.body().getKegiatan();
                    Log.d("Retrofit GET","Kegiatan"+String.valueOf(keg_itemList.size()));
                    mAdapter = new KegiatanAdapter(keg_itemList,getApplicationContext());
                    recyclerView.setAdapter(mAdapter);

                    //rvKegiatan.setAdapter(new KegiatanAdapter(keg_itemList, getApplicationContext()));
//                    kegiatanAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    FancyToast.makeText(getApplicationContext(), "Gagal mengambil data", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                FancyToast.makeText(getApplicationContext(), "Periksa jaringan Anda", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });
    }

}


class NilaiAdapter extends RecyclerView.Adapter <NilaiAdapter.NilaiHolder> {

    List<NilaiItem> nilaiItems;
    Context context;

    public NilaiAdapter(List<NilaiItem> nilaiItemList, Context context) {
        this.nilaiItems = nilaiItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public NilaiAdapter.NilaiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zzz_ins, parent, false);
        return new NilaiAdapter.NilaiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NilaiAdapter.NilaiHolder holder, int position) {
        final NilaiItem nilaiItem = nilaiItems.get(position);
        holder.mt.setText(nilaiItem.getNama());
        holder.id.setText(nilaiItem.getId_matkul());
//        holder.smt.setText(nilaiItem.getId_smt());
        holder.sks.setText("SKS "+nilaiItem.getSks());
        holder.nil.setText(nilaiItem.getNilai());
    }

    @Override
    public int getItemCount() {
        return nilaiItems.size();
    }

    public class NilaiHolder extends RecyclerView.ViewHolder {

        TextView id,mt,smt,sks,nil;

        public NilaiHolder(View view) {
            super(view);
            id =view.findViewById(R.id.tvIdMatkul);
            mt = view.findViewById(R.id.ins_tb_matkul);
            sks = view.findViewById(R.id.tvSks);
            nil = view.findViewById(R.id.tvGrade);

        }
    }
}