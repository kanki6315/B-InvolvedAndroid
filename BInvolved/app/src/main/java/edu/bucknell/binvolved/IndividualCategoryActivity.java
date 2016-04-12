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


/**
 * Layout for an individual Category
 *
 * Created by gilbertkim on 3/31/16.
 */
public class IndividualCategoryActivity extends Activity {

    Button button;
    ImageView image;

    // parts for the card view
    ImageView eventImage;
    TextView eventName;
    TextView eventDateTime;

    // parts for the recycler view
    private ArrayList<Event> events;
    private RecyclerView rv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.individual_category);

        addListenerOnButton();

        // get elements for card view
        eventImage = (ImageView) findViewById(R.id.event_photo);
        eventName = (TextView) findViewById(R.id.event_name);
        eventDateTime = (TextView) findViewById(R.id.event_date_time);

        // get elements for recycler view
        rv=(RecyclerView)findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        initializeData();
        initializeAdapter();
    }

    public void addListenerOnButton() {
        image = (ImageView) findViewById(R.id.cat_banner);
        button = (Button) findViewById(R.id.follow_update);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                System.out.print("got here");
            }
        });
    }

    /**
     * Initializes temporary data.
     *
     */
    private void initializeData() {

        events = new ArrayList<Event>();

        // inputs to Event constructor:
        //String name, String date, String startTime, String endTime, String location,
        //int photoID, String organizations, String categories, String description

        events.add(new Event("name1", "4/7/2016", "7:00 PM", "10:00 PM", "location",
                R.drawable.rsz_1chrysalis, "org1", "Free Food;Alcohol(21+)", "description"));
        events.add(new Event("name2", "4/20/2016", "6:15 PM", "11:30 AM", "location",
                R.drawable.ace, "org2", "Dance;Music", "description"));
        events.add(new Event("name3", "4/30/2016", "7:00 PM", "2:00 AM", "location",
                R.drawable.ace, "org1", "Theater;Social", "description"));
        events.add(new Event("name4", "5/5/2016", "11:00 AM", "3:00 PM", "location",
                R.drawable.ace, "org1", "Free Food;Alcohol(21+)", "description"));
        events.add(new Event("name5", "5/10/2016", "10:00 AM", "5:00 PM", "location",
                R.drawable.ace, "org2", "Free Food", "description"));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(events);
        rv.setAdapter(adapter);
    }
}
