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
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    private CardView health,education,tour,handicraft,employ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        health=v.findViewById(R.id.health);
        education=v.findViewById(R.id.education);
        tour=v.findViewById(R.id.tour);
        handicraft=v.findViewById(R.id.handicraft);
        employ=v.findViewById(R.id.employment);

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
                FragmentTransaction frag_trans = getFragmentManager().beginTransaction();
                frag_trans.replace(R.id.fragment_container,new EducationFragment());
                frag_trans.addToBackStack(null);
                Toast.makeText(getActivity(), "Education Facilities", Toast.LENGTH_SHORT).show();
                frag_trans.commit();
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
