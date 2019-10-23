package com.example.sirdiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthList;

    private TextInputLayout emailf,passf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailf=findViewById(R.id.email_enter);
        passf=findViewById(R.id.pass_enter);

        mAuth = FirebaseAuth.getInstance();
        mAuthList = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(LoginActivity.this, SignoutActivity.class));
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthList);
    }

    public void createaccount_clicked(View view) {
        startActivity(new Intent(this,CreateAccountActivity.class));
    }

    public void login_clicked(View View){
        String email = emailf.getEditText().getText().toString().trim();
        String pass = passf.getEditText().getText().toString().trim();

        if(email.isEmpty()){
            emailf.setError("Field Can't be Empty");
            return;
        }
        if(pass.isEmpty()){
            passf.setError("Field Can't be Empty");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailf.setError("Enter Valid Email");
            return;
        }
        if(pass.length()<6){
            passf.setError("Minimum length of password should be 6");
            return;
        }

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, SignoutActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void forget_clicked(View view) {
        Toast.makeText(this, "Not Applicable", Toast.LENGTH_SHORT).show();
    }
}
