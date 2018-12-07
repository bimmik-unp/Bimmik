package com.unysoft.bimmik.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.Kegiatan;
import com.unysoft.bimmik.mahasiswa.fragment.EditKegiatan;
import com.unysoft.bimmik.model.Keg_item;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.KegiatanHolder> {

    List<Keg_item> keg_itemList;
    Context context;
    public onItemClick listener;

    public KegiatanAdapter(List<Keg_item> keg_itemList, Context context) {
        this.keg_itemList = keg_itemList;
        this.context = context;
    }

    public interface onItemClick {
        void mDeleteClick(View v, int postition);
        void mEditClick(View v, int position);
    }

    @NonNull
    @Override
    public KegiatanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zzz_kegiatan, parent, false);
        return new KegiatanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KegiatanAdapter.KegiatanHolder holder, final int position) {
        final Keg_item keg_item = keg_itemList.get(position);
        holder.nama.setText(keg_item.getNama());
        holder.ket.setText(keg_item.getKet());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditKegiatan.class);
                intent.putExtra("nk",keg_item.getNama());
                intent.putExtra("kk",keg_item.getKet());
                context.startActivity(intent);
            }
        });
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = keg_item.getId();
                hapus(id);
            }
        });

    }

    private void hapus(String id) {
        BaseApiService baseApiService = ApiClient.getClient().create(BaseApiService.class);
        baseApiService.deleteKegiatan(id).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")){
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
        return keg_itemList.size();
    }

    public class KegiatanHolder extends RecyclerView.ViewHolder {

        TextView nama, ket;
        ImageButton edit, hapus;

        public KegiatanHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.kegiatan_tb_nm);
            ket = view.findViewById(R.id.kegiatan_tb_ket);
            edit = view.findViewById(R.id.kegiatan_edit);
            hapus = view.findViewById(R.id.kegiatan_hapus);

        }

    }

}
