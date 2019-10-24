package com.example.sirdiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bot_nav;
    private Toolbar toolbar;

    private DrawerLayout nav_drawer;
    private NavigationView nav_view;
    private ActionBarDrawerToggle toggle;

    private long backpressedtime;
    private Toast backtoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bot_nav=findViewById(R.id.bottom_navigation);
        open_fragments_bottom_nav();

        nav_drawer=findViewById(R.id.drawer_layout);
        nav_view=findViewById(R.id.drawer_view);
        open_fragments_drawer_nav();

        toggle=new ActionBarDrawerToggle(this,nav_drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        nav_drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            //nav_view.setCheckedItem(R.id.drawer_home);
        }
    }

    private void open_fragments_drawer_nav() {
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.drawer_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeFragment()).commit();
                        break;
                    case R.id.drawer_Profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ProfileFragment()).commit();
                        break;
                    case R.id.drawer_notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new NotificationFragment()).commit();
                        break;
                    case R.id.drawer_setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new SettingFragment()).commit();
                        break;
                }
                nav_drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void open_fragments_bottom_nav() {
        bot_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.bottom_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeFragment()).commit();
                        break;
                    case R.id.bottom_notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new NotificationFragment()).commit();
                        break;
                    case R.id.bottom_Profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ProfileFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(nav_drawer.isDrawerOpen(GravityCompat.START)){
            nav_drawer.closeDrawer(GravityCompat.START);
        } else{
            if(backpressedtime+2000>System.currentTimeMillis()){
                backtoast.cancel();
                super.onBackPressed();
                return;
            } else{
                backtoast=Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
                backtoast.show();
            }
            backpressedtime= System.currentTimeMillis();
        }
    }
}
