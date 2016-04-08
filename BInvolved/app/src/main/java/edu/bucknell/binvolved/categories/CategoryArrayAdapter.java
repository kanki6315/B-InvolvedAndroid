package edu.bucknell.binvolved.categories;

/**
 * Created by Alex on 4/3/2016.
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.bucknell.binvolved.R;

public class CategoryArrayAdapter extends ArrayAdapter<Category>{
    private static final String tag = "CategoryArrayAdapter";
    private static final String ASSETS_DIR = "images/";
    private Context context;

    private ImageView countryIcon;
    private TextView countryName;

    private List<Category> categories = new ArrayList<Category>();

    public CategoryArrayAdapter(Context context, int textViewResourceId,
                               List<Category> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.categories= objects;
    }

    public int getCount() {
        return this.categories.size();
    }

    public Category getItem(int index) {
        return this.categories.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            // ROW INFLATION
            Log.d(tag, "Starting XML Row Inflation ... ");
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.country_listitem, parent, false);
            Log.d(tag, "Successfully completed XML Row Inflation!");
        }

        // Get item
        Category category = getItem(position);

        // Get reference to ImageView
        countryIcon = (ImageView) row.findViewById(R.id.country_icon);

        // Get reference to TextView - country_name
        countryName = (TextView) row.findViewById(R.id.country_name);

        //Set country name
        countryName.setText(category.name);

        // Set country icon usign File path
        String imgFilePath = ASSETS_DIR + category.resourceId;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this.context.getResources().getAssets()
                    .open(imgFilePath));
            countryIcon.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row;
    }
}

