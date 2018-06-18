package com.example.francoislf.mynews.Models;

public class RecyclerSectionFormat {

    private String mSection, mSubSection, mDate, mBody, mWebImage, mWebUrl;

    public RecyclerSectionFormat(String section, String subSection, String date, String body, String webImage, String webUrl){
        this.mSection = section;
        this.mSubSection = subSection;
        this.mDate = date;
        this.mBody = body;
        this.mWebImage = webImage;
        this.mWebUrl = webUrl;
    }

    public String getSectionFormat(){
        if (mSubSection.isEmpty()) return mSection;
        else return mSection + " > " + mSubSection;
    }

    public String getRecyclerDate(){
        char[] chars = mDate.toCharArray();
        String result = "";
        result += chars[8];
        result += chars[9] + "/";
        result += chars[5];
        result += chars[6] + "/";
        result += chars[2];
        result += chars[3];
        return result;
    }

    public String getBody(){
        if (mBody.toCharArray().length < 60) return mBody;
        else {
            String result = "";
            char[] chars = mBody.toCharArray();
            for (int i = 0 ; i < 60 ; i++) result += chars[i];
            result += "...";
            return result;
        }
    }

    public String getWebImage(){
        return mWebImage;
    }

    public String getWebUrl() {
        return mWebUrl;
    }
}
