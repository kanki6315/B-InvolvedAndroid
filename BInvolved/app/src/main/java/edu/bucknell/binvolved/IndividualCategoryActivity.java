package edu.bucknell.binvolved;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Layout for an individual Category
 *
 * Created by gilbertkim on 3/31/16.
 */
public class IndividualCategoryActivity extends Activity {

    // banner image for Category
    ImageView image;
    // follow update button for Category
    Button button;

    // parts for the card view
    ImageView eventImage;
    TextView eventName;
    TextView eventDateTime;

    // parts for recycler view 1: This Week
    private ArrayList<Event> events1;
    private RecyclerView rv1;

    // parts for recycler view 2: Top Events
    private ArrayList<Event> events2;
    private RecyclerView rv2;

    // parts for recycler view 3: All Events
    private ArrayList<Event> events3;
    private RecyclerView rv3;

    /**
     * Specifies what to do on creation of the page.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_category);

        // get elements for Category
        image = (ImageView) findViewById(R.id.cat_banner);
        addListenerOnButton();

        // get elements for card view
        eventImage = (ImageView) findViewById(R.id.event_photo);
        eventName = (TextView) findViewById(R.id.event_name);
        eventDateTime = (TextView) findViewById(R.id.event_date_time);

        // get elements for recycler views and set layout manager for each
        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);
        rv1.setLayoutManager(llm1);
        rv1.setHasFixedSize(true);
        LinearLayoutManager llm2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv2 =(RecyclerView)findViewById(R.id.recycler_view_2);
        rv2.setLayoutManager(llm2);
        rv2.setHasFixedSize(true);
        LinearLayoutManager llm3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv3 =(RecyclerView)findViewById(R.id.recycler_view_3);
        rv3.setLayoutManager(llm3);
        rv3.setHasFixedSize(true);

        // initialize stuff
        initializeData();
        initializeAdapter();
    }

    /**
     * Adds an onClickListener to the button for following updates.
     */
    public void addListenerOnButton() {
        button = (Button) findViewById(R.id.follow_update);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                System.out.println("The follow update button got pressed.");
            }
        });
    }

    /**
     * Initializes temporary data. This will be replaced with actual data from the CSV file.
     *
     */
    private void initializeData() {
        events1 = new ArrayList<Event>();

        // inputs to Event constructor:
        //String name, String date, String startTime, String endTime, String location,
        //int photoID, String organizations, String categories, String description

        events1.add(new Event("name1", "4/7/2016", "7:00 PM", "10:00 PM", "location",
                R.drawable.rsz_1chrysalis, "org1", "Free Food;Alcohol(21+)", "description"));
        events1.add(new Event("name2", "4/20/2016", "6:15 PM", "11:30 AM", "location",
                R.drawable.ace, "org2", "Dance;Music", "description"));
        events1.add(new Event("name3", "4/30/2016", "7:00 PM", "2:00 AM", "location",
                R.drawable.ace, "org1", "Theater;Social", "description"));
        events1.add(new Event("name4", "5/5/2016", "11:00 AM", "3:00 PM", "location",
                R.drawable.ace, "org1", "Free Food;Alcohol(21+)", "description"));
        events1.add(new Event("name5", "5/10/2016", "10:00 AM", "5:00 PM", "location",
                R.drawable.ace, "org2", "Free Food", "description"));


        events2 = new ArrayList<Event>();
        List<Event> catEvents = Category.getCategoryWithName("Free Food").getEvents();


        events3 = new ArrayList<Event>();
    }

    /**
     * Initilizes the adapter for the RecyclerView.
     */
    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(events1);
        rv1.setAdapter(adapter);
    }
}
