package com.example.francoislf.mynews.Models;

import java.util.ArrayList;

public class SearchPreferences {

    private String mSearchString;
    private String mBeginDateString;
    private String mEndDateString;
    private ArrayList<String> mListCheckBoxString;

    public SearchPreferences(){
        mSearchString = "";
        mBeginDateString = "";
        mEndDateString = "";
        mListCheckBoxString = new ArrayList<>();
        mListCheckBoxString.add("Top Stories");
        mListCheckBoxString.add("Most Popular");
    }


    // GETTER AND SETTER

    public String getSearchString() {
        return mSearchString;
    }

    public void setSearchString(String searchString) {
        mSearchString = searchString;
    }

    public String getBeginDateString() {
        return mBeginDateString;
    }

    public void setBeginDateString(String beginDateString) {
        mBeginDateString = beginDateString;
    }

    public String getEndDateString() {
        return mEndDateString;
    }

    public void setEndDateString(String endDateString) {
        mEndDateString = endDateString;
    }

    public ArrayList<String> getListCheckBoxString() {
        return mListCheckBoxString;
    }

    public void setListCheckBox(ArrayList<String> listCheckBox) {
        mListCheckBoxString = listCheckBox;
    }

    public void addCheckBox(String checkBox){
        mListCheckBoxString.add(checkBox);
    }
}
