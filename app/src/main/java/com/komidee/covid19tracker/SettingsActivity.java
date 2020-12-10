package com.komidee.covid19tracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    ListView listView;
    Switch nightSwitch;
    Switch notificationSwitch;


    public static final String SHARED_PREF = "setNightModeSharedPref";
    public static final String SWITCH1 = "switch1";
    public static final String SWITCH2 = "switch2";
    public static boolean switchOnOff = false;
    public static boolean notificationSwitchOnOff = true;
    final  static String APP_NAME = "com.vairagii.psychologyfacts";
    private static final String job_tag = "Notification";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        listView = findViewById(R.id.list);
        listView.setDividerHeight(0);
        listView.setDivider(null);



        final RelativeLayout nightLayout = findViewById(R.id.nightRel);
        nightSwitch = findViewById(R.id.nightButton);

        final RelativeLayout notificationLayout = findViewById(R.id.nightRe2);
        notificationSwitch = findViewById(R.id.notificationButton);


        switchOnOff = sharedPreferences.getBoolean(SWITCH1,false);
        nightSwitch.setChecked(switchOnOff);

        notificationSwitchOnOff = sharedPreferences.getBoolean(SWITCH2,true);
        notificationSwitch.setChecked(notificationSwitchOnOff);

        final Intent intent = new Intent(SettingsActivity.this,FinalHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchOnOff) {
                    nightSwitch.setChecked(false);
                    saveData(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    finish();
                    startActivity(intent);

                }
                else  {
                    nightSwitch.setChecked(true);
                    saveData(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    finish();
                    startActivity(intent);
                }
            }
        });
        nightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(nightSwitch.isChecked()) {
                    nightSwitch.setChecked(false);
                    saveData(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    finish();
                    startActivity(intent);



                }
                else {
                    nightSwitch.setChecked(true);
                    saveData(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    finish();


                    startActivity(intent);


                }



            }
        });

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(notificationSwitchOnOff) {
                    notificationSwitch.setChecked(false);
                    notificationSaveData(false);
                    notificationSwitchOnOff = false;

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("general")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });

                }

                else {
                    notificationSwitch.setChecked(true);
                    notificationSaveData(true);
                    notificationSwitchOnOff = true;

                    FirebaseMessaging.getInstance().subscribeToTopic("general")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                }
            }
        });

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationSwitch.isChecked()) {
                    notificationSwitch.setChecked(false);
                    notificationSaveData(false);

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("general")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                }
                else {
                    notificationSwitch.setChecked(true);
                    notificationSaveData(true);
                    FirebaseMessaging.getInstance().subscribeToTopic("general")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                }
            }
        });






        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        toolbar.setBackgroundColor(getColor(R.color.backColor));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<FactData> androidSettings = new ArrayList<>();

//        androidSettings.add(new FactData("Donate now COVID-19 India",R.drawable.ic_maps_and_flags));
        androidSettings.add(new FactData( "Share feedback",R.drawable.ic_email_black_24dp));
        androidSettings.add(new FactData( "Rate Us",R.drawable.ic_google));
        androidSettings.add(new FactData("Invite Friends",R.drawable.ic_like));
        androidSettings.add(new FactData( "Terms & Conditions",R.drawable.ic_shield));
        androidSettings.add(new FactData("Privacy Policy",R.drawable.ic_privacy));
        androidSettings.add(new FactData("More Apps",R.drawable.ic_search));
        androidSettings.add(new FactData("About Us",R.drawable.ic_about));



        AndroidSettingsAdapter androidSettingsAdapter = new AndroidSettingsAdapter(this, androidSettings);

        listView.setAdapter(androidSettingsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

//                    case 0:
//                        Uri donateUri = Uri.parse("https://pmnrf.gov.in/en/online-donation");
//
//                        // Create a new intent to view the earthquake URI
//                        Intent donateIntent = new Intent(Intent.ACTION_VIEW, donateUri);
//
//                        // Send the intent to launch a new activity
//                        startActivity(donateIntent);
//                        break;

                    case 0:
                        Intent Email = new Intent(Intent.ACTION_SEND);
                        Email.setType("text/email");
                        Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"rj.harsh3@gmail.com"});
                        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                        Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
                        startActivity(Intent.createChooser(Email, "Select email app."));
                        break;
                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_NAME)));
                        break;

                    case 2:
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Download Covid19TrackerApp:" + "\n" + "Covid19 tracker app provides you real-time data of Corona Virus for free" + " \n" + "Download Now:" + " \n"  +"http://covid19tracker.aadhuniksolution.com";
                        String shareSubject = "EarthquakeApp";
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                        startActivity(Intent.createChooser(sharingIntent, "Share Using"));
                        break;
                    case 3:
                        Uri earthquakeUri = Uri.parse("https://www.termsfeed.com/terms-conditions/a3c62b3a823958083b1bc7213c1d3a1f");

                        // Create a new intent to view the earthquake URI
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                        // Send the intent to launch a new activity
                        startActivity(websiteIntent);
                        break;

                    case 4:

                        Uri privacyUri = Uri.parse("https://psychology-facts.flycricket.io/privacy.html");

                        // Create a new intent to view the earthquake URI
                        Intent privacyIntent = new Intent(Intent.ACTION_VIEW, privacyUri);

                        // Send the intent to launch a new activity
                        startActivity(privacyIntent);
                        break;
                    case 5:
                        Uri factbookUri = Uri.parse("https://play.google.com/store/apps/developer?id=komidee");

                        Intent factbookIntent = new Intent(Intent.ACTION_VIEW,factbookUri);

                        startActivity(factbookIntent);
                        break;

                    case 6:

                        Intent aboutIntent = new Intent(SettingsActivity.this, AboutActivity.class);
                        startActivity(aboutIntent);
                        break;


                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }



    public void saveData(boolean isChecked) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH1,isChecked);
        editor.apply();

    }

    public void notificationSaveData(boolean isOn) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH2,isOn);
        editor.apply();
    }




}
