package com.example.francoislf.mynews.Controllers.Utils;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.example.francoislf.mynews.R;

import static com.example.francoislf.mynews.Controllers.OtherActivities.NotificationsActivity.NOTIFICATION_CODE;
import static com.example.francoislf.mynews.Controllers.Utils.App.CHANNEL;

public class MyAlarmReceiver extends BroadcastReceiver {

    private NotificationManagerCompat mNotificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {
        String response = intent.getExtras().getString("extra");

        mNotificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("TITLE")
                .setContentText("There are " + response + " for new articles")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        mNotificationManagerCompat.notify(NOTIFICATION_CODE, notification);
    }
}
