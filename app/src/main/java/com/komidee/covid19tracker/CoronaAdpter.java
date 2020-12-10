package com.komidee.covid19tracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.util.ArrayList;

public class CoronaAdpter extends PagerAdapter {
    ArrayList<FactData> mFactData;
    Context mContext;
    View view;
    public CoronaAdpter(Context context, ArrayList<FactData> factData) {
        this.mContext = context;
        this.mFactData = factData;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
         view = layoutInflater.inflate(R.layout.corna_view,container,false);
        TextView textView;
        Button coronaButton;
        ImageView keyImage;
        TextView covidText;
        final ImageView imageView;
        TextView nameTextView;
        ImageView shareImage;
        TextView bottomText;
        ImageView accountImage;
        TextView subTextView;
        textView = view.findViewById(R.id.viewpagerText);
        coronaButton = view.findViewById(R.id.infoButton);
        subTextView = view.findViewById(R.id.subText);
        keyImage = view.findViewById(R.id.keyUp);
        covidText = view.findViewById(R.id.covidText);
        nameTextView =view.findViewById(R.id.psychologyTextView);
        accountImage = view.findViewById(R.id.account_name);
        imageView = view.findViewById(R.id.coronaImage);
        bottomText = view.findViewById(R.id.bottomText);
        shareImage = view.findViewById(R.id.share_horizontal);
        textView.setText(mFactData.get(position).getTitle());
        imageView.setImageResource(mFactData.get(position).getImgURL());

        String text = "<font color=#FFFFFF>COVID-19</font> <font color=#f9b856>Facts</font>";
        nameTextView.setText(Html.fromHtml(text));
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Download Covid19TrackerApp:" + "\n" + "Covid19 tracker app provides you real-time data of Corona Virus for free" + " \n" + "Download Now:" + " \n"  +"http://covid19tracker.aadhuniksolution.com";
                String shareSubject = "EarthquakeApp";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
            }
        });

//        if(position==0) {
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            String tv = "<font color=#FFFFFF>COVID-19</font> <font color=#FA5E74>Real-Time Data</font>";
//            nameTextView.setText(Html.fromHtml(tv));
//            bottomText.setVisibility(View.GONE);
//            shareImage.setVisibility(View.GONE);
//            accountImage.setVisibility(View.GONE);
//            coronaButton.setVisibility(View.VISIBLE);
//            covidText.setVisibility(View.VISIBLE);
//            keyImage.setVisibility(View.VISIBLE);
//            subTextView.setVisibility(View.VISIBLE);
//        }


        keyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri earthquakeUri = Uri.parse("https://bit.ly/2xC8Uy3");

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                mContext.startActivity(websiteIntent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mFactData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
