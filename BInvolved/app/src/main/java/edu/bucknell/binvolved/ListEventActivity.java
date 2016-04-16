package edu.bucknell.binvolved;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Layout for the list view of Events.
 *
 * Created by gilbertkim.
 */
public class ListEventActivity extends AppCompatActivity {

    // parts for the list item view
    ImageView eventImage;
    TextView eventName;
    TextView eventDateTime;

    // parts for recycler view 1: This Week
    private List<Event> events1;
    private RecyclerView rv1;

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // get elements for card view
        eventImage = (ImageView) findViewById(R.id.event_photo);
        eventName = (TextView) findViewById(R.id.event_name);
        eventDateTime = (TextView) findViewById(R.id.event_date_time);

        // get elements for recycler views
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);

        // initialize stuff
        setLayoutManagers();

        events1 = getIntent().getParcelableArrayListExtra("Event List");

        initializeAdapters();


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabPagerAdapter adapter = new TabPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Sets the layout managers for the recycler views.
     */
    private void setLayoutManagers() {
        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv1.setLayoutManager(llm1);
        rv1.setHasFixedSize(true);
    }


    /**
     * Initializes the adapter for the RecyclerView.
     */
    private void initializeAdapters() {
        ListViewEventAdapter adapter1 = new ListViewEventAdapter(context, events1);
        rv1.setAdapter(adapter1);
    }
}
