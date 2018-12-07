package com.unysoft.bimmik.mahasiswa;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.utils.SharedPrefManager;

public class Print extends AppCompatActivity {

    private WebView webView = null;
//    private static final String URL = "http://teagardenapp.com/bimmikapp/cetak.php";
    private static final String URLP = "http://teagardenapp.com/bimmikapp/cetak.php?";


    SharedPrefManager sharedPrefManager;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String idDosen, idMhs, foto,namax;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mhs_print);
        preferences = this.getSharedPreferences("MySaving", Context.MODE_PRIVATE);
        editor = preferences.edit();

        idDosen = preferences.getString("ID_DOSEN","");
        idMhs = preferences.getString("ID_MHS", "");
        foto = preferences.getString("FOTO_MHS", "");
        namax=preferences.getString("NAMA_MHS","");
        String URLX =URLP+"id_mhs="+idMhs+"&nama="+namax;

        this.webView = (WebView) findViewById(R.id.webview_print);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClient webViewClient = new WebViewClient();
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(URLX);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}