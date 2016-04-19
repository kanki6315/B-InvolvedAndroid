package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity {

    Button button;
    Button button2;
    Button button3;

    List<Event> allEvents;
    List<Organization> allOrganizations;
    List<Category> allCategories;

    final Context context = this;

    // parts for the recycler views
    private List<Category> categories;
    private RecyclerView rv1;
    private List<Event> upcomingEvents;
    private RecyclerView rv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // read in the CSV files
        readInOrganizations();
        readInCategories();
        readInEvents();



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

        setLayoutManagersAndInitializeAdapters();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
