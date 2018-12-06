package com.unysoft.bimmik.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.Profile;
import com.unysoft.bimmik.model.DosenModel;
import com.unysoft.bimmik.model.ResponseDosen;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileDosen extends BottomSheetDialogFragment {

    TextView nama, nim;
    String idosen, ndosen;
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
        }

        nama = view.findViewById(R.id.profileDosen_nama);
            nama.setText(ndosen);
        nim = view.findViewById(R.id.profileDosen_nim);
            nim.setText(idosen);
//        hp = view.findViewById(R.id.profileDosen_noHp);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ApiClient.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
//        Call<DosenModel> call = baseApiService.getProfileDosen(idosen);
//        call.enqueue(new Callback<DosenModel>() {
//            @Override
//            public void onResponse(Call<DosenModel> call, Response<DosenModel> response) {
//                if (response.body().getValue().equals("1")){
//                    Toast.makeText(getActivity(), idosen, Toast.LENGTH_SHORT).show();
//                    nama.setText(response.body().getNama());
//                    email.setText(response.body().getEmail());
////                    hp.setText(response.body().getNohp);
//                } else {
//                    Log.d("error: ", response.body().getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DosenModel> call, Throwable t) {
//                Log.d("Jaringan: ", t.getMessage());
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

}
