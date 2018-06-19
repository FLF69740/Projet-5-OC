package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity implements SearchResultFragment.OnButtonClickedListener {

    public static final String SHARED_DEFAULT_SEARCH = "SHARED_DEFAULT_SEARCH";
    private SearchPreferences mSearchPreferences;

    SearchResultFragment mSearchResultFragment;

    @BindView(R.id.activity_search_result_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ButterKnife.bind(this);

        configureFragment();
        configureToolBar();

    }

    // Configure SearchResultFragment into this Activity
    protected void configureFragment() {
        mSearchResultFragment = (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_search_result);

        if (mSearchResultFragment == null){
            mSearchResultFragment = new SearchResultFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_search_result, mSearchResultFragment)
                    .commit();
        }
    }

    // Configure the Toolbar
    protected void configureToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SEARCH RESULT");
    }

    @Override
    public void onResume() {
        super.onResume();

        Gson gson = new Gson();
        String json = getIntent().getStringExtra(SHARED_DEFAULT_SEARCH);
        Type type = new TypeToken<SearchPreferences>() {}.getType();
        mSearchPreferences = gson.fromJson(json, type);

        if (mSearchPreferences == null) mSearchPreferences = new SearchPreferences();

        mSearchResultFragment.updateFragmentData(mSearchPreferences);
    }


    @Override
    public void onButtonClicked(View view, String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.LINK_WEBVIEW, url);
        startActivity(intent);
    }
}
