package edu.bucknell.binvolved;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Layout for the list view of Categories.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class ListCategoryActivity extends Activity {

    /*
    // parts for the list item view
    ImageView categoryImage;
    TextView categoryName;
    */

    private List<Category> followingCategories;
    private List<Category> allCategories;
    private RecyclerView rv1;

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        // get elements for card view
        categoryImage = (ImageView) findViewById(R.id.event_photo);
        categoryName = (TextView) findViewById(R.id.event_name);
        */

        // get elements for recycler views
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);

        // initialize stuff
        setLayoutManagers();

        followingCategories = getIntent().getParcelableArrayListExtra("Following Categories");
        allCategories = getIntent().getParcelableArrayListExtra("All Categories");

        ListViewCategoryAdapter adapter1 = new ListViewCategoryAdapter(context, followingCategories);
        rv1.setAdapter(adapter1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("TAB SELECTED: " + tab.getPosition());
                // following tab
                if (tab.getPosition() == 0) {
                    ListViewCategoryAdapter blah = new ListViewCategoryAdapter(context, followingCategories);
                    rv1.swapAdapter(blah, false);
                }
                // all tab
                if (tab.getPosition() == 1) {
                    ListViewCategoryAdapter blah = new ListViewCategoryAdapter(context, allCategories);
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
