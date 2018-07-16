package com.example.francoislf.mynews.Controllers.Utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App  extends Application{

    public static final String CHANNEL = "CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL, "NOTIFCATION", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This notification allow to create an message when new article is published according to preferences setting");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
