package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Tab Fragment for the "All" tab. If there is nothing to display,
 * then the text "No Events" is displayed. This should happen only
 * when there are no events. There should always be Categories and
 * Organizations to display.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class TabFragmentAll extends Fragment{

    /**
     * Sets the TextView in the Tab Fragment layout to have the correct text value
     * if there is no Event, Category, or Organization to display in the list.
     * Else, the TextView is empty.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // get the Tab Fragment layout
        View view = inflater.inflate(R.layout.tab_fragment, container, false);

        // get the TextView
        TextView all = (TextView) view.findViewById(R.id.nothing_to_display);
        int allEvents = getArguments().getInt("All Events", 0);
        int allOrganizations = getArguments().getInt("All Organizations", 0);
        int allCategories = getArguments().getInt("All Categories", 0);

        // if nothing to display, then set the TextView text
        if (allCategories + allEvents + allOrganizations == 0) {
            all.setText(R.string.no_events);
        } else {
            all.setText("");
        }
        return view;
    }
}
