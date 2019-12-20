package com.example.sirdiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.sirdiapp.Handicraft.HandicraftActivity;
import com.example.sirdiapp.Health.HealthActivity;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        CardView health = v.findViewById(R.id.health);
        CardView education = v.findViewById(R.id.education);
        CardView tour = v.findViewById(R.id.tour);
        CardView handicraft = v.findViewById(R.id.handicraft);
        CardView employ = v.findViewById(R.id.employment);

        //onclicking on each domain card
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HealthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(getContext(), "Health Care Facilities", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EducationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(getContext(), "Education Facilities", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Not Applicable", Toast.LENGTH_SHORT).show();
            }
        });

        handicraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HandicraftActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(getContext(), "Handicraft Items", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        employ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Not Applicable", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
