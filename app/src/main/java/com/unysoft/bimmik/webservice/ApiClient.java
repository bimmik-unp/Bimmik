package com.unysoft.bimmik.webservice;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL ="http://teagardenapp.com/bimmikapp/api/";

    private static Retrofit retrofit = null;

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
