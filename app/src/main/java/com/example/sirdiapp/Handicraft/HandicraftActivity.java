package com.example.sirdiapp.Handicraft;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sirdiapp.MainActivity;
import com.example.sirdiapp.R;
import com.google.android.material.tabs.TabLayout;

public class HandicraftActivity extends AppCompatActivity implements HandicraftArtFragment.OnFragmentInteractionListener, HandicraftCraftFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handicraft);

        TabLayout tablayout =findViewById(R.id.tablayout);
        tablayout.addTab(tablayout.newTab().setText("Art"));
        tablayout.addTab(tablayout.newTab().setText("Craft"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final HandicraftTabAdapter adapter = new HandicraftTabAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //on pressing back button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HandicraftActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}
