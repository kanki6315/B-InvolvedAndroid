package edu.bucknell.binvolved;

import java.util.List;
import java.util.ArrayList;
import android.os.Parcelable;
import android.os.Parcel;
import android.support.annotation.Nullable;

/**
 * Class for a Category, which is a way to group similar types of Events.
 * The Category class implements Parcelable so that I can pass lists of Category objects
 * between activities.
 *
 * A Category object has:
 *      name: String
 *      photo ID for the small icon image: int
 *      photo ID for the large banner image: int
 *      list of Events: List<Event>
 *
 *
 * Created by gilbertkim on 4/4/16.
 */
public class Category implements Parcelable{

    // static List to keep track of all Categories
    public static List<Category> allCategories = new ArrayList<Category>();
    // static List to keep track of all Category names
    public static List<String> allCategoryNames = new ArrayList<String>();

    // Name of the Category
    String name;
    // int for the small photo used in the CardView for the Category
    int smallPhotoID;
    // int for the banner photo used in the individual Category page
    int bannerPhotoID;
    // List of Events under the Category
    List<Event> events;


    /**
     * Constructor for a Category object.
     *
     * @param name              the name of the Category
     * @param smallPhotoID      the small photo
     * @param bannerPhotoID     the banner photo
     */
    public Category(String name, int smallPhotoID, int bannerPhotoID) {
        this.name = name;
        this.smallPhotoID = smallPhotoID;
        this.bannerPhotoID = bannerPhotoID;
        this.events = new ArrayList<Event>();

        // add to static list of all Categories
        Category.allCategories.add(this);
        Category.allCategoryNames.add(this.name);
    }

    /**
     * Constructor for a Category object following Parcelable. This is used to pass
     * instances of Category between activities on the app.
     *
     * @param in        Parcel object
     */
    public Category(Parcel in) {
        String[] data = new String[1];

        in.readStringArray(data);
        Category temp = Category.getCategoryWithName(data[0]);
        this.name = temp.getName();
        this.smallPhotoID = temp.getSmallPhotoID();
        this.bannerPhotoID = temp.getBannerPhotoID();
        this.events = temp.getEvents();
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
     * Category can be attained with that information.
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
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    /**
     * Returns the Category object as a String. Used only for debugging purposes.
     * @return      the Category object as a String
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

        return "Category Name: " + this.name + "\nSmallPhotoID: " + this.smallPhotoID
                + "\nBannerPhotoID: " + this.bannerPhotoID + "\nEvents: " + events;
    }

    /**
     * Adds the Event object to the Category's list of Events.
     *
     * @param event     the Event object to add
     */
    public void addEvent(Event event) {
        this.events.add(event);
    }

    /**
     * Returns the list of Events for the Category.
     *
     * @return          the list of Events
     */
    public List<Event> getEvents() {
        return this.events;
    }

    /**
     * Returns the name of the Category.
     *
     * @return          the name of the Category
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the ID of the banner image of the Category.
     *
     * @return          the ID of the banner image
     */
    public int getBannerPhotoID() {
        return this.bannerPhotoID;
    }

    /**
     * Returns the ID of the small image for the Category.
     *
     * @return          the ID of the small image
     */
    public int getSmallPhotoID() {
        return this.smallPhotoID;
    }

    /**
     * Returns the Category object with the specified name.
     * This is a static method.
     *
     * @param catName       the name of the Category to check for
     * @return              the Category object
     */
    @Nullable
    public static Category getCategoryWithName(String catName) {
        // if find Category, return it
        if (allCategoryNames.contains(catName)) {
            return allCategories.get(allCategoryNames.indexOf(catName));
        }

        System.out.println("Category: getCategoryWithName(): Did not find a Category. Ideally, this should never print.");
        System.out.println("Category: getCategoryWithName(): catName: " + catName);
        // if no Category found, create a new one
        Category newCat = new Category(catName, 0, 0);
        allCategories.add(newCat);
        allCategoryNames.add(catName);
        return newCat;
    }
}
