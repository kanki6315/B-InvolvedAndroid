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
    ArrayList<Event> allEvents;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<Event> followingEvents, ArrayList<Event> allEvents) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.followingEvents = followingEvents;
        this.allEvents = allEvents;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Bundle args = new Bundle();
                args.putParcelableArrayList("Following Events", followingEvents);
                TabFragmentFollow tabFollow = new TabFragmentFollow();
                tabFollow.setArguments(args);
                System.out.println("TabPagerAdapter: tabFollow tapped");
                return tabFollow;
            case 1:
                Bundle args2 = new Bundle();
                args2.putParcelableArrayList("All Events", allEvents);
                TabFragmentAll tabAll = new TabFragmentAll();
                tabAll.setArguments(args2);
                System.out.println("TabPagerAdapter: tabAll tapped");
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