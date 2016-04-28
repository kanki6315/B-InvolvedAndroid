package edu.bucknell.binvolved;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;

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
 * Layout for an individual Category
 *
 * Created by gilbertkim on 3/31/16.
 */
//public class IndividualCategoryActivity extends AppCompatActivity {
public class IndividualCategoryActivity extends AppCompatActivity {

    // Category name
    TextView categoryName;
    // banner image for Category
    ImageView image;
    // follow update button for Category
    Button buttonFollowCategory;
    // more button for all Events
    Button buttonAllEvents;

    boolean following = false;

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

    // Category
    Category category;


    final Context context = this;

    /**
     * Specifies what to do on creation of the page.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_category);

        Bundle inputs = getIntent().getExtras();
        category = Category.getCategoryWithName(inputs.getString("Category Name"));

        // get elements for Category
        categoryName = (TextView) findViewById(R.id.category_name);
        categoryName.setText(category.getName());
        image = (ImageView) findViewById(R.id.category_banner);
        //image.setImageResource(category.getBannerPhotoID());
        image.setBackgroundResource(category.getBannerPhotoID());

        addListenerOnButton();

        // get elements for card view
        eventImage = (ImageView) findViewById(R.id.event_photo);
        eventName = (TextView) findViewById(R.id.event_name);
        eventDateTime = (TextView) findViewById(R.id.event_date_time);

        // get elements for recycler views
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);
        rv2 =(RecyclerView)findViewById(R.id.recycler_view_2);
        rv3 =(RecyclerView)findViewById(R.id.recycler_view_3);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(category.getName());

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

        //resultDrawer.setSelection(categories);

        // initialize stuff
        setLayoutManagers();
        initializeData();
        initializeAdapters();
    }

    /**
     * Adds an onClickListener to the follow button for following updates
     * and for the more button for all Events.
     */
    public void addListenerOnButton() {
        buttonFollowCategory = (Button) findViewById(R.id.follow_update);
        if (category.getFollowingCategories().contains(Category.getCategoryWithName(category.getName()))) {
            following = true;
            buttonFollowCategory.setText(R.string.unfollow);
        } else {
            following = false;
            buttonFollowCategory.setText(R.string.follow_updates);
        }
        buttonFollowCategory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // clicked to unfollow
                if (following) {
                    following = false;
                    Category.removeFromFollowingCategories(category.getName());
                    new AlertDialog.Builder(context)
                            .setTitle("No longer following " + category.getName())
                            //.setMessage("Now following " + category.getName())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                    buttonFollowCategory.setText(R.string.follow_updates);
                } else {
                    following = true;
                    Category.addToFollowingCategories(category.getName());
                    new AlertDialog.Builder(context)
                            .setTitle("Now following " + category.getName())
                            //.setMessage("Now following " + category.getName())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                    buttonFollowCategory.setText(R.string.unfollow);
                }
            }
        });

        buttonAllEvents = (Button) findViewById(R.id.all_events_more);
        buttonAllEvents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent(context, ListEventActivity.class);
                localIntent.putExtra("On Tab", "All");
                localIntent.putParcelableArrayListExtra("All Events", Event.getAllEventsForCategory(category.getName()));
                localIntent.putParcelableArrayListExtra("Following Events", Event.getFollowingEventsForCategory(category.getName()));
                startActivity(localIntent);
            }
        });
    }

    /**
     * Sets the layout managers for the recycler views.
     */
    private void setLayoutManagers() {
        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv1.setLayoutManager(llm1);
        rv1.setHasFixedSize(true);

        LinearLayoutManager llm2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv2.setLayoutManager(llm2);
        rv2.setHasFixedSize(true);

        LinearLayoutManager llm3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv3.setLayoutManager(llm3);
        rv3.setHasFixedSize(true);
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
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        Calendar calendarNextWeek = Calendar.getInstance(TimeZone.getDefault());
        calendarNextWeek.add(Calendar.DATE, 7);
        //System.out.println("today: " + today.getStartTime());
        //System.out.println("one week: " + calendarNextWeek.getStartTime());
        events1 = new ArrayList<Event>();

        /*
        events1.add(new Event("name1", "4/7/2016", "7:00 PM", "10:00 PM", "location",
                R.drawable.ace, "org1", "Free Food;Alcohol(21+)", "description"));
        events1.add(new Event("name2", "4/20/2016", "6:15 PM", "11:30 AM", "location",
                R.drawable.ace, "org2", "Dance;Music", "description"));
        events1.add(new Event("name3", "4/30/2016", "7:00 PM", "2:00 AM", "location",
                R.drawable.ace, "org1", "Theater;Social", "description"));
        */
        for (Event event:categoryEvents) {
            if (event.getStartCalendar().after(today) && event.getStartCalendar().before(calendarNextWeek)) {
                events1.add(event);
            }
        }
        System.out.println("events1 size: " + events1.size());


        // TODO: top Events: how to determine a top event
        events2 = new ArrayList<Event>();
        int min2 = Math.min(categoryEvents.size(), 5);
        int count2 = 0;
        for (Event event:categoryEvents) {
            if (event.getStartCalendar().after(today)) {
                events2.add(event);
                count2 += 1;
            }
            if (count2 == min2) {
                break;
            }
        }
        /*
        events2.add(new Event("name1", "4/7/2016", "7:00 PM", "10:00 PM", "location",
                R.drawable.ace, "org1", "Free Food;Alcohol(21+)", "description"));
        events2.add(new Event("name2", "4/20/2016", "6:15 PM", "11:30 AM", "location",
                R.drawable.ace, "org2", "Dance;Music", "description"));
        events2.add(new Event("name3", "4/30/2016", "7:00 PM", "2:00 AM", "location",
                R.drawable.ace, "org1", "Theater;Social", "description"));
        events2.add(new Event("name4", "5/5/2016", "11:00 AM", "3:00 PM", "location",
                R.drawable.ace, "org1", "Free Food;Alcohol(21+)", "description"));
        events2.add(new Event("name5", "5/10/2016", "10:00 AM", "5:00 PM", "location",
                R.drawable.ace, "org2", "Free Food", "description"));
        */
        System.out.println("events2 size: " + events2.size());

        // a subset of all Events
        events3 = new ArrayList<Event>();
        int min = Math.min(categoryEvents.size(), 10);
        int count = 0;
        for (Event event:categoryEvents) {
            if (event.getStartCalendar().after(today)) {
                events3.add(event);
                count += 1;
            }
            if (count == min) {
                break;
            }
        }

        System.out.println("events3 size: " + events3.size());

    }

    /**
     * Initializes the adapter for the RecyclerView.
     */
    private void initializeAdapters(){
        CardViewEventAdapter adapter1 = new CardViewEventAdapter(context, events1);
        rv1.setAdapter(adapter1);

        CardViewEventAdapter adapter2 = new CardViewEventAdapter(context, events2);
        rv2.setAdapter(adapter2);

        CardViewEventAdapter adapter3 = new CardViewEventAdapter(context, events3);
        rv3.setAdapter(adapter3);
    }






/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up buttonFollowCategory, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
