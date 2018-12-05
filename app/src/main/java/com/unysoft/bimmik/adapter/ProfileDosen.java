package com.unysoft.bimmik.adapter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.Profile;
import com.unysoft.bimmik.model.ResponseDosen;
import com.unysoft.bimmik.webservice.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileDosen extends BottomSheetDialogFragment {

    private static final String URL = "http://teagardenapp.com/bimmikapp/api/";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public static ProfileDosen newInstance() {
        return new ProfileDosen();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dosen_dashboard, container, false);


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
//        Call<ResponseDosen> call = baseApiService.getProfileDosen(preferences.getString("ID_DOSEN", ""));
//        call.enqueue(new Callback<ResponseDosen>() {
//            @Override
//            public void onResponse(Call<ResponseDosen> call, Response<ResponseDosen> response) {
//                if (response.body().getValue().equals("1")){
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDosen> call, Throwable t) {
//
//            }
//        });

        return view;
    }

}
