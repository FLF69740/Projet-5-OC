package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractActivity extends AppCompatActivity {


    public static final String SHARED_DEFAULT_SEARCH = "SHARED_DEFAULT_SEARCH";
    protected SearchPreferences mSearchPreferences;

    protected abstract void configureFragment();
    protected abstract String configureToolBarTitle();

    @BindView(R.id.activity_article_search_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_search);

        ButterKnife.bind(this);

        configureFragment();
        configureToolBar();
    }

    // Configure the Toolbar
    protected void configureToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(configureToolBarTitle());
    }

}
