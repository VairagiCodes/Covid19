package com.komidee.covid19tracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewAdpater;
    static ArrayList<Article> articles;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private View loadingIndicator;
    private View lodingIndicator1;
    private ImageView imageView;
    private TextView textView;
    private TextView textView1;
    Context context;
    static int page;
    private Boolean isScrolling = false;
    private int currentItems,totalItems,scrollOutItem;
    private View button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        context = rootView.getContext();

        swipeRefreshLayout = rootView.findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout1 = rootView.findViewById(R.id.swipeToRefresh1);
        loadingIndicator = rootView.findViewById(R.id.spinner_loading);
        lodingIndicator1 = rootView.findViewById(R.id.spinner_loading_end);


        page =1;
        articles = null;

        mRecyclerView = rootView.findViewById(R.id.rv1);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout1.setVisibility(View.GONE);
        button = swipeRefreshLayout1.findViewById(R.id.tryAgainButton);
        imageView = swipeRefreshLayout1.findViewById(R.id.ghost);
        textView = swipeRefreshLayout1.findViewById(R.id.ghost_text);
        textView1 = swipeRefreshLayout1.findViewById(R.id.sub_text);
        articles = new ArrayList<>();
        parseJSON();
        mNewAdpater = new NewsAdapter(getContext(),articles);
        mRecyclerView.setAdapter(mNewAdpater);



//        //ToolbarCoding
//
//        setHasOptionsMenu(true);
//
//        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
//        toolbar.setTitle("News");
//        toolbar.setElevation(5);
//        toolbar.setTitleMarginStart(50);
//        toolbar.setBackgroundColor(context.getColor(R.color.backColor));
//        toolbar.setTitleTextColor(context.getColor(R.color.textColorEarthquakeLocation));
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ConnectivityManager cm =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

                if(networkInfo!=null&&articles.isEmpty()) {

                    parseJSON();
                }

                if (networkInfo == null) {
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
        });

        swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ConnectivityManager cm =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout1.setRefreshing(false);
                    }
                }, 3000);

                if(networkInfo!=null) {

                    parseJSON();
                }

                if (networkInfo == null) {
                    swipeRefreshLayout1.setRefreshing(false);
                }

            }
        });



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if(networkInfo!=null) {
                    swipeRefreshLayout1.setRefreshing(true);
                    parseJSON();
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                    lodingIndicator1.setVisibility(View.GONE);


                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItem = layoutManager.findFirstVisibleItemPosition();

                if(isScrolling && ((currentItems + scrollOutItem) == totalItems)&&page<5) {
                    isScrolling = false;
                    page++;
                    lodingIndicator1.setVisibility(View.VISIBLE);

                    parseJSON();

                    if(page==5) {
                        mRecyclerView.setPadding(0,0,0,0);
                    }


                }
            }
        });







        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).

        return rootView;
    }

    private void parseJSON() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-2);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(calendar.getTime());

        String NEWS_REQUEST_URL = "https://newsapi.org/v2/everything?q=corona OR covid19 OR Corona OR coronavirus OR covid19news OR CORONA OR COVID19&from="+currentDate+"&sortBy=relevancy,popularity&page=" + page + "&language=en" + "&apiKey=188f2b1eb6b042d780dba0329f2f7e83";

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
                        articles.add(earthquake);
                    }




                    mNewAdpater.notifyDataSetChanged();





                } catch (JSONException e) {
                    e.printStackTrace();
                }




                loadingIndicator.setVisibility(View.GONE);
                lodingIndicator1.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout1.setRefreshing(false);
                    }
                }, 3000);

                swipeRefreshLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout1.setVisibility(View.GONE);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError) {


                    button.setVisibility(View.VISIBLE);
                    loadingIndicator.setVisibility(View.GONE);
                    lodingIndicator1.setVisibility(View.GONE);
                    swipeRefreshLayout1.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ic_koala);
                    textView.setText(R.string.no_internet);
                    textView1.setText(R.string.check_internet);

                }
                else if(error instanceof TimeoutError) {

                    loadingIndicator.setVisibility(View.GONE);
                    lodingIndicator1.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Time Out!!!",Toast.LENGTH_SHORT).show();
                }

                else if(error instanceof ServerError) {
                    button.setVisibility(View.VISIBLE);
                    loadingIndicator.setVisibility(View.GONE);
                    swipeRefreshLayout1.setVisibility(View.VISIBLE);
                    lodingIndicator1.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ic_koala);
                    textView.setText(R.string.server_error);
                    textView1.setText(R.string.server_error_dec);
                }



                error.printStackTrace();


            }
        });

        MySingleton.getInstance(getContext()).addToRequestQue(request);

    }
}
