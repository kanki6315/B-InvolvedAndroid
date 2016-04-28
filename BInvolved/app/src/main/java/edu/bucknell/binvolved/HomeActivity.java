package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity {

    public static boolean appStarted = false;

    Button button;
    Button button2;
    Button button3;

    Button moreCategories;
    Button moreUpcomingEvents;

    ArrayList<Event> allEvents;
    ArrayList<Organization> allOrganizations;
    ArrayList<Category> allCategories;

    final Context context = this;

    // parts for the recycler views
    private List<Category> categories;
    private RecyclerView rv1;
    private List<Event> upcomingEvents;
    private RecyclerView rv2;



    ViewFlipper viewFlipper;
    // banner photos
    ImageView bannerPhoto_1;
    ImageView bannerPhoto_2;
    ImageView bannerPhoto_3;
    ImageView bannerPhoto_4;
    ImageView bannerPhoto_5;
    ImageView bannerPhoto_6;


    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    int imageDisplay = 0;


    final int NUM_IMAGES = 6;
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
            params.setMargins(6,0,6,0);

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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // if already loaded, don't redo data
        //if (HomeActivity.appStarted == true) {
        //
        //    return;
        //}


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        if (HomeActivity.appStarted == false) {
            // read in the CSV files
            readInOrganizations();
            allOrganizations = Organization.sortOrganizationsAlphabetically(allOrganizations);
            readInCategories();
            allCategories = Category.sortCategoriesAlphabetically(allCategories);
            readInEvents();
            allEvents = Event.sortEventsByStartDate(allEvents);
        } else {
            allOrganizations = Organization.getAllOrganizations();
            allCategories = Category.getAllCategories();
            allEvents = Event.getAllEvents();
        }

        /*
        // button to go to individual category page
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent localIntent = new Intent(context, ListEventActivity.class);
                ArrayList<Event> events = new ArrayList<Event>();
                events.add(new Event("name1", "4/7/2016", "7:00 PM", "10:00 PM", "location",
                        R.drawable.ace, "org1", "Free Food;Alcohol(21+)", "description"));
                events.add(new Event("name2", "4/20/2016", "6:15 PM", "11:30 AM", "location",
                        R.drawable.ace, "org2", "Dance;Music", "description"));
                events.add(new Event("name3", "4/30/2016", "7:00 PM", "2:00 AM", "location",
                        R.drawable.ace, "org1", "Theater;Social", "description"));
                localIntent.putParcelableArrayListExtra("Following Events", events);

                ArrayList<Event> events2 = new ArrayList<Event>();
                events2.add(new Event("name10", "5/7/2016", "7:00 PM", "10:00 PM", "location",
                        R.drawable.ace, "org10", "Free Food;Alcohol(21+)", "description"));
                events2.add(new Event("name20", "5/20/2016", "6:15 PM", "11:30 AM", "location",
                        R.drawable.ace, "org20", "Dance;Music", "description"));
                events2.add(new Event("name30", "5/30/2016", "7:00 PM", "2:00 AM", "location",
                        R.drawable.ace, "org10", "Theater;Social", "description"));
                localIntent.putParcelableArrayListExtra("All Events", events2);

                startActivity(localIntent);
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent localIntent = new Intent(context, ListOrganizationActivity.class);
                //localIntent.putExtra("Event Name", "Some Event Name");
                ArrayList<Organization> organizations = new ArrayList<>();
                organizations.addAll(allOrganizations.subList(0,10));
                localIntent.putParcelableArrayListExtra("All Organizations", organizations);
                ArrayList<Organization> organizations2 = new ArrayList<>();
                organizations2.addAll(allOrganizations.subList(0,5));
                localIntent.putParcelableArrayListExtra("Following Organizations", organizations2);
                startActivity(localIntent);
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent localIntent = new Intent(context, ListCategoryActivity.class);
                //localIntent.putExtra("Organization Name", "Uptown");
                ArrayList<Category> categories = new ArrayList<>();
                categories.addAll(allCategories.subList(0,10));
                localIntent.putParcelableArrayListExtra("All Categories", categories);
                ArrayList<Category> categories2 = new ArrayList<>();
                categories2.addAll(allCategories.subList(0,5));
                localIntent.putParcelableArrayListExtra("Following Categories", categories2);
                startActivity(localIntent);
            }
        });
        */


        moreCategories = (Button) findViewById(R.id.more_categories);
        moreCategories.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent(context, ListCategoryActivity.class);
                localIntent.putExtra("On Tab", "All");
                localIntent.putParcelableArrayListExtra("All Categories", Category.getAllCategories());
                localIntent.putParcelableArrayListExtra("Following Categories", Category.getFollowingCategories());
                startActivity(localIntent);
            }
        });

        moreUpcomingEvents = (Button) findViewById(R.id.more_upcoming_events);
        moreUpcomingEvents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent(context, ListEventActivity.class);
                localIntent.putExtra("On Tab", "All");
                localIntent.putParcelableArrayListExtra("All Events", Event.getAllEvents());
                localIntent.putParcelableArrayListExtra("Following Events", Event.getFollowingEvents());
                startActivity(localIntent);
            }
        });


        // do setup for recycler view
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);
        rv2 =(RecyclerView)findViewById(R.id.recycler_view_2);
        int minCategoryDisplay = Math.min(5, allCategories.size());
        categories = allCategories.subList(0,minCategoryDisplay);


        Calendar now = Calendar.getInstance(TimeZone.getDefault());

        int minEventDisplay = Math.min(5,allEvents.size());
        upcomingEvents = new ArrayList<Event>();
        int count = 0;
        for (Event event: allEvents) {
            if (event.getStartCalendar().getTime().after(now.getTime())) {
                upcomingEvents.add(event);
                count += 1;
            }
            if (count == minEventDisplay) {
                break;
            }
        }



        addDots();
        selectDot(0);


        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        setupBannerPhotos();


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

                        System.out.println("x1: " + x1 + " x2: " + x2);

                        // left to right sweep
                        if (x1<(x2-50)) {
                            imageDisplay -= 1;
                            viewFlipper.setInAnimation(context, R.anim.slide_in_from_left);
                            viewFlipper.setOutAnimation(context, R.anim.slide_out_to_right);
                            viewFlipper.showNext();
                            selectDot(Math.abs(imageDisplay) % NUM_IMAGES);
                        } else {
                            // right to left sweep
                            if (x1 > (x2 + 50)) {
                                imageDisplay += 1;
                                viewFlipper.setInAnimation(context, R.anim.slide_in_from_right);
                                viewFlipper.setOutAnimation(context, R.anim.slide_out_to_left);
                                viewFlipper.showPrevious();
                                selectDot(Math.abs(imageDisplay) % NUM_IMAGES);
                            } else {
                                //System.out.println("GOING TO EVENT: " + Math.abs(imageDisplay) % NUM_IMAGES);
                                //goToEvent(Math.abs(imageDisplay) % NUM_IMAGES);
                            }
                        }
                    }
                    case MotionEvent.ACTION_MOVE: {
                    }
                }
                return true;
            }
        });





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

        final Drawer resultDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(home, yourEvents, allEvents, organizations, categories, settings, help)
                .withSelectedItemByPosition(0)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // clicked item
                        if(drawerItem.equals(home.getIdentifier())) {
                            // don't do intent to HomeActivity because will double the data
                            return false;
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


        setLayoutManagersAndInitializeAdapters();

        HomeActivity.appStarted = true;
    }



    public void goToEvent(int num) {
        ImageView[] bannerPhotos = {bannerPhoto_6, bannerPhoto_5, bannerPhoto_4, bannerPhoto_3, bannerPhoto_2, bannerPhoto_1};

        Intent localIntent = new Intent(context, IndividualEventActivity.class);
        localIntent.putExtra("Event Name", allEvents.get(num).getName());
        localIntent.putExtra("Event Date", allEvents.get(num).getDateAndTime());
        startActivity(localIntent);

    }

    public void setupBannerPhotos() {

        bannerPhoto_1 = (ImageView) findViewById(R.id.bannerPhoto_1);
        bannerPhoto_1.setBackgroundResource(allEvents.get(0).getPhotoID());

        bannerPhoto_2 = (ImageView) findViewById(R.id.bannerPhoto_2);
        bannerPhoto_2.setBackgroundResource(allEvents.get(1).getPhotoID());

        bannerPhoto_3 = (ImageView) findViewById(R.id.bannerPhoto_3);
        bannerPhoto_3.setBackgroundResource(allEvents.get(2).getPhotoID());

        bannerPhoto_4 = (ImageView) findViewById(R.id.bannerPhoto_4);
        bannerPhoto_4.setBackgroundResource(allEvents.get(3).getPhotoID());

        bannerPhoto_5 = (ImageView) findViewById(R.id.bannerPhoto_5);
        bannerPhoto_5.setBackgroundResource(allEvents.get(4).getPhotoID());

        bannerPhoto_6 = (ImageView) findViewById(R.id.bannerPhoto_6);
        bannerPhoto_6.setBackgroundResource(allEvents.get(5).getPhotoID());

    }



    /**
     * Reads the CSV file of Organizations and creates the Organization objects.
     */
    public void readInOrganizations() {
        // read CSV file for Organizations
        allOrganizations = new ArrayList<Organization>();
        InputStream inputStream = getResources().openRawResource(R.raw.organizations);
        CSVFile csvFile = new CSVFile(inputStream);
        List scoreList = csvFile.read();
        // ignore first row because it is the title row
        for (int i = 1; i < scoreList.size(); i++) {
            String[] organizationInfo = (String[]) scoreList.get(i);

            // inputs to Organization constructor:
            // String name, int logoPhotoID, int photo1ID, int photo2ID, int photo3ID

            // group together description
            String description = getDescription(5, organizationInfo);

            // get all image IDs according to the names
            int logoPhotoID = context.getResources().getIdentifier(organizationInfo[1], "drawable", context.getPackageName());
            int photo1ID = context.getResources().getIdentifier(organizationInfo[2], "drawable", context.getPackageName());
            int photo2ID = context.getResources().getIdentifier(organizationInfo[3], "drawable", context.getPackageName());
            int photo3ID = context.getResources().getIdentifier(organizationInfo[4], "drawable", context.getPackageName());
            allOrganizations.add(new Organization(organizationInfo[0], logoPhotoID, photo1ID, photo2ID, photo3ID, description));

            //System.out.println("organizationInfo: " + organizationInfo[0] + " " + organizationInfo[1] + " " + organizationInfo[2] + " " + organizationInfo[3] + " " + organizationInfo[4]);
        }
    }


    /**
     * Reads the CSV file of Categories and creates the Category objects.
     */
    public void readInCategories() {
        // read CSV file for Categories
        allCategories = new ArrayList<Category>();
        InputStream inputStream = getResources().openRawResource(R.raw.categories);
        CSVFile csvFile = new CSVFile(inputStream);
        List scoreList = csvFile.read();
        // ignore first row because it is the title row
        for (int i = 1; i < scoreList.size(); i++) {
            String[] categoryInfo = (String[]) scoreList.get(i);

            // inputs to Category constructor:
            // String name, int smallPhotoID, int bannerPhotoID

            // get all image IDs according to the names
            int smallPhotoID = context.getResources().getIdentifier(categoryInfo[1], "drawable", context.getPackageName());
            int bannerPhotoID = context.getResources().getIdentifier(categoryInfo[2], "drawable", context.getPackageName());
            allCategories.add(new Category(categoryInfo[0],smallPhotoID, bannerPhotoID));

            //System.out.println("categoryInfo: " + categoryInfo[0] + " " + categoryInfo[1] + " " + categoryInfo[2]);
        }
    }

    /**
     * Reads the CSV file of Events and creates the Event objects.
     */
    public void readInEvents() {
        // read CSV file for events
        allEvents = new ArrayList<Event>();
        InputStream inputStream = getResources().openRawResource(R.raw.events);
        CSVFile csvFile = new CSVFile(inputStream);
        List scoreList = csvFile.read();
        // ignore first row because it is the title row
        for (int i = 1; i < scoreList.size(); i++) {
            String[] eventData = (String[]) scoreList.get(i);

            // group together description
            String description = getDescription(8, eventData);

            // inputs to Event constructor:
            // String name, String date, String startTime, String endTime, String location,
            // int photoID, String organizations, String categories, String description

            // get image ID according to its name
            int photoID = context.getResources().getIdentifier(eventData[5], "drawable", context.getPackageName());
            allEvents.add(new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4],
                    photoID, eventData[6], eventData[7], description));
        }
    }

    private void setLayoutManagersAndInitializeAdapters() {
        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv1.setLayoutManager(llm1);
        rv1.setHasFixedSize(true);

        LinearLayoutManager llm2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv2.setLayoutManager(llm2);
        rv2.setHasFixedSize(true);

        // adapters
        CardViewCategoryAdapter adapter1 = new CardViewCategoryAdapter(context, categories);
        rv1.setAdapter(adapter1);

        CardViewEventAdapter adapter2 = new CardViewEventAdapter(context, upcomingEvents);
        rv2.setAdapter(adapter2);
    }

    /**
     * Starts the specified integer index and combines the String values
     * in the array from that point on.
     *
     * @param num           index to start at
     * @param data          array of String values
     * @return              the combination of String values
     */
    private String getDescription(int num, String[] data) {
        String description = "";
        boolean removedFirstQuote = false;
        for (int j = num; j < data.length; j++) {
            description += data[j];
            if (j == num && description.charAt(0) == '"') {
                description = description.substring(1);
                removedFirstQuote = true;
            }
            if (j != data.length - 1) {
                description += ",";
            } else {
                if (removedFirstQuote) {
                    description = description.substring(0,description.length()-1);
                }
            }
        }
        return description;
    }

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
        if (id == R.id.home_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
