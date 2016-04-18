package edu.bucknell.binvolved.categories;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import edu.bucknell.binvolved.R;

public class CategoryActivity extends AppCompatActivity{
    private List<Category> categoryList= new ArrayList<Category>();

    /** Called when the activity is first created. */

    public List<Category> getArrayList() {
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the View layer
        setContentView(R.layout.listview);
        setTitle("TestIconizedListView");

        // Create Parser for raw/countries.xml
        /*
        CategoryParser categoryParser = new CategoryParser();
        InputStream inputStream = getResources().openRawResource(
                R.raw.countries);

        // Parse the inputstream
        categoryParser.parse(inputStream);

        // Get Countries
        List<Category> categoryList = categoryParser.getList();
        */
        List<Category> categoryList = getArrayList();
        // Create a customized ArrayAdapter
        CategoryArrayAdapter adapter = new CategoryArrayAdapter(
                getApplicationContext(), R.layout.country_listitem, categoryList);

        // Get reference to ListView holder
        ListView lv = (ListView) this.findViewById(R.id.countryLV);

        // Set the ListView adapter
        lv.setAdapter(adapter);


    }
}