package com.example.e_challan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.security.spec.ECField;

public class SplashScreen extends Activity {

    private long SPLASH_TIME_OUT =3000;

    public static final String mypreference = "MyEChallanFile";

    private SharedPreferences sharedpreferences;
    private String LoginCheck = "LoginCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                if(sharedpreferences.contains(LoginCheck) && sharedpreferences.getInt(LoginCheck,1)==1) {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(getApplicationContext(),Login.class));
                // close this activity
                finish();
                }
            }
        }, SPLASH_TIME_OUT);



    }

}
