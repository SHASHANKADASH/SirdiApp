package com.example.sirdiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sirdiapp.Authentication.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener{

    BottomNavigationView bot_nav;
    //private Toolbar toolbar;

    DrawerLayout nav_drawer;
    NavigationView nav_view;
    //private ActionBarDrawerToggle toggle;

    private long backpressedtime;
    Toast backtoast;

    FloatingActionButton emergency,ambulance,police,fire;
    Animation open,close,clockwise,anticlockwise;
    boolean isopen=false;

    CircleImageView draw_img;
    TextView draw_user,draw_email;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        bot_nav=findViewById(R.id.bottom_navigation);

        nav_drawer=findViewById(R.id.drawer_layout);
        nav_view=findViewById(R.id.drawer_view);


        emergency=findViewById(R.id.emergency_button);
        ambulance=findViewById(R.id.ambulance_button);
        police=findViewById(R.id.police_button);
        fire=findViewById(R.id.fire_button);

        open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        clockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        anticlockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        View header=nav_view.getHeaderView(0);

        user = FirebaseAuth.getInstance().getCurrentUser();
        draw_img=header.findViewById(R.id.drawer_user_pic);
        draw_user=header.findViewById(R.id.drawer_user_name);
        draw_email=header.findViewById(R.id.drawer_user_email);

        if (user != null) {
            if (user.getEmail() != null) {
                draw_email.setText(user.getEmail());
            }
            if (user.getDisplayName() != null) {
                draw_user.setText(user.getDisplayName());
            }
            if (user.getPhotoUrl() != null) {
                Picasso.get()
                        .load(user.getPhotoUrl().toString())
                        .into(draw_img);
            }
        }

        open_fragments_bottom_nav();
        open_fragments_drawer_nav();
        floating_button_clicked();

        /*toggle=new ActionBarDrawerToggle(this,nav_drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        nav_drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            nav_view.setCheckedItem(R.id.drawer_home);
            bot_nav.setSelectedItemId(R.id.bottom_home);
        }
    }

    //on clicking on nav drawer button
    public void open_fragments_drawer_nav() {
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.drawer_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeFragment()).commit();
                        bot_nav.setSelectedItemId(R.id.bottom_home);
                        break;
                    case R.id.drawer_Profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ProfileFragment()).commit();
                        bot_nav.setSelectedItemId(R.id.bottom_Profile);
                        break;
                    case R.id.drawer_notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new NotificationFragment()).commit();
                        bot_nav.setSelectedItemId(R.id.bottom_notification);
                        break;
                    case R.id.drawer_setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new SettingFragment()).commit();
                        bot_nav.setSelectedItemId(R.id.bottom_dummy);
                        break;
                    case R.id.drawer_contact:
                    case R.id.drawer_share:
                    case R.id.drawer_terms:
                    case R.id.drawer_feedback:
                    case R.id.drawer_about:
                        Toast.makeText(MainActivity.this, "Not Applicable Now", Toast.LENGTH_SHORT).show();
                        break;
                }
                nav_drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    //on clicking buttom nav drawer
    public void open_fragments_bottom_nav() {
        bot_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.bottom_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeFragment()).commit();
                        nav_view.setCheckedItem(R.id.drawer_home);
                        break;
                    case R.id.bottom_notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new NotificationFragment()).commit();
                        nav_view.setCheckedItem(R.id.drawer_notification);
                        break;
                    case R.id.bottom_Profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ProfileFragment()).commit();
                        nav_view.setCheckedItem(R.id.drawer_Profile);
                        break;
                }
                return true;
            }
        });
    }

    //on pressed back button
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

    //panic button clicked
    public void floating_button_clicked(){
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isopen){
                    police.startAnimation(close);
                    ambulance.startAnimation(close);
                    fire.startAnimation(close);
                    emergency.startAnimation(anticlockwise);
                    police.setClickable(false);
                    ambulance.setClickable(false);
                    fire.setClickable(false);
                    isopen=false;
                }
                else{
                    police.startAnimation(open);
                    ambulance.startAnimation(open);
                    fire.startAnimation(open);
                    emergency.startAnimation(clockwise);
                    police.setClickable(true);
                    ambulance.setClickable(true);
                    fire.setClickable(true);
                    isopen=true;
                }
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Not Applicable Now", Toast.LENGTH_SHORT).show();
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Not Applicable Now", Toast.LENGTH_SHORT).show();
            }
        });

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Not Applicable Now", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void open_drawer(View view) {
        nav_drawer.openDrawer(GravityCompat.START);
    }

    //what happens when top right button clicked
    public void three_dots(View view) {
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.toolbar_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return false;
        }
    }
}
