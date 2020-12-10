package com.komidee.covid19tracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsViewPagerAdpter extends PagerAdapter {

    private ArrayList<Article> models;
    private Context context;

    public NewsViewPagerAdpter(ArrayList<Article> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.news_list,container,false);
        TextView titleTextView;
        TextView desTextView;
        TextView titleShort;
        ImageView imageView;
        LinearLayout bottomLinearLayout;

        imageView = view.findViewById(R.id.imageView);
        titleTextView = view.findViewById(R.id.titleTextView);
        desTextView = view.findViewById(R.id.desTextView);
        titleShort = view.findViewById(R.id.titleShort);
        bottomLinearLayout = view.findViewById(R.id.bottomLinearLayout);


        titleTextView.setText(models.get(position).getTitle());
        desTextView.setText(models.get(position).getDescription());
        titleShort.setText(models.get(position).getTitle());

        if (models.get(position).getUrlToImage().isEmpty()) {
            Picasso.with(context).load("https://loremflickr.com/cache/resized/65535_48988158066_2776799238_320_240_g.jpg").fit().centerInside().error(R.drawable.placeholder).into(imageView);
        }
        else {
            Picasso.with(context).load(models.get(position).getUrlToImage()).fit().centerInside().error(R.drawable.placeholder).into(imageView);
        }

        bottomLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri newsUri =  Uri.parse(models.get(position).getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent =  new Intent(Intent.ACTION_VIEW, newsUri);
                websiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Send the intent to launch a new activity
                context.startActivity(websiteIntent);
            }
        });



        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return models.size();
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
