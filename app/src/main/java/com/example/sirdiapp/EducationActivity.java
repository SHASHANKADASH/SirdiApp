package com.example.sirdiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EducationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
    }

    //on pressing back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EducationActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}
