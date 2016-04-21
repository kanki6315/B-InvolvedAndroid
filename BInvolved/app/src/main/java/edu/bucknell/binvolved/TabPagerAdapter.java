package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Adapter for the Tab Pager. Creates the corresponding Tab Fragment for each Tab
 * and passes in the necessary information to determine if anything is being displayed.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    ArrayList<Event> followingEvents;
    ArrayList<Organization> followingOrganizations;
    ArrayList<Category> followingCategories;

    ArrayList<Event> allEvents;

    /**
     * Constructor for the TabPagerAdapter Class. Takes in a list of following Events,
     * following Organizations, following Categories, and all Events.
     *
     * @param fm
     * @param NumOfTabs
     * @param followingEvents
     * @param followingOrganizations
     * @param followingCategories
     * @param allEvents
     */
    public TabPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<Event> followingEvents,
                           ArrayList<Organization> followingOrganizations, ArrayList<Category> followingCategories, ArrayList<Event> allEvents) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.followingEvents = followingEvents;
        this.followingOrganizations = followingOrganizations;
        this.followingCategories = followingCategories;
        this.allEvents = allEvents;
    }

    /**
     * Passes in the ArrayLists of the necessary data to the Fragment.
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // send the list of following Events, Organizations, and Categories
                // to the "Following" Tab Fragment
                Bundle args = new Bundle();
                args.putInt("Following Events", followingEvents.size());
                args.putInt("Following Organizations", followingOrganizations.size());
                args.putInt("Following Categories", followingCategories.size());
                TabFragmentFollow tabFollow = new TabFragmentFollow();
                tabFollow.setArguments(args);
                return tabFollow;
            case 1:
                // send the list of all Events to the "All" Tab Fragment
                Bundle args2 = new Bundle();
                args2.putInt("All Events", allEvents.size());
                TabFragmentAll tabAll = new TabFragmentAll();
                tabAll.setArguments(args2);
                return tabAll;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}