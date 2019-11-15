package com.example.sirdiapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    private Uri mImageUri;
    private CircleImageView mImageview;
    private TextInputLayout username;
    private ProgressDialog dialog;

    private StorageReference profiledataref;
    private StorageTask task;
    private FirebaseUser user;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        mImageview = findViewById(R.id.new_image_view);
        username=findViewById(R.id.user_enter);
        progress=findViewById(R.id.update_progress);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        profiledataref = FirebaseStorage.getInstance()
                .getReference("profilepics/");
        user = mAuth.getCurrentUser();

        loaduserdata();
    }

    private void loaduserdata() {
        if (user != null) {
            if (user.getDisplayName() != null) {
                Objects.requireNonNull(username.getEditText()).setText(user.getDisplayName());
            }
            if (user.getPhotoUrl() != null) {
                progress.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(user.getPhotoUrl().toString())
                        .error(R.drawable.blankprofile_round)
                        .into(mImageview, new Callback() {
                            @Override
                            public void onSuccess() {
                                progress.setVisibility(View.INVISIBLE);
                                YoYo.with(Techniques.FadeIn)
                                        .duration(1000)
                                        .repeat(0)
                                        .playOn(findViewById(R.id.new_image_view));
                            }

                            @Override
                            public void onError(Exception e) {
                                progress.setVisibility(View.INVISIBLE);
                            }
                        });
            }
        }
    }

    public void choose_profile_img(View view) {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent
                .createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data!=null&& data.getData()!=null){
            mImageUri=data.getData();

            Picasso.get().load(mImageUri)
                    .into(mImageview);
        }
    }

    public void cancelupdate_clicked(View view) {
        Intent intent = new Intent(NewProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public void update_profile_clicked(View view) {
        if (task != null && task.isInProgress()) {
            Toast.makeText(this, "Wait Server is Busy", Toast.LENGTH_SHORT).show();
        } else {

            String user_name = Objects.requireNonNull(username.getEditText()).getText().toString().trim();

            if (user_name.isEmpty()) {
                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .repeat(0)
                        .playOn(username);
                username.setError("*Username Required!");
                username.requestFocus();
                return;
            }

            if (mImageUri == null) {
                Toast.makeText(this, "Choose Image", Toast.LENGTH_SHORT).show();
                return;
            }

            dialog = new ProgressDialog(NewProfileActivity.this, R.style.AppCompatAlertDialogStyle);
            dialog.setTitle("Please Wait");
            dialog.setMessage("Updating...");
            dialog.show();

            profileupdate(user_name);
        }
    }

    private void profileupdate(final String name) {
        final StorageReference profileimgref = profiledataref.child(System.currentTimeMillis()
                + "." + getFileExtension(mImageUri));

        task = profileimgref.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(NewProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        profileimgref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (user != null && mImageUri != null) {
                                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .setPhotoUri(uri)
                                            .build();

                                    user.updateProfile(profileUpdate)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        dialog.dismiss();
                                                        Toast.makeText(NewProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(NewProfileActivity.this, MainActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(NewProfileActivity.this, "Uploading", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
