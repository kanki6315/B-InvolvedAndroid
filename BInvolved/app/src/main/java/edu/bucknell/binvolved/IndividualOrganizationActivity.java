package edu.bucknell.binvolved;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Layout for an individual Organization
 *
 * Created by gilbertkim on 4/11/16.
 */
public class IndividualOrganizationActivity extends Activity {

    // Organization name
    TextView organizationName;
    // description of the Organization
    TextView organizationDescription;



    // images for Organization
    Gallery gallery;
    // banner photo
    ImageView organizationBannerPhoto;
    // logo
    ImageView organizationLogoPhoto;



    // follow update button
    Button buttonFollowOrganization;
    // contact button
    Button contact;

    // parts for the card view
    ImageView eventImage;
    TextView eventName;
    TextView eventDateTime;

    // parts for recycler view 1: Upcoming Events
    private List<Event> events1;
    private RecyclerView rv1;

    // Organization
    Organization organization;

    Button moreOrganizationEvents;


    final Context context = this;


    /**
     * Specifies what to do on creation of the page.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_organization);

        Bundle inputs = getIntent().getExtras();
        organization = Organization.getOrganizationWithName(inputs.getString("Organization Name"));

        // get and set elements for Organization
        organizationName = (TextView) findViewById(R.id.organizationName);
        organizationName.setText(organization.getName());
        organizationDescription = (TextView) findViewById(R.id.organizationDescription);
        organizationDescription.setText(organization.getDescription());

        // set up images
        gallery = (Gallery) findViewById(R.id.gallery);
        organizationBannerPhoto = (ImageView) findViewById(R.id.organizationBannerPhoto);
        organizationBannerPhoto.setImageResource(organization.getImages()[1]);
        organizationLogoPhoto = (ImageView) findViewById(R.id.organizationLogoImage);
        organizationLogoPhoto.setImageResource(organization.getImages()[0]);

        // sets up the button listeners
        addButtonOnClickListeners();

        // get elements for card view
        eventImage = (ImageView) findViewById(R.id.event_photo);
        eventName = (TextView) findViewById(R.id.event_name);
        eventDateTime = (TextView) findViewById(R.id.event_date_time);

        // get elements for recycler views
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);
        initializeData();
        setLayoutManagerAndInitializeAdapter();

    }

    /**
     * Sets up the onClickListener for the follow and contact buttons.
     */
    public void addButtonOnClickListeners() {
        buttonFollowOrganization = (Button) findViewById(R.id.follow_organization);
        buttonFollowOrganization.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Organization.addToFollowingOrganizations(organization.getName());
            }
        });

        contact = (Button) findViewById(R.id.contact_organization);
        contact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Do something");
            }
        });


        moreOrganizationEvents = (Button) findViewById(R.id.more_organization_events);
        moreOrganizationEvents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent(context, ListEventActivity.class);
                localIntent.putExtra("On Tab", "All");
                localIntent.putParcelableArrayListExtra("All Events", Event.getAllEventsForOrganization(organization.getName()));
                localIntent.putParcelableArrayListExtra("Following Events", Event.getFollowingEventsForOrganization(organization.getName()));
                startActivity(localIntent);
            }
        });
    }

    /**
     * Sets up the layout manager and adapter for the recycler view of events.
     */
    private void setLayoutManagerAndInitializeAdapter() {
        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv1.setLayoutManager(llm1);
        rv1.setHasFixedSize(true);

        CardViewEventAdapter adapter1 = new CardViewEventAdapter(context, events1);
        rv1.setAdapter(adapter1);
    }

    /**
     * Initializes the data for the recycler view of events.
     */
    private void initializeData() {
        List<Event> organizationEvents = organization.getEvents();
        events1 = new ArrayList<Event>();

        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        int min = Math.min(organizationEvents.size(), 10);
        int count = 0;
        for (Event event:organizationEvents) {
            if (event.getStartCalendar().after(today)) {
                events1.add(event);
                count += 1;
            }
            if (count == min) {
                break;
            }
        }

        System.out.println("ORGANIZATION EVENTS: " + organizationEvents.size());

    }
}
