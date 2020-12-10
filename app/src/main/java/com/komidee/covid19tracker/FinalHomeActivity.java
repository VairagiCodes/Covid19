package com.komidee.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class FinalHomeActivity extends AppCompatActivity {
    static BottomNavigationView bottomNav;
    private DrawerLayout drawerLayout;
    Menu menu;
    boolean isSavedFrg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final_home);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(5);
        toolbar.setBackgroundColor(getColor(R.color.toolBarColor));
        toolbar.setTitleTextColor(getColor(R.color.textColorEarthquakeLocation));

        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.coronaInfoStatusBarColor));

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();




        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.home:

                        selectedFragment = new HomeFragment();
                        isSavedFrg = false;
                        toolbar.setVisibility(View.GONE);
                        break;
                    case R.id.news:
                        selectedFragment = new NewsFragment();
                        isSavedFrg = true;
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setTitle("Corona News");
                        break;
                    case R.id.GujInfo:
                        selectedFragment = new GujFragment();
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setTitle("GujCovid19 Info");
                        break;
                    case R.id.more:
                        selectedFragment = new MoreAppsFragment();
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setTitle("Our Other Apps");
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                return true;
            }
        });



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.home:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                   case R.id.coronaVirus:

                       intent = new Intent(FinalHomeActivity.this,CoronaVirusActivity.class);
                       startActivity(intent);
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                    case R.id.setting:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(FinalHomeActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.share:
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Download Covid19TrackerApp:" + "\n" + "Covid19 tracker app provides you real-time data of Corona Virus for free" + " \n" + "Download Now:" + " \n"  +"http://covid19tracker.aadhuniksolution.com";
                        String shareSubject = "EarthquakeApp";
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                        startActivity(Intent.createChooser(sharingIntent, "Share Using"));
                        break;
                    case R.id.feedBack:
                        Intent Email = new Intent(Intent.ACTION_SEND);
                        Email.setType("text/email");
                        Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"rj.harsh3@gmail.com"});
                        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                        Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
                        startActivity(Intent.createChooser(Email, "Select email app."));
                        break;
                    case R.id.more:
//                       Uri factbookUri = Uri.parse("https://play.google.com/store/search?q=Harsh Ruparelia");
//
//                       Intent factbookIntent = new Intent(Intent.ACTION_VIEW,factbookUri);
//
//                       startActivity(factbookIntent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.about:

                        intent = new Intent(FinalHomeActivity.this,AboutActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.rate:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+ SettingsActivity.APP_NAME)));
                        break;
//                   case R.id.coronaInfo:
//                       startActivity(new Intent(FinalHomeActivity.this,CoronaInfoActivity.class));
//                       drawerLayout.closeDrawer(GravityCompat.START);
//                       break;
//                   case R.id.web:
//                       drawerLayout.closeDrawer(GravityCompat.START);
//                        webIntent = new Intent(FinalHomeActivity.this,WebViewActivity.class);
//                       webIntent.putExtra("urlToLoad","guj");
//                       startActivity(webIntent);
//                       break;
                    case R.id.updates:
                        Uri earthquakeUri = Uri.parse("http://covid19tracker.aadhuniksolution.com");

                        // Create a new intent to view the earthquake URI
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                        // Send the intent to launch a new activity
                        startActivity(websiteIntent);
                        break;

                }

                return false;

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.setting:
                Intent intent = new Intent(FinalHomeActivity.this,SettingsActivity.class);
                startActivity(intent);
                break;



        }
        return super.onOptionsItemSelected(item);
    }

}


