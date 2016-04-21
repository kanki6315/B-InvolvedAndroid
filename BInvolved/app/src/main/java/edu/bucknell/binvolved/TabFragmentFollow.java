package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Tab Fragment for the "Following" tab. If there is nothing to display,
 * then the text "Following Nothing" is displayed to indicate to the user
 * that he/she is not following any Events, Categories, or Organizations.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class TabFragmentFollow extends Fragment{

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
        TextView following = (TextView) view.findViewById(R.id.nothing_to_display);
        int followingEvents = getArguments().getInt("Following Events", 0);
        int followingOrganizations = getArguments().getInt("Following Organizations", 0);
        int followingCategories = getArguments().getInt("Following Categories", 0);

        // if nothing to display, then set the TextView text
        if (followingCategories + followingEvents + followingOrganizations == 0) {
            following.setText(R.string.following_nothing);
        } else {
            following.setText("");
        }
        return view;
    }
}
