package com.example.sirdiapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sirdiapp.Authentication.LoginActivity;

public class StartActivity extends AppCompatActivity {

    //for delay in opening App
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        YoYo.with(Techniques.FadeInUp)
                .duration(2000)
                .repeat(0)
                .playOn(findViewById(R.id.start_text));

        YoYo.with(Techniques.FadeInUp)
                .duration(2000)
                .repeat(0)
                .playOn(findViewById(R.id.start_logo));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
