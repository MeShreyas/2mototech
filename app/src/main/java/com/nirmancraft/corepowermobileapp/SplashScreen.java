package com.nirmancraft.corepowermobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nirmancraft.corepower.R;

public class SplashScreen extends Activity {

    private static final long SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainScreenIntent = new Intent(SplashScreen.this,LicenseActivity.class);
                startActivity(mainScreenIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }



}
