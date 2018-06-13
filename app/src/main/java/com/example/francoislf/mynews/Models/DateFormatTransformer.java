package com.example.francoislf.mynews.Models;

public class DateFormatTransformer {

    private String mDateInput, day, month, year;

    public DateFormatTransformer(String date){
        this.mDateInput = date;
        char[] charDate = date.toCharArray();

        day = "" + charDate[0] + charDate[1];
        month = "" + charDate[3] + charDate[4];
        year = "" + charDate[6] + charDate[7] + charDate[8] + charDate[9];
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getDateInput() {
        return mDateInput;
    }

    public String getDateStringOutput(){
        return (year + month + day);
    }

}
