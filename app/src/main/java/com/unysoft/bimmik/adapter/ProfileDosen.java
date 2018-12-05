package com.unysoft.bimmik.adapter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.Profile;
import com.unysoft.bimmik.model.ResponseDosen;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileDosen extends BottomSheetDialogFragment {

    TextView nama, email, hp;

    public static ProfileDosen newInstance() {
        return new ProfileDosen();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.profile_dosen, container, false);

        nama = view.findViewById(R.id.profileDosen_nama);
        email = view.findViewById(R.id.profileDosen_email);
        hp = view.findViewById(R.id.profileDosen_noHp);

        view.findViewById(R.id.profileDosen_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

}
