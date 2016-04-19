package edu.bucknell.binvolved;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Layout for the list view of Events.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class ListEventActivity extends AppCompatActivity {

    /*
    // parts for the list item view
    ImageView eventImage;
    TextView organizationName;
    TextView eventDateTime;
    */

    private ArrayList<Event> followingEvents;
    private ArrayList<Event> allEvents;
    private RecyclerView rv1;


    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        // get elements for card view
        eventImage = (ImageView) findViewById(R.id.event_photo);
        eventName = (TextView) findViewById(R.id.event_name);
        eventDateTime = (TextView) findViewById(R.id.event_date_time);
        */

        // get elements for recycler views
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);

        // initialize stuff
        setLayoutManagers();

        followingEvents = getIntent().getParcelableArrayListExtra("Following Events");
        allEvents = getIntent().getParcelableArrayListExtra("All Events");

        ListViewEventAdapter adapter1 = new ListViewEventAdapter(context, followingEvents);
        rv1.setAdapter(adapter1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //final TabPagerAdapter adapter = new TabPagerAdapter
        //        (getSupportFragmentManager(), tabLayout.getTabCount(), followingEvents, allEvents);
        //viewPager.setAdapter(adapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                System.out.println("TAB SELECTED: " + tab.getPosition());
                // following tab
                if (tab.getPosition() == 0) {
                    ListViewEventAdapter blah = new ListViewEventAdapter(context, followingEvents);
                    rv1.swapAdapter(blah, false);
                }
                // all tab
                if (tab.getPosition() == 1) {
                    ListViewEventAdapter blah = new ListViewEventAdapter(context, allEvents);
                    rv1.swapAdapter(blah, false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                System.out.println("TAB UNSELECTED");

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                System.out.println("TAB RESELECTED");
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
}
