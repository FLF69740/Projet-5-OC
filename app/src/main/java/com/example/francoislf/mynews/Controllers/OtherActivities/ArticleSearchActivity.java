package com.example.francoislf.mynews.Controllers.OtherActivities;

import com.example.francoislf.mynews.R;

public class ArticleSearchActivity extends AbstractActivity {

    ArticleSearchFragment mArticleSearchFragment;

    // Configure Fragment into activity
    protected void configurefragment(){
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


}
