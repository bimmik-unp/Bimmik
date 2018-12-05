package com.unysoft.bimmik.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.unysoft.bimmik.MainActivity;

import java.util.HashMap;


public class SharedPrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int MODE_PRIVATE = 0;

    private static final String PREFER_NAME = "BimmikPref";

    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_PASS = "pass";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_SKS = "total_sks";

    public SharedPrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, MODE_PRIVATE);
        editor = pref.edit();
    }

    public void loginSession(String email, String passwd) {
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASS, passwd);
        editor.commit();
    }

    public String getEmail(){
        return pref.getString(KEY_EMAIL,"");
    }

    public String getTotalSks(){
        return pref.getString(KEY_SKS,"");
    }

    public void saveSpBoolean(String keySp, boolean value){
        editor.putBoolean(keySp, value);
        editor.commit();
    }

    public void simpanUser(User user) {

    }

    public HashMap<String, String> userDetail(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_NAMA, pref.getString(KEY_NAMA, null));
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent in = new Intent(context, MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);
    }

    public Boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
