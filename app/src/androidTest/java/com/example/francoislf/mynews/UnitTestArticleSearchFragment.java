package com.example.francoislf.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.example.francoislf.mynews.Controllers.OtherActivities.ArticleSearchFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UnitTestArticleSearchFragment {

    ArticleSearchFragment mArticleSearchFragment;

    @Before
    public void setUp(){
        mArticleSearchFragment = new ArticleSearchFragment();
    }


    @Test
    public void returnTrueIfEditTextNotEmpty(){
        mArticleSearchFragment.setEditTextLength(true);
        assertEquals(true, mArticleSearchFragment.editTextNotEmpty());
    }

    @Test
    public void returnTrueIfOneCheckBoxChecked(){
        mArticleSearchFragment.setCheckBoxChecked(true);
        assertEquals(true, mArticleSearchFragment.checkBoxChecked());
    }

    @Test
    public void testStateWithTheBoth(){
        mArticleSearchFragment.setCheckBoxChecked(false);
        mArticleSearchFragment.setEditTextLength(false);
        assertEquals(false, mArticleSearchFragment.getWidgetActionState());

        mArticleSearchFragment.setCheckBoxChecked(false);
        mArticleSearchFragment.setEditTextLength(true);
        assertEquals(false, mArticleSearchFragment.getWidgetActionState());

        mArticleSearchFragment.setCheckBoxChecked(true);
        mArticleSearchFragment.setEditTextLength(false);
        assertEquals(false, mArticleSearchFragment.getWidgetActionState());

        mArticleSearchFragment.setCheckBoxChecked(true);
        mArticleSearchFragment.setEditTextLength(true);
        assertEquals(true, mArticleSearchFragment.getWidgetActionState());
    }
}
