package com.komidee.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Objects;

public class CoronaVirusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_virus);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(5);
        setTitle("Corona Facts");
        toolbar.setBackgroundColor(getColor(R.color.toolBarColor));
        toolbar.setTitleTextColor(getColor(R.color.textColorEarthquakeLocation));
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewPager_corona);
        ArrayList<FactData> list = new ArrayList<>();
        list.add(new FactData("",R.drawable.besmart));
        list.add(new FactData("",R.drawable.corona11));
        list.add(new FactData("",R.drawable.corona11));
        list.add(new FactData("",R.drawable.corona2));
        list.add(new FactData("",R.drawable.corona3));
        list.add(new FactData("",R.drawable.corona5));
        list.add(new FactData("",R.drawable.corona6));
        list.add(new FactData("",R.drawable.corona8));
        list.add(new FactData("",R.drawable.corona7));
        list.add(new FactData("",R.drawable.corona9));
        list.add(new FactData("",R.drawable.corona12));
        list.add(new FactData("",R.drawable.corona13));
        list.add(new FactData("",R.drawable.corona14));
        list.add(new FactData("",R.drawable.corona15));
        list.add(new FactData("",R.drawable.corona16));
        list.add(new FactData("",R.drawable.corona17));
        list.add(new FactData("",R.drawable.corona18));
        list.add(new FactData("",R.drawable.corona19));
        list.add(new FactData("",R.drawable.corona20));
        list.add(new FactData("",R.drawable.corona21));
        list.add(new FactData("",R.drawable.corona22));
        list.add(new FactData("",R.drawable.corona23));
        list.add(new FactData("",R.drawable.corona24));
        list.add(new FactData("",R.drawable.corona25));
        list.add(new FactData("",R.drawable.corona26));
        list.add(new FactData("",R.drawable.corona10));

        CoronaAdpter coronaAdpter = new CoronaAdpter(this,list);
        viewPager.setAdapter(coronaAdpter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.viewpager_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home) {
            finish();
            super.onBackPressed();
        }
        else if(item.getItemId()==R.id.setting) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Download Covid19TrackerApp:" + "\n" + "Covid19 tracker app provides you real-time data of Corona Virus for free" + " \n" + "Download Now:" + " \n"  +"http://covid19tracker.aadhuniksolution.com";
            String shareSubject = "EarthquakeApp";
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            startActivity(Intent.createChooser(sharingIntent, "Share Using"));
        }
        return super.onOptionsItemSelected(item);
    }
}
