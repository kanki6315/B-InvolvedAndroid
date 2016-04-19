package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by gilbertkim on 4/15/16.
 */
public class TabFragmentAll extends Fragment{

    /*
    private List<Event> allEvents;
    private RecyclerView rv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        allEvents = getArguments().getParcelableArrayList("All Events");
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_all, container, false);
    }

}
