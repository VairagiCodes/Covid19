package com.komidee.covid19tracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        View overLay = findViewById(R.id.aboutActivity);
        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("About");
        toolbar.setBackgroundColor(getColor(R.color.backColor));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //More Apps on click listener

        LinearLayout linearLayoutFactbook  = findViewById(R.id.factbookLinear);
        linearLayoutFactbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri factbookUri = Uri.parse("https://play.google.com/store/search?q=Harsh Ruparelia");

                Intent factbookIntent = new Intent(Intent.ACTION_VIEW,factbookUri);

                startActivity(factbookIntent);
            }
        });




        //Feedback Us

        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "rj.harsh3@gmail.com" });
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
                startActivity(Intent.createChooser(Email, "Select email app."));
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
}
