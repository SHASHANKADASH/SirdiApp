package com.example.sirdiapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ProfileUpdateActivity extends AppCompatActivity {
    
    ImageView pro_img;
    EditText pro_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        pro_img=(ImageView)findViewById(R.id.profile_image);
        pro_name=(EditText)findViewById(R.id.profile_name);
        
        pro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_choose();
            }
        });

        pro_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_choose();
            }
        });
    }

    private void name_choose() {
    }

    private void img_choose(){
        
    }
}
