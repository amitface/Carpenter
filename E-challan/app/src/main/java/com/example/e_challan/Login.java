package com.example.e_challan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class Login extends AppCompatActivity implements View.OnClickListener{

    public static final String mypreference = "MyEChallanFile";

    private SharedPreferences sharedpreferences;
    private String LoginCheck = "LoginCheck";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
       save();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    public void save() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(LoginCheck,1);
        editor.commit();

    }
}
