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

    private TextInputLayout new_emailf, new_passf,new_userf,new_passf1;
    private CircleImageView imgpick;

    static int PReqCode=1;
    static int REQUESTCODE=1;
    Uri pickedImgUri;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        new_emailf = findViewById(R.id.email_create);
        new_passf = findViewById(R.id.pass_create);
        new_userf = findViewById(R.id.user_create);
        new_passf1 = findViewById(R.id.pass_retype);
        imgpick = findViewById(R.id.img_view);

        imgpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=22){
                    checkforpermit();
                }
                else{
                    opengallery();
                }
            }
        });
    }

    private void opengallery() {
        Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,REQUESTCODE);
    }

    private void checkforpermit() {
        if(ContextCompat.checkSelfPermission(CreateAccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(CreateAccountActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(CreateAccountActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }
        }
        else{
            opengallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==REQUESTCODE&&data!=null){
            pickedImgUri=data.getData();
            imgpick.setImageURI(pickedImgUri);
        }
    }

    public void cancel_clicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void signup_clicked(View view) {
        final String new_email = new_emailf.getEditText().getText().toString().trim();
        final String new_pass = new_passf.getEditText().getText().toString().trim();
        final String new_user = new_userf.getEditText().getText().toString().trim();
        final String new_pass1 = new_passf1.getEditText().getText().toString().trim();

        error(new_email,new_pass,new_pass1,new_user);
        register_user(new_email,new_user,new_pass);
    }

    private void register_user(String new_email, final String new_user, String new_pass) {

        mAuth.createUserWithEmailAndPassword(new_email, new_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(CreateAccountActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                    updateuser(pickedImgUri,mAuth.getCurrentUser(),new_user);

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

    private void updateuser(Uri pickedImgUri, final FirebaseUser currentUser,final String new_user) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("Profile_photos");
        final StorageReference imgfilepath = mStorage.child(pickedImgUri.getLastPathSegment());
        imgfilepath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgfilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileupdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(new_user)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileupdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(CreateAccountActivity.this, "Register Complete", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    private void error(String new_email, String new_pass, String new_pass1, String new_user) {
        if(new_user.isEmpty()){
            new_userf.setError("*Username Required!");
            new_userf.requestFocus();
            return;
        }

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

        if(new_user.length()>10){
            new_userf.setError("*Username Can't Exceed 10 characters!");
            new_userf.requestFocus();
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
