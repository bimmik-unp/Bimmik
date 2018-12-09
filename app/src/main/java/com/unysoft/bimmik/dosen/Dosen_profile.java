package com.unysoft.bimmik.dosen;

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
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.unysoft.bimmik.MainActivity;
import com.unysoft.bimmik.R;
import com.unysoft.bimmik.mahasiswa.Profile;
import com.unysoft.bimmik.model.DosenModel;
import com.unysoft.bimmik.model.ResponsePassword;
import com.unysoft.bimmik.model.ResponseUpdate;
import com.unysoft.bimmik.utils.GLOBAL;
import com.unysoft.bimmik.utils.SharedPrefManager;
import com.unysoft.bimmik.utils.Value;
import com.unysoft.bimmik.webservice.ApiClient;
import com.unysoft.bimmik.webservice.BaseApiService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dosen_profile extends AppCompatActivity {

    public static final String URL = "https://teagardenapp.com/bimmikapp/api/";

    private int GALLERY = 1;
    private int CAMERA = 2;

    CircleImageView profile;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    EditText ETnim, ETnama, ETemail, ETnohp;
    BaseApiService baseApiService;
    String id,nama,email,nohp,pic,idDsn;

    String foto, mediaPath, picprof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dosen_profile);

        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        idDsn=preferences.getString("ID_DOSEN","");

        profile = findViewById(R.id.dosen_profile_img);
        ETnim = findViewById(R.id.dosen_profile_et_id);
        ETnama = findViewById(R.id.dosen_profile_et_nama);
        ETemail = findViewById(R.id.dosen_profile_et_email);
        ETnohp = findViewById(R.id.dosen_profile_et_noHp);
        ETnim.setEnabled(false);

        ambilData();
//        findViewById(R.id.dosen_profile_fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPictureDialog();
//            }
//        });

        findViewById(R.id.dosen_profile_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 0);
            }
        });

        findViewById(R.id.dosen_profile_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.dosen_profile_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dosen_profile.this);
                builder.setMessage("Anda yakin ingin logout?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("STATUS_LOGIN_DOSEN", "FALSE");
                        editor.clear();
                        editor.apply();
                        Intent in = new Intent(Dosen_profile.this, MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                        finish();
                    }
                }).setNegativeButton("Tidak", null).show();
            }
        });


        findViewById(R.id.dosen_profile_btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDosen();
            }
        });

        baseApiService=ApiClient.getClient().create(BaseApiService.class);
        //GANTI PASSWORD
        findViewById(R.id.profile_btn_gantiPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(Dosen_profile.this);
                View view = layoutInflater.inflate(R.layout.mhs_dialog_pwd, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Dosen_profile.this);
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
                                    Call<ResponsePassword> call = baseApiService.updatePassword2(idDsn, np);
                                    call.enqueue(new Callback<ResponsePassword>() {
                                        @Override
                                        public void onResponse(Call<ResponsePassword> call, Response<ResponsePassword> response) {
                                            if (response.body().getValue().equals("1")) {
                                                FancyToast.makeText(Dosen_profile.this, "Data telah diubah.\nSilahkan login ulang", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                                editor.putString("STATUS_LOGIN", "FALSE");
                                                editor.clear();
                                                editor.apply();
                                                startActivity(new Intent(Dosen_profile.this, MainActivity.class)
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
    private void ambilData() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ETnim.setText(preferences.getString("ID_DOSEN",""));
        ETnama.setText(preferences.getString("NAMA_DOSEN",""));
        ETemail.setText(preferences.getString("EMAIL_DOSEN",""));
        ETnohp.setText(preferences.getString("NO_HP",""));
        pic =preferences.getString("FOTO","");

        if (pic.isEmpty()){
            Glide.with(Dosen_profile.this)
                    .load(R.drawable.user)
                    .into(profile);
        } else {
            Glide.with(Dosen_profile.this)
                    .load(pic)
                    .into(profile);
        }
        progressDialog.dismiss();

    }
    private void UploadGambar(){

        progressDialog = new ProgressDialog(Dosen_profile.this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        File imagefile = new File(mediaPath);

        RequestBody reqBody = RequestBody.create(MediaType.parse("*/*"), imagefile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("file", imagefile.getName(), reqBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), imagefile.getName());
        Log.d("uploadFile", "uploadFile--> " + partImage + " <--Mulaiii = " + filename);
        baseApiService.uploadPicDsn(partImage,filename).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                String value = response.body().getValue();
                String message = response.body().getMessage();
                String location = response.body().getLocation();

                if (value.equals("1")) {
                    progressDialog.dismiss();
                    foto = URL + location;
                    updateDosen();
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
    public void EditDosen() {
        try {
            UploadGambar();
        } catch (Exception e) {
            foto = picprof;
            updateDosen();
        }
    }

    private void updateDosen(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Update data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        id=ETnim.getText().toString();
        nama=ETnama.getText().toString();
        email=ETemail.getText().toString();
        nohp=ETnohp.getText().toString();

        baseApiService.updateDosen(nama,email,nohp,foto,id).enqueue(new Callback<DosenModel>() {
            @Override
            public void onResponse(Call<DosenModel> call, Response<DosenModel> response) {

                if (response.body().getValue().equals("1")){
                    progressDialog.dismiss();
                    FancyToast.makeText(Dosen_profile.this, "Berhasil perbarui data Silahkan Login Kembali !", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    editor.putString("STATUS_LOGIN_DOSEN", "FALSE");
                    editor.clear();
                    editor.apply();
                    Intent in = new Intent(Dosen_profile.this, MainActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    finish();
                }else {
                    progressDialog.dismiss();
                    FancyToast.makeText(Dosen_profile.this, "Gagal perbarui data", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }

            }

            @Override
            public void onFailure(Call<DosenModel> call, Throwable t) {

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
//                                takePhotoFromCamera();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
//    }
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





//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == this.RESULT_CANCELED) {
//            return;
//        }
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
//                    Toast.makeText(Dosen_profile.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                    profile.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(Dosen_profile.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == CAMERA) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            profile.setImageBitmap(thumbnail);
//            saveImage(thumbnail);
//            Toast.makeText(Dosen_profile.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//        }
//
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
}
