package edu.bucknell.binvolved;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by gilbertkim on 4/15/16.
 */
public class TabFragmentFollow extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_follow, container, false);

        TextView following = (TextView) view.findViewById(R.id.following);
        int followingEvents = getArguments().getInt("Following Events", 0);
        int followingOrganizations = getArguments().getInt("Following Organizations", 0);
        int followingCategories = getArguments().getInt("Following Categories", 0);

        if (followingCategories + followingEvents + followingOrganizations == 0) {
            following.setText(R.string.following_nothing);
        } else {
            following.setText("");
        }

        return view;

    }




}
