package com.example.francoislf.mynews.Controllers.OtherActivities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class AbstractFragment extends Fragment{

    protected SearchPreferences mSearchPreferences;

    @BindView(R.id.article_search_editText) EditText mEditText;
    @BindView(R.id.article_search_begin_date) EditText mEditTextBeginDate;
    @BindView(R.id.article_search_end_date) EditText mEditTextEndDate;
    @BindView(R.id.article_search_chekBox_arts) CheckBox mCheckBoxArts;
    @BindView(R.id.article_search_chekBox_business) CheckBox mCheckBoxBusiness;
    @BindView(R.id.article_search_chekBox_technology) CheckBox mCheckBoxTechnology;
    @BindView(R.id.article_search_chekBox_politics) CheckBox mCheckBoxPolitics;
    @BindView(R.id.article_search_chekBox_sports) CheckBox mCheckBoxSports;
    @BindView(R.id.article_search_chekBox_travel) CheckBox mCheckBoxTravel;
    @BindView(R.id.article_search_button_search) Button mButtonLaunchSearch;
    @BindView(R.id.article_search_separator) LinearLayout mLinearLayoutSeparator;
    @BindView(R.id.article_search_layout_notification) LinearLayout mLinearLayoutNotification;
    @BindView(R.id.article_search_begin_date_title) TextView mTextViewBeginDate;
    @BindView(R.id.article_search_end_date_title) TextView mTextViewEndTitle;
    @BindView(R.id.article_search_notifications) Switch mNotificationSwitch;

    protected CheckBox mCheckBoxes[] = new CheckBox[6];
    protected boolean mCheckBoxChecked = false;

    protected boolean mEditTextLength;

    protected abstract AbstractFragment newInstance();
    protected abstract int getFragmentLayout();
    protected abstract void hiddenWidget();
    protected abstract void widgetActionState();
    protected abstract void editCalendars();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);

        ButterKnife.bind(this, view);

        this.hiddenWidget();
        this.initCheckBoxTable();
        this.numberCheckBoxChecked();
        this.enableOrDisableEditText();
        this.editCalendars();



        return view;
    }

    // Creation of CheckBox Table
    public void initCheckBoxTable(){
        mCheckBoxes[0] = mCheckBoxArts;
        mCheckBoxes[1] = mCheckBoxBusiness;
        mCheckBoxes[2] = mCheckBoxTechnology;
        mCheckBoxes[3] = mCheckBoxPolitics;
        mCheckBoxes[4] = mCheckBoxSports;
        mCheckBoxes[5] = mCheckBoxTravel;
    }

    // Listener definition of EditText
    protected void enableOrDisableEditText() {

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditTextLength(s.toString().length() != 0);
                widgetActionState();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Listener definition of CheckBoxes
    protected void numberCheckBoxChecked(){

        for (int i = 0 ; i < mCheckBoxes.length ; i++){
            mCheckBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    boolean result = false;
                    for (int j = 0 ; j < mCheckBoxes.length ; j++) {
                        if (mCheckBoxes[j].isChecked()) result = true;
                    }
                    setCheckBoxChecked(result);
                    widgetActionState();
                }
            });
        }
    }

    // Define if EditText is Empty or not
    public void setEditTextLength(boolean bool){
        mEditTextLength = bool;
    }

    // Return the EditText boolean
    public boolean editTextNotEmpty(){
        return mEditTextLength;
    }

    // Define if one checkBox (or more) is checked or not
    public void setCheckBoxChecked(boolean bool){
        mCheckBoxChecked = bool;
    }

    // Return the checkBoxChecked boolean
    public boolean checkBoxChecked() {
        return mCheckBoxChecked;
    }

    // Return the double test (EditText and CheckBoxes)
    public boolean getWidgetActionState(){
        return (editTextNotEmpty() && checkBoxChecked());
    }

    // Configure SharedPreferences into fragment
    public void updateFragmentData(SearchPreferences searchPreferences){

        this.mSearchPreferences = searchPreferences;

        mEditText.setText(mSearchPreferences.getSearchString());

        for (int i = 0 ; i < mCheckBoxes.length ; i++){
            for (int j = 0 ; j < mSearchPreferences.getListCheckBoxString().size() ; j++)
                if (mCheckBoxes[i].getText().toString().equals(mSearchPreferences.getListCheckBoxString().get(j)))
                    mCheckBoxes[i].setChecked(true);
        }

        mNotificationSwitch.setChecked(mSearchPreferences.getSwitchState());

    }
}
