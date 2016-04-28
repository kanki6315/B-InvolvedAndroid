package edu.bucknell.binvolved;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import android.support.v7.app.AppCompatActivity;


/**
 * List Activity for the list view of Events. Uses Fragments to create the list
 * so that the Events can be separated by date.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class ListEventActivity extends AppCompatActivity/*FragmentActivity*/ {

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

        System.out.println("ListEventActivity.java: onCreate(): allEvents size: " + allEvents.size());
        System.out.println("ListEventActivity.java: onCreate(): followingEvents size: " + followingEvents.size());

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


        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List of Events");

        // create drawer and build for each screen
        //new DrawerBuilder().withActivity(this).build();
        final PrimaryDrawerItem home = new PrimaryDrawerItem().withName(R.string.drawer_item_home);
        final PrimaryDrawerItem yourEvents = new PrimaryDrawerItem().withName(R.string.drawer_item_your_events);
        final PrimaryDrawerItem allEvents = new PrimaryDrawerItem().withName(R.string.drawer_item_all_events);
        final PrimaryDrawerItem organizations = new PrimaryDrawerItem().withName(R.string.drawer_item_organizations);
        final PrimaryDrawerItem categories = new PrimaryDrawerItem().withName(R.string.drawer_item_categories);
        final PrimaryDrawerItem settings = new PrimaryDrawerItem().withName(R.string.drawer_item_settings);
        final PrimaryDrawerItem help = new PrimaryDrawerItem().withName(R.string.drawer_item_help);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("B-Involved").withIcon(R.drawable.bucknell_logo)
                )
                .build();


        // navigation drawer
        final Drawer resultDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(home, yourEvents, allEvents, organizations, categories, settings, help)
                .withSelectedItemByPosition(3)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // clicked item
                        if(drawerItem.equals(home.getIdentifier())) {
                            Intent localIntent = new Intent(context, HomeActivity.class);
                            startActivity(localIntent);
                        }
                        if(drawerItem.equals(yourEvents.getIdentifier())) {
                            Intent localIntent = new Intent(context, ListEventActivity.class);
                            localIntent.putExtra("On Tab", "Following");
                            localIntent.putParcelableArrayListExtra("All Events", Event.getAllEvents());
                            localIntent.putParcelableArrayListExtra("Following Events", Event.getFollowingEvents());
                            startActivity(localIntent);
                        }
                        if (drawerItem.equals(allEvents.getIdentifier())) {
                            Intent localIntent = new Intent(context, ListEventActivity.class);
                            localIntent.putExtra("On Tab", "All");
                            localIntent.putParcelableArrayListExtra("All Events", Event.getAllEvents());
                            localIntent.putParcelableArrayListExtra("Following Events", Event.getFollowingEvents());
                            startActivity(localIntent);
                        }
                        if (drawerItem.equals(organizations.getIdentifier())) {
                            Intent localIntent = new Intent(context, ListOrganizationActivity.class);
                            localIntent.putExtra("On Tab", "All");
                            localIntent.putParcelableArrayListExtra("All Organizations", Organization.getAllOrganizations());
                            localIntent.putParcelableArrayListExtra("Following Organizations", Organization.getFollowingOrganizations());
                            startActivity(localIntent);
                        }
                        if (drawerItem.equals(categories.getIdentifier())) {
                            Intent localIntent = new Intent(context, ListCategoryActivity.class);
                            localIntent.putExtra("On Tab", "All");
                            localIntent.putParcelableArrayListExtra("All Categories", Category.getAllCategories());
                            localIntent.putParcelableArrayListExtra("Following Categories", Category.getFollowingCategories());
                            startActivity(localIntent);
                        }
                        if (drawerItem.equals(settings.getIdentifier())) {
                            Intent localIntent = new Intent(context, SettingsActivity.class);
                            //localIntent.putExtra("On Tab", "Following");
                            startActivity(localIntent);
                        }
                        return true;
                    }
                })
                .build();

        //resultDrawer.setSelection(allEvents);
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
        System.out.println("ListEventActivity.java: setListAdapterStuff(): Adapter count: " + mAdapter.getCount());
        listView.setAdapter(mAdapter);
    }
}
