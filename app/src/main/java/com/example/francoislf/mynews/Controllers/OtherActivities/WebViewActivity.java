package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.example.francoislf.mynews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    WebViewFragment mWebViewFragment;
    public static final String LINK_WEBVIEW = "LINK_WEBVIEW";

    @BindView(R.id.activity_webview_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ButterKnife.bind(this);

        this.configureToolBar();
        this.configureFragment();
    }

    // Configure SearchResultFragment into this Activity
    protected void configureFragment() {
        mWebViewFragment = (WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_webview);

        if (mWebViewFragment == null){
            mWebViewFragment = new WebViewFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_webview, mWebViewFragment)
                    .commit();
        }
    }

    // Configure the Toolbar
    protected void configureToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("WEB VIEW ARTICLE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String url = getIntent().getStringExtra(LINK_WEBVIEW);
        mWebViewFragment.configureUrlWebView(url);
    }
}
