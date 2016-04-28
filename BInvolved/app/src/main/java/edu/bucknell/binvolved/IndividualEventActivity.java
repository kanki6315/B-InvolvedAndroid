package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Calendar;
import java.util.List;

/**
 * Layout for an individual Event
 *
 * Created by Arjuna and Gilbert.
 */
public class IndividualEventActivity extends AppCompatActivity {

    // Event banner image
    ImageView eventBannerImage;

    // Organization logo image
    ImageView eventLogoImage;

    // Event name
    TextView eventName;
    // Event date
    TextView eventDate;
    // Event time
    TextView eventTime;
    // Event location
    TextView eventLocation;
    // Event Organization(s)
    TextView eventOrganizations;
    List<Organization> eventOrganizationsList;

    // Event description
    TextView eventTextDescription;

    // Button to follow an Event
    Button followEvent;
    Button shareEvent;

    // Event
    Event event;

    // Things for organization thingy
    TextView organizationText;
    ImageView organizationPhoto;

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_event);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle inputs = getIntent().getExtras();
        event = Event.getEventWithNameAndDateAndTime(inputs.getString("Event Name"), inputs.getString("Event Date"));

        eventBannerImage = (ImageView) findViewById(R.id.event_banner_image);
        //eventBannerImage.setImageResource(event.getPhotoID());
        eventBannerImage.setBackgroundResource(event.getPhotoID());

        eventOrganizationsList = event.getOrganizations();
        // get logo for first organization
        eventLogoImage = (ImageView) findViewById(R.id.logoImage);
        eventLogoImage.setImageResource(eventOrganizationsList.get(0).getImages()[0]);
        eventLogoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IndividualOrganizationActivity.class);
                intent.putExtra("Organization Name", eventOrganizationsList.get(0).getName());
                context.startActivity(intent);
            }
        });
        eventOrganizations = (TextView) findViewById(R.id.eventOrganization);
        eventOrganizations.setText(getAllOrganizationNames(eventOrganizationsList));

        eventName = (TextView) findViewById(R.id.eventName);
        eventName.setText(event.getName());

        Calendar calendar = event.getStartCalendar();

        eventDate = (TextView) findViewById(R.id.eventDate);
        eventDate.setText(event.getDate());
        eventTime = (TextView) findViewById(R.id.eventTime);
        eventTime.setText(getTimeRange());
        eventLocation = (TextView) findViewById(R.id.eventLocation);
        eventLocation.setText(event.getLocation());

        eventTextDescription = (TextView) findViewById(R.id.eventTextDescription);
        eventTextDescription.setText(event.getDescription());

        followEvent = (Button) findViewById(R.id.follow_event);
        followEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event.addToFollowingEvents(event.getName(), event.getDateAndTime());
            }
        });
        shareEvent = (Button) findViewById(R.id.share);
        shareEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String sendString = "Hey! I found " + event.getName() + " using the B-Involved app. Want to go?";
                sendIntent.putExtra(Intent.EXTRA_TEXT, sendString);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Send to"));
            }
        });
        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(event.getName());

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
                .withSelectedItemByPosition(3)
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

        //resultDrawer.setSelection(allEvents);

        organizationPhoto = (ImageView) findViewById(R.id.category_photo);
        organizationText = (TextView) findViewById(R.id.category_name);
        organizationText.setText(getAllOrganizationNames(eventOrganizationsList));
        organizationPhoto.setImageResource(eventOrganizationsList.get(0).getImages()[0]);

        }


    public String getTimeRange() {
        String start = event.getStartTime();
        String end = event.getEndTime();

        System.out.println("start: " + start + " end: " + end);

        if (start.contains("AM") && end.contains("AM")) {
            return start.substring(0,start.indexOf("AM")) + "-" + end;
        }
        if (start.contains("PM") && end.contains("PM")) {
            return start.substring(0,start.indexOf("PM")) + "-" + end;
        }
        return start + "-" + end;
    }


    /**
     * Returns the names of the Organizations in the inputted List as a String.
     *
     * @param listOrgs      list of Organizations
     * @return              String combining all of the names
     */
    public String getAllOrganizationNames(List<Organization> listOrgs) {
        String result = "";
        for (Organization org: listOrgs) {
            result += org.getName() + ", ";
        }
        if (result.length() > 0) {
            result = result.substring(0,result.length()-2);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_individual_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
