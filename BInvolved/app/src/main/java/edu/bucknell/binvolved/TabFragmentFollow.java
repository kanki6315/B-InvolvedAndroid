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
//public class TabFragmentFollow extends AppCompatActivity{

    /*
    private List<Event> followingEvents;
    private RecyclerView rv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        followingEvents = getArguments().getParcelableArrayList("Following Events");
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab_fragment_follow, container, false);

    }




}
