package com.example.francoislf.mynews.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.francoislf.mynews.Controllers.Fragments.HelpFragment;
import com.example.francoislf.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity {

    private HelpFragment mHelpFragment;

    @BindView(R.id.activity_help_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        this.configureFragment();
        this.configureToolbar();
    }

    private void configureFragment(){
        mHelpFragment = (HelpFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_help);
        if (mHelpFragment == null){
            mHelpFragment = new HelpFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_help, mHelpFragment)
                    .commit();
        }
    }

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("HELP SECTION");
    }
}
