package edu.bucknell.binvolved;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcel;

/**
 * Class for an Organization, which creates Events.
 * The Event class implements Parcelable so that I can pass lists of Event objects
 * between activities.
 *
 * Created by gilbertkim on 4/4/16.
 */
public class Organization implements Parcelable {

    // Name of the Organization
    String name;
    // Array of ints for the photos for the Organization; has set length of 4
    // the first image is the logo; the next 3 are images of events
    int[] images;
    // List of Event objects by the Organization
    List<Event> events;
    // Description of the Organization
    String description;

    // static list of all Organization objects created
    public static List<Organization> allOrganizations = new ArrayList<Organization>();
    // static list of all Organization names used
    public static List<String> allOrganizationNames = new ArrayList<String>();
    // static list to keep track of following Organizations
    public static List<Organization> followingOrganizations = new ArrayList<Organization>();

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
            Organization temp = Organization.getOrganizationWithName(name);
            this.name = temp.getName();
            this.images = temp.getImages();
            this.events = temp.getEvents();
            this.description = temp.getDescription();
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
     * Constructor for an Organization object following Parcelable. This is used to pass
     * instances of Organizations between activities on the app.
     *
     * @param in        Parcel object
     */
    public Organization(Parcel in) {
        String[] data = new String[1];

        in.readStringArray(data);
        Organization temp = Organization.getOrganizationWithName(data[0]);
        this.name = temp.getName();
        this.images = temp.getImages();
        this.events = temp.getEvents();
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
     * Organization can be attained with that information.
     *
     * @param dest      Parcel destination
     * @param flags     int flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.getName()});
    }

    /**
     * TODO: I have no idea what this does. Needs a description.
     * It is part of the Parcelable part.
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Organization createFromParcel(Parcel in) {
            return new Organization(in);
        }
        public Organization[] newArray(int size) {
            return new Organization[size];
        }
    };

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

    /**
     * Returns an ArrayList of all Organization objects.
     * This is a static method.
     *
     * @return              the ArrayList of Organizations
     */
    public static ArrayList<Organization> getAllOrganizations() {
        ArrayList<Organization> allOrganizations = new ArrayList<Organization>();
        allOrganizations.addAll(Organization.allOrganizations);
        return Organization.sortOrganizationsAlphabetically(allOrganizations);
    }

    /**
     * Returns an ArrayList of all following Organization objects.
     * This is a static method.
     *
     * @return              the ArrayList of Organizations
     */
    public static ArrayList<Organization> getFollowingOrganizations() {
        ArrayList<Organization> followingOrganizations = new ArrayList<Organization>();
        followingOrganizations.addAll(Organization.followingOrganizations);
        return Organization.sortOrganizationsAlphabetically(followingOrganizations);
    }

    /**
     * Adds the Organization with the specified name to the list
     * of following Organizations
     *
     * @param name          the name of the Organization
     */
    public static void addToFollowingOrganizations(String name) {
        Organization.followingOrganizations.add(Organization.getOrganizationWithName(name));
    }

    /**
     * Removes the Organization with the specified name from the list
     * of following Organizations
     *
     * @param name          the name of the Organization
     */
    public static void removeFromFollowingOrganizations(String name) {
        Organization.followingOrganizations.remove(Organization.getOrganizationWithName(name));
    }

    /**
     * Sorts the ArrayList of Organizations to be in alphabetical order according to the name.
     *
     * @param organizations     the ArrayList of Organizations
     * @return                  the sorted ArrayList
     */
    public static ArrayList<Organization> sortOrganizationsAlphabetically(ArrayList<Organization> organizations) {
        for (int i = 0; i < organizations.size(); i++) {
            for (int j = 0; j < organizations.size(); j++) {
                if (organizations.get(i).getName().compareTo(organizations.get(j).getName()) < 0) {
                    Organization temp = organizations.get(j);
                    organizations.set(j, organizations.get(i));
                    organizations.set(i, temp);
                }
            }
        }
        return organizations;
    }
}
