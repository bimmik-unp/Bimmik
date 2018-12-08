package com.unysoft.bimmik.dosen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.MainActivity;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.Dashboard;
import com.unysoft.bimmik.model.Dosen_list_mhs;
import com.unysoft.bimmik.model.GetMahasiswa;
import com.unysoft.bimmik.model.ResponseMahasiswa;
import com.unysoft.bimmik.utils.GLOBAL;
import com.unysoft.bimmik.utils.RecyclerItemClickListener;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dosen_dashboard extends AppCompatActivity {

    private static final String URL = "http://teagardenapp.com/bimmikapp/api/";

    boolean doubleBackToExitPressedOnce = false;
    ProgressDialog loading;
    String foto;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CircleImageView profile;

    RecyclerView recyclerView;
    ListMhsAdapter listMhsAdapter;
    List<ResponseMahasiswa> dosen_list_mhs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dosen_dashboard);

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        TextView nama = findViewById(R.id.dashboard_nm_dosen);
        nama.setText(preferences.getString("NAMA_DOSEN", ""));
        foto=preferences.getString("FOTO","");
        profile = findViewById(R.id.imgDosen);

        if  (foto.isEmpty()){
            Glide.with(Dosen_dashboard.this).load(R.drawable.user).into(profile);
        } else {
            Glide.with(Dosen_dashboard.this).load(foto).into(profile);
        }

        recyclerView = findViewById(R.id.dosen_dashboard_rv);
        listMhsAdapter= new ListMhsAdapter(dosen_list_mhs, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ambilMhs();

        loading = new ProgressDialog(this);
        loading.setMessage("Mengambil data");
        loading.setCancelable(false);
        loading.show();

        findViewById(R.id.dosen_dashboard_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dosen_dashboard.this, Dosen_profile.class));
            }
        });
        findViewById(R.id.dosen_profile_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dosen_dashboard.this);
                builder.setMessage("Anda yakin ingin logout?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("STATUS_LOGIN_DOSEN", "FALSE");
                        editor.clear();
                        editor.apply();
                        Intent in = new Intent(Dosen_dashboard.this, MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                        finish();
                    }
                }).setNegativeButton("Tidak", null).show();
            }
        });
    }

    private void ambilMhs() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseApiService baseApiService = retrofit.create(BaseApiService.class);

        Call<GetMahasiswa> call = baseApiService.getMahasiswaBimbingan(preferences.getString("ID_DOSEN",""));
        call.enqueue(new Callback<GetMahasiswa>() {
            @Override
            public void onResponse(Call<GetMahasiswa> call, Response<GetMahasiswa> response) {
                if (response.body().getValue().equals("1")) {
                    loading.dismiss();
                    final List<ResponseMahasiswa> dosen_list_mhsList= response.body().getSemuamahasiswa();
                    recyclerView.setAdapter(new ListMhsAdapter(dosen_list_mhsList, getApplicationContext()));
                    listMhsAdapter.notifyDataSetChanged();
                    initDataIntent(dosen_list_mhsList);
                } else {
                    loading.dismiss();
                    FancyToast.makeText(getApplicationContext(), "Gagal mengambil data", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<GetMahasiswa> call, Throwable t) {
                loading.dismiss();
                FancyToast.makeText(getApplicationContext(), "Kesalahan jaringan: "+t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });
    }

    private void initDataIntent(final List<ResponseMahasiswa> dosen_list_mhsList) {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id_mhs = dosen_list_mhsList.get(position).getId_mhs();
                String nama_mhs = dosen_list_mhsList.get(position).getNama();
                String email_mhs = dosen_list_mhsList.get(position).getEmail();
                String noHp = dosen_list_mhsList.get(position).getNo_hp();
                String prodi = dosen_list_mhsList.get(position).getProdi();

                Intent detailMhs = new Intent(Dosen_dashboard.this, Dosen_detail_mhs.class);
                detailMhs.putExtra("id_mhs", id_mhs);
                detailMhs.putExtra("nama_mhs", nama_mhs);
                detailMhs.putExtra("email_mhs", email_mhs);
                detailMhs.putExtra("nohp_mhs", noHp);
                detailMhs.putExtra("prodi_mhs", prodi);
                startActivity(detailMhs);
            }
        }));
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

class ListMhsAdapter extends RecyclerView.Adapter <ListMhsAdapter.ListHolder> {

    List<ResponseMahasiswa> dosen_list_mhs;
    Context context;

    public ListMhsAdapter(List<ResponseMahasiswa> dosenListMhsList, Context context) {
        this.dosen_list_mhs = dosenListMhsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListMhsAdapter.ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dosen_list_mhs, parent, false);
        return new ListMhsAdapter.ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMhsAdapter.ListHolder holder, int position) {
        final ResponseMahasiswa dosenListMhs= dosen_list_mhs.get(position);
        holder.nama.setText(dosenListMhs.getNama());
        Glide.with(context)
                .load(dosenListMhs.getFoto())
                .into(holder.imgMhs);
//        holder.img.setImageResource(dosenListMhs.getImg());

        holder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Dosen_message.class);
                intent.putExtra("id_mhs",dosenListMhs.getId_mhs());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dosen_list_mhs.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {

        TextView nama;
        CircleImageView imgMhs;
        ImageButton msg;
//        ImageView img;

        public ListHolder(final View view) {
            super(view);
            nama = view.findViewById(R.id.dosen_list_nm_mhs);
            imgMhs = view.findViewById(R.id.dosen_list_img_mhs);
            msg=view.findViewById(R.id.btMsg);
//            img = view.findViewById(R.id.dosen_list_img_mhs);
//            msg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context,Dosen_message.class);
//                    context.startActivity(intent);
//                }
//            });

        }


    }
}
