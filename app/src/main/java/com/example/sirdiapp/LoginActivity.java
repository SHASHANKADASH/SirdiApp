package com.example.sirdiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText new_emailf,new_passf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new_emailf=(EditText)findViewById(R.id.email_enter);
        new_passf=(EditText)findViewById(R.id.pass_enter);

        findViewById(R.id.create_account).setOnClickListener(this);
        findViewById(R.id.sign_in).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.create_account:
                startActivity(new Intent(this,CreateAccountActivity.class));
                break;
            case R.id.sign_in:
                login();
                break;
        }
    }

    private void login(){
        String email = new_emailf.getText().toString().trim();
        String pass = new_passf.getText().toString().trim();

        if(email.isEmpty()){
            new_emailf.setError("Email is required");
            new_emailf.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            new_emailf.setError("Enter Valid Email");
            new_emailf.requestFocus();
            return;
        }

        if(pass.length()<6){
            new_passf.setError("Minimum length of password should be 6");
            new_passf.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            new_passf.setError("Password is required");
            new_passf.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, ProfileUpdateActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
