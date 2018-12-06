package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.MainActivity;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.model.ResponsePassword;
import com.unysoft.bimmik.model.ResponseUpdate;
import com.unysoft.bimmik.utils.GLOBAL;
import com.unysoft.bimmik.utils.SharedPrefManager;
import com.unysoft.bimmik.utils.User;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Profile extends AppCompatActivity {

    private static final String URL = "http://teagardenapp.com/bimmikapp/api/";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    private int GALLERY = 1;
    private int CAMERA = 2;

    EditText nim, nama, email, noHp, prodi;
    String na,em,hp,prod,pw, idM;

    CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_profile);

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        idM = preferences.getString("ID_MHS", "");

        nim = findViewById(R.id.profile_et_nim);
        nama = findViewById(R.id.profile_et_nama);
        email = findViewById(R.id.profile_et_email);
        noHp = findViewById(R.id.profile_et_noHp);
        prodi = findViewById(R.id.profile_et_prodi);

        profile=findViewById(R.id.dosen_profile_img);

        nama.setText(preferences.getString("NAMA_MHS", ""));
        nim.setText(preferences.getString("ID_MHS", ""));
        email.setText(preferences.getString("EMAIL_MHS",""));
        noHp.setText(preferences.getString("NO_HP",""));
        prodi.setText(preferences.getString("PRODI",""));

        findViewById(R.id.profile_btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        //GANTI FOTO
        findViewById(R.id.change_img_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        //BACK
        findViewById(R.id.profile_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //LOGOUT
        findViewById(R.id.profile_btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Profile.this)
                        .setMessage("Logout dari aplikasi ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GLOBAL.id_mhs = "";
                                editor.putString("STATUS_LOGIN", "FALSE");
                                editor.clear();
                                editor.apply();
                                Intent i = new Intent(Profile.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });

        //GANTI PASSWORD
        findViewById(R.id.profile_btn_gantiPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(Profile.this);
                View view = layoutInflater.inflate(R.layout.mhs_dialog_pwd, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setView(view);

                final EditText newPwd = view.findViewById(R.id.profile_et_newPwd);
                final EditText confNewPwd = view.findViewById(R.id.profile_et_confNewPwd);

                builder.setCancelable(false)
                        .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                progressDialog = new ProgressDialog(getApplicationContext());
//                                progressDialog.setMessage("Sedang perbarui data");
//                                progressDialog.setCancelable(false);
//                                progressDialog.show();

                                final String np = newPwd.getText().toString();
                                String cnp = confNewPwd.getText().toString();

                                if (!cnp.equals(np)) {
                                    FancyToast.makeText(getApplicationContext(), "Konfirmasi password tidak sama", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                                } else {
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    BaseApiService baseApiService = retrofit.create(BaseApiService.class);
                                    Call<ResponsePassword> call = baseApiService.updatePassword(idM, np);
                                    call.enqueue(new Callback<ResponsePassword>() {
                                        @Override
                                        public void onResponse(Call<ResponsePassword> call, Response<ResponsePassword> response) {
                                            if (response.body().getValue().equals("1")) {
                                                FancyToast.makeText(Profile.this, "Data telah diubah.\nSilahkan login ulang", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                                editor.putString("STATUS_LOGIN", "FALSE");
                                                editor.clear();
                                                editor.apply();
                                                startActivity(new Intent(Profile.this, MainActivity.class)
                                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                finish();
                                            } else {
//                                                progressDialog.dismiss();
                                                FancyToast.makeText(getApplicationContext(), response.body().getMessage(), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponsePassword> call, Throwable t) {
//                                            progressDialog.dismiss();
                                            FancyToast.makeText(getApplicationContext(), t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void updateUser() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Update data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        na = nama.getText().toString().trim();
        em = email.getText().toString().trim();
        hp = noHp.getText().toString().trim();
        prod = prodi.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiService baseApiService = retrofit.create(BaseApiService.class);
        Call<ResponseUpdate> call = baseApiService.mhsUpdate(na, em, hp, prod, preferences.getString("ID_MHS",""));
        call.enqueue(new Callback<ResponseUpdate>() {
            @Override
            public void onResponse(Call<ResponseUpdate> call, Response<ResponseUpdate> response) {
                if (response.body().getValue().equals("1")){
                    progressDialog.dismiss();
                    FancyToast.makeText(Profile.this, "Data telah diubah.\nSilahkan login ulang", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    editor.putString("STATUS_LOGIN", "FALSE");
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(Profile.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else {
                    progressDialog.dismiss();
                    FancyToast.makeText(Profile.this, "Gagal perbarui data", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdate> call, Throwable t) {
                progressDialog.dismiss();
                FancyToast.makeText(Profile.this, t.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(Profile.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Profile.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(Profile.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/sdcard/myPackage/files/media/");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


}
