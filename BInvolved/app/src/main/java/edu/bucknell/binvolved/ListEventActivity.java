package edu.bucknell.binvolved;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * List Activity for the list view of Events. Uses Fragments to create the list
 * so that the Events can be separated by date.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class ListEventActivity extends FragmentActivity {

    //private String onTab;
    private ArrayList<Event> followingEvents;
    private ArrayList<Event> allEvents;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the layout
        setContentView(R.layout.list_view);

        // get the intent inputs
        String onTab = getIntent().getStringExtra("On Tab");
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
                followingEvents, new ArrayList<Organization>(), new ArrayList<Category>(), allEvents);
        viewPager.setAdapter(adapter);

        // set listener for the tab layout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                System.out.println("TAB SELECTED: tab.getPosition()" + tab.getPosition());
                setListAdapterStuff(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //System.out.println("TAB UNSELECTED");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //setListAdapterStuff(tab.getPosition());
                System.out.println("TAB RESELECTED: tab.getPosition(): " + tab.getPosition());
            }
        });

        // set the list and selected tab
        TabLayout.Tab tab;
        if (onTab.equals("Following")) {
            tab = tabLayout.getTabAt(0);
        } else {
            tab = tabLayout.getTabAt(1);
        }
        tab.select();
    }

    /**
     * Sets the adapter to have the correct Event data for the chosen tab.
     *
     * @param tab       the tab that was selected
     */
    public void setListAdapterStuff(int tab) {
        ListViewEventAdapter mAdapter = new ListViewEventAdapter(context);
        Calendar lastOne = Calendar.getInstance(TimeZone.getDefault());
        if (tab == 0) {
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
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);
    }
}
