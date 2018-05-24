package com.example.francoislf.mynews.Controllers.OtherActivities;

import com.example.francoislf.mynews.R;

public class NotificationsActivity extends AbstractActivity {

    NotificationsFragment mNotificationsFragment;

    @Override
    protected void configurefragment() {
        mNotificationsFragment = (NotificationsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_articles_search);
        if (mNotificationsFragment == null){
            mNotificationsFragment = new NotificationsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_articles_search, mNotificationsFragment)
                    .commit();
        }
    }

    @Override
    protected String configureToolBarTitle() {
        return "Notifications";
    }
}
