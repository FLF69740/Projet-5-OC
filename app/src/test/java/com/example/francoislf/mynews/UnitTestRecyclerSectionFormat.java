package com.example.francoislf.mynews;

import com.example.francoislf.mynews.Models.RecyclerSectionFormat;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class UnitTestRecyclerSectionFormat {

    @Test
    public void testSectionWithSubsectionFormat(){
        RecyclerSectionFormat recyclerSectionFormat = new RecyclerSectionFormat("US","Politics","","","","");

        assertThat("Section and SubSection not Empty", recyclerSectionFormat.getSectionFormat().equals("US > Politics"));
    }

    @Test
    public void testSectionWithEmptySubsectionFormat(){
        RecyclerSectionFormat recyclerSectionFormat = new RecyclerSectionFormat("US","","","","","");

        assertThat("Section and SubSection not Empty", recyclerSectionFormat.getSectionFormat().equals("US"));
    }

    @Test
    public void testDateFormat(){
        RecyclerSectionFormat recyclerSectionFormat = new RecyclerSectionFormat("","","2018-06-22-...","","","");

        assertEquals("22/06/18",recyclerSectionFormat.getRecyclerDate());
    }

    @Test
    public void testBodyFormatWithoutEndThreePoints(){
        String body1 = "";
        for (int i = 1 ; i < 6 ; i++) body1 += "123456789";

        assertEquals(45, body1.length());

        RecyclerSectionFormat recyclerSectionFormat = new RecyclerSectionFormat("","","",body1,"","");

        assertThat("Body without ...", !recyclerSectionFormat.getBody().contains("..."));
    }

    @Test
    public void testBodyFormatWithEndThreePoints(){
        String body1 = "";
        for (int i = 1 ; i < 10 ; i++) body1 += "123456789";

        assertEquals(81, body1.length());

        RecyclerSectionFormat recyclerSectionFormat = new RecyclerSectionFormat("","","",body1,"https://...jpg","https://...com");

        assertEquals(63, recyclerSectionFormat.getBody().length());
        assertThat("Body without ...", recyclerSectionFormat.getBody().contains("..."));
        assertEquals("https://...jpg", recyclerSectionFormat.getWebImage());
        assertEquals("https://...com", recyclerSectionFormat.getWebUrl());
    }
}
