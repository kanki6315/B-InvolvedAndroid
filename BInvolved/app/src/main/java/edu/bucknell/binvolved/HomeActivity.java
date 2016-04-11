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
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button button;
    Button button2;

    List<Event> allEvents;
    List<Organization> allOrganizations;
    List<Category> allCategories;

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

        final Context context = this;

        // button to go to individual category page
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Bundle localBundle = new Bundle();
                localBundle.putString("Event Name", "Some event name");
                Intent localIntent = new Intent(context, IndividualCategoryActivity.class);
                localIntent.putExtras(localBundle);
                startActivity(localIntent);
            }
        });

        // button to go to individual event page
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Bundle localBundle = new Bundle();
                localBundle.putString("Category Name", "Free Food");
                Intent localIntent = new Intent(context, IndividualEventActivity.class);
                localIntent.putExtras(localBundle);
                startActivity(localIntent);
            }
        });

        // read in the CSV files
        readInOrganizations();
        readInCategories();
        readInEvents();

        // do setup for recycler view
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);
        rv2 =(RecyclerView)findViewById(R.id.recycler_view_2);
        categories = allCategories.subList(0,3);
        upcomingEvents = allEvents.subList(0,2);
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

            // get all image IDs according to the names
            //int logoPhotoID = context.getResources().getIdentifier(organizationInfo[1], "drawable", context.getPackageName());
            //int photo1ID = context.getResources().getIdentifier(organizationInfo[2], "drawable", context.getPackageName());
            //int photo2ID = context.getResources().getIdentifier(organizationInfo[3], "drawable", context.getPackageName());
            //int photo3ID = context.getResources().getIdentifier(organizationInfo[4], "drawable", context.getPackageName());
            allOrganizations.add(new Organization(organizationInfo[0],1,2,3,4/*logoPhotoID, photo1ID, photo2ID, photo3ID*/));
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
            //int smallPhotoID = context.getResources().getIdentifier(organizationInfo[1], "drawable", context.getPackageName());
            //int bannerPhotoID = context.getResources().getIdentifier(organizationInfo[2], "drawable", context.getPackageName());
            allCategories.add(new Category(categoryInfo[0],1,2/*smallPhotoID, bannerPhotoID*/));
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

            // inputs to Event constructor:
            // String name, String date, String startTime, String endTime, String location,
            // int photoID, String organizations, String categories, String description

            // get image ID according to its name
            //int photoID = context.getResources().getIdentifier(eventData[5], "drawable", context.getPackageName());
            allEvents.add(new Event(eventData[0], eventData[1], eventData[2], eventData[3], eventData[4],
                    1/*photoID*/, eventData[6], eventData[7], eventData[8]));
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
        RVCategoryAdapter adapter1 = new RVCategoryAdapter(categories);
        rv1.setAdapter(adapter1);

        RVAdapter adapter2 = new RVAdapter(upcomingEvents);
        rv2.setAdapter(adapter2);
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
