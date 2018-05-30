package com.example.francoislf.mynews.Controllers.OtherActivities;

import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ArticleSearchActivity extends AbstractActivity {

    ArticleSearchFragment mArticleSearchFragment;

    // Configure Fragment into activity
    protected void configureFragment(){
        mArticleSearchFragment = (ArticleSearchFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_articles_search);
        if (mArticleSearchFragment == null){
            mArticleSearchFragment = new ArticleSearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_articles_search, mArticleSearchFragment)
                    .commit();
        }
    }

    @Override
    protected String configureToolBarTitle() {
        return "Search Articles";
    }

    @Override
    public void onResume() {
        super.onResume();

        Gson gson = new Gson();
        String json = getIntent().getStringExtra(SHARED_DEFAULT_SEARCH);
        Type type = new TypeToken<SearchPreferences>() {}.getType();
        mSearchPreferences = gson.fromJson(json, type);

        if (mSearchPreferences == null) mSearchPreferences = new SearchPreferences();

        mArticleSearchFragment.updateFragmentData(mSearchPreferences);
    }


}
