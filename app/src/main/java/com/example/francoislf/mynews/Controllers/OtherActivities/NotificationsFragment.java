package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.view.View;

import com.example.francoislf.mynews.R;

public class NotificationsFragment extends AbstractFragment {
    @Override
    protected AbstractFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_article_search;
    }

    // definition of the widgets from xml file who must be hidden for this fragment
    @Override
    protected void hiddenWidget() {
        mTextViewBeginDate.setVisibility(View.GONE);
        mTextViewEndTitle.setVisibility(View.GONE);
        mEditTextBeginDate.setVisibility(View.GONE);
        mEditTextEndDate.setVisibility(View.GONE);
        mButtonLaunchSearch.setVisibility(View.GONE);
    }

    @Override
    protected void widgetActionState() {
        mNotificationSwitch.setEnabled(editTextNotEmpty() && checkBoxChecked());
    }

    @Override
    protected void editCalendars() {

    }


}
