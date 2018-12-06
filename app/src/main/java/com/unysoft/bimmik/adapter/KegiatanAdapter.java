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

    }

    @Override
    public int getItemCount() {
        return keg_itemList.size();
    }

    public class KegiatanHolder extends RecyclerView.ViewHolder {

        TextView nama, ket;
        ImageButton edit, hapus;

        public KegiatanHolder(View view, final onItemClick onItemClick) {
            super(view);
            nama = view.findViewById(R.id.kegiatan_tb_nm);
            ket = view.findViewById(R.id.kegiatan_tb_ket);
            edit = view.findViewById(R.id.kegiatan_edit);
            hapus = view.findViewById(R.id.kegiatan_hapus);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            onItemClick.mEditClick(v, pos);
                        }
                    }
                }
            });
        }

        public KegiatanHolder(View view) {
            super(view);
        }
    }
}
