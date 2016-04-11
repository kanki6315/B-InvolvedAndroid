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
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Layout for an individual Category
 *
 * Created by gilbertkim on 3/31/16.
 */
public class IndividualCategoryActivity extends Activity {

    // Category name
    TextView categoryName;
    // banner image for Category
    ImageView image;
    // follow update buttonFollowCategory for Category
    Button buttonFollowCategory;
    // more buttonFollowCategory for all Events
    Button buttonAllEvents;

    // parts for the card view
    ImageView eventImage;
    TextView eventName;
    TextView eventDateTime;

    // parts for recycler view 1: This Week
    private List<Event> events1;
    private RecyclerView rv1;

    // parts for recycler view 2: Top Events
    private List<Event> events2;
    private RecyclerView rv2;

    // parts for recycler view 3: All Events
    private List<Event> events3;
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
        categoryName = (TextView) findViewById(R.id.category_name);
        categoryName.setText(getIntent().getStringExtra("Category Name"));
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
        initializeAdapters();
    }

    /**
     * Adds an onClickListener to the follow button for following updates
     * and for the more button for all Events.
     */
    public void addListenerOnButton() {
        buttonFollowCategory = (Button) findViewById(R.id.follow_update);
        buttonFollowCategory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                System.out.println("The follow update button got pressed.");
            }
        });

        buttonAllEvents = (Button) findViewById(R.id.all_events_more);
        buttonAllEvents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                System.out.println("The more event button got pressed.");
            }
        });
    }

    /**
     * Initializes temporary data. This will be replaced with actual data from the CSV file.
     *
     */
    private void initializeData() {


        // inputs to Event constructor:
        //String name, String date, String startTime, String endTime, String location,
        //int photoID, String organizations, String categories, String description

        // gets the Category name and the list of Events
        String categoryName = this.categoryName.getText().toString();
        System.out.println("categoryName: " + categoryName);
        Category category = Category.getCategoryWithName(categoryName);
        System.out.println("category: " + category);
        List<Event> categoryEvents = category.getEvents();
        System.out.println("categoryEvents: " + categoryEvents);

        // get Events within a week of the current date
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Calendar calendarNextWeek = Calendar.getInstance(TimeZone.getDefault());
        calendarNextWeek.add(Calendar.DATE, 7);
        events1 = new ArrayList<Event>();

        events1.add(new Event("name1", "4/7/2016", "7:00 PM", "10:00 PM", "location",
                R.drawable.rsz_1chrysalis, "org1", "Free Food;Alcohol(21+)", "description"));
        events1.add(new Event("name2", "4/20/2016", "6:15 PM", "11:30 AM", "location",
                R.drawable.ace, "org2", "Dance;Music", "description"));
        events1.add(new Event("name3", "4/30/2016", "7:00 PM", "2:00 AM", "location",
                R.drawable.ace, "org1", "Theater;Social", "description"));
        /*
        for (Event event:categoryEvents) {
            if (event.getStartCalendar().before(calendarNextWeek)) {
                events1.add(event);
            }
        }
        System.out.println("events1 size: " + events1.size());
        */

        // TODO: top Events: how to determine a top event
        events2 = new ArrayList<Event>();
        events2.add(new Event("name1", "4/7/2016", "7:00 PM", "10:00 PM", "location",
                R.drawable.rsz_1chrysalis, "org1", "Free Food;Alcohol(21+)", "description"));
        events2.add(new Event("name2", "4/20/2016", "6:15 PM", "11:30 AM", "location",
                R.drawable.ace, "org2", "Dance;Music", "description"));
        events2.add(new Event("name3", "4/30/2016", "7:00 PM", "2:00 AM", "location",
                R.drawable.ace, "org1", "Theater;Social", "description"));
        events2.add(new Event("name4", "5/5/2016", "11:00 AM", "3:00 PM", "location",
                R.drawable.ace, "org1", "Free Food;Alcohol(21+)", "description"));
        events2.add(new Event("name5", "5/10/2016", "10:00 AM", "5:00 PM", "location",
                R.drawable.ace, "org2", "Free Food", "description"));
        System.out.println("events2 size: " + events2.size());

        // a subset of all Events
        System.out.println("categoryEvents size: " + categoryEvents.size());
        int min = Math.min(categoryEvents.size(), 10);

        //events3 = new ArrayList<Event>();

        events3 = categoryEvents.subList(0,min);

        System.out.println("events3 size: " + events3.size());

    }

    /**
     * Initilizes the adapter for the RecyclerView.
     */
    private void initializeAdapters(){
        RVAdapter adapter1 = new RVAdapter(events1);
        rv1.setAdapter(adapter1);

        RVAdapter adapter2 = new RVAdapter(events2);
        rv2.setAdapter(adapter2);

        RVAdapter adapter3 = new RVAdapter(events3);
        rv3.setAdapter(adapter3);
    }
}
