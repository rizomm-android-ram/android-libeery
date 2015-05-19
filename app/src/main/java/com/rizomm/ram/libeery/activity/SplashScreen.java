package com.rizomm.ram.libeery.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rizomm.ram.libeery.R;

/**
 * Classe permettant de gérer un splashScreen.
 * Définie comme activité de lancement dans le Manifest.
 */
public class SplashScreen extends Activity {

    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
