package edu.bucknell.binvolved.categories;

import android.app.ActionBar;

/**
 * Created by Alex on 4/7/2016.
 */
public class TabListener implements ActionBar.TabListener {
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction transaction) {
        switch (tab.getPosition()) {
            case 0:
                if (firstFragment == null) {
                    firstFragment = new FirstFragment();
                    transaction.add(R.id.fragment_container,
                            firstFragment, "FIRST");

                } else {
                    transaction.attach(firstFragment);
                }
                break;
            case 1:
                if (secondFragment == null) {
                    secondFragment = new SecondFragment();
                    transaction.add(R.id.fragment_container, secondFragment, "SECOND");
                } else {
                    transaction.attach(secondFragment);
                }
                break;
        }
    }

    };

}
