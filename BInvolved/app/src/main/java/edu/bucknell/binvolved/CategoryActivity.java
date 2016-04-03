package edu.bucknell.binvolved;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by gilbertkim on 3/31/16.
 */
public class CategoryActivity extends Activity {

    Button button;
    ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_home);

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
