package edu.bucknell.binvolved;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;


/**
 * Created by gilbertkim on 3/31/16.
 */
public class IndividualCategoryActivity extends Activity {

    Button button;
    ImageView image;


    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_category);


        // do stuff for recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        //recyclerView.setAdapter(new BasicListAdapter(this));





        addListenerOnButton();
    }

    public void addListenerOnButton() {
        image = (ImageView) findViewById(R.id.cat_banner);
        button = (Button) findViewById(R.id.follow_update);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                System.out.print("got here");
            }
        });
    }
}
