package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ArticleSearchActivity extends AbstractActivity implements ArticleSearchFragment.OnButtonClickedListener {

    ArticleSearchFragment mArticleSearchFragment;

    public static final String BUNDLE_EXTRA_SEARCH_ARTICLE = "BUNDLE_EXTRA_SEARCH_ARTICLE";

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

    // Set the title of the toolbar with the name of the loaded fragment
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

    // put the new json to the Main Activity
    @Override
    public void onButtonClicked(View view, String json) {
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_EXTRA_SEARCH_ARTICLE, json);
        setResult(RESULT_OK, intent);
        finish();
    }
}
