package com.example.francoislf.mynews;

import com.example.francoislf.mynews.Models.DateFormatTransformer;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnitTestDateFormatTransformer {

    @Test
    public void testIfClassContructorAndSetter(){
        DateFormatTransformer mDateFormatTransformer = new DateFormatTransformer("01/01/2018");
        assertThat("Verification of not-null constructor", !mDateFormatTransformer.getDateInput().isEmpty());
    }

    @Test
    public void testInputDateFormat(){
        DateFormatTransformer mDateFormatTransformer = new DateFormatTransformer("01/01/2018");
        char[] date = mDateFormatTransformer.getDateInput().toCharArray();

        assertThat("FirstSlashPosition", date[2] == '/');
        assertThat("SecondSlashPosition", date[5] == '/');
    }

    @Test
    public void testGetterDay() {
        DateFormatTransformer mDateFormatTransformer = new DateFormatTransformer("01/02/2018");
        assertThat("Verification of the value of the String Day", mDateFormatTransformer.getDay().equals("01"));
    }

    @Test
    public void testGetterMonth() {
        DateFormatTransformer mDateFormatTransformer = new DateFormatTransformer("01/02/2018");
        assertThat("Verification of the value of the String Month", mDateFormatTransformer.getMonth().equals("02"));
    }

    @Test
    public void testGetterYear() {
        DateFormatTransformer mDateFormatTransformer = new DateFormatTransformer("01/02/2018");
        assertThat("Verification of the value of the String Month", mDateFormatTransformer.getYear().equals("2018"));
    }

    @Test
    public void testDateConversion(){
        DateFormatTransformer mDateFormatTransformer = new DateFormatTransformer("01/02/2018");
        assertThat("Verification of the conversion of String date", mDateFormatTransformer.getDateStringOutput().equals("20180201"));
    }
}
