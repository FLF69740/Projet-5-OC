package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import com.example.francoislf.mynews.R;
import com.google.gson.Gson;

public class NotificationsFragment extends AbstractFragment {

    private onSaveSituationListener mCallBack;

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
        mNotificationSwitch.setChecked(false);
    }

    @Override
    protected void editCalendars() {// do nothing
    }

    // upload the listener to the switch button
    public void switckButtonWork(){
        mNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                submit(isChecked);
            }
        });
    }

    public interface onSaveSituationListener{
        void onSaveSituation(String json, boolean bool);
    }

    // Call the method that creating callback after being attached to parent activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    // Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            mCallBack = (NotificationsFragment.onSaveSituationListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

    // define what happened when Switch Notification state changed
    public void submit(boolean bool){
        mSearchPreferences.resetCheckBoxList();
        for (int j = 0 ; j < mCheckBoxes.length ; j++) {
            if (mCheckBoxes[j].isChecked()) mSearchPreferences.addCheckBox(mCheckBoxes[j].getText().toString());
        }
        mSearchPreferences.setBeginDateString(mEditTextBeginDate.getText().toString());
        mSearchPreferences.setEndDateString(mEditTextEndDate.getText().toString());
        mSearchPreferences.setSearchString(mEditText.getText().toString());
        mSearchPreferences.setSwitchState(Boolean.toString(bool));
        Gson gson = new Gson();
        String json = gson.toJson(mSearchPreferences);
        mCallBack.onSaveSituation(json, bool);
    }


}
