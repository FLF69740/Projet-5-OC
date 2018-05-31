package com.example.francoislf.mynews.Controllers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mListTitle;

    public PageAdapter(FragmentManager fm, ArrayList<String> listTitle) {
        super(fm);
        this.mListTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return (PageFragment.newInstance(this.mListTitle.get(position)));
    }

    @Override
    public int getCount() {
        return mListTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mListTitle.get(position);
    }
}
