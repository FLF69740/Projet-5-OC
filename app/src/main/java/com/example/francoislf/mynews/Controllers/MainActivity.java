package com.example.francoislf.mynews.Controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.francoislf.mynews.Controllers.OtherActivities.ArticleSearchActivity;
import com.example.francoislf.mynews.Controllers.OtherActivities.NotificationsActivity;
import com.example.francoislf.mynews.Controllers.OtherActivities.SearchResultActivity;
import com.example.francoislf.mynews.Controllers.OtherActivities.WebViewActivity;
import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PageFragment.OnButtonClickedListener {

    @BindView(R.id.activity_main_toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_main_drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView mNavigationView;
    @BindView(R.id.activity_main_viewpager) ViewPager mViewPager;
    @BindView(R.id.activity_main_tabLayout) TabLayout mTabLayout;

    private SharedPreferences mJSonPreferences;
    public static final String SHARED_DEFAULT_SEARCH = "SHARED_DEFAULT_SEARCH";
    public static final int SEARCH_ARTICLE_REQUEST_CODE = 10;

    private SearchPreferences mSearchPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        load();

        // ToolBar Configuration
        setToolbarConfiguration();

        // Navigation Drawer Configuration
        configureDrawerLayout();
        mNavigationView.setNavigationItemSelectedListener(this);

        // ViewPager Configuration
        configureViewPager();

        mTabLayout.getTabAt(0).select();
    }

    /**
     *  IHM CONFIGURATION ----- NAVIGATION DRAWER -----
     */

    // Drawer Layout Configuration
    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#000000"));
        toggle.syncState();

        Menu m = mNavigationView.getMenu();
        MenuItem[] listMenuItem = new MenuItem[mSearchPreferences.getListCheckBoxString().size()];
        for (int i = 0 ; i < mSearchPreferences.getListCheckBoxString().size() ; i++)
            listMenuItem[i] = m.add(0,i,i,mSearchPreferences.getListCheckBoxString().get(i));

    }

    // Navigation item click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mTabLayout.getTabAt(item.getItemId()).select();
        this.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     *  IHM CONFIGURATION ----- VIEWPAGER -----
     */

    // Configure ViewPager
    private void configureViewPager(){
        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),mSearchPreferences.getListCheckBoxString()));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    /**
     *  IHM CONFIGURATION ----- TOOLBAR -----
     */

    // Configure ToolBar
    private void setToolbarConfiguration(){
        setSupportActionBar(mToolbar);
    }

    // Catch Item icon into ToolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    // Add actions to item icon from ToolBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_activity_parametres :
                launchNotifications();
                return true;
            case  R.id.menu_activity_search :
                launchSearchArticleActivity();
                return true;
            default : return super.onOptionsItemSelected(item);
        }
    }

    // Methods in order to launch new Activity : SearchArticleActivity
    private void launchSearchArticleActivity(){
        Intent intent = new Intent(this, ArticleSearchActivity.class);
        intent.putExtra(ArticleSearchActivity.SHARED_DEFAULT_SEARCH, mJSonPreferences.getString(SHARED_DEFAULT_SEARCH, null));
        this.startActivityForResult(intent, SEARCH_ARTICLE_REQUEST_CODE);
    }

    // Methods in order to launch new Activity : NotificationsActivity
    private void launchNotifications(){
        Intent intent = new Intent(this, NotificationsActivity.class);
        this.startActivity(intent);
    }

    /**
     *  DATA UPDATE
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (SEARCH_ARTICLE_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            String json = data.getStringExtra(ArticleSearchActivity.BUNDLE_EXTRA_SEARCH_ARTICLE);
            save(SHARED_DEFAULT_SEARCH, json);
            finish();
            startActivity(getIntent());
            load();
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra(ArticleSearchActivity.SHARED_DEFAULT_SEARCH, mJSonPreferences.getString(SHARED_DEFAULT_SEARCH, null));
            this.startActivity(intent);
        }
    }

    // Load JSon in order to create class object with Gson library
    private void load(){
        mJSonPreferences = getSharedPreferences(SHARED_DEFAULT_SEARCH, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mJSonPreferences.getString(SHARED_DEFAULT_SEARCH, null);
        Type type = new TypeToken<SearchPreferences>() {}.getType();
        mSearchPreferences = gson.fromJson(json, type);

        if (mSearchPreferences == null) mSearchPreferences = new SearchPreferences();
    }

    // Save current class object to SharedPreferences with Gson library (JSon format)
    private void save(String fileName, String json){
        mJSonPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        mJSonPreferences.edit().putString(fileName, json).apply();
    }

    @Override
    public void onButtonClicked(View view, String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.LINK_WEBVIEW, url);
        startActivity(intent);
    }
}