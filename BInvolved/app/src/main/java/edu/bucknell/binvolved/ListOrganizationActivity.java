package edu.bucknell.binvolved;

import android.content.Context;
import android.os.Bundle;
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

        followingOrganizations = getIntent().getParcelableArrayListExtra("Following Organizations");
        allOrganizations = getIntent().getParcelableArrayListExtra("All Organizations");

        ListViewOrganizationAdapter adapter1 = new ListViewOrganizationAdapter(context, followingOrganizations);
        rv1.setAdapter(adapter1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                System.out.println("TAB SELECTED: " + tab.getPosition());
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
