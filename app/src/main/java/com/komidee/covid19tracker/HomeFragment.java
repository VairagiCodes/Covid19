package com.komidee.covid19tracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    AutoViewPagerAdapter autoViewPagerAdapter;
    TextView totalCaseTextView;
    TextView activeCaseTextView;
    TextView newCaseTextView;
    TextView totalDeathsTextView;
    TextView dateTextView;
    Spinner countrySpinner;
    ArrayList<String> countryName;
    String country;
    private View loadingIndicator;
    ScrollView scrollView;
    private SwipeRefreshLayout mEmptyStateView;
    private SwipeRefreshLayout swipeRefreshLayout;
    Button button;
    TextView newDeathsTextView;
    TextView recoveredTextView;
    ArrayList<FactData> list;
    int countryInt;
    ViewPager viewPager;
    public static String COUNTRY_NAME1 = "country_nm";
    public static String SHARED_FAV = "shared fav";
    private DrawerLayout drawerLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        loadData();


        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setElevation(5);
        toolbar.setBackgroundColor(getContext().getColor(R.color.backColor));
        toolbar.setTitleTextColor(getContext().getColor(R.color.textColorEarthquakeLocation));
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        drawerLayout = rootView.findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle((Activity) getContext(),drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        countryName = new ArrayList<>();
        countryName.add("USA");
        countryName.add("Italy");
        countryName.add("India");
        countryName.add("Spain");
        countryName.add("Germany");
        countryName.add("China");
        countryName.add("France");
        countryName.add("Iran");
        countryName.add("Pakistan");
        countryName.add("UK");
        countryName.add("Turkey");
        countryName.add("Switzerland");
        countryName.add("Belgium");
        countryName.add("Netherlands");
        countryName.add("Canada");
        countryName.add("Austria");
        countryName.add("Portugal");
        countryName.add("Brazil");
        countryName.add("Israel");
        countryName.add("Sweden");
        countryName.add("Norway");
        countryName.add("Australia");
        countryName.add("Ireland");
        countryName.add("Russia");
        countryName.add("Denmark");
        countryName.add("Chile");
        countryName.add("Malaysia");
        countryName.add("Japan");
        countryName.add("Philippines");
        countryName.add("UAE");
        countryName.add("Singapore");
        country = countryName.get(countryInt);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter(getContext(),R.layout.spinner_item,countryName);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        totalCaseTextView = rootView.findViewById(R.id.title1);
        activeCaseTextView =rootView.findViewById(R.id.title2);
        newCaseTextView = rootView.findViewById(R.id.title3);
        totalDeathsTextView = rootView.findViewById(R.id.title4);
        recoveredTextView = rootView.findViewById(R.id.title5);
        newDeathsTextView = rootView.findViewById(R.id.title6);
        loadingIndicator = rootView.findViewById(R.id.spinner_loading);
        scrollView = rootView.findViewById(R.id.scrollViewCorona);
        mEmptyStateView = rootView.findViewById(R.id.swipeToRefresh1);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeToRefresh);
        dateTextView = rootView.findViewById(R.id.updateDate);
        countrySpinner = rootView.findViewById(R.id.spinner_corona_info);
        button  =rootView.findViewById(R.id.tryAgainButton);
        countrySpinner.setAdapter(dataAdapter);
        countrySpinner.setOnItemSelectedListener(this);
        countrySpinner.setSelection(countryInt);

        parseJson();

        viewPager = rootView.findViewById(R.id.viewPager_corona_live);
        list = new ArrayList<>();
        list.add(new FactData("",R.drawable.slide1));
        list.add(new FactData("",R.drawable.slide2));
        list.add(new FactData("",R.drawable.slide3));

        autoViewPagerAdapter = new AutoViewPagerAdapter(getContext(),list);
        viewPager.setAdapter(autoViewPagerAdapter);
        viewPager.setCurrentItem(2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ConnectivityManager cm =
                        (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                Network networkInfo = cm.getActiveNetwork();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

                if(networkInfo!=null) {

                    parseJson();
                }

                if (networkInfo == null) {
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
        });


        mEmptyStateView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEmptyStateView.setRefreshing(false);
                    }
                }, 3000);

                ConnectivityManager cm =
                        (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(networkInfo!=null) {

                    parseJson();
                }


            }

        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager cm =
                        (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if (networkInfo != null) {
                    mEmptyStateView.setRefreshing(true);
                    mEmptyStateView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mEmptyStateView.setRefreshing(false);
                        }
                    },3000);
                    parseJson();
                }
            }

        });


        NavigationView navigationView = rootView.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.home:
                        drawerLayout.closeDrawer(GravityCompat.START);


                        break;
                   case R.id.coronaVirus:

                       intent = new Intent(getContext(),CoronaVirusActivity.class);
                       startActivity(intent);
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                    case R.id.setting:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(getContext(),SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.share:
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Download PsychologyApp:" + "\n" + "Psychology Facts app provides mind-blowing psychology facts and life hacks that everyone should know" + " \n" + "Download Now:" + " \n"  +"https://play.google.com/store/apps/details?id=com.vairagii.psychologyfacts";
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

                        intent = new Intent(getContext(),AboutActivity.class);
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

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        country = adapterView.getItemAtPosition(i).toString();
        saveData(i);
        parseJson();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


      private void parseJson() {

        swipeRefreshLayout.setRefreshing(true);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://covid-193.p.rapidapi.com/history?&country="+country)
                .get()
                .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "6db3d49c27msh2000acc90e0e926p1402f3jsnccded5f201f1")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {

                    final String jsonData = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                JSONObject jsonObject = new JSONObject(jsonData);
                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                JSONObject  currentCoronaData = jsonArray.getJSONObject(0);
                                String currentTime =currentCoronaData.getString("time");

                                JSONObject coronaCase = currentCoronaData.getJSONObject("cases");
                                JSONObject deathCase = currentCoronaData.getJSONObject("deaths");
                                String total = Integer.toString(coronaCase.getInt("total"));
                                String activeCases = Integer.toString(coronaCase.getInt("active"));
                                String newCases = coronaCase.getString("new");
                                String totalDeaths = Integer.toString(deathCase.getInt("total"));
                                String recovered = Integer.toString(coronaCase.getInt("recovered"));
                                String newDeaths = deathCase.getString("new");
                                String time = convertUtc2Local(currentTime);


                                totalCaseTextView.setText(total);
                                activeCaseTextView.setText(activeCases);
                                newCaseTextView.setText(newCases);
                                totalDeathsTextView.setText(totalDeaths);
                                recoveredTextView.setText(recovered);
                                newDeathsTextView.setText(newDeaths);
                                dateTextView.setText("Last Updated on "+time);
                                loadingIndicator.setVisibility(View.GONE);
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                                mEmptyStateView.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

            }

            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEmptyStateView.setVisibility(View.VISIBLE);
                        loadingIndicator.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                    }
                });

                e.printStackTrace();
            }


        });


  }

        private void saveData(int name) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_FAV,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt(COUNTRY_NAME1,name);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_FAV,Context.MODE_PRIVATE);
        countryInt = sharedPreferences.getInt(COUNTRY_NAME1,0);
    }


    public static String convertUtc2Local(String utcTime) {
        String time = "";
        if (utcTime != null) {
            SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date gpsUTCDate = null;//from  ww  w.j  a va 2 s  . c  o  m
            try {
                gpsUTCDate = utcFormatter.parse(utcTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat localFormatter = new SimpleDateFormat("LLL dd, yyyy h:mm: a", Locale.ENGLISH);
            localFormatter.setTimeZone(TimeZone.getDefault());
            assert gpsUTCDate != null;
            time = localFormatter.format(gpsUTCDate.getTime());
        }
        return time;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
