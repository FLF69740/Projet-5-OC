package com.example.francoislf.mynews;

import com.example.francoislf.mynews.Controllers.OtherActivities.ArticleSearchFragment;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class UnitTestArticleSearchFragment {

    ArticleSearchFragment mArticleSearchFragment;
    private Calendar calendar;
    private Calendar calendar2;

    @Before
    public void setUp(){
        mArticleSearchFragment = new ArticleSearchFragment();
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
    }


    @Test
    public void returnTrueIfEditTextNotEmpty(){
        mArticleSearchFragment.setEditTextLength(true);
        assertEquals(true, mArticleSearchFragment.editTextNotEmpty());
    }

    @Test
    public void returnTrueIfOneCheckBoxChecked(){
        mArticleSearchFragment.initCheckBoxTable();
        mArticleSearchFragment.setCheckBoxChecked(true);
        assertEquals(true, mArticleSearchFragment.checkBoxChecked());
    }

    @Test
    public void testStateWithEditTextAndCheckBox(){
        mArticleSearchFragment.setCheckBoxChecked(false);
        mArticleSearchFragment.setEditTextLength(false);
        assertFalse(mArticleSearchFragment.getWidgetActionState());

        mArticleSearchFragment.setCheckBoxChecked(false);
        mArticleSearchFragment.setEditTextLength(true);
        assertFalse(mArticleSearchFragment.getWidgetActionState());

        mArticleSearchFragment.setCheckBoxChecked(true);
        mArticleSearchFragment.setEditTextLength(false);
        assertFalse(mArticleSearchFragment.getWidgetActionState());

        mArticleSearchFragment.setCheckBoxChecked(true);
        mArticleSearchFragment.setEditTextLength(true);
        assertTrue(mArticleSearchFragment.getWidgetActionState());
    }

    @Test
    public void testBeginDateImplementation() {
        calendar.set(Calendar.YEAR, 1900);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        mArticleSearchFragment.setDateString(calendar,0);
        assertEquals("01/01/1900", mArticleSearchFragment.getDateString(0));
    }

    @Test
    public void testEndDateImplementation() {
        calendar2.set(Calendar.YEAR, 1900);
        calendar2.set(Calendar.MONTH, 0);
        calendar2.set(Calendar.DAY_OF_MONTH, 1);

        mArticleSearchFragment.setDateString(calendar2,1);
        assertEquals("01/01/1900", mArticleSearchFragment.getDateString(1));
    }

    @Test
    public void testIfDatesLogicWorks() {
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 20);

        calendar2.set(Calendar.YEAR, 2018);
        calendar2.set(Calendar.MONTH, 7);
        calendar2.set(Calendar.DAY_OF_MONTH, 20);

        assertTrue(mArticleSearchFragment.getComparationDates(calendar, calendar2));

        calendar2.set(Calendar.MONTH, 1);
        assertFalse(mArticleSearchFragment.getComparationDates(calendar, calendar2));
    }
}
