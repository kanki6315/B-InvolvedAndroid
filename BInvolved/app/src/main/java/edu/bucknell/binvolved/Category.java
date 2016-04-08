package edu.bucknell.binvolved;

import java.util.List;
import java.util.ArrayList;

/**
 * Class for a Category, which is a way to group similar types of Events.
 *
 * Created by gilbertkim on 4/4/16.
 */
public class Category {

    // Name of the Category
    String name;
    // int for the small photo used in the CardView for the Category
    int smallPhotoID;
    // int for the banner photo used in the Category page
    int bannerPhotoID;
    // List of Events under the Category
    List<Event> events;

    // static List to keep track of all Categories
    public static List<Category> allCategories = new ArrayList<Category>();
    // static List to keep track of all Category names
    public static List<String> allCategoryNames = new ArrayList<String>();

    /**
     * Constructor for a Category object.
     * @param name              the name of the Category
     * @param smallPhotoID      the small photo
     * @param bannerPhotoID     the banner photo
     */
    public Category(String name, int smallPhotoID, int bannerPhotoID) {
        this.name = name;
        this.smallPhotoID = smallPhotoID;
        this.bannerPhotoID = bannerPhotoID;
        this.events = new ArrayList<Event>();
    }

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
        events = events.substring(0, events.length() - 2);

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
     * @return          the list of Events
     */
    public List<Event> getEvents() {
        return this.events;
    }

    /**
     * Returns the Category object with the specified name.
     * If no such Category exists, then one is created with the images set to a default set.
     *
     * @param catName       the name of the Category to check for
     * @return              the Category object
     */
    public static Category getCategoryWithName(String catName) {
        // if find Category, return it
        if (allCategoryNames.contains(catName)) {
            return allCategories.get(allCategoryNames.indexOf(catName));
        }

        // if no Category found, create a new one
        Category newCat = new Category(catName, 0, 0);
        allCategories.add(newCat);
        allCategoryNames.add(catName);
        return newCat;
    }
}
