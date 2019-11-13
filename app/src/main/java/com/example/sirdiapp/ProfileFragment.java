package com.example.sirdiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    Button update;
    TextView user_disname, user_email;
    CircleImageView view;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        update = v.findViewById(R.id.update_button);
        user_disname = v.findViewById(R.id.username);
        user_email = v.findViewById(R.id.email);
        view = v.findViewById(R.id.profile_image);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            if (user.getEmail() != null) {
                user_email.setText(user.getEmail());
            }
            if (user.getDisplayName() != null) {
                user_disname.setText(user.getDisplayName());
            }
            if (user.getPhotoUrl() != null) {
                Picasso.get()
                        .load(user.getPhotoUrl().toString())
                        .into(view);
            }
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return v;
    }
}
