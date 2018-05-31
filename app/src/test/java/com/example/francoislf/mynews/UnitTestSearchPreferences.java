package com.example.francoislf.mynews;


import com.example.francoislf.mynews.Models.SearchPreferences;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UnitTestSearchPreferences {

    @Test
    public void testSharedPreferencesLoading() {
        SearchPreferences searchPreferences = new SearchPreferences();
        searchPreferences.resetCheckBoxList();
        assertEquals("Top Stories", searchPreferences.getListCheckBoxString().get(0));
        assertEquals(2, searchPreferences.getListCheckBoxString().size());

        searchPreferences.setSearchString("VirtualText for Edit Text");
        assertEquals(true, searchPreferences.getSearchString().equals("VirtualText for Edit Text"));

        searchPreferences.setBeginDateString("01/01/2018");
        assertEquals(true, searchPreferences.getBeginDateString().equals("01/01/2018"));

        searchPreferences.setEndDateString("01/01/2018");
        assertEquals(true, searchPreferences.getEndDateString().equals("01/01/2018"));

        ArrayList<String> ListStringExemple = new ArrayList<>();
        ListStringExemple.add("Top Stories");
        ListStringExemple.add("Most Popular");
        searchPreferences.setListCheckBox(ListStringExemple);
        assertEquals(true, searchPreferences.getListCheckBoxString().equals(ListStringExemple));

        searchPreferences.addCheckBox("Arts");
        ListStringExemple.add("Arts");
        assertEquals(true, searchPreferences.getListCheckBoxString().equals(ListStringExemple));

    }
}
