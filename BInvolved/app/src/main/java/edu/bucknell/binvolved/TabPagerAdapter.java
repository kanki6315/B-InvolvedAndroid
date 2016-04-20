package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by gilbertkim on 4/15/16.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    ArrayList<Event> followingEvents;
    ArrayList<Organization> followingOrganizations;
    ArrayList<Category> followingCategories;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<Event> followingEvents,
                           ArrayList<Organization> followingOrganizations, ArrayList<Category> followingCategories) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.followingEvents = followingEvents;
        this.followingOrganizations = followingOrganizations;
        this.followingCategories = followingCategories;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Bundle args = new Bundle();
                args.putInt("Following Events", followingEvents.size());
                args.putInt("Following Organizations", followingOrganizations.size());
                args.putInt("Following Categories", followingCategories.size());
                TabFragmentFollow tabFollow = new TabFragmentFollow();
                tabFollow.setArguments(args);
                return tabFollow;
            case 1:
                Bundle args2 = new Bundle();
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