package edu.bucknell.binvolved;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Class for an Event, which are activities created by Organizations.
 *
 * Created by gilbertkim on 4/4/16.
 */
public class Event {

    // Name of the Event
    String name;
    // Start date and time of the Event
    Calendar start;
    // End date and time of the Event
    Calendar end;
    // Location of the Event
    String location;
    // Photo for the Event
    int photoID;
    // List of Organizations that are hosting the Event
    List<Organization> organizations;
    // List of Categories for the Event
    List<Category> categories;
    // Long description of the Event
    String description;

    /**
     * Constructor for an Event object. If the Event ends in an AM time and starts in a PM time,
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
    }

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
        if (startTime.contains("PM")) {
            hour += 12;
        }
        int minute = Integer.parseInt(startTime.substring(startTime.indexOf(":")+1, startTime.indexOf(" ")));

        // set the starting date and time of the Event
        this.start = Calendar.getInstance(TimeZone.getDefault());
        //this.start.clear();
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
        if (endTime.contains("PM")) {
            hour += 12;
        }
        int minute = Integer.parseInt(endTime.substring(endTime.indexOf(":")+1, endTime.indexOf(" ")));

        // set the ending date and time of the Event
        this.end = Calendar.getInstance(TimeZone.getDefault());
        //this.end.clear();
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
        this.organizations = new ArrayList<Organization>();
        while (organizations.contains(";")) {
            // add Organization to List of Organizations
            String orgName = organizations.substring(0,organizations.indexOf(";"));
            this.organizations.add(Organization.getOrganizationWithName(orgName));
            organizations = organizations.substring(organizations.indexOf(";")+1);

            // update Organization's List of events
            Organization.getOrganizationWithName(orgName).addEvent(this);
        }
        this.organizations.add(Organization.getOrganizationWithName(organizations));
    }

    /**
     * Parses through the string of Category names, separated by semicolons(;).
     * and sets the "categories" attribute of the Event to refer to all Categories.
     * Updates the Category object to refer this Event in its list of Events
     *
     * @param categories        String of semicolon delimited Category names
     */
    public void parseCategories(String categories) {
        this.categories = new ArrayList<Category>();
        while (categories.contains(";")) {
            // add Category to List of Categories
            String catName = categories.substring(0,categories.indexOf(";"));
            this.categories.add(Category.getCategoryWithName(catName));
            categories = categories.substring(categories.indexOf(";")+1);

            // update Category's List of events
            if (catName.equals("Free Food")) {
                System.out.println("Adding additional event to Category Free Food");
            }
            Category.getCategoryWithName(catName).addEvent(this);
        }
        this.categories.add(Category.getCategoryWithName(categories));
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
}



