package com.example.sirdiapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    Uri mImageUri;
    CircleImageView mImageview;
    TextInputLayout username,phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        mImageview = findViewById(R.id.profile_image_view);
        username=findViewById(R.id.user_enter);
        phoneno=findViewById(R.id.phone_enter);
    }

    public void choose_profile_img(View view) {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data!=null&& data.getData()!=null){
            mImageUri=data.getData();

            Picasso.get().load(mImageUri)
                    //.resize(200, 200)
                    //.centerCrop()
                    //.centerInside()
                    //.fit()
                    //.rotate(90f)
                    .into(mImageview);
        }
    }

    public void rotate_image(View view) {
        Picasso.get().load(mImageUri)
                .rotate(90f)
                .into(mImageview);
    }

    public void signout_clicked(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(NewProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void cancelupdate_clicked(View view) {
        Intent intent = new Intent(NewProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void update_profile_clicked(View view) {

    }
}
