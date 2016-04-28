package edu.bucknell.binvolved;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.content.Context;
import android.widget.ViewFlipper;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


/**
 * Layout for an individual Organization
 *
 * Created by gilbertkim on 4/11/16.
 */
public class IndividualOrganizationActivity extends AppCompatActivity {

    // Organization name
    TextView organizationName;
    // description of the Organization
    TextView organizationDescription;


    ViewFlipper viewFlipper;
    // banner photo
    ImageView organizationBannerPhoto_1;
    ImageView organizationBannerPhoto_2;
    ImageView organizationBannerPhoto_3;
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

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    int imageDisplay = 0;


    final int NUM_IMAGES = 3;
    List<ImageView> dots;
    public void addDots() {
        dots = new ArrayList<>();
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.dots);

        for(int i = 0; i < NUM_IMAGES; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.pager_dot_not_selected);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4,0,4,0);

            dotsLayout.addView(dot, params);

            dots.add(dot);
        }
    }

    public void selectDot(int idx) {
        for(int i = 0; i < NUM_IMAGES; i++) {
            if (i ==idx) {
                System.out.println("selected: " + i);
                dots.get(i).setImageResource(R.drawable.pager_dot_selected);
            } else {
                System.out.println("not selected: " + i);
                dots.get(i).setImageResource(R.drawable.pager_dot_not_selected);
                //dots.get(i).
            }
        }
    }




    /**
     * Specifies what to do on creation of the page.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_organization);

        addDots();
        selectDot(0);

        Bundle inputs = getIntent().getExtras();
        organization = Organization.getOrganizationWithName(inputs.getString("Organization Name"));

        // get and set elements for Organization
        organizationName = (TextView) findViewById(R.id.organizationName);
        organizationName.setText(organization.getName());
        organizationDescription = (TextView) findViewById(R.id.organizationDescription);
        organizationDescription.setText(organization.getDescription());

        // set up images
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        //organizationBannerPhoto = (ImageView) findViewById(R.id.organizationBannerPhoto);
        //organizationBannerPhoto.setImageResource(organization.getImages()[1]);
        //organizationBannerPhoto.setBackgroundResource(organization.getImages()[1]);
        organizationLogoPhoto = (ImageView) findViewById(R.id.organizationLogoImage);
        organizationLogoPhoto.setImageResource(organization.getImages()[0]);


        organizationBannerPhoto_1 = (ImageView) findViewById(R.id.organizationBannerPhoto_1);
        organizationBannerPhoto_1.setBackgroundResource(organization.getImages()[1]);
        organizationBannerPhoto_2 = (ImageView) findViewById(R.id.organizationBannerPhoto_2);
        organizationBannerPhoto_2.setBackgroundResource(organization.getImages()[2]);
        organizationBannerPhoto_3 = (ImageView) findViewById(R.id.organizationBannerPhoto_3);
        organizationBannerPhoto_3.setBackgroundResource(organization.getImages()[3]);



        // change the banner photo
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        x1=e.getX();
                        y1=e.getY();
                    }
                    case MotionEvent.ACTION_UP: {
                        x2=e.getX();
                        y2=e.getY();

                        // left to right sweep
                        if (x1<x2) {
                            //if (viewFlipper.getDisplayedChild() == 0) {
                            //    break;
                            //}
                            imageDisplay -= 1;
                            viewFlipper.setInAnimation(context,R.anim.slide_in_from_left);
                            viewFlipper.setOutAnimation(context, R.anim.slide_out_to_right);
                            viewFlipper.showNext();
                            selectDot(Math.abs(imageDisplay)%NUM_IMAGES);
                        }

                        // right to left sweep
                        if (x1>x2) {
                            //if (viewFlipper.getDisplayedChild() == 3) {
                            //    break;
                            //}
                            imageDisplay += 1;
                            viewFlipper.setInAnimation(context,R.anim.slide_in_from_right);
                            viewFlipper.setOutAnimation(context, R.anim.slide_out_to_left);
                            viewFlipper.showPrevious();
                            selectDot(Math.abs(imageDisplay)%NUM_IMAGES);
                        }
                    }
                }
                return true;
            }
        });



        // sets up the button listeners
        addButtonOnClickListeners();

        // get elements for card view
        eventImage = (ImageView) findViewById(R.id.event_photo);
        eventName = (TextView) findViewById(R.id.event_name);
        eventDateTime = (TextView) findViewById(R.id.event_date_time);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(organization.getName());

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

        //resultDrawer.setSelection(organizations);

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
