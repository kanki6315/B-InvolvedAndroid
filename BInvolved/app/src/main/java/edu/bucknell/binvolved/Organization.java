package edu.bucknell.binvolved;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for an Organization, which creates Events.
 *
 * Created by gilbertkim on 4/4/16.
 */
public class Organization {

    // Name of the Organization
    String name;
    // Array of ints for the photos for the Organization; has set length of 4
    int[] images;
    // List of Event objects by the Organization
    List<Event> events;
    // Description of the Organization
    String description;

    // static list of all Organization objects created
    public static List<Organization> allOrganizations = new ArrayList<Organization>();
    // static list of all Organization names used
    public static List<String> allOrganizationNames = new ArrayList<String>();

    /**
     * Constructor for an Organization object.
     *
     * @param name          the name of the Organization
     * @param logoPhotoID   int representing the photo for the Organization logo
     * @param photo1ID      int representing one extra photo for the Organization
     * @param photo2ID      int representing another extra photo
     * @param photo3ID      int representing a third extra photo
     * @param description   String describing the Organization
     */
    public Organization(String name, int logoPhotoID, int photo1ID, int photo2ID, int photo3ID, String description) {
        // first check to see if the Organization name already exists
        if (allOrganizationNames.contains(name)) {
            // got a duplicate Organization name
            //this = allOrganizations.get(allOrganizationNames.indexOf(name));
            return;
        }

        // new Organization name
        this.name = name;
        this.images = new int[4];
        this.images[0] = logoPhotoID;
        this.images[1] = photo1ID;
        this.images[2] = photo2ID;
        this.images[3] = photo3ID;
        this.events = new ArrayList<Event>();
        this.description = description;

        // add to static List of Organizations
        allOrganizations.add(this);
        allOrganizationNames.add(name);
    }

    /**
     * Returns the Organization object as a String. Used only for debugging purposes.
     * @return      the Organization object as a String
     */
    @Override
    public String toString() {
        // List of Events as a String
        String events = "";
        for (Event event : this.events) {
            events += event.getName() + ", ";
        }
        if (events.length() > 0) {
            events = events.substring(0, events.length() - 2);
        }

        return "Organization Name: " + this.name + "\nLogoPhotoID: " + this.images[0]
                + "\nOtherPhotoIDs: " + this.images[1] + ", " + this.images[2] + ", " + this.images[3]
                + "\nEvents: " + events;
    }

    /**
     * Adds the Event object to the Organization's list of Events.
     *
     * @param event     the Event object to add
     */
    public void addEvent(Event event) {
        this.events.add(event);
    }

    /**
     * Returns the list of Events for the Organization.
     *
     * @return          the list of Events
     */
    public List<Event> getEvents() {
        return this.events;
    }

    /**
     * Returns the name of the Organization.
     *
     * @return          the name of the Organization
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a list of image ID numbers.
     *
     * @return          list of image ID numbers
     */
    public int[] getImages() {
        return this.images;
    }

    /**
     * Returns the description of the Organization.
     *
     * @return          the description of the Organization
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the Organization object with the specified name.
     * If the Organization name is not found, then creates a new one
     * with the images set to a default image.
     *
     * @param orgName       the Organization name to check for.
     * @return              the Organization object
     */
    public static Organization getOrganizationWithName(String orgName) {
        // if find Organization, return it
        if (allOrganizationNames.contains(orgName)) {
            return allOrganizations.get(allOrganizationNames.indexOf(orgName));
        }

        // if no Organization found, create a new one
        Organization newOrg = new Organization(orgName, 0, 0, 0, 0, "Description");
        allOrganizations.add(newOrg);
        allOrganizationNames.add(orgName);
        return newOrg;
    }
}
