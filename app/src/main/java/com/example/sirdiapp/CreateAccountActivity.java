package com.example.sirdiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class CreateAccountActivity extends AppCompatActivity {

    private TextInputLayout new_emailf, new_passf,new_passf1;

    private FirebaseAuth mAuth;

    private long backpressedtime;
    private Toast backtoast;

    ProgressBar create_probar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        new_emailf = findViewById(R.id.email_create);
        new_passf = findViewById(R.id.pass_create);
        new_passf1 = findViewById(R.id.pass_retype);
        create_probar=findViewById(R.id.create_progress);
    }

    @Override
    public void onBackPressed() {
        if(backpressedtime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            super.onBackPressed();
            return;
        } else{
            backtoast=Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        backpressedtime= System.currentTimeMillis();
    }

    public void cancel_clicked(View view) {
        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void signup_clicked(View view) {
        String new_email = new_emailf.getEditText().getText().toString().trim();
        String new_pass = new_passf.getEditText().getText().toString().trim();
        String new_pass1 = new_passf1.getEditText().getText().toString().trim();

        if (new_email.isEmpty()) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(new_emailf);
            new_emailf.setError("*Email Required!");
            new_emailf.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(new_email).matches()) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(new_emailf);
            new_emailf.setError("*Enter Valid Email!");
            new_emailf.requestFocus();
            return;
        }

        if (new_pass.isEmpty()) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(new_passf);
            new_passf.setError("*Password Required!");
            new_passf.requestFocus();
            return;
        }

        if (new_pass.length() < 6) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(new_passf);
            new_passf.setError("*Minimum length should be 6!");
            new_passf.requestFocus();
            return;
        }

        if (new_pass1.isEmpty()) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(new_passf1);
            new_passf1.setError("*Retype Password!");
            new_passf1.requestFocus();
            return;
        }

        if (new_pass1.length() < 6) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(new_passf1);
            new_passf1.setError("*Minimum length should be 6!");
            new_passf1.requestFocus();
            return;
        }

        if(!new_pass.equals(new_pass1)){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(new_passf1);
            new_passf1.setError("*Password doesn't match!");
            new_passf1.requestFocus();
            return;
        }
        create_probar.setVisibility(View.VISIBLE);
        create_probar.setProgress(50);
        register_user(new_email,new_pass);
    }

    private void register_user(String new_email, String new_pass) {

        mAuth.createUserWithEmailAndPassword(new_email, new_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                create_probar.setProgress(80);
                if (task.isSuccessful()) {
                    Toast.makeText(CreateAccountActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    create_probar.setVisibility(View.GONE);

                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(CreateAccountActivity.this, "Email Already Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateAccountActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}
