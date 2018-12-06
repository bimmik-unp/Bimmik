package com.unysoft.bimmik.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.model.Keg_item;

import java.util.List;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.KegiatanHolder> {

    List<Keg_item> keg_itemList;
    Context context;

    public KegiatanAdapter(List<Keg_item> keg_itemList, Context context) {
        this.keg_itemList = keg_itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public KegiatanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zzz_kegiatan, parent, false);
        return new KegiatanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KegiatanAdapter.KegiatanHolder holder, int position) {
        final Keg_item keg_item = keg_itemList.get(position);
        holder.nama.setText(keg_item.getNama());
        holder.ket.setText(keg_item.getKet());
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        holder.hapus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
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
//            edit = view.findViewById(R.id.kegiatan_edit);
//            hapus = view.findViewById(R.id.kegiatan_hapus);

        }
    }
}
