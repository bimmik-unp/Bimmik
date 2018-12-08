package com.unysoft.bimmik.dosen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.adapter.MessageAdapter;
import com.unysoft.bimmik.model.GetMessage;
import com.unysoft.bimmik.model.MessageItem;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dosen_message extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MessageItem> list;
    BaseApiService baseApiService;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String nmDosen, idDosen,idMhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dosen_pesan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        final Intent intent = getIntent();
        idMhs = intent.getStringExtra("id_mhs");

        baseApiService = ApiClient.getClient().create(BaseApiService.class);

        recyclerView = findViewById(R.id.mhs_ev_pesan);
        adapter = new MessageAdapter(list,getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        idDosen = preferences.getString("ID_DOSEN","");
        nmDosen = preferences.getString("NAMA_DOSEN","");

        baseApiService.getPesan(idMhs).enqueue(new Callback<GetMessage>() {
            @Override
            public void onResponse(Call<GetMessage> call, Response<GetMessage> response) {
                if (response.body().getValue().equals("1")){
                    adapter = new MessageAdapter(response.body().getSemuapesan(), Dosen_message.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetMessage> call, Throwable t) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.pesan_mhs_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(Dosen_message.this);
                View pView = inflater.inflate(R.layout.dialog_pesan, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Dosen_message.this);
                builder.setView(pView);
                final EditText editTextTitle = (EditText) pView.findViewById(R.id.editTextTitle);
                    editTextTitle.setText(idMhs);
                    editTextTitle.setEnabled(false);
                final EditText editTextMessage = (EditText) pView.findViewById(R.id.editTextMessage);
                final String idDsn = idDosen;

                builder
                        .setCancelable(false)
                        .setPositiveButton("Kirim",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //getting the values
                                        String title = editTextTitle.getText().toString().trim();
                                        String message = editTextMessage.getText().toString().trim();
                                        //sending the message
                                        sendMessage(idDsn,idMhs, title, message);
                                    }
                                })
                        .setNegativeButton("Batal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ambilPesan();
    }

    private void ambilPesan() {
        baseApiService.getPesan(idMhs).enqueue(new Callback<GetMessage>() {
            @Override
            public void onResponse(Call<GetMessage> call, Response<GetMessage> response) {
                if (response.body().getValue().equals("1")){
                    final List<MessageItem> messageItemList = response.body().getSemuapesan();
                    recyclerView.setAdapter(new MessageAdapter(messageItemList, getApplicationContext()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetMessage> call, Throwable t) {

            }
        });
    }

    private void sendMessage(String idDosen,String idMhs, String title, String message) {
        final ProgressDialog progressDialog = new ProgressDialog(Dosen_message.this);
        progressDialog.setMessage("Mengirim pesan");
        progressDialog.show();

        baseApiService.kirimPesan(idDosen,idMhs,title,message).enqueue(new Callback<GetMessage>() {
            @Override
            public void onResponse(Call<GetMessage> call, Response<GetMessage> response) {
                if (response.body().getValue().equals("1")){
                    progressDialog.dismiss();
                    FancyToast.makeText(Dosen_message.this,"Pesan terkirim",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                } else {
                    Toast.makeText(Dosen_message.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetMessage> call, Throwable t) {
                Toast.makeText(Dosen_message.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
