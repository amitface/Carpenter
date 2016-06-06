package com.converge.carpenter.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by amit on 2/4/16.
 */
public class SplashActivity extends Activity
{
    private TextView smart_text;
    private TextView karad_text;
    private Animation animationFalling;
    private Animation animationUP;
    private Button enter_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();


            }
        }, 3000);
    }


    }




