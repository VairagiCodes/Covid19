package com.komidee.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class AutoViewPagerAdapter extends PagerAdapter {
    ArrayList<FactData> mFactData;
    Context mContext;
    View view;

    AutoViewPagerAdapter(Context context, ArrayList<FactData> arrayList) {
        this.mContext = context;
        this.mFactData = arrayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.viewpager_corona_live_data, container, false);
        ImageView imageView;
        imageView = view.findViewById(R.id.slideImage);
        imageView.setImageResource(mFactData.get(position).getImgURL());

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
        container.removeView((View) object);
    }

}
