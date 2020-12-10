package com.komidee.covid19tracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class EarthquakeNewsDetailActivity extends AppCompatActivity {

    ViewPager viewPagerNews;
    private NewsViewPagerAdpter mNewAdpater;
    private ArrayList<Article> mAndroidNews;
    int page = NewsFragment.page;
    int position;
    boolean mPageEnd;
    int mainPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_news_detail);


        position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            position = extras.getInt("ViewpagerPosition");
        }


        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAndroidNews = new ArrayList<>();
        mAndroidNews.addAll(NewsFragment.articles);
        viewPagerNews = findViewById(R.id.viewPager_top_stories);
        mNewAdpater = new NewsViewPagerAdpter(mAndroidNews,getBaseContext());
        viewPagerNews.setAdapter(mNewAdpater);
        viewPagerNews.setCurrentItem(position);
        viewPagerNews.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==(viewPagerNews.getAdapter().getCount()-5)&&mPageEnd&&page<5) {
                    mainPosition = viewPagerNews.getCurrentItem();
                    page++;
                    parseJSON();
                    mPageEnd = false;

                }
                else if(position == (viewPagerNews.getAdapter().getCount()-1)&&mPageEnd&&(page==5)) {
                    Toast.makeText(getBaseContext(),"No More Data..", Toast.LENGTH_SHORT).show();
                }
                else {
                    mPageEnd  = false;
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                mPageEnd  = true;

            }
        });
    }

    private void parseJSON() {
      String NEWS_REQUEST_URL = "https://newsapi.org/v2/everything?q=earthquake&sortBy=relevancy&sortby=publishedAt&page="+page+"&apiKey=fee96220192e4622848455c474c04ceb";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, NEWS_REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray earthquakeArray  = response.getJSONArray("articles");

                    for(int i=0;i<earthquakeArray.length();i++) {
                        JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                        JSONObject source = currentEarthquake.getJSONObject("source");
                        String author = currentEarthquake.getString("author");
                        String title = currentEarthquake.getString("title");
                        String description = currentEarthquake.getString("description");
                        String url = currentEarthquake.getString("url");
                        String urlToImage = currentEarthquake.getString("urlToImage");
                        String publishedAt = currentEarthquake.getString("publishedAt");
                        String sourceName = source.getString("name");

                        Article earthquake = new Article(author,title,description,url,urlToImage,publishedAt,sourceName);
                        mAndroidNews.add(earthquake);

                    }

                    mNewAdpater = new NewsViewPagerAdpter(mAndroidNews,getBaseContext());
                    mNewAdpater.notifyDataSetChanged();
                    viewPagerNews.setAdapter(mNewAdpater);
                    viewPagerNews.setCurrentItem(mainPosition);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError) {

                    Toast.makeText(getBaseContext(), "No Internet Connection..", Toast.LENGTH_LONG).show();

                }
                else if(error instanceof TimeoutError) {
                    Toast.makeText(getBaseContext(), "Server Time Out", Toast.LENGTH_LONG).show();


                }

                else if(error instanceof ServerError) {
                    Toast.makeText(getBaseContext(), "Server Error..", Toast.LENGTH_LONG).show();

                }
                error.printStackTrace();


            }
        });

        MySingleton.getInstance(this).addToRequestQue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open:

                Uri newsUri =  Uri.parse(mAndroidNews.get(viewPagerNews.getCurrentItem()).getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent =  new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
                break;
            case R.id.share_menu:
                Toast.makeText(this, "Sharing...", Toast.LENGTH_SHORT).show();

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, mAndroidNews.get(viewPagerNews.getCurrentItem()).getTitle() + "\n" + mAndroidNews.get(viewPagerNews.getCurrentItem()).getUrl() + "\n" + "Save Time Read News With Short Description FactbookApp:" + "\n https://play.google.com/store/apps/details?id=com.komidee.hp.factbook");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "hello");

                startActivity(sharingIntent);
                break;
            case android.R.id.home:
                finish();
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
