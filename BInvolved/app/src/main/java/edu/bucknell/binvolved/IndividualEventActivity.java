package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;

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



    // Event
    Event event;

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_event);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: need more than just a name to uniquely identify an event
        Bundle inputs = getIntent().getExtras();
        event = Event.getEventWithNameAndDateAndTime(inputs.getString("Event Name"), inputs.getString("Event Date"));

        eventBannerImage = (ImageView) findViewById(R.id.event_banner_image);
        eventBannerImage.setImageResource(event.getPhotoID());

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
        eventTime.setText(event.getTime());
        eventLocation = (TextView) findViewById(R.id.eventLocation);
        eventLocation.setText(event.getLocation());

        eventTextDescription = (TextView) findViewById(R.id.eventTextDescription);
        eventTextDescription.setText(event.getDescription());
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
