package edu.bucknell.binvolved;

import android.content.Context;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Layout for the list view of Events.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class ListEventActivity extends /*ListFragment*//*ListActivity*/FragmentActivity {

    private String onTab;
    private ArrayList<Event> followingEvents;
    private ArrayList<Event> allEvents;
    private RecyclerView rv1;

    final Context context = this;

    @Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // replaced
        //View view = inflater.inflate(R.layout.list_view, container, false);
        setContentView(R.layout.list_view);

        // get the intent inputs
        onTab = getIntent().getStringExtra("On Tab");
        followingEvents = getIntent().getParcelableArrayListExtra("Following Events");
        followingEvents = Event.sortEventsByStartDate(followingEvents);
        allEvents = getIntent().getParcelableArrayListExtra("All Events");
        allEvents = Event.sortEventsByStartDate(allEvents);

        // add the data to the list view
        setListAdapterStuff(0);

        // set the tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // set layout for possible text when no following objects
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                followingEvents, new ArrayList<Organization>(), new ArrayList<Category>());
        viewPager.setAdapter(adapter);

        // set listener for the tab layout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //System.out.println("TAB SELECTED: " + tab.getPosition());
                // following tab
                if (tab.getPosition() == 0) {
                    setListAdapterStuff(0);
                }
                // all tab
                if (tab.getPosition() == 1) {
                    setListAdapterStuff(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //System.out.println("TAB UNSELECTED");

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                System.out.println("TAB RESELECTED");
            }
        });

        // set the list and selected tab
        //ListViewEventAdapter adapter1;
        TabLayout.Tab tab;
        if (onTab.equals("Following")) {
            //adapter1 = new ListViewEventAdapter(context, followingEvents);
            tab = tabLayout.getTabAt(0);
        } else {
            //adapter1 = new ListViewEventAdapter(context, allEvents);
            tab = tabLayout.getTabAt(1);
        }
        //rv1.setAdapter(adapter1);
        tab.select();


        //return view;


        /*
        setContentView(R.layout.list_view);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // get elements for recycler views
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);

        // initialize stuff
        setLayoutManagers();

        // get the intent inputs
        onTab = getIntent().getStringExtra("On Tab");
        followingEvents = getIntent().getParcelableArrayListExtra("Following Events");
        allEvents = getIntent().getParcelableArrayListExtra("All Events");

        // set the tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // set layout for possible text when no following objects
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                followingEvents, new ArrayList<Organization>(), new ArrayList<Category>());
        viewPager.setAdapter(adapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // set listener for the tab layout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //System.out.println("TAB SELECTED: " + tab.getPosition());
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
                //System.out.println("TAB UNSELECTED");

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                System.out.println("TAB RESELECTED");
            }
        });

        // set the list and selected tab
        //ListViewEventAdapter adapter1;
        TabLayout.Tab tab;
        if (onTab.equals("Following")) {
            //adapter1 = new ListViewEventAdapter(context, followingEvents);
            tab = tabLayout.getTabAt(0);
        } else {
            //adapter1 = new ListViewEventAdapter(context, allEvents);
            tab = tabLayout.getTabAt(1);
        }
        //rv1.setAdapter(adapter1);
        tab.select();
        */
    }

    public void setListAdapterStuff(int tab) {
        ListViewAdapter mAdapter = new ListViewAdapter(context);
        Calendar lastOne = Calendar.getInstance(TimeZone.getDefault());
        if (onTab.equals("Following") || tab == 0) {
            for (Event event: followingEvents) {
                if (lastOne.get(Calendar.DATE) == event.getStartCalendar().get(Calendar.DATE)) {
                    mAdapter.addItem(event.getName(), event.getStartTime(), event.getLocation(), event.getPhotoID(), event.getDateAndTime());
                } else {
                    lastOne = event.getStartCalendar();
                    mAdapter.addSectionHeaderItem(event.getDate());
                    mAdapter.addItem(event.getName(), event.getStartTime(), event.getLocation(), event.getPhotoID(), event.getDateAndTime());
                }
            }
        } else {
            for (Event event: allEvents) {
                if (lastOne.get(Calendar.DATE) == event.getStartCalendar().get(Calendar.DATE)) {
                    mAdapter.addItem(event.getName(), event.getStartTime(), event.getLocation(), event.getPhotoID(), event.getDateAndTime());
                } else {
                    lastOne = event.getStartCalendar();
                    mAdapter.addSectionHeaderItem(event.getDate());
                    mAdapter.addItem(event.getName(), event.getStartTime(), event.getLocation(), event.getPhotoID(), event.getDateAndTime());
                }
            }
        }
        ListView listv = (ListView) findViewById(R.id.list);
        listv.setAdapter(mAdapter);
        //setListAdapter(mAdapter);
    }


    /**
     * Sets the layout managers for the recycler views.
     */
    private void setLayoutManagers() {
        //LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //rv1.setLayoutManager(llm1);
        //rv1.setHasFixedSize(true);
    }
}
