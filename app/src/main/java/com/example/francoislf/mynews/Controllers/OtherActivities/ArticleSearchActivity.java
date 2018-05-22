package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.francoislf.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleSearchActivity extends AppCompatActivity {

    @BindView(R.id.activity_article_search_toolbar) Toolbar mToolbar;
    ArticleSearchFragment mArticleSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_search);

        ButterKnife.bind(this);

        configutrToolBar();
        configurefragment();
    }

    // Configure Fragment into activity
    private void configurefragment(){
        mArticleSearchFragment = (ArticleSearchFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_articles_search);
        if (mArticleSearchFragment == null){
            mArticleSearchFragment = new ArticleSearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_articles_search, mArticleSearchFragment)
                    .commit();
        }
    }

    // Configure ToolBar
    private void configutrToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
