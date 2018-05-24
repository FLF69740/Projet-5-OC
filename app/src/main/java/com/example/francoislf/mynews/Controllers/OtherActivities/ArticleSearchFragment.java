package com.example.francoislf.mynews.Controllers.OtherActivities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.francoislf.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleSearchFragment extends AbstractFragment {

    Calendar mCalendarBegin = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateBegin = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mCalendarBegin.set(Calendar.YEAR, year);
            mCalendarBegin.set(Calendar.MONTH, month);
            mCalendarBegin.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(mEditTextBeginDate, mCalendarBegin, 1);
        }
    };

    DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mCalendarEnd.set(Calendar.YEAR, year);
            mCalendarEnd.set(Calendar.MONTH, month);
            mCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(mEditTextEndDate, mCalendarEnd,2);
        }
    };

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

    // Button state
    @Override
    protected void widgetActionState() {
        mButtonLaunchSearch.setEnabled(editTextNotEmpty() && checkBoxChecked());
    }

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

    private void updateLabel(EditText editText, Calendar calendar, int pos) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        editText.setText(sdf.format(calendar.getTime()));

        if ((pos == 2) && (mEditTextBeginDate.getText().toString().isEmpty())) {
            mEditTextBeginDate.setText(sdf.format(calendar.getTime()));
            mCalendarBegin = mCalendarEnd;
        }

        if (mCalendarBegin.getTimeInMillis() > mCalendarEnd.getTimeInMillis()) {
            mEditTextBeginDate.setText(sdf.format(mCalendarEnd.getTime()));
            mEditTextEndDate.setText(sdf.format(mCalendarBegin.getTime()));

            Calendar calendarTemp = mCalendarEnd;
            mCalendarEnd = mCalendarBegin;
            mCalendarBegin = calendarTemp;

        }


        Log.i("TAGO", mEditTextBeginDate.getText().toString() + " - " + mEditTextEndDate.getText().toString() +
                " : " + mCalendarBegin.getTimeInMillis() + " - " + mCalendarEnd.getTimeInMillis());

    }

}
