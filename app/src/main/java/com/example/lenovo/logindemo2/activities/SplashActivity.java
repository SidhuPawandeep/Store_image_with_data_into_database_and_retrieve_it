package com.example.lenovo.logindemo2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.lenovo.logindemo2.R;

public class SplashActivity extends BaseActivity {
    private static int SPLASH_TIME_OUT = 2000;
   // android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        TextView splashTV=(TextView)findViewById(R.id.splashTV);
        new Handler().postDelayed(new Runnable() {

                       /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
