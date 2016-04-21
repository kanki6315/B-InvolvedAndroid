package edu.bucknell.binvolved;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.TabLayout;

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
