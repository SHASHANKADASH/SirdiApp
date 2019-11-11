package com.example.sirdiapp;

import android.app.Application;

import com.firebase.client.Firebase;

public class FirebaseClientSetup extends Application {

    //This is for setting up firebase in app
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
