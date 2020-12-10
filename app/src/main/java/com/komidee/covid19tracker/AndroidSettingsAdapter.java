package com.komidee.covid19tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AndroidSettingsAdapter extends ArrayAdapter<FactData> {

    public AndroidSettingsAdapter(AppCompatActivity context, ArrayList<FactData> androidFlavors) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0,androidFlavors);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.setting_list, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final FactData currentAndroidFlavor = getItem(position);



        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.textView);
        ImageView imageView = listItemView.findViewById(R.id.imageView);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView

        assert currentAndroidFlavor != null;
        imageView.setImageResource(currentAndroidFlavor.getImgURL());
        nameTextView.setText(currentAndroidFlavor.getTitle());




        return listItemView;

    }
}
