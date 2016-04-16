package edu.bucknell.binvolved;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.TimeZone;
import android.os.Parcelable;
import android.os.Parcel;
import android.support.annotation.Nullable;

/**
 * Class for an Event, which represents an activity created by Organizations.
 * The Event class implements Parcelable so that I can pass lists of Event objects
 * between activities.
 *
 * An Event object has:
 *      name: String
 *      starting date and time: Calendar
 *      ending date and time: Calendar
 *      location: String
 *      photo ID in drawable directory: int
 *      list of Organizations hosting the event: List<Organization>
 *      list of Categories that the Event is under: List<Category>
 *      description: String
 *
 * NOTE: This class only works for Events that happen in one day or go PM to AM.
 *
 *
 * Created by gilbertkim on 4/4/16.
 */
public class Event implements Parcelable {

    // array of the names of the days of the week
    final String[] days = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    // static list of all Event objects that are created
    public static List<Event> allEvents = new ArrayList<Event>();

    // Name of the Event
    String name;
    // Starting date and time of the Event
    Calendar start;
    // Ending date and time of the Event
    Calendar end;
    // Location of the Event
    String location;
    // Photo ID for the Event
    int photoID;
    // List of Organizations that are hosting the Event
    List<Organization> organizations;
    // List of Categories for the Event
    List<Category> categories;
    // Long description of the Event
    String description;


    /**
     * Constructor for an Event object. If the Event starts in a PM time and ends in an AM time,
     * then the ending date is adjusted to be the next day.
     *
     * @param name              the name of the Event
     * @param date              the date of the Event in MM/DD/YYYY format as a String
     * @param startTime         the starting time of the Event in HH:MM AM/PM format as a String (ex: 10:00 PM)
     * @param endTime           the ending time of the Event in HH:MM AM/PM format as a String
     * @param location          the location of the Event
     * @param photoID           int representing the photo for the Event
     * @param organizations     the names of the Organizations hosting the Event, delimited by semicolons(;)
     * @param categories        the names of the Categories for the Event, delimited by semicolons(;)
     * @param description       the long description for the Event
     */
    public Event(String name, String date, String startTime, String endTime, String location,
                 int photoID, String organizations, String categories, String description) {
        this.name = name;
        setStartCalendarDate(date, startTime);
        setEndCalendarDate(date, startTime, endTime);
        this.location = location;
        this.photoID = photoID;
        parseOrganizations(organizations);
        parseCategories(categories);
        this.description = description;

        // add to static list of all Events
        Event.allEvents.add(this);
    }

    /**
     * Constructor for an Event object following Parcelable. This is used to pass
     * instances of Events between activities on the app.
     *
     * @param in        Parcel object
     */
    public Event(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        Event temp = Event.getEventWithNameAndDateAndTime(data[0],data[1]);
        this.name = temp.getName();
        this.start = temp.getStartCalendar();
        this.end = temp.getEndCalendar();
        this.location = temp.getLocation();
        this.photoID = temp.getPhotoID();
        this.organizations = temp.getOrganizations();
        this.categories = temp.getCategories();
        this.description = temp.getDescription();
    }

    /**
     * TODO: I don't know what this method is for. Give it a description.
     * It is meant as part of the Parcelable part.
     *
     * @return      int describing the contents
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Prepares the Parcel to have the correct information so that a specific
     * Event can be attained with that information.
     *
     * @param dest      Parcel destination
     * @param flags     int flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.getName(), this.getDateAndTime()});
    }

    /**
     * TODO: I have no idea what this does. Needs a description.
     * It is part of the Parcelable part.
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    /**
     * Returns the Event object as a String. Used only for debugging purposes.
     * @return      the Event object as a String
     */
    @Override
    public String toString() {
        // List of Organizations as a String
        String organizations = "";
        for (Organization org: this.organizations) {
            organizations += org.getName() + ", ";
        }
        organizations = organizations.substring(0,organizations.length()-2);

        return "Event Name: " + this.name + "\nStart: " + this.start.toString() + "\nEnd: " + this.end.toString()
                + "\nLocation: " + this.location + "\nOrganizations: " + organizations + "\nPhoto: " + this.photoID;
    }

    /**
     * Sets the "start" attribute of the Event object to hold the correct date and time.
     *
     * @param date          the date in MM/DD/YYYY format as a String
     * @param startTime     the time in HH:MM AM/PM format as a String
     */
    public void setStartCalendarDate(String date, String startTime) {
        // get month, day, and year values
        int month = Integer.parseInt(date.substring(0,date.indexOf("/")));
        date = date.substring(date.indexOf("/")+1);
        int day = Integer.parseInt(date.substring(0,date.indexOf("/")));
        date = date.substring(date.indexOf("/")+1);
        int year = Integer.parseInt(date);

        // get hour and minute values
        int hour = Integer.parseInt(startTime.substring(0,startTime.indexOf(":")));
        // special case for 12 PM and 12 AM
        if (startTime.contains("PM") && hour < 12) {
            hour += 12;
        }
        if (startTime.contains("AM") && hour == 12) {
            hour = 0;
        }
        int minute = Integer.parseInt(startTime.substring(startTime.indexOf(":")+1, startTime.indexOf(" ")));

        // set the starting date and time of the Event
        this.start = Calendar.getInstance(TimeZone.getDefault());
        this.start.set(year, month-1, day, hour, minute);
    }

    /**
     * Sets the "end" attribute of the Event object to hold the correct date and time.
     *
     * @param date          the date in MM/DD/YYYY format as a String
     * @param startTime     the starting time in HH:MM AM/PM format as a String
     * @param endTime       the ending time in HH:MM AM/PM format as a String
     */
    public void setEndCalendarDate(String date, String startTime, String endTime) {
        // get month, day, and year values
        int month = Integer.parseInt(date.substring(0,date.indexOf("/")));
        date = date.substring(date.indexOf("/")+1);
        int day = Integer.parseInt(date.substring(0,date.indexOf("/")));
        date = date.substring(date.indexOf("/")+1);
        int year = Integer.parseInt(date);

        // get hour and minute values
        int hour = Integer.parseInt(endTime.substring(0,endTime.indexOf(":")));
        // special case for 12 PM and 12 AM
        if (endTime.contains("PM") && hour < 12) {
            hour += 12;
        }
        if (endTime.contains("AM") && hour == 12) {
            hour = 0;
        }
        int minute = Integer.parseInt(endTime.substring(endTime.indexOf(":")+1, endTime.indexOf(" ")));

        // set the ending date and time of the Event
        this.end = Calendar.getInstance(TimeZone.getDefault());
        this.end.set(year, month-1, day, hour, minute);
        // adjust if the event goes past midnight
        if (endTime.contains("AM") && startTime.contains("PM")) {
            this.end.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /**
     * Parses through the string of Organization names, separated by semicolons(;),
     * and sets the "organizations" attribute of the Event to reference all
     * participating Organizations. Updates the Organization object to refer
     * this Event in its list of Events.
     *
     * @param organizations     String of semicolon delimited Organization names
     */
    public void parseOrganizations(String organizations) {
        //System.out.println("Event: parseOrganizations(): organizations: " + organizations);
        this.organizations = new ArrayList<Organization>();
        while (organizations.contains(";")) {
            // add Organization to List of Organizations
            String orgName = organizations.substring(0,organizations.indexOf(";"));
            this.organizations.add(Organization.getOrganizationWithName(orgName));
            organizations = organizations.substring(organizations.indexOf(";")+1);

            // update Organization's static List of Events
            Organization.getOrganizationWithName(orgName).addEvent(this);
        }
        // add last one
        this.organizations.add(Organization.getOrganizationWithName(organizations));
        Organization.getOrganizationWithName(organizations).addEvent(this);
    }

    /**
     * Parses through the string of Category names, separated by semicolons(;).
     * and sets the "categories" attribute of the Event to refer to all Categories.
     * Updates the Category object to refer this Event in its list of Events
     *
     * @param categories        String of semicolon delimited Category names
     */
    public void parseCategories(String categories) {
        //System.out.println("Event: parseCategories(): categories: " + categories);
        this.categories = new ArrayList<Category>();
        while (categories.contains(";")) {
            // add Category to List of Categories
            String catName = categories.substring(0,categories.indexOf(";"));
            this.categories.add(Category.getCategoryWithName(catName));
            categories = categories.substring(categories.indexOf(";")+1);

            // update Category's static List of Events
            Category.getCategoryWithName(catName).addEvent(this);
        }
        // add last one
        this.categories.add(Category.getCategoryWithName(categories));
        Category.getCategoryWithName(categories).addEvent(this);
    }

    /**
     * Returns the name of the Event object.
     *
     * @return      the name attribute
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the ID of the Event image.
     *
     * @return      the ID attribute
     */
    public int getPhotoID() {
        return this.photoID;
    }

    /**
     * Returns the starting Calendar date of the Event object.
     *
     * @return      the starting Calendar date
     */
    public Calendar getStartCalendar() {
        return this.start;
    }

    /**
     * Returns the ending Calendar date of the Event object.
     *
     * @return      the ending Calendar date
     */
    public Calendar getEndCalendar() {
        return this.end;
    }

    /**
     * Returns the list of Organizations for the Event object.
     *
     * @return      the list of Organizations
     */
    public List<Organization> getOrganizations() {
        return this.organizations;
    }

    /**
     * Returns the location of the Event object.
     *
     * @return      the location String
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Returns the description of the Event object.
     *
     * @return      the description String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the list of Categories for the Event object.
     *
     * @return      the list of Categories
     */
    public List<Category> getCategories() {
        return this.categories;
    }

    /**
     * Returns the day and date of the Event as "DAY MM/DD".
     *
     * @return      String expressing the day and date of the Event
     */
    public String getDate() {
        Calendar calendar = this.getStartCalendar();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        return days[day] + " " + (month+1) + "/" + date;
    }

    /**
     * Returns the starting time of the Event as "HH:MM AM/PM"
     *
     * @return      String expressing the starting time of the Event
     */
    public String getStartTime() {
        Calendar calendar = this.getStartCalendar();
        int hour = calendar.get(Calendar.HOUR);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String extension = "PM";

        if (hour == hourOfDay && hour < 12) {
            extension = "AM";
        }
        // special case for 12AM and 12PM
        if ((hour == 0 && hourOfDay == 12) || (hour == 0 && hourOfDay == 0)) {
            hour = 12;
        }
        if (minute != 0) {
            extension = ":" + minute + extension;
        }
        return hour + extension;
    }

    /**
     * Returns the ending time of the Event as "HH:MM AM/PM"
     *
     * @return      String expressing the ending time of the Event
     */
    public String getEndTime() {
        Calendar calendar = this.getEndCalendar();
        int hour = calendar.get(Calendar.HOUR);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String extension = "PM";

        if (hour == hourOfDay && hour < 12) {
            extension = "AM";
        }
        // special case for 12AM and 12PM
        if ((hour == 0 && hourOfDay == 12) || (hour == 0 && hourOfDay == 0)) {
            hour = 12;
        }
        if (minute != 0) {
            extension = ":" + minute + extension;
        }
        return hour + extension;
    }

    /**
     * Returns the day, date, and starting time of the Event as "DAY MM/DD HH:MM AM/PM"
     *
     * @return      String expressing the day, date, and starting time of the Event
     */
    public String getDateAndTime() {
        return getDate() + " " + getStartTime();
    }

    /**
     * Returns the Event object with the specified name, date, and starting time.
     * This is a static method.
     *
     * @param name              name of the Event
     * @param dateAndTime       String result of getDateAndTime() method
     * @return                  the Event object
     */
    @Nullable
    public static Event getEventWithNameAndDateAndTime(String name, String dateAndTime) {
        for (Event event: allEvents) {
            if (event.getDateAndTime().equals(dateAndTime)) {
                return event;
            }
        }

        // Event not found so return null. Bad if this happens
        System.out.println("Event: getEventWithNameAndDateAndTime(): Did not find an Event. This should never print.");
        return null;
    }
}



