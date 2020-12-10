package com.komidee.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private static boolean isNotification = true;
    public static String SWITCH3 = "switch3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this,FinalHomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

        View overLay = findViewById(R.id.myLayout);
        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setNightMode();


        SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.SHARED_PREF,MODE_PRIVATE);
        isNotification = sharedPreferences.getBoolean(SWITCH3,true);

        if(isNotification) {
            isNotification = false;

            FirebaseMessaging.getInstance().subscribeToTopic("general")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
        }

    }

    public void setNightMode() {
        SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.SHARED_PREF,MODE_PRIVATE);
        boolean nightMode = sharedPreferences.getBoolean(SettingsActivity.SWITCH1,false);

        if(nightMode) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);



        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void saveBool(boolean isOn) {
        SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH3,isOn);
        editor.apply();
    }
}
