package com.unysoft.bimmik.mahasiswa.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unysoft.bimmik.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDosen extends BottomSheetDialogFragment {

    TextView nama, nim;
    CircleImageView fotodos;
    String idosen, ndosen,foto;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public static ProfileDosen newInstance() {
        return new ProfileDosen();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.profile_dosen, container, false);

        preferences = getActivity().getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        Bundle bundle = this.getArguments();
        if (bundle != null){
            idosen = bundle.getString("idosen");
            ndosen = bundle.getString("ndosen");
            foto = bundle.getString("foto");
        }

        nama = view.findViewById(R.id.profileDosen_nama);
            nama.setText(ndosen);
        nim = view.findViewById(R.id.profileDosen_nim);
            nim.setText(idosen);
        fotodos = view.findViewById(R.id.profileDosen_img);
            Glide.with(this).load(foto).into(fotodos);

        return view;
    }

}
