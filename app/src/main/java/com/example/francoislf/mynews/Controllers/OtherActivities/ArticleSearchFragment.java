package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.francoislf.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleSearchFragment extends AbstractFragment {

    private Calendar mCalendarBegin = Calendar.getInstance();
    private Calendar mCalendarEnd = Calendar.getInstance();

    private String mBeginDateString = "", mEndDateString = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);



    @Override
    protected AbstractFragment newInstance() {
        return new ArticleSearchFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_article_search;
    }

    // definition of the widgets from xml file who must be hidden for this fragment
    @Override
    protected void hiddenWidget() {
        mLinearLayoutNotification.setVisibility(View.GONE);
        mLinearLayoutSeparator.setVisibility(View.GONE);
    }

    /**
     *  DATE ALGORITHMS
     */

    DatePickerDialog.OnDateSetListener dateBegin = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setDateString(calendar,0);
            if (getComparationDates(calendar, mCalendarEnd)){
                updateLabel(0);
                mCalendarBegin = calendar;
            }
            else alertDateMessage();
        }
    };

    DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setDateString(calendar,1);
            if (getComparationDates(mCalendarBegin, calendar)){
                updateLabel(1);
                mCalendarEnd = calendar;
            }
            else alertDateMessage();
        }
    };

    private void alertDateMessage(){
        Toast.makeText(this.getContext(),"Start and end dates are reversed", Toast.LENGTH_SHORT).show();
    }

    // Define Listeners from Calendars (Begin and End Dates)
    protected void editCalendars(){
        mEditTextBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dateBegin,
                        mCalendarBegin.get(Calendar.YEAR), mCalendarBegin.get(Calendar.MONTH), mCalendarBegin.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mEditTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dateEnd,
                        mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH), mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(int pos) {

        switch (pos){
            case 0 : mEditTextBeginDate.setText(getDateString(pos)); break;
            case 1 : mEditTextEndDate.setText(getDateString(pos)); break;
        }

        if ((pos == 1) && (mEditTextBeginDate.getText().toString().isEmpty())) {
            mEditTextBeginDate.setText(getDateString(pos));
            mCalendarBegin = mCalendarEnd;
        }
    }

    // Define the String of the target date
    public void setDateString(Calendar calendar, int position){
        if (position == 0) mBeginDateString = sdf.format(calendar.getTime());
        else mEndDateString = sdf.format(calendar.getTime());
    }

    // Return the String of the target date
    public String getDateString(int position) {
        if (position == 0) return mBeginDateString;
        else return mEndDateString;
    }

    // Comparation between the dates
    public boolean getComparationDates(Calendar calendar1, Calendar calendar2){
        if (calendar1.getTimeInMillis() > calendar2.getTimeInMillis()) return false;
        else return true;
    }





    // Button state
    @Override
    protected void widgetActionState() {
        mButtonLaunchSearch.setEnabled(this.getWidgetActionState());
    }

    // define what happened when Button search is clicked
    @Override
    protected void listenerRules() {
        mButtonLaunchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0 ; j < mCheckBoxes.length ; j++) {
                    if (mCheckBoxes[j].isChecked()) mSearchPreferences.addCheckBox(mCheckBoxes[j].getText().toString());
                }
            }
        });
    }

}
