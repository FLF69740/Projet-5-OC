package com.example.francoislf.mynews.Controllers.Utils;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.example.francoislf.mynews.Models.HttpRequest.ArticleSearch;
import com.example.francoislf.mynews.Models.HttpRequest.ArticlesStreams;
import com.example.francoislf.mynews.Models.SearchPreferences;
import com.example.francoislf.mynews.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import static com.example.francoislf.mynews.Controllers.Activities.NotificationsActivity.LIST_CHECKBOXES_REQUEST_CODE;
import static com.example.francoislf.mynews.Controllers.Activities.NotificationsActivity.NOTIFICATION_CODE;
import static com.example.francoislf.mynews.Controllers.Activities.NotificationsActivity.TITLE_REQUEST_CODE;
import static com.example.francoislf.mynews.Controllers.Utils.App.CHANNEL;

public class MyAlarmReceiver extends BroadcastReceiver {

    private NotificationManagerCompat mNotificationManagerCompat;
    private Disposable mDisposable;
    private SearchPreferences mSearchPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        mSearchPreferences = new SearchPreferences();
        mSearchPreferences.setListCheckBox(intent.getExtras().getStringArrayList(LIST_CHECKBOXES_REQUEST_CODE));
        String response = intent.getExtras().getString(TITLE_REQUEST_CODE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);
        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarEnd.get(Calendar.DAY_OF_MONTH) - 1);
        String stringStart = sdf.format(calendarStart.getTime());
        String stringEnd= sdf.format(calendarEnd.getTime());
        executeHttpRequest(context, response, stringStart, stringEnd);
    }

    private void notificationShow(Context context, int number, String text){
        mNotificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(R.drawable.ic_event_available_black_24dp)
                .setContentTitle("MY NEWS")
                .setContentText("There are " + number + " new articles about " + text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        mNotificationManagerCompat.notify(NOTIFICATION_CODE, notification);
    }

    /**
     *  HTTP (RxJAVA)
     */

    // Execute Search Articles Stream
    private void executeHttpRequest(final Context context, final String text, String start, String end){
        this.mDisposable = ArticlesStreams.streamArticleSearch(text, start, end)
                .map(getFunctionNewDeskFilter())
                .subscribeWith(new DisposableObserver<ArticleSearch>() {
                    @Override
                    public void onNext(ArticleSearch articleSearch) {
                        getUpdateUI(articleSearch, context, text);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAGO", "On Error " + Log.getStackTraceString(e));
                    }
                    @Override
                    public void onComplete() {
                        Log.i("TAGO", "TopStories streams on complete");
                    }
                });
    }

    // Create function to add new_desk filter
    private Function<ArticleSearch, ArticleSearch> getFunctionNewDeskFilter(){
        return new Function<ArticleSearch, ArticleSearch>() {
            @Override
            public ArticleSearch apply(ArticleSearch articleSearch) throws Exception {
                ArrayList<ArticleSearch.Doc> filteredDoc = new ArrayList<>();
                for (int j = 0; j < mSearchPreferences.getListCheckBoxString().size(); j++) {
                    for (int i = 0; i < articleSearch.getResponse().getDocs().size(); i++) {
                        if (articleSearch.getResponse().getDocs().get(i).getNewDesk().equals(mSearchPreferences.getListCheckBoxString().get(j)))
                            filteredDoc.add(articleSearch.getResponse().getDocs().get(i));
                    }
                }
                articleSearch.getResponse().setDocs(filteredDoc);
                return articleSearch;
            }
        };
    }

    // Final verification if new articles exist, after conditions filters
    private void getUpdateUI(ArticleSearch articleSearch, Context context, String text){
        if (!articleSearch.getResponse().getDocs().isEmpty()) {
            int numberOfArticles = articleSearch.getResponse().getDocs().size();
            this.notificationShow(context, numberOfArticles, text);
        }
    }
}
