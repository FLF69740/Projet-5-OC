package com.example.francoislf.mynews;


import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.francoislf.mynews.Controllers.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class InterfaceTestMainActivity {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    // test if Tollbar appear without problem
    @Test
    public void testToolBarIsDisplayed(){
        onView(withId(R.id.activity_main_toolbar)).check(matches(isDisplayed()));
    }

    // test if Navigation Drawer open and close
    // test if main items : most popular and Top Stories ; are clickable
    @Test
    public void testOpenCloseDrawerAndMainItemSelection() throws Exception{
        onView(withId(R.id.activity_main_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.activity_main_drawer_layout)).perform(DrawerActions.close());
        Thread.sleep(2000);

        onView(withId(R.id.activity_main_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.activity_main_nav_view)).perform(NavigationViewActions.navigateTo(0));
        Thread.sleep(4000);
        onView(withId(R.id.activity_main_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.activity_main_nav_view)).perform(NavigationViewActions.navigateTo(1));
    }

    // test if ViewPager appear and swiping work
    @Test
    public void testViewPagerNewInstance() throws Exception {
        onView(withId(R.id.activity_main_viewpager)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_main_viewpager)).perform(swipeLeft());
        Thread.sleep(2000);
    }
/*
    // test if TabLayout appear and
    @Test
    public void testTabLayout() throws Exception {
        onView(withId(R.id.activity_main_tabLayout)).perform(click());
        Thread.sleep(2000);
        onView(withText("PAGE : 1")).perform(click());
        Thread.sleep(2000);
    }
*/
}