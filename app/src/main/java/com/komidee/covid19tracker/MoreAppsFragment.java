package com.komidee.covid19tracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MoreAppsFragment extends Fragment {
    LinearLayout earthquakeAppLayout;
    LinearLayout psychologyAppLayout;
    final private static String EARTHQUAKE_APP_NAME = "com.komidee.earthquakeapp";
    final  static String PSY_APP_NAME = "com.vairagii.psychologyfacts";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.other_fragment, container, false);
        earthquakeAppLayout = rootView.findViewById(R.id.earthquakeApp);
        psychologyAppLayout = rootView.findViewById(R.id.psychologyApp);

        earthquakeAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + EARTHQUAKE_APP_NAME)));
            }
        });
        psychologyAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PSY_APP_NAME)));
            }
        });
        return rootView;
    }
}
