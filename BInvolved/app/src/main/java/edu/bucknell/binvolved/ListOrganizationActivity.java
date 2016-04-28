package edu.bucknell.binvolved;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;
import java.util.ArrayList;

/**
 * Layout for the list view of Organizations.
 *
 * Created by gilbertkim on 4/19/16.
 */
public class ListOrganizationActivity extends AppCompatActivity {

    private String onTab;
    private ArrayList<Organization> followingOrganizations;
    private ArrayList<Organization> allOrganizations;
    private RecyclerView rv1;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        // get elements for recycler views
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);

        // initialize stuff
        setLayoutManagers();

        // get the intent inputs
        onTab = getIntent().getStringExtra("On Tab");
        followingOrganizations = getIntent().getParcelableArrayListExtra("Following Organizations");
        allOrganizations = getIntent().getParcelableArrayListExtra("All Organizations");

        // set the tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // set layout for possible text when no following objects
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                new ArrayList<Event>(), followingOrganizations, new ArrayList<Category>(), new ArrayList<Event>());
        viewPager.setAdapter(adapter);

        // set listener for the tab layout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //System.out.println("TAB SELECTED: " + tab.getPosition());
                // following tab
                if (tab.getPosition() == 0) {
                    ListViewOrganizationAdapter blah = new ListViewOrganizationAdapter(context, followingOrganizations);
                    rv1.swapAdapter(blah, false);
                }
                // all tab
                if (tab.getPosition() == 1) {
                    ListViewOrganizationAdapter blah = new ListViewOrganizationAdapter(context, allOrganizations);
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
        //ListViewOrganizationAdapter adapter1;
        TabLayout.Tab tab;
        if (onTab.equals("Following")) {
            //adapter1 = new ListViewOrganizationAdapter(context, followingOrganizations);
            tab = tabLayout.getTabAt(0);
        } else {
            //adapter1 = new ListViewOrganizationAdapter(context, allOrganizations);
            tab = tabLayout.getTabAt(1);
        }
        //rv1.setAdapter(adapter1);
        tab.select();



        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List of Organizations");

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
     * Sets the layout managers for the recycler views.
     */
    private void setLayoutManagers() {
        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv1.setLayoutManager(llm1);
        rv1.setHasFixedSize(true);
    }
}