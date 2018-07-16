package com.example.francoislf.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.francoislf.mynews.Controllers.Fragments.NotificationsFragment;
import com.example.francoislf.mynews.Controllers.Utils.MyAlarmReceiver;
import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;

public class NotificationsActivity extends AbstractActivity implements NotificationsFragment.onSaveSituationListener {

    public static final int NOTIFICATION_CODE = 100;
    public static final String TITLE_REQUEST_CODE = "TITLE_REQUEST_CODE";
    public static final String LIST_CHECKBOXES_REQUEST_CODE = "LIST_CHECKBOXES_REQUEST_CODE";
    private PendingIntent mPendingIntent;
    private NotificationsFragment mNotificationsFragment;
    private SharedPreferences mJSonNotifications;
    public static final String SHARED_DEFAULT_NOTIFICATIONS = "SHARED_DEFAULT_NOTIFICATIONS";

    @Override
    protected void configureFragment() {
        mNotificationsFragment = (NotificationsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_articles_search);
        if (mNotificationsFragment == null){
            mNotificationsFragment = new NotificationsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_articles_search, mNotificationsFragment)
                    .commit();
        }
    }

    @Override
    protected String configureToolBarTitle() {
        return "Notifications";
    }

    @Override
    public void onResume() {
        super.onResume();
        mNotificationsFragment.switckButtonWork();
        load();
    }

    // Load JSon in order to create class object with Gson library
    private void load(){
        mJSonNotifications = getSharedPreferences(SHARED_DEFAULT_NOTIFICATIONS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mJSonNotifications.getString(SHARED_DEFAULT_NOTIFICATIONS, null);
        Type type = new TypeToken<SearchPreferences>() {}.getType();
        mSearchPreferences = gson.fromJson(json, type);
        if (mSearchPreferences == null) mSearchPreferences = new SearchPreferences();
        mNotificationsFragment.updateFragmentData(mSearchPreferences);
    }

    // Save current class object to SharedPreferences with Gson library (JSon format)
    private void save(String fileName, String json){
        mJSonNotifications = getSharedPreferences(fileName, MODE_PRIVATE);
        mJSonNotifications.edit().putString(fileName, json).apply();
    }

    /**
     *  ALARM MANAGER
     */

    private void configureAlarmManager(){
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        intent.putExtra(TITLE_REQUEST_CODE, mSearchPreferences.getSearchString());
        intent.putExtra(LIST_CHECKBOXES_REQUEST_CODE, mSearchPreferences.getListCheckBoxString());
        mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("TAGO", "AlarmManager configured");
    }

    private void startAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 38);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 120000, mPendingIntent);
        Toast.makeText(this,"NOTIFICATION SYSTEM ACTIVATED", Toast.LENGTH_SHORT).show();
    }

    private void stopAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mPendingIntent);
        Toast.makeText(this,"NOTIFICATION SYSTEM STOPPED", Toast.LENGTH_SHORT).show();
    }

    /**
     *  Callback
     */

    @Override
    public void onSaveSituation(String json, boolean bool) {
        save(SHARED_DEFAULT_NOTIFICATIONS, json);
        this.configureAlarmManager();
        if (bool) startAlarm();
        else stopAlarm();
    }
}
