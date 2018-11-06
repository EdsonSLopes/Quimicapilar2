package com.pilar.quimica.quimicapilar.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.pilar.quimica.quimicapilar.R;

public class Splashscreen extends AppCompatActivity {

    // Instanciando variáveis
    private int count=0;
    private Boolean trocaActivity= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(),LoginActivity.class ));
                finish();
            }
        } ,4000);
    }
}
