package com.unysoft.bimmik.mahasiswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.MainActivity;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.dosen.Dosen_profile;
import com.unysoft.bimmik.model.ResponsePassword;
import com.unysoft.bimmik.model.ResponseUpdate;
import com.unysoft.bimmik.utils.GLOBAL;
import com.unysoft.bimmik.utils.SharedPrefManager;
import com.unysoft.bimmik.utils.User;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    String na,em,hp,prod,pw,pic, idM;
    String foto, mediaPath, picprof;

    BaseApiService baseApiService;

    CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_profile);

        baseApiService = ApiClient.getClient().create(BaseApiService.class);

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        idM = preferences.getString("ID_MHS", "");

        nim = findViewById(R.id.profile_et_nim);
        nama = findViewById(R.id.profile_et_nama);
        email = findViewById(R.id.profile_et_email);
        noHp = findViewById(R.id.profile_et_noHp);
        prodi = findViewById(R.id.profile_et_prodi);
        profile =findViewById(R.id.profile_img);

        ambildata();

        //GANTI FOTO
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 0);
            }
        });

        //BACK
        findViewById(R.id.profile_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.profile_btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditMhs();
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
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();

                Log.d("onActivityResult", "OK1");

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                Log.d("onActivityResult", "OK2");

                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    //  imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

                    if (mediaPath != null) {

                        // imgView.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                        profile.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

                    }
                }
                cursor.close();

            } else {
                Toast.makeText(this, "Anda Belum Memilih Foto Baru", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Terjadi Kesalahan!", Toast.LENGTH_LONG).show();
        }
    }

    private void ambildata() {
        nama.setText(preferences.getString("NAMA_MHS", ""));
        nim.setText(preferences.getString("ID_MHS", ""));
        email.setText(preferences.getString("EMAIL_MHS",""));
        noHp.setText(preferences.getString("NO_HP",""));
        prodi.setText(preferences.getString("PRODI",""));
        pic =preferences.getString("FOTO_MHS","");

        if (pic.isEmpty()){
            Glide.with(Profile.this).load(R.drawable.user).into(profile);
        } else {
            Glide.with(Profile.this).load(pic).into(profile);
        }

    }

    private void UploadGambar(){

        progressDialog = new ProgressDialog(Profile.this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        File imagefile = new File(mediaPath);

        RequestBody reqBody = RequestBody.create(MediaType.parse("*/*"), imagefile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("file", imagefile.getName(), reqBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), imagefile.getName());
        Log.d("uploadFile", "uploadFile--> " + partImage + " <--Mulaiii = " + filename);
        baseApiService.uploadPicMhs(partImage,filename).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                String value = response.body().getValue();
                String message = response.body().getMessage();
                String location = response.body().getLocation();

                if (value.equals("1")) {
                    progressDialog.dismiss();
                    foto = URL + location;
                    updateUser();
                    Log.d("onResponse", message + " <====> " + foto);
                } else {
                    Log.d("onResponse", "Gagal");
                }

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });

    }
    public void EditMhs() {
        try {
            UploadGambar();
        } catch (Exception e) {
            foto = picprof;
            updateUser();
        }
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
        Call<ResponseUpdate> call = baseApiService.mhsUpdate(na, em, hp, prod, foto, preferences.getString("ID_MHS",""));
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


//    private void showPictureDialog(){
//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
//        pictureDialog.setTitle("Select Action");
//        String[] pictureDialogItems = {
//                "Select photo from gallery",
//                "Capture photo from camera" };
//        pictureDialog.setItems(pictureDialogItems,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                choosePhotoFromGallary();
//                                break;
//                            case 1:
//                                FancyToast.makeText(Profile.this, "Dalam proses", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
//    public void choosePhotoFromGallary() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, GALLERY);
//    }
//    private void takePhotoFromCamera() {
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
//    }
//    public String saveImage(Bitmap myBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + "/sdcard/myPackage/files/media/");
//        // have the object build the directory structure, if needed.
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//    }
//    }


}
