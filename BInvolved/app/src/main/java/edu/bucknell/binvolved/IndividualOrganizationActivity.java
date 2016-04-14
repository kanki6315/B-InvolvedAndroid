package edu.bucknell.binvolved;

import android.app.Activity;
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
import java.util.List;

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
        buttonFollowOrganization = (Button) findViewById(R.id.follow_update);
        buttonFollowOrganization.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Do something");
            }
        });

        contact = (Button) findViewById(R.id.contact_organization);
        contact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Do something");
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

        RVEventAdapter adapter1 = new RVEventAdapter(context, events1);
        rv1.setAdapter(adapter1);
    }

    /**
     * Initializes the data for the recycler view of events.
     */
    private void initializeData() {
        List<Event> organizationEvents = organization.getEvents();
        int min = Math.min(organizationEvents.size(), 5);
        events1 = organizationEvents.subList(0,min);

    }
}
