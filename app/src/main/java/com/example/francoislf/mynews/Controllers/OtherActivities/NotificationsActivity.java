package com.example.francoislf.mynews.Controllers.OtherActivities;

import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class NotificationsActivity extends AbstractActivity {

    NotificationsFragment mNotificationsFragment;

    @Override
    protected void configureFragment() {
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

    @Override
    public void onResume() {
        super.onResume();

        Gson gson = new Gson();
        String json = getIntent().getStringExtra(SHARED_DEFAULT_SEARCH);
        Type type = new TypeToken<SearchPreferences>() {}.getType();
        mSearchPreferences = gson.fromJson(json, type);

        if (mSearchPreferences == null) mSearchPreferences = new SearchPreferences();

        mNotificationsFragment.updateFragmentData(mSearchPreferences);
    }
}
