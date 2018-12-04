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

    TextView tvNama, tvNim, tvEmail, tvNohp, tvProdi;
    String idMhs, namaMhs, emailMhs, nohpMhs, prodiMhs;
    Spinner spin_smt;

    RecyclerView rvNilai, rvKegiatan;

    KegiatanAdapter kegiatanAdapter;
    List<Keg_item> keg_itemList = new ArrayList<>();


    NilaiAdapter nilaiSemesterAdapter;
    List<NilaiItem> nilaiItems = new ArrayList<>();

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dosen_detail_mhs);

        tvNama = findViewById(R.id.dosen_profileMhs_tvNama);
        tvNim = findViewById(R.id.dosen_profileMhs_tvNim);
        tvEmail = findViewById(R.id.dosen_profileMhs_tvEmail);
        tvNohp = findViewById(R.id.dosen_profileMhs_tvNohp);
        tvProdi = findViewById(R.id.dosen_profileMhs_tvProdi);

        rvKegiatan = findViewById(R.id.dosen_detailMhs_rvKegiatan);
        kegiatanAdapter = new KegiatanAdapter(keg_itemList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvKegiatan.setLayoutManager(layoutManager);
        rvKegiatan.setItemAnimator(new DefaultItemAnimator());
        AmbilKegiatan();

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

        loading = new ProgressDialog(this);
        loading.setMessage("Mengambil data...");
        loading.setCancelable(false);
        loading.show();
    }

//    private void ambilNilai() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
//
//        Call<Value> call = baseApiService.detailGetNilai(GLOBAL.id_smt, idMhs);
//        call.enqueue(new Callback<Value>() {
//            @Override
//            public void onResponse(Call<Value> call, Response<Value> response) {
//                Toast.makeText(Dosen_detail_mhs.this, GLOBAL.id_smt+" - "+idMhs, Toast.LENGTH_SHORT).show();
//                if (response.body().getValue().equals("1")) {
//                    loading.dismiss();
//                    final List<NilaiItem> nilaiItemList= response.body().getNilaiItems();
//                    rvNilai.setAdapter(new NilaiAdapter(nilaiItemList, getApplicationContext()));
//                    nilaiSemesterAdapter.notifyDataSetChanged();
//                } else {
//                    loading.dismiss();
//                    FancyToast.makeText(getApplicationContext(), "Gagal mengambil data", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Value> call, Throwable t) {
//                loading.dismiss();
//                FancyToast.makeText(getApplicationContext(), "Kesalahan jaringan: "+t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
//            }
//        });
//    }

    private void AmbilKegiatan() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseApiService baseApiService = retrofit.create(BaseApiService.class);

        Call<Value> call = baseApiService.getKegiatan(idMhs);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")) {
                    loading.dismiss();
                    final List<Keg_item> keg_itemList = response.body().getKegiatan();
                    rvKegiatan.setAdapter(new KegiatanAdapter(keg_itemList, getApplicationContext()));
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
        holder.smt.setText(nilaiItem.getId_smt());
        holder.sks.setText(nilaiItem.getSks());
        holder.nil.setText(nilaiItem.getNilai());
    }

    @Override
    public int getItemCount() {
        return nilaiItems.size();
    }

    public class NilaiHolder extends RecyclerView.ViewHolder {

        TextView mt,smt,sks,nil;

        public NilaiHolder(View view) {
            super(view);
            mt = view.findViewById(R.id.ins_tb_matkul);
            smt = view.findViewById(R.id.ins_tb_smt);
            sks = view.findViewById(R.id.ins_tb_sks);
            nil = view.findViewById(R.id.ins_tb_nilai);

        }
    }
}