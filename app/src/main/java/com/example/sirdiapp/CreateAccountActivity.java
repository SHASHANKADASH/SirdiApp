package com.example.sirdiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateAccountActivity extends AppCompatActivity {

    private TextInputLayout new_emailf, new_passf,new_passf1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        new_emailf = findViewById(R.id.email_create);
        new_passf = findViewById(R.id.pass_create);
        new_passf1 = findViewById(R.id.pass_retype);
    }

    public void cancel_clicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void signup_clicked(View view) {
        final String new_email = new_emailf.getEditText().getText().toString().trim();
        final String new_pass = new_passf.getEditText().getText().toString().trim();
        final String new_pass1 = new_passf1.getEditText().getText().toString().trim();

        error(new_email,new_pass,new_pass1);
        register_user(new_email,new_pass);
    }

    private void register_user(String new_email, String new_pass) {

        mAuth.createUserWithEmailAndPassword(new_email, new_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(CreateAccountActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CreateAccountActivity.this, SignoutActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
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

    private void error(String new_email, String new_pass, String new_pass1) {

        if (new_email.isEmpty()) {
            new_emailf.setError("*Email Required!");
            new_emailf.requestFocus();
            return;
        }

        if (new_pass.isEmpty()) {
            new_passf.setError("*Password Required!");
            new_passf.requestFocus();
            return;
        }

        if (new_pass1.isEmpty()) {
            new_passf1.setError("*Retype Password!");
            new_passf1.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(new_email).matches()) {
            new_emailf.setError("*Enter Valid Email!");
            new_emailf.requestFocus();
            return;
        }

        if (new_pass.length() < 6) {
            new_passf.setError("*Minimum length should be 6!");
            new_passf.requestFocus();
            return;
        }

        if (new_pass1.length() < 6) {
            new_passf1.setError("*Minimum length should be 6!");
            new_passf1.requestFocus();
            return;
        }

        if(!new_pass.equals(new_pass1)){
            new_passf1.setError("*Password doesn't match!");
            new_passf1.requestFocus();
            return;
        }
    }

}
