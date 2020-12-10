package com.komidee.covid19tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context mContext;
    private ArrayList<Article> mAndroidNews;





    public NewsAdapter(Context mContext, ArrayList<Article> mAndroidNews) {
        this.mContext = mContext;
        this.mAndroidNews = mAndroidNews;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.news,parent,false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        Article currentNews = mAndroidNews.get(position);
        holder.titleTextView.setText(currentNews.getTitle());
        holder.authorTextView.setText(currentNews.getAuthor());
        holder.sourceTextView.setText(currentNews.getSourceName());
        holder.publishedTextView.setText(DateFormat(currentNews.getPublishedAt()));

        if (currentNews.getUrlToImage().isEmpty()) {
            Picasso.with(mContext).load("https://loremflickr.com/cache/resized/65535_48988158066_2776799238_320_240_g.jpg").fit().centerInside().error(R.drawable.placeholder).into(holder.imageView);
        }
            else {
            Picasso.with(mContext).load(currentNews.getUrlToImage()).fit().centerInside().error(R.drawable.placeholder).into(holder.imageView);
        }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,EarthquakeNewsDetailActivity.class);
                    intent.putExtra("ViewpagerPosition",position);
                    mContext.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return mAndroidNews.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView authorTextView;
        TextView sourceTextView;
        TextView publishedTextView;
        ImageView imageView;



        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.title);
            authorTextView = itemView.findViewById(R.id.author);
            sourceTextView = itemView.findViewById(R.id.source);
            publishedTextView = itemView.findViewById(R.id.publishedAt);


        }

    }

    public static String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat( "d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }





}
