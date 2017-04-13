package com.android.korama;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.android.korama.model.Post;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


/**
 * Created by Chaimaa on 05/04/2017.
 */

public class MessageService extends FirebaseMessagingService {

    private static final String TAG = "MessageService";
    private static int notificationId = 0;
    public static final String INTENT_FILTER = "INTENT_FILTER";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        //Intent intent = new Intent(INTENT_FILTER);
        //sendBroadcast(intent);


        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        String from = remoteMessage.getFrom();
        Log.d(TAG, "From: " + from);

        // Check if message contains a data payload.
        Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0) {
            Log.d(TAG, "Message data payload: " + data);
            switch (from) {
                case "/topics/new_article":
                    onNewArticleMessage(data);
                    break;
                /*case "/topics/log":
                    onLogMessage(data);
                    break;
                case "/topics/login":
                    onLoginMessage(data);
                    break;
                case "/topics/auth":
                    onAuthMessage(data);
                    break;
                case "/topics/save_post":
                    onSavePostMessage(data);
                    break;

                case "/topics/new_page":
                    onNewPageMessage(data);
                    break;*/
            }
        }

        // Check if message contains a notification payload. ????? always null
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }


    private void onNewArticleMessage(Map<String, String> data) {
        String title = data.get("title");
        if (!TextUtils.isEmpty(title)) {
           String body = data.get("body");
           /* Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_cat1)
                    .setColor(0xFFFFD146)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setGroup("Warmbeer blog")
                    .setContentText("body...")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body)
                            .setBigContentTitle(title)
                            .setSummaryText(body))
                    .setDefaults(DEFAULT_ALL)
                    .build();
            showNotification(6, notification);*/


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setColor(ContextCompat.getColor(this,R.color.primary))
                            .setContentTitle(getResources().getString(R.string.app_title))
                            .setContentText(body);

            Intent resultIntent = new Intent(this, CheeseDetailActivity.class);
            //Intent upIntent = new Intent(this, PrintHttpResultTest.class);
            //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            // Adds the back stack
            //stackBuilder.addParentStack(PrintHttpResultTest.class);
            //stackBuilder.addParentStack(CheeseDetailActivity.class);
            // Adds the Intent to the top of the stack
            //stackBuilder.addNextIntent(resultIntent);

            Post post = new Post();
            post.setTitle(title);
            resultIntent.putExtra("Post",post);

            /*PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );*/
            PendingIntent resultPendingIntent =
                    TaskStackBuilder.create(this)
                            // add all of DetailsActivity's parents to the stack,
                            // followed by DetailsActivity itself
                             .addNextIntentWithParentStack(resultIntent)
                              //.addParentStack(CheeseDetailActivity.class)
                              //.addNextIntent(resultIntent)
                              .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(resultPendingIntent);
            notificationId ++;
            Log.d(TAG, "notificationId: " + notificationId);
            setNotificationClick(mBuilder);
            showNotification(notificationId, mBuilder.build());

        }
    }
    private  void setNotificationClick(NotificationCompat.Builder mBuilder){
        //Intent intent = new Intent(context, CheeseDetailActivity.class);

    }

    private void showNotification(int id, Notification notification) {

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(id, notification);

        /*NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        boolean exists = false;
        if (Build.VERSION.SDK_INT >= 23) {
            for (StatusBarNotification n : nm.getActiveNotifications()) {
                if (n.getId() == id) {
                    exists = true;
                }
            }
        }
        if (exists) {
            notification.defaults = 0;
        }

        nm.notify(id, notification);
        Notification group = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_cat1)
                .setColor(0xFFFFD146)
                .setGroup("Warmbeer blog")
                .setContentTitle("")
                .setGroupSummary(true)
                .build();
        nm.notify(12, group);*/
    }
/*
    private void onNewPageMessage(Map<String, String> data) {
        String title = data.get("title");
        if (!TextUtils.isEmpty(title)) {
            String body = data.get("body");
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_cat1)
                    .setColor(0xFFFFD146)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setDefaults(DEFAULT_ALL)
                    .setGroup("Warmbeer blog")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body)
                            .setBigContentTitle(title)
                            .setSummaryText(body))
                    .build();
            showNotification(5, notification);
        }

    }



    private void onSavePostMessage(Map<String, String> data) {
        String title = data.get("title");
        if (!TextUtils.isEmpty(title)) {
            String body = data.get("body");
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_cat1)
                    .setGroup("Warmbeer blog")
                    .setColor(0xFFFFD146)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setContentText(body)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body)
                            .setBigContentTitle(title)
                            .setSummaryText(body))
                    .setDefaults(DEFAULT_ALL)
                    .build();
            showNotification(4, notification);
        }

    }

    private void onAuthMessage(Map<String, String> data) {
        String body = data.get("body");
        if (!TextUtils.isEmpty(body)) {
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_cat1)
                    .setDefaults(DEFAULT_ALL)
                    .setColor(0xFFFFD146)
                    .setAutoCancel(true)
                    .setGroup("Warmbeer blog")
                    .setContentTitle("Auth request")
                    .setContentText("Login attempt for username: " + body)
                    .build();
            showNotification(2, notification);
        }

    }

    private void onLoginMessage(Map<String, String> data) {
        String body = data.get("body");
        if (!TextUtils.isEmpty(body)) {
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_cat1)
                    .setColor(0xFFFFD146)
                    .setDefaults(DEFAULT_ALL)
                    .setGroup("Warmbeer blog")
                    .setContentTitle("User login")
                    .setContentText("Login logged in: " + body)
                    .setPriority(PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .build();
            showNotification(3, notification);
        }

    }

    private void onLogMessage(Map<String, String> data) {
        String topic = data.get("topic");
        if ("ssl".equals(topic)) {
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_cat1)
                    .setColor(0xFFFFD146)
                    .setDefaults(DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setGroup("Warmbeer blog")
                    .setContentTitle("Certificate update result")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(data.get("message"))
                            .setBigContentTitle("Certificate update result")
                            .setSummaryText(data.get("message")))
                    .setContentText(data.get("message").replace('\n', ' '))
                    .build();
            showNotification(1, notification);
        }
    }
*/

}
