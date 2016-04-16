package edu.bucknell.binvolved;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Layout for the list view of Categories.
 *
 * Created by gilbertkim on 4/15/16.
 */
public class ListCategoryActivity extends Activity {

    // parts for the list item view
    ImageView categoryImage;
    TextView categoryName;

    // parts for recycler view 1: This Week
    private List<Category> categories;
    private RecyclerView rv1;

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // get elements for card view
        categoryImage = (ImageView) findViewById(R.id.event_photo);
        categoryName = (TextView) findViewById(R.id.event_name);

        // get elements for recycler views
        rv1 =(RecyclerView)findViewById(R.id.recycler_view_1);

        // initialize stuff
        setLayoutManagers();

        categories = getIntent().getParcelableArrayListExtra("Category List");

        initializeAdapters();

    }

    /**
     * Sets the layout managers for the recycler views.
     */
    private void setLayoutManagers() {
        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv1.setLayoutManager(llm1);
        rv1.setHasFixedSize(true);
    }


    /**
     * Initializes the adapter for the RecyclerView.
     */
    private void initializeAdapters() {
        ListViewCategoryAdapter adapter1 = new ListViewCategoryAdapter(context, categories);
        rv1.setAdapter(adapter1);
    }
}
